package sync.tt

//import scala.collection.mutable.Set

class Row(protected var cells: Set[Cell], protected var owner: TruthTable, l_Location: String) extends LocatedElement(l_Location) {

  def getCells(): Set[Cell] = {
    cells
  }

  def addCells(c: Cell): Unit = {
    cells += c
    +this addCells ()
  }

  def removeCells(c: Cell): Unit = {
    cells -= c
    +this removeCells ()
  }

  def getOwner(): TruthTable = {
    owner
  }

  def setOwner(o: TruthTable): Unit = {
    owner = o
    +this setOwner ()
  }

  override def toString(): String = {
    "Row:" + " location=" + location
  }

}



    