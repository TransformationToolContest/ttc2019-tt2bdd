package tt

import org.rosi_project.model_management.sum.compartments.IAssociation

class CellPortPort(private val sInstance: Cell, private val tInstance: Port) extends IAssociation {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[CellPortPort " + source + ", " + target + "]"
  }

  def getSourceIns(): Cell = {
    return sInstance
  }

  def getTargetIns(): Port = {
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



    