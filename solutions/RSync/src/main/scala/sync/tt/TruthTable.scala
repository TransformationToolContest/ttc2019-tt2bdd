package sync.tt

class TruthTable(protected var name: String, protected var rows: Set[Row], protected var ports: Set[Port], l_Location: String) extends LocatedElement(l_Location) {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    require(n != null)
    name = n
    +this syncSetName ()
  }

  def getRows(): Set[Row] = {
    rows
  }

  def addRows(r: Row): Unit = {
    require(r != null)
    require(!rows.contains(r))
    rows += r
    +this syncAddRows (r)
  }

  def removeRows(r: Row): Unit = {
    require(r != null)
    require(rows.contains(r))
    rows -= r
    +this syncRemoveRows (r)
  }

  def getPorts(): Set[Port] = {
    ports
  }

  def addPorts(p: Port): Unit = {
    require(p != null)
    require(!ports.contains(p))
    ports += p
    +this syncAddPorts (p)
  }

  def removePorts(p: Port): Unit = {
    require(p != null)
    require(ports.contains(p))
    ports -= p
    +this syncRemovePorts (p)
  }

  override def toString(): String = {
    "TruthTable:" + " name=" + name + " location=" + location
  }

}



    