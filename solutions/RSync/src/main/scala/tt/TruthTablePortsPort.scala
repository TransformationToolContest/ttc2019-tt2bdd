package tt

import org.rosi_project.model_management.sum.compartments.IComposition

class TruthTablePortsPort(private val sInstance: TruthTable, private val tInstance: Port) extends IComposition {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[TruthTablePortsPort " + source + ", " + target + "]"
  }

  def getSourceIns(): TruthTable = {
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



    