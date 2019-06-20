package sync.tt

//import scala.collection.mutable.Set

class TruthTable(protected var name: String, protected var rows: Set[Row], protected var ports: Set[Port], l_Location: String) extends LocatedElement(l_Location) {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this setName ()
  }

  def getRows(): Set[Row] = {
    rows
  }

  def addRows(r: Row): Unit = {
    rows += r
    +this addRows ()
  }

  def removeRows(r: Row): Unit = {
    rows -= r
    +this removeRows ()
  }

  def getPorts(): Set[Port] = {
    ports
  }

  def addPorts(p: Port): Unit = {
    ports += p
    +this addPorts ()
  }

  def removePorts(p: Port): Unit = {
    ports -= p
    +this removePorts ()
  }

  override def toString(): String = {
    "TruthTable:" + " name=" + name + " location=" + location
  }

}



    