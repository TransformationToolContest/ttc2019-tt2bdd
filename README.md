# The TTC 2019 TT2BDD ATL Zoo Case

## Solutions

**Please note** that unfortunately - like provided ATL solution - the solutions cannot be started directly using MS Windows, since they use `cd ../dir && ./gradlew`. If only Windows is available, these commands have to be adapted.

### JastAdd Relational RAGs Solution

The solution can be found at [solutions/RelationalRAGSolution](https://git-st.inf.tu-dresden.de/ttc/bdd/tree/master/solutions/RelationalRAGSolution).
All variants described in the [paper](http://www.transformation-tool-contest.eu/papers/TTC19_paper_4.pdf) are started from separate subfolders with a solution-specific suffix.

If there are problems, please submit an issue or write to johannes.mey@tu-dresden.de

### SCROLL RSYNC Solution

The solution can be found at [solutions/RSYNC](https://git-st.inf.tu-dresden.de/ttc/bdd/tree/master/solutions/RSync).
All variants described in the [paper](http://www.transformation-tool-contest.eu/papers/TTC19_paper_6.pdf) are started from separate subfolders with a solution-specific suffix.

If there are problems, please submit an issue or write to christopher.werner@tu-dresden.de

## Prerequisites

* 64-bit operating system
* Python 3.3 or higher
* R

## Solution Prerequisites

The relational RAG solutions use Gradle as build tool. Thus there are no extra prerequisites.

However, in order to run the RSYNC solutions, the following tools are necessary:

* Scala (optional, will be downloaded automatically via SBT if not present)
* [SBT](https://www.scala-sbt.org/) 1.x

## Using the framework

The `scripts` directory contains the `run.py` script.
At a first glance, invoke it without any arguments so that the solution will be built, benchmarked, running times visualized and the results compared to the reference solution's.
One might fine tune the script for the following purposes:
* `run.py -b` -- builds the projects
* `run.py -b -s` -- builds the projects without testing
* `run.py -g` -- generates the instance models
* `run.py -m` -- run the benchmark without building
* `run.py -v` -- visualizes the results of the latest benchmark
* `run.py -e` -- compare results to the reference output. The benchmark shall already been executed using `-m`.
* `run.py -m -e` -- run benchmark without building, then extract and compare results to the reference output
* `run.py -t` -- build the project and run tests (usually unit tests as defined for the given solution)

The `config` directory contains the configuration for the scripts:
* `config.json` -- configuration for the model generation and the benchmark
  * *Note:* the timeout as set in the benchmark configuration (default: 6000 seconds) applies to the gross cumulative runtime of the tool for a given changeset and update sequences. This also includes e.g. Initialization time which is not required by the benchmark framework to be measured.
    Timeout is only applied to the solutions' run phase (see `-m` for `run.py`), so it is not applied to e.g. the build phase (see `-b` for `run.py`).

### Running the benchmark

The script runs the benchmark for the given number of runs, for the specified tools and input models.

The benchmark results are stored in a CSV file. The header for the CSV file is stored in the `output/header.csv` file.

## Implementing the benchmark for a new tool

To implement a tool, you need to create a new directory in the solutions directory and give it a suitable name.
