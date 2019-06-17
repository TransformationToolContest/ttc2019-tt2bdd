package ttc2019

import sync.tt._

class CreateTruthTableSync extends ICreateTruthTable {
  
  var mapping: Map[Int, Object] = Map.empty 
  
  def createTruthTable(name: String, id: Int): Unit = {
    mapping = mapping + (id -> new TruthTable(name, Set.empty, Set.empty, null))
  }
  
  def createInputPort(name: String, id: Int): Unit = {
    mapping = mapping + (id -> new InputPort(name, Set.empty, null, null))
  }
  
  def createOutputPort(name: String, id: Int): Unit = {
    mapping = mapping + (id -> new OutputPort(name, Set.empty, null, null))
  }
  
  def createRow(id: Int): Unit = {
    mapping = mapping + (id -> new Row(Set.empty, null, null))
  }
  
  def createCell(value: Boolean, id: Int): Unit = {
    mapping = mapping + (id -> new Cell(value, null, null, null))
  }
  
  def createTruthTableRowsRow(tt: Int, row: Int): Unit = {
    val t = mapping.get(tt).get.asInstanceOf[TruthTable]
    val r = mapping.get(row).get.asInstanceOf[Row]
    t.addRows(r)
    r.setOwner(t)
  }
  
  def createTruthTablePortsPort(tt: Int, port: Int): Unit = {
    val t = mapping.get(tt).get.asInstanceOf[TruthTable]
    val p = mapping.get(port).get.asInstanceOf[Port]
    t.addPorts(p)
    p.setOwner(t)
  }
  
  def createRowCellsCell(row: Int, cell: Int): Unit = {
    val c = mapping.get(cell).get.asInstanceOf[Cell]
    val r = mapping.get(row).get.asInstanceOf[Row]
    c.setOwner(r)
    r.addCells(c)
  }
  
  def createCellPortPort(cell: Int, port: Int): Unit = {
    val c = mapping.get(cell).get.asInstanceOf[Cell]
    val p = mapping.get(port).get.asInstanceOf[Port]
    c.setPort(p)
    p.addCells(c)
  }
}