package sync.bddg

class OutputPort(protected var assignments: Set[Assignment], p_Name: String, p_Owner: BDD) extends Port(p_Name, p_Owner) {

  def getAssignments(): Set[Assignment] = {
    assignments
  }

  def addAssignments(a: Assignment): Unit = {
    assignments += a
    +this addAssignments ()
  }

  def removeAssignments(a: Assignment): Unit = {
    assignments -= a
    +this removeAssignments ()
  }

  override def toString(): String = {
    "OutputPort:" + " name=" + name
  }

}



    