package bdd

class InputPort(p_Name: String) extends Port(p_Name) {

  override def toString(): String = {
    "InputPort:" + " name=" + name
  }

}



    