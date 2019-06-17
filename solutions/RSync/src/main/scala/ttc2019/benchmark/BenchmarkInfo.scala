package ttc2019.benchmark

/** The `BenchmarkInfo` simply wraps some meta information about the current benchmark.
  *
  * @param tool   the tool which is currently executing the benchmark
  * @param model  the model being tested
  * @param runIdx the current index of the benchmark execution
  */
case class BenchmarkInfo(tool: String, model: String, runIdx: Int)
