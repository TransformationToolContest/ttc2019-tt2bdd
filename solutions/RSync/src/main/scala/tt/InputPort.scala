package tt

class InputPort(p_Name: String, p_Location: String) extends Port(p_Name, p_Location) {

  override def toString(): String = {
    "InputPort:" + " name=" + name
  }

}



    