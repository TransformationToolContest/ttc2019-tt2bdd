package ttc2019.benchmark

/** The `Benchmark` provides stopwatch functionality for keeping track of the time consumption of
  * some block of code.
  */
class Benchmark {

  private var benchmark: BenchmarkState = new IdleState

  private var startTimestamp: Long = _
  private var endTimestamp: Long = _

  /** Starts a new benchmark execution.
    *
    * @throws IllegalStateException if the benchmark is already running
    */
  def start(): Unit = benchmark = benchmark.start()

  /** Stops the current execution of the benchmark and calculates the time spent.
    *
    * @throws IllegalStateException if the benchmark was not running
    */
  def stop(): Long = {
    benchmark = benchmark.stop()
    benchmark.duration.getOrElse(throw new IllegalStateException)
  }

  /** Provides the time consumed during the last benchmark execution.
    */
  def duration: Option[Long] = benchmark.duration

  /** Resets the starting time of the benchmark to the current nano time and flushes the last stop
    * time.
    */
  private def restartBenchmark(): Unit = {
    startTimestamp = System.nanoTime()
    endTimestamp = -1L
  }

  /** To handle the different benchmark states, they have been reified and the state pattern is
    * applied.
    *
    * The state diagram looks as follows:
    * {{{
    *
    *    |
    *    V
    * +------+  start()    +---------+
    * | Idle | ----------> | Running |
    * +------+             +---------+
    *                        |     ^
    *                 stop() |     | start()
    *                        V     |
    *                      +---------+
    *                      | Stopped |
    *                      +---------+
    * }}}
    *
    * All other transitions are forbidden and result in a `IllegalStateException`.
    *
    * For details about the individual methos consult the documentation of [[Benchmark]].
    */
  abstract class BenchmarkState {

    def start(): BenchmarkState

    def stop(): BenchmarkState

    def duration: Option[Long]

  }

  class IdleState extends BenchmarkState {

    override def start(): BenchmarkState = {
      restartBenchmark()
      new RunningState
    }

    override def stop(): BenchmarkState = throw new IllegalStateException

    override def duration: Option[Long] = None

  }

  class RunningState extends BenchmarkState {

    override def start(): BenchmarkState = throw new IllegalStateException

    override def stop(): BenchmarkState = {
      Benchmark.this.endTimestamp = System.nanoTime()
      new StoppedState
    }

    override def duration: Option[Long] = Some(System.nanoTime() - Benchmark.this.startTimestamp)

  }

  class StoppedState extends BenchmarkState {

    override def start(): BenchmarkState = {
      restartBenchmark()
      new RunningState
    }

    override def stop(): BenchmarkState = throw new IllegalStateException

    override def duration: Option[Long] = Some(Benchmark.this.endTimestamp - Benchmark.this.startTimestamp)

  }

}
