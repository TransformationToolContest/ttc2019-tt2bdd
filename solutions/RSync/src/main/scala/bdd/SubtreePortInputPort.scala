package bdd

import org.rosi_project.model_management.sum.compartments.IAssociation

class SubtreePortInputPort(private val sInstance: Subtree, private val tInstance: InputPort) extends IAssociation {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[SubtreePortInputPort " + source + ", " + target + "]"
  }

  def getSourceIns(): Subtree = {
    return sInstance
  }

  def getTargetIns(): InputPort = {
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



    