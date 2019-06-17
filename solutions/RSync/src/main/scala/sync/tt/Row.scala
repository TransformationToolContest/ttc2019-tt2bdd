package sync.tt

class Row(protected var cells: Set[Cell], protected var owner: TruthTable, l_Location: String) extends LocatedElement(l_Location) {

  def getCells(): Set[Cell] = {
    cells
  }

  def setCells(c: Set[Cell]): Unit = {
    cells = c
    +this changeCells ()
  }

  def addCells(c: Cell): Unit = {
    cells += c
    +this changeCells ()
  }
  
  def removeCells(c: Cell): Unit = {
    cells -= c
    +this changeCells ()
  }

  def getOwner(): TruthTable = {
    owner
  }

  def setOwner(o: TruthTable): Unit = {
    owner = o
    +this changeOwner ()
  }

  override def toString(): String = {
    "Row:" + " location=" + location
  }

}



    