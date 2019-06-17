package bdd

import org.rosi_project.model_management.sum.compartments.IAssociation

class AssignmentPortOutputPort(private val sInstance: Assignment, private val tInstance: OutputPort) extends IAssociation {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[AssignmentPortOutputPort " + source + ", " + target + "]"
  }

  def getSourceIns(): Assignment = {
    return sInstance
  }

  def getTargetIns(): OutputPort = {
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



    