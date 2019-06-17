package sync.bddg

class Leaf(protected var assignments: Set[Assignment], t_OwnerSubtreeForOne: Set[Subtree], t_OwnerSubtreeForZero: Set[Subtree], t_OwnerBDD: BDD) extends Tree(t_OwnerSubtreeForOne, t_OwnerSubtreeForZero, t_OwnerBDD) {

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
    "Leaf:"
  }

}



    