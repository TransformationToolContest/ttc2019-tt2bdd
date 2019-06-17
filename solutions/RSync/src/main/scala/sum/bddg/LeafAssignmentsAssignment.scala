package sum.bddg

import org.rosi_project.model_management.sum.compartments.IComposition

class LeafAssignmentsAssignment(private val sInstance: Leaf, private val tInstance: Assignment) extends IComposition {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[LeafAssignmentsAssignment " + source + ", " + target + "]"
  }

  def getSourceIns(): Leaf = {
    return sInstance
  }

  def getTargetIns(): Assignment = {
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



    