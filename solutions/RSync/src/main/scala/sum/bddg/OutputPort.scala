package sum.bddg

class OutputPort(p_Name: String) extends Port(p_Name) {

  override def toString(): String = {
    "OutputPort:" + " name=" + name
  }

}



    