package tt

import org.rosi_project.model_management.sum.compartments.IComposition

class RowCellsCell(private val sInstance: Row, private val tInstance: Cell) extends IComposition {

  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()
    sInstance play this.source
    tInstance play this.target
  }

  override def toString(): String = {
    "[RowCellsCell " + source + ", " + target + "]"
  }

  def getSourceIns(): Row = {
    return sInstance
  }

  def getTargetIns(): Cell = {
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



    