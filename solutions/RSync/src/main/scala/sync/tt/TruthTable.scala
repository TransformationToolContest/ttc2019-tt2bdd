package sync.tt

class TruthTable(protected var name: String, protected var rows: Set[Row], protected var ports: Set[Port], l_Location: String) extends LocatedElement(l_Location) {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this syncSetName ()
  }

  def getRows(): Set[Row] = {
    rows
  }

  def addRows(r: Row): Unit = {
    rows += r
    +this syncAddRows (r)
  }

  def removeRows(r: Row): Unit = {
    rows -= r
    +this syncRemoveRows (r)
  }

  def getPorts(): Set[Port] = {
    ports
  }

  def addPorts(p: Port): Unit = {
    ports += p
    +this syncAddPorts (p)
  }

  def removePorts(p: Port): Unit = {
    ports -= p
    +this syncRemovePorts (p)
  }

  override def toString(): String = {
    "TruthTable:" + " name=" + name + " location=" + location
  }

}



    