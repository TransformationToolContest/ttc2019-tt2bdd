package ttc2019

/** The `ProcessMode` describes the strategy that should be used in order to create the equivalent
  * binary decision tree or binary decision diagram.
  */
object ProcessMode extends Enumeration {
  type ProcessMode = Value

  /** A default binary decision tree should be generated.
    */
  val BDT,

  /** A binary decision tree should be generated but the input ports may be visited in any
    * order.
    */
  BDTU,

  /** A binary decision diagram should be generated.
    */
  BDD,

  /** A binary decision diagram should be generated and the input ports may be visited in any
    * order.
    */
  BDDU = Value
}
