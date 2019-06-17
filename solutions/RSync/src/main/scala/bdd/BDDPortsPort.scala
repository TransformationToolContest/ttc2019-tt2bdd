package bdd

import org.rosi_project.model_management.sum.compartments.IComposition

class BDDPortsPort(private val sInstance: BDD, private val tInstance: Port) extends IComposition {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[BDDPortsPort " + source + ", " + target + "]"
  }

  def getSourceIns(): BDD = {
    return sInstance
  }

  def getTargetIns(): Port = {
    return tInstance
  }

  class Source extends ICompositionSource {

    override def toString(): String = {
      "S: (" + sInstance + ")"
    }

  }

  class Target extends ICompositionTarget {

    override def toString(): String = {
      "T: (" + tInstance + ")"
    }

  }

}



    