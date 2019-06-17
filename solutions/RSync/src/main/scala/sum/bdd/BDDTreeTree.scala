package sum.bdd

import org.rosi_project.model_management.sum.compartments.IComposition

class BDDTreeTree(private val sInstance: BDD, private val tInstance: Tree) extends IComposition {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[BDDTreeTree " + source + ", " + target + "]"
  }

  def getSourceIns(): BDD = {
    return sInstance
  }

  def getTargetIns(): Tree = {
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



    