package sync.tt

class Row(protected var cells: Set[Cell], protected var owner: TruthTable, l_Location: String) extends LocatedElement(l_Location) {

  def getCells(): Set[Cell] = {
    cells
  }

  def addCells(c: Cell): Unit = {
    cells += c
    +this syncAddCells (c)
  }

  def removeCells(c: Cell): Unit = {
    cells -= c
    +this syncRemoveCells (c)
  }

  def getOwner(): TruthTable = {
    owner
  }

  def setOwner(o: TruthTable): Unit = {
    owner = o
    +this syncSetOwner ()
  }

  override def toString(): String = {
    "Row:" + " location=" + location
  }

}



    