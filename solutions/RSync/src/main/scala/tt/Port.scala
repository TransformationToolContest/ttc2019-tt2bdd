package tt

abstract class Port(protected var name: String, l_Location: String) extends LocatedElement(l_Location) {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this changeName ()
  }

  override def toString(): String = {
    "Port:" + " name=" + name + " location=" + location
  }

}



    