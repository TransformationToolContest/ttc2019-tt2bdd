package ttc2019

import org.eclipse.emf.ecore.EObject

/**
 * Interface for creating a truth Table.
 */
trait ICreateTruthTable {
  
  def createTruthTable(name: String, id: EObject): Unit
  
  def createInputPort(name: String, id: EObject): Unit
  
  def createOutputPort(name: String, id: EObject): Unit
  
  def createRow(id: EObject): Unit
  
  def createCell(value: Boolean, id: EObject): Unit
  
  def createTruthTableRowsRow(tt: EObject, row: EObject): Unit
  
  def createTruthTablePortsPort(tt: EObject, port: EObject): Unit
  
  def createRowCellsCell(row: EObject, cell: EObject): Unit
  
  def createCellPortPort(cell: EObject, port: EObject): Unit
}