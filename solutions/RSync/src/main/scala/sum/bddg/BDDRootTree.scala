package sum.bddg

import org.rosi_project.model_management.sum.compartments.IDirectAssoziation

class BDDRootTree(private val sInstance: BDD, private val tInstance: Tree) extends IDirectAssoziation {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[BDDRootTree " + source + ", " + target + "]"
  }

  def getSourceIns(): BDD = {
    return sInstance
  }

  def getTargetIns(): Tree = {
    return tInstance
  }

  class Source extends IDirectAssoziationSource {

    override def toString(): String = {
      "S: (" + sInstance + ")"
    }

  }

  class Target extends IDirectAssoziationTarget {

    override def toString(): String = {
      "T: (" + tInstance + ")"
    }

  }

}



    