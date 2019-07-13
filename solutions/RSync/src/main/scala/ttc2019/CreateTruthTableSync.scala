package ttc2019

import _root_.sync.tt._
import org.eclipse.emf.ecore.EObject

class CreateTruthTableSync extends ICreateTruthTable {
  
  var mapping: Map[EObject, Object] = Map.empty 
  
  def createTruthTable(name: String, id: EObject): Unit = {
    mapping += (id -> new TruthTable(name, Set.empty, Set.empty, null))
  }
  
  def createInputPort(name: String, id: EObject): Unit = {
    mapping += (id -> new InputPort(name, Set.empty, null, null))
  }
  
  def createOutputPort(name: String, id: EObject): Unit = {
    mapping += (id -> new OutputPort(name, Set.empty, null, null))
  }
  
  def createRow(id: EObject): Unit = {
    mapping += (id -> new Row(Set.empty, null, null))
  }
  
  def createCell(value: Boolean, id: EObject): Unit = {
    mapping += (id -> new Cell(value, null, null, null))
  }
  
  def createTruthTableRowsRow(tt: EObject, row: EObject): Unit = {
    //println("addRow")
    val t = mapping.get(tt).get.asInstanceOf[TruthTable]
    val r = mapping.get(row).get.asInstanceOf[Row]
    t.addRows(r)
    r.setOwner(t)
  }
  
  def createTruthTablePortsPort(tt: EObject, port: EObject): Unit = {
    //println("addPort")
    val t = mapping.get(tt).get.asInstanceOf[TruthTable]
    val p = mapping.get(port).get.asInstanceOf[Port]
    t.addPorts(p)
    p.setOwner(t)
  }
  
  def createRowCellsCell(row: EObject, cell: EObject): Unit = {
    //println("addCellInRow")
    val c = mapping.get(cell).get.asInstanceOf[Cell]
    val r = mapping.get(row).get.asInstanceOf[Row]
    c.setOwner(r)
    r.addCells(c)
  }
  
  def createCellPortPort(cell: EObject, port: EObject): Unit = {
    //println("addCellInPort")
    val c = mapping.get(cell).get.asInstanceOf[Cell]
    val p = mapping.get(port).get.asInstanceOf[Port]
    c.setPort(p)
    p.addCells(c)
  }
}