import ttc2019.{CompleteTTCProcess, MetricMeasurement, ProcessMode, TTCProcessConfiguration}
import ttc2019.benchmark.{Benchmark, BenchmarkInfo, ReportingUtility}

import scala.reflect.io.File

/** The `MainApp` wraps the whole transformation process and adapts our solution to the TTC
  * environment that it will be deployed in.
  */
object MainApp extends App {

  val Initialize = "Initialization"
  val Load = "Load"
  val Run = "Run"

  var processMode = ProcessMode.BDT
  if (args.length >= 1) {
    args.apply(0) match {
        // working as intended!
      case "bdt" => processMode = ProcessMode.BDTU
      case "bdd" => processMode = ProcessMode.BDDU
      case "bdt-u" => processMode = ProcessMode.BDT
      case "bdd-u" => processMode = ProcessMode.BDD
    }
  }

  private val modelPath = sys.env.get("ModelPath")
  private val benchmarkInfo = fetchBenchmarkInfo()

  if (modelPath.isEmpty) {
    sys.error("Expected model to convert in the ModelPath environment variable")
  }

  modelPath.foreach(modelPath => {
    val ttModelFile = File(modelPath)
    val bddModelFile = buildBddModelFile(ttModelFile)
    val processConfig = TTCProcessConfiguration(ttFileName = modelPath, bddFileName = bddModelFile.jfile.getCanonicalPath, processMode = processMode)

    val benchmarkingService: Benchmark = new Benchmark
    var benchmarkDuration: Long = -1
    val reportingService: ReportingUtility = new ReportingUtility

    // PHASE INIT
    benchmarkingService.start()
    CompleteTTCProcess.initialize(processConfig)
    benchmarkDuration = benchmarkingService.stop()
    reportingService.report(benchmarkInfo, Initialize, benchmarkDuration)

    // PHASE LOAD
    benchmarkingService.start()
    CompleteTTCProcess.load()
    benchmarkDuration = benchmarkingService.stop()
    reportingService.report(benchmarkInfo, Load, benchmarkDuration)

    // PHASE RUN
    benchmarkingService.start()
    CompleteTTCProcess.run()
    benchmarkDuration = benchmarkingService.stop()
    if (CompleteTTCProcess.bdt) {
      reportingService.report(benchmarkInfo, Run, benchmarkDuration, Some(MetricMeasurement.printMetricsBDT()))
    } else {
      reportingService.report(benchmarkInfo, Run, benchmarkDuration, Some(MetricMeasurement.printMetricsBDD()))
    }

    CompleteTTCProcess.doWriteOut()
    CompleteTTCProcess.validateModelEquality()
  })

  /** Retrieves information about the benchmark that should be executed.
    */
  private def fetchBenchmarkInfo(): BenchmarkInfo = {
    val tool = sys.env.getOrElse("Tool", "")
    val model = sys.env.getOrElse("Model", "")
    val runIdx: Integer = Integer.valueOf(sys.env.getOrElse("RunIndex", "0"))
    BenchmarkInfo(tool, model, runIdx)
  }

  /** Constructs the model file for the generated BDD based on the TT model.
    *
    * Assuming the TT model file adheres to the pattern `/some/path/[FILENAME].ttmodel`, the output
    * file will be `/current/path/[FILENAME]-generated.bddmodel`.
    */
  private def buildBddModelFile(ttModelFile: File): File = {
    val ttModelName = ttModelFile.name

    val bddModelName = ttModelName.replace(".ttmodel", "").concat("-generated.bddmodel")

    File(System.getProperty("user.dir")) / File(bddModelName)
  }

}
