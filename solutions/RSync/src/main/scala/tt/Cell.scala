package tt

class Cell(protected var value: Boolean, l_Location: String) extends LocatedElement(l_Location) {

  def getValue(): Boolean = {
    value
  }

  def setValue(v: Boolean): Unit = {
    value = v
    +this changeValue ()
  }

  override def toString(): String = {
    "Cell:" + " value=" + value + " location=" + location
  }

}



    