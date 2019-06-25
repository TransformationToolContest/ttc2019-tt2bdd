package sync.bdd

class InputPort(protected var subtrees: Set[Subtree], p_Name: String, p_Owner: BDD) extends Port(p_Name, p_Owner) {

  def getSubtrees(): Set[Subtree] = {
    subtrees
  }

  def addSubtrees(s: Subtree): Unit = {
    subtrees += s
    +this syncAddSubtrees (s)
  }

  def removeSubtrees(s: Subtree): Unit = {
    subtrees -= s
    +this syncRemoveSubtrees (s)
  }

  override def toString(): String = {
    "InputPort:" + " name=" + name
  }

}



    