package sync.tt

abstract class Port(protected var name: String, protected var cells: Set[Cell], protected var owner: TruthTable, l_Location: String) extends LocatedElement(l_Location) {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this syncSetName ()
  }

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
    "Port:" + " name=" + name + " location=" + location
  }

}



    