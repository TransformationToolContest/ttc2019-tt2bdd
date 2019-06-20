package sync.tt

//import scala.collection.mutable.Set

abstract class Port(protected var name: String, protected var cells: Set[Cell], protected var owner: TruthTable, l_Location: String) extends LocatedElement(l_Location) {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this setName ()
  }

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
    "Port:" + " name=" + name + " location=" + location
  }

}



    