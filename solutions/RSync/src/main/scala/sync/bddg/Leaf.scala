package sync.bddg

class Leaf(protected var assignments: Set[Assignment], t_OwnerSubtreeForOne: Set[Subtree], t_OwnerSubtreeForZero: Set[Subtree], t_OwnerBDD: BDD) extends Tree(t_OwnerSubtreeForOne, t_OwnerSubtreeForZero, t_OwnerBDD) {

  def getAssignments(): Set[Assignment] = {
    assignments
  }

  def addAssignments(a: Assignment): Unit = {
    assignments += a
    +this syncAddAssignments (a)
  }

  def removeAssignments(a: Assignment): Unit = {
    assignments -= a
    +this syncRemoveAssignments (a)
  }

  override def toString(): String = {
    "Leaf:"
  }

}



    