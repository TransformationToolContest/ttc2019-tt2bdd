package sync.bdd

class Subtree(protected var treeForOne: Tree, protected var treeForZero: Tree, protected var port: InputPort, t_OwnerSubtreeForOne: Subtree, t_OwnerSubtreeForZero: Subtree, t_OwnerBDD: BDD) extends Tree(t_OwnerSubtreeForOne, t_OwnerSubtreeForZero, t_OwnerBDD) {

  def getTreeForOne(): Tree = {
    treeForOne
  }

  def setTreeForOne(t: Tree): Unit = {
    treeForOne = t
    +this setTreeForOne ()
  }

  def getTreeForZero(): Tree = {
    treeForZero
  }

  def setTreeForZero(t: Tree): Unit = {
    treeForZero = t
    +this setTreeForZero ()
  }

  def getPort(): InputPort = {
    port
  }

  def setPort(p: InputPort): Unit = {
    port = p
    +this setPort ()
  }

  override def toString(): String = {
    "Subtree:"
  }
  
  def getAvgPath(): Double = {
    return 0.5 * treeForOne.getAvgPath() + 0.5 * treeForZero.getAvgPath() + 1
  }
  
  def getMinPath(): Int = {
    val minZero = treeForZero.getMinPath()
    val minOne = treeForOne.getMinPath()
    if (minZero < minOne) {
      return minZero + 1
    }
    return minOne + 1
  }
  
  def getMaxPath(): Int = {
    val maxZero = treeForZero.getMaxPath()
    val maxOne = treeForOne.getMaxPath()
    if (maxZero > maxOne) {
      return maxZero + 1
    }
    return maxOne + 1
  }

}



    