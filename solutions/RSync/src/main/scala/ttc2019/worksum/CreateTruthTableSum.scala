package ttc2019.worksum

import sum.tt._
import org.eclipse.emf.ecore.EObject
import ttc2019.ICreateTruthTable

class CreateTruthTableSum extends ICreateTruthTable {
  
  var mapping: Map[EObject, Object] = Map.empty 
  
  def createTruthTable(name: String, id: EObject): Unit = {
    mapping = mapping + (id -> new TruthTable(name, null))
  }
  
  def createInputPort(name: String, id: EObject): Unit = {
    mapping = mapping + (id -> new InputPort(name, null))
  }
  
  def createOutputPort(name: String, id: EObject): Unit = {
    mapping = mapping + (id -> new OutputPort(name, null))
  }
  
  def createRow(id: EObject): Unit = {
    mapping = mapping + (id -> new Row(null))
  }
  
  def createCell(value: Boolean, id: EObject): Unit = {
    mapping = mapping + (id -> new Cell(value, null))
  }
  
  def createTruthTableRowsRow(tt: EObject, row: EObject): Unit = {
    (new TruthTableRowsRow(mapping.get(tt).get.asInstanceOf[TruthTable], mapping.get(row).get.asInstanceOf[Row])).initialize()
  }
  
  def createTruthTablePortsPort(tt: EObject, port: EObject): Unit = {
    (new TruthTablePortsPort(mapping.get(tt).get.asInstanceOf[TruthTable], mapping.get(port).get.asInstanceOf[Port])).initialize()
  }
  
  def createRowCellsCell(row: EObject, cell: EObject): Unit = {
    (new RowCellsCell(mapping.get(row).get.asInstanceOf[Row], mapping.get(cell).get.asInstanceOf[Cell])).initialize()
  }
  
  def createCellPortPort(cell: EObject, port: EObject): Unit = {
    (new CellPortPort(mapping.get(cell).get.asInstanceOf[Cell], mapping.get(port).get.asInstanceOf[Port])).initialize()
  }
}