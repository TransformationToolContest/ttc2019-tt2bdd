package sync.tt

class TruthTable(protected var name: String, protected var rows: Set[Row], protected var ports: Set[Port], l_Location: String) extends LocatedElement(l_Location) {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this changeName ()
  }

  def getRows(): Set[Row] = {
    rows
  }

  def setRows(r: Set[Row]): Unit = {
    rows = r
    +this changeRows ()
  }

  def addRows(r: Row): Unit = {
    rows += r
    +this changeRows ()
  }

  def getPorts(): Set[Port] = {
    ports
  }

  def setPorts(p: Set[Port]): Unit = {
    ports = p
    +this changePorts ()
  }

  def addPorts(p: Port): Unit = {
    ports += p
    +this changePorts ()
  }

  override def toString(): String = {
    "TruthTable:" + " name=" + name + " location=" + location
  }

}



    