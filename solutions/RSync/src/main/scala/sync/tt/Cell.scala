package sync.tt

class Cell(protected var value: Boolean, protected var port: Port, protected var owner: Row, l_Location: String) extends LocatedElement(l_Location) {

  def getValue(): Boolean = {
    value
  }

  def setValue(v: Boolean): Unit = {
    value = v
    +this changeValue ()
  }

  def getPort(): Port = {
    port
  }

  def setPort(p: Port): Unit = {
    port = p
    +this changePort ()
  }

  def getOwner(): Row = {
    owner
  }

  def setOwner(o: Row): Unit = {
    owner = o
    +this changeOwner ()
  }

  override def toString(): String = {
    "Cell:" + " value=" + value + " location=" + location
  }

}



    