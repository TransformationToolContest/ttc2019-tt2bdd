package sync.tt

class Cell(protected var value: Boolean, protected var port: Port = null, protected var owner: Row = null, l_Location: String = null) extends LocatedElement(l_Location) {

  def getValue(): Boolean = {
    value
  }

  def setValue(v: Boolean): Unit = {
    value = v
    +this setValue ()
  }

  def getPort(): Port = {
    port
  }

  def setPort(p: Port): Unit = {
    port = p
    +this setPort ()
  }

  def getOwner(): Row = {
    owner
  }

  def setOwner(o: Row): Unit = {
    owner = o
    +this setOwner ()
  }

  override def toString(): String = {
    "Cell:" + " value=" + value + " location=" + location
  }

}



    