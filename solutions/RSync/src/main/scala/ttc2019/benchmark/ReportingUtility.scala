package ttc2019.benchmark

import ttc2019.MetricMeasurement

/** The `ReportingUtility` takes care of writing benchmarking info to `stdout`.
  *
  * Such information includes time consumption for specific phases of the benchmark as well
  * as the total amount of memory consumed during each of these phases.
  *
  * @param reportMemoryUsage whether the utility should log the allocated memory
  */
class ReportingUtility(reportMemoryUsage: Boolean = false) {

  private val rt = Runtime.getRuntime

  private val initialMemoryConsumption: Long = calculateUsedMemory()

  /** Writes the benchmarking info to `stdout`. Time consumption has to be specified by the user
    * whereas memory consumption will be calculated on-the-fly.
    *
    * @param benchmark information about the benchmark being executed
    * @param phase     the phase that was monitored
    * @param duration  the amount of time the tool consumed for executing the benchmark
    */
  def report(benchmark: BenchmarkInfo, phase: String, duration: Long, metrics: Option[Metrics] = None): Unit = {
    println(s"${benchmark.tool};${benchmark.model};${benchmark.runIdx};$phase;Time;$duration")
    if (reportMemoryUsage) {
      val consumedMemory = calculateUsedMemory(overhead = initialMemoryConsumption)
      println(s"${benchmark.tool};${benchmark.model};${benchmark.runIdx};$phase;Memory;$consumedMemory")
    }

    metrics.foreach(metrics => {
      println(s"${benchmark.tool};${benchmark.model};${benchmark.runIdx};$phase;AssignmentNodes;${metrics.nLeafs}")
      println(s"${benchmark.tool};${benchmark.model};${benchmark.runIdx};$phase;DecisionNodes;${metrics.nSubTrees}")
      println(s"${benchmark.tool};${benchmark.model};${benchmark.runIdx};$phase;MaxPath;${metrics.maxPathLength}")
      println(s"${benchmark.tool};${benchmark.model};${benchmark.runIdx};$phase;MinPath;${metrics.minPathLength}")
      println(s"${benchmark.tool};${benchmark.model};${benchmark.runIdx};$phase;AvgPath;${metrics.avgPathLength}")
    })
  }

  /** Checks, whether the Garbage collector should be invoked. This has to specified via the `NoGC`
    * environment variable.
    */
  private def shouldRunGarbageCollector: Boolean = sys.env.getOrElse("NoGC", "") != "true"

  /** Determines the amount of memory that is currently being used by the application.
    *
    * @param overhead if there is memory that was already in use before the actual application has
    *                 been invoked, this amount may be specified here and will not be included in
    *                 the calculation
    * @return the amount of used memory in bytes
    */
  private def calculateUsedMemory(overhead: Long = 0): Long = {
    if (shouldRunGarbageCollector) {
      rt.gc()
    }
    rt.totalMemory() - rt.freeMemory() - overhead
  }

}
