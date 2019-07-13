package sync.bddg

class OutputPort(protected var assignments: Set[Assignment], p_Name: String, p_Owner: BDD) extends Port(p_Name, p_Owner) {

  def getAssignments(): Set[Assignment] = {
    assignments
  }

  def addAssignments(a: Assignment): Unit = {
    require(a != null)
    require(!assignments.contains(a))
    assignments += a
    +this syncAddAssignments (a)
  }

  def removeAssignments(a: Assignment): Unit = {
    require(a != null)
    require(assignments.contains(a))
    assignments -= a
    +this syncRemoveAssignments (a)
  }

  override def toString(): String = {
    "OutputPort:" + " name=" + name
  }

}



    