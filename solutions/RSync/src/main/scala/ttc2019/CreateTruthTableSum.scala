package ttc2019

import sum.tt._

class CreateTruthTableSum extends ICreateTruthTable {
  
  var mapping: Map[Int, Object] = Map.empty 
  
  def createTruthTable(name: String, id: Int): Unit = {
    mapping = mapping + (id -> new TruthTable(name, null))
  }
  
  def createInputPort(name: String, id: Int): Unit = {
    mapping = mapping + (id -> new InputPort(name, null))
  }
  
  def createOutputPort(name: String, id: Int): Unit = {
    mapping = mapping + (id -> new OutputPort(name, null))
  }
  
  def createRow(id: Int): Unit = {
    mapping = mapping + (id -> new Row(null))
  }
  
  def createCell(value: Boolean, id: Int): Unit = {
    mapping = mapping + (id -> new Cell(value, null))
  }
  
  def createTruthTableRowsRow(tt: Int, row: Int): Unit = {
    (new TruthTableRowsRow(mapping.get(tt).get.asInstanceOf[TruthTable], mapping.get(row).get.asInstanceOf[Row])).initialize()
  }
  
  def createTruthTablePortsPort(tt: Int, port: Int): Unit = {
    (new TruthTablePortsPort(mapping.get(tt).get.asInstanceOf[TruthTable], mapping.get(port).get.asInstanceOf[Port])).initialize()
  }
  
  def createRowCellsCell(row: Int, cell: Int): Unit = {
    (new RowCellsCell(mapping.get(row).get.asInstanceOf[Row], mapping.get(cell).get.asInstanceOf[Cell])).initialize()
  }
  
  def createCellPortPort(cell: Int, port: Int): Unit = {
    (new CellPortPort(mapping.get(cell).get.asInstanceOf[Cell], mapping.get(port).get.asInstanceOf[Port])).initialize()
  }
}