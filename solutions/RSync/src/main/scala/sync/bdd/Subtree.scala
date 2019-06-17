package sync.bdd

class Subtree(protected var treeForOne: Tree, protected var treeForZero: Tree, protected var port: InputPort, t_OwnerSubtreeForOne: Subtree, t_OwnerSubtreeForZero: Subtree, t_OwnerBDD: BDD) extends Tree(t_OwnerSubtreeForOne, t_OwnerSubtreeForZero, t_OwnerBDD) {

  def getTreeForOne(): Tree = {
    treeForOne
  }

  def setTreeForOne(t: Tree): Unit = {
    treeForOne = t
    +this changeTreeForOne ()
  }

  def getTreeForZero(): Tree = {
    treeForZero
  }

  def setTreeForZero(t: Tree): Unit = {
    treeForZero = t
    +this changeTreeForZero ()
  }

  def getPort(): InputPort = {
    port
  }

  def setPort(p: InputPort): Unit = {
    port = p
    +this changePort ()
  }

  override def toString(): String = {
    this.getStringDeep() + "Subtree: " + port + " \n 0" + treeForZero + "\n 1" + treeForOne
  }

}



    