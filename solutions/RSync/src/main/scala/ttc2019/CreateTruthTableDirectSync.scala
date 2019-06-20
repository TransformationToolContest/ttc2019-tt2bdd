package ttc2019

import sync.tt._
import org.eclipse.emf.ecore.EObject

class CreateTruthTableDirectSync extends ICreateTruthTable {
  
  //var mapping: Map[EObject, Object] = Map.empty
  var truthttable: TruthTable = null
  var rows: java.util.Map[EObject, Row] = new java.util.HashMap()
  var cells: java.util.Map[EObject, Cell] = new java.util.HashMap()
  var ports: java.util.Map[EObject, Port] = new java.util.HashMap()
  
  def createTruthTable(name: String, id: EObject): Unit = {
    truthttable = new TruthTable(name, Set.empty, Set.empty, null)
  }
  
  def createInputPort(name: String, id: EObject): Unit = {
    //mapping += (id -> new InputPort(name, Set.empty, null, null))
    ports.put(id, new InputPort(name, Set.empty, null, null))
  }
  
  def createOutputPort(name: String, id: EObject): Unit = {
    //mapping += (id -> new OutputPort(name, Set.empty, null, null))
    ports.put(id, new OutputPort(name, Set.empty, null, null))
  }
  
  def createRow(id: EObject): Unit = {
    //mapping += (id -> new Row(Set.empty, null, null))
    rows.put(id, new Row(Set.empty, null, null))
  }
  
  def createCell(value: Boolean, id: EObject): Unit = {
    //mapping += (id -> new Cell(value, null, null, null))
    cells.put(id, new Cell(value, null, null, null))
  }
  
  def createTruthTableRowsRow(tt: EObject, row: EObject): Unit = {
    //val t = mapping.get(tt).get.asInstanceOf[TruthTable]
    //val r = mapping.get(row).get.asInstanceOf[Row]
    val r = rows.get(row)
    truthttable.addRows(r)
    r.setOwner(truthttable)
  }
  
  def createTruthTablePortsPort(tt: EObject, port: EObject): Unit = {
    //val t = mapping.get(tt).get.asInstanceOf[TruthTable]
    //val p = mapping.get(port).get.asInstanceOf[Port]
    val p = ports.get(port)
    truthttable.addPorts(p)
    p.setOwner(truthttable)
  }
  
  def createRowCellsCell(row: EObject, cell: EObject): Unit = {
    //val c = mapping.get(cell).get.asInstanceOf[Cell]
    //val r = mapping.get(row).get.asInstanceOf[Row]
    val c = cells.get(cell)
    val r = rows.get(row)
    c.setOwner(r)
    r.addCells(c)
  }
  
  def createCellPortPort(cell: EObject, port: EObject): Unit = {
    //val c = mapping.get(cell).get.asInstanceOf[Cell]
    //val p = mapping.get(port).get.asInstanceOf[Port]
    val c = cells.get(cell)
    val p = ports.get(port)
    c.setPort(p)
    p.addCells(c)
  }
}