package sync.bddg

class Leaf(protected var assignments: Set[Assignment], t_OwnerSubtreeForOne: Set[Subtree], t_OwnerSubtreeForZero: Set[Subtree], t_OwnerBDD: BDD) extends Tree(t_OwnerSubtreeForOne, t_OwnerSubtreeForZero, t_OwnerBDD) {

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
    "Leaf:"
  }
  
  def getAvgPath(): Double = 0.0
  
  def getMinPath(): Int = 0
  
  def getMaxPath(): Int = 0

}



    