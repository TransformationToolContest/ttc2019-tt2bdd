package ttc2019

trait ICreateTruthTable {
  
  def createTruthTable(name: String, id: Int): Unit
  
  def createInputPort(name: String, id: Int): Unit
  
  def createOutputPort(name: String, id: Int): Unit
  
  def createRow(id: Int): Unit
  
  def createCell(value: Boolean, id: Int): Unit
  
  def createTruthTableRowsRow(tt: Int, row: Int): Unit
  
  def createTruthTablePortsPort(tt: Int, port: Int): Unit
  
  def createRowCellsCell(row: Int, cell: Int): Unit
  
  def createCellPortPort(cell: Int, port: Int): Unit
}