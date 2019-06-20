package ttc2019.worksum

import sum.tt._
import ttc2019.metamodels.create.TtCreationHelper
import org.rosi_project.model_management.core.ModelElementLists
import ttc2019.IWriteOutputModel

object WriteSumTtOutput extends IWriteOutputModel {
  def generateEverything(outputFile: String): Unit = {
    val creation = new TtCreationHelper()
    
    val pkgName = "sum.tt."
    
    val tt = ModelElementLists.getDirectElementsFromType(pkgName + "TruthTable").asInstanceOf[Set[TruthTable]]
    val in = ModelElementLists.getDirectElementsFromType(pkgName + "InputPort").asInstanceOf[Set[InputPort]]
    val ou = ModelElementLists.getDirectElementsFromType(pkgName + "OutputPort").asInstanceOf[Set[OutputPort]]
    val ro = ModelElementLists.getDirectElementsFromType(pkgName + "Row").asInstanceOf[Set[Row]]
    val ce = ModelElementLists.getDirectElementsFromType(pkgName + "Cell").asInstanceOf[Set[Cell]]
    
    val cpp = ModelElementLists.getDirectElementsFromType(pkgName + "CellPortPort").asInstanceOf[Set[CellPortPort]]
    val rcc = ModelElementLists.getDirectElementsFromType(pkgName + "RowCellsCell").asInstanceOf[Set[RowCellsCell]]
    val tpp = ModelElementLists.getDirectElementsFromType(pkgName + "TruthTablePortsPort").asInstanceOf[Set[TruthTablePortsPort]]
    val trr = ModelElementLists.getDirectElementsFromType(pkgName + "TruthTableRowsRow").asInstanceOf[Set[TruthTableRowsRow]]
    
    /*println(tt)
    println(in)
    println(ou)
    println(ro)
    println(ce)
    
    println(cpp)
    println(rcc)
    println(tpp)
    println(trr)*/
    
    //add normal instances
    tt.foreach(o => {
      creation.createTT(o, o.getName())
    })
    in.foreach(o => {
      creation.createInputPort(o, o.getName())
    })
    ou.foreach(o => {
      creation.createOutputPort(o, o.getName())
    })
    ro.foreach(o => {
      creation.createRow(o)
    })
    ce.foreach(o => {
      creation.createCell(o, o.getValue())
    })   
    
    //add connections    
    cpp.foreach(o => {
      creation.addPortCellsConnection(o.getTargetIns(), o.getSourceIns())
    })
    rcc.foreach(o => {
      creation.addRowCellsConnection(o.getSourceIns(), o.getTargetIns())
    })
    tpp.foreach(o => {
      creation.addTTPortConnection(o.getTargetIns())
    })
    trr.foreach(o => {
      creation.addTTRowConnection(o.getTargetIns())
    })
    
    //generate output file
    creation.generate(outputFile)
  }
}