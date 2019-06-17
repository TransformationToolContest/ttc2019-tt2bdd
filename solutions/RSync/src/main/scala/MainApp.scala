import ttc2019.{CompleteTTCProcess, TTCProcessConfiguration}
import ttc2019.benchmark.{Benchmark, BenchmarkInfo, ReportingUtility}

import scala.reflect.io.File

/** The `MainApp` wraps the whole transformation process and adapts our solution to the TTC
  * environment that it will be deployed in.
  */
object MainApp extends App {

  private val Initialize = "Initialize"
  private val Load = "Load"
  private val Run = "Run"

  private val modelPath = sys.env.get("ModelPath")
  private val benchmarkInfo = fetchBenchmarkInfo()

  if (modelPath.isEmpty) {
    sys.error("Expected model to convert in the ModelPath environment variable")
  }

  modelPath.foreach(modelPath => {
    val ttModelFile = File(modelPath)
    val bddModelFile = buildBddModelFile(ttModelFile)
    val processConfig = TTCProcessConfiguration(ttFileName = modelPath, bddFileName = bddModelFile.jfile.getCanonicalPath)

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
    reportingService.report(benchmarkInfo, Run, benchmarkDuration)
  })

  /** Retrieves information about the benchmark that should be executed.
    */
  private def fetchBenchmarkInfo(): BenchmarkInfo = {
    val tool = sys.env.getOrElse("Tool", "")
    val model = sys.env.getOrElse("Model", "")
    val runIdx = Integer.valueOf(sys.env.getOrElse("RunIndex", ""))
    BenchmarkInfo(tool, model, runIdx)
  }

  /** Constructs the model file for the generated BDD based on the TT model.
    *
    * Assuming the TT model file adheres to the pattern `[FILENAME].ttmodel`, the output file will
    * be `[FILENAME]-generated.bddmodel`.
    */
  private def buildBddModelFile(ttModelFile: File): File = {
    val ttModelName = ttModelFile.name
    val parentDir = ttModelFile.parent

    val bddModelName = ttModelName.replace(".ttmodel", "").concat("-generated.bddmodel")

    parentDir / File(bddModelName)
  }

}
