package sync.bdd

class Subtree(protected var treeForOne: Tree, protected var treeForZero: Tree, protected var port: InputPort, t_OwnerSubtreeForOne: Subtree, t_OwnerSubtreeForZero: Subtree, t_OwnerBDD: BDD) extends Tree(t_OwnerSubtreeForOne, t_OwnerSubtreeForZero, t_OwnerBDD) {

  def getTreeForOne(): Tree = {
    treeForOne
  }

  def setTreeForOne(t: Tree): Unit = {
    treeForOne = t
    +this syncSetTreeForOne ()
  }

  def getTreeForZero(): Tree = {
    treeForZero
  }

  def setTreeForZero(t: Tree): Unit = {
    treeForZero = t
    +this syncSetTreeForZero ()
  }

  def getPort(): InputPort = {
    port
  }

  def setPort(p: InputPort): Unit = {
    port = p
    +this syncSetPort ()
  }

  override def toString(): String = {
    "Subtree:"
  }

  override def getAvgPath(): Double = {
    return 0.5 * (treeForOne.getAvgPath() + treeForZero.getAvgPath()) + 1
  }

  override def getMinPath(): Int = {
    Math.min(treeForZero.getMinPath(), treeForOne.getMinPath()) + 1
  }

  override def getMaxPath(): Int = {
    Math.max(treeForZero.getMaxPath(), treeForOne.getMaxPath()) + 1
  }

}



    