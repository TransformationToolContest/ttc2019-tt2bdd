package ttc2019

import sync.tt._

object TTFactory {
  def getInputPort(name: String) = new InputPort(name, Set.empty, null, null)
  
  def getTruthTable(name: String) = new TruthTable(name, Set.empty, Set.empty, null)
  
  def getOutputPort(name: String) = new OutputPort(name, Set.empty, null, null)
  
  def getRow() = new Row(Set.empty, null, null)
  
  def getCell(value: Boolean) = new Cell(value, null, null, null)
}