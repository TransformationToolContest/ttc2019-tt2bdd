package sync.tt

class Cell(protected var value: Boolean, protected var port: Port, protected var owner: Row, l_Location: String) extends LocatedElement(l_Location) {

  def getValue(): Boolean = {
    value
  }

  def setValue(v: Boolean): Unit = {
    value = v
    +this syncSetValue ()
  }

  def getPort(): Port = {
    port
  }

  def setPort(p: Port): Unit = {
    require(p != null)
    port = p
    +this syncSetPort ()
  }

  def getOwner(): Row = {
    owner
  }

  def setOwner(o: Row): Unit = {
    require(o != null)
    owner = o
    +this syncSetOwner ()
  }

  override def toString(): String = {
    "Cell:" + " value=" + value + " location=" + location
  }

}



    