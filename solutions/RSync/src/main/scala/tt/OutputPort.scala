package tt

class OutputPort(p_Name: String, p_Location: String) extends Port(p_Name, p_Location) {

  override def toString(): String = {
    "OutputPort:" + " name=" + name
  }

}



    