package sum.bddg

import org.rosi_project.model_management.sum.compartments.IAssociation

class SubtreeTreeForOneTree(private val sInstance: Subtree, private val tInstance: Tree) extends IAssociation {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[SubtreeTreeForOneTree " + source + ", " + target + "]"
  }

  def getSourceIns(): Subtree = {
    return sInstance
  }

  def getTargetIns(): Tree = {
    return tInstance
  }

  class Source extends IAssociationSource {

    override def toString(): String = {
      "S: (" + sInstance + ")"
    }

  }

  class Target extends IAssociationTarget {

    override def toString(): String = {
      "T: (" + tInstance + ")"
    }

  }

}



    