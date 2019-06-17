package sync.bdd

class OutputPort(protected var assignments: Set[Assignment], p_Name: String, p_Owner: BDD) extends Port(p_Name, p_Owner) {

  def getAssignments(): Set[Assignment] = {
    assignments
  }

  def setAssignments(a: Set[Assignment]): Unit = {
    assignments = a
    +this changeAssignments ()
  }

  def addAssignments(a: Assignment): Unit = {
    assignments += a
    +this changeAssignments ()
  }

  override def toString(): String = {
    "OutputPort:" + " name=" + name
  }

}



    