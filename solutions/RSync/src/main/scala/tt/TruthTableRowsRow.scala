package tt

import org.rosi_project.model_management.sum.compartments.IComposition

class TruthTableRowsRow(private val sInstance: TruthTable, private val tInstance: Row) extends IComposition {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[TruthTableRowsRow " + source + ", " + target + "]"
  }

  def getSourceIns(): TruthTable = {
    return sInstance
  }

  def getTargetIns(): Row = {
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



    