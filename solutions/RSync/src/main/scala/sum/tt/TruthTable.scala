package sum.tt

class TruthTable(protected var name: String, l_Location: String) extends LocatedElement(l_Location) {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this changeName ()
  }

  override def toString(): String = {
    "TruthTable:" + " name=" + name + " location=" + location
  }

}



    