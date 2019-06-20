package ttc2019

import ttc2019.ProcessMode.ProcessMode

/** The `ProcessConfiguration` contains all necessary information to setup an entire transformation
  * workflow as specified by the [[CompleteTTCProcess]].
  *
  * More specifically, it contains the following information:
  *
  *   - `ttFileName` references the name (i.e. path!) of the truth table to transform
  *   - `bddFileName` references the name (i.e. path) of the file where the generated binary
  *     decision diagram should be stored
  *   - `ttEcoreName` references the path to the truth table EMF model
  *   - `processMode` sets the strategy to use for generating the binary decision tree / diagram
  */
case class TTCProcessConfiguration(ttFileName: String, bddFileName: String, ttEcoreName: String = "TT.ecore", processMode: ProcessMode)

