package bdd

import org.rosi_project.model_management.sum.compartments.IComposition

class SubtreeTreeForZeroTree(private val sInstance: Subtree, private val tInstance: Tree) extends IComposition {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[SubtreeTreeForZeroTree " + source + ", " + target + "]"
  }

  def getSourceIns(): Subtree = {
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



    