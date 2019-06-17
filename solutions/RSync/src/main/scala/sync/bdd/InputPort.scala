package sync.bdd

class InputPort(protected var subtrees: Set[Subtree], p_Name: String, p_Owner: BDD) extends Port(p_Name, p_Owner) {

  def getSubtrees(): Set[Subtree] = {
    subtrees
  }

  def setSubtrees(s: Set[Subtree]): Unit = {
    subtrees = s
    +this changeSubtrees ()
  }

  def addSubtrees(s: Subtree): Unit = {
    subtrees += s
    +this changeSubtrees ()
  }

  override def toString(): String = {
    "InputPort:" + " name=" + name
  }

}



    