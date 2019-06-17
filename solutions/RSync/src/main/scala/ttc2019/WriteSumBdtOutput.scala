package ttc2019

import sum.bdd._

import ttc2019.metamodels.create.BdtCreationHelper
import org.rosi_project.model_management.core.ModelElementLists

object WriteSumBdtOutput extends IWriteOutputModel {
  
  def generateEverything(outputFile: String): Unit = {
    val creation = new BdtCreationHelper()
    
    val pkgName = "sum.bdd."
    
    val bd = ModelElementLists.getDirectElementsFromType(pkgName + "BDD").asInstanceOf[List[BDD]]
    val in = ModelElementLists.getDirectElementsFromType(pkgName + "InputPort").asInstanceOf[List[InputPort]]
    val ou = ModelElementLists.getDirectElementsFromType(pkgName + "OutputPort").asInstanceOf[List[OutputPort]]
    val as = ModelElementLists.getDirectElementsFromType(pkgName + "Assignment").asInstanceOf[List[Assignment]]
    val le = ModelElementLists.getDirectElementsFromType(pkgName + "Leaf").asInstanceOf[List[Leaf]]
    val su = ModelElementLists.getDirectElementsFromType(pkgName + "Subtree").asInstanceOf[List[Subtree]]
    
    val apo = ModelElementLists.getDirectElementsFromType(pkgName + "AssignmentPortOutputPort").asInstanceOf[List[AssignmentPortOutputPort]]
    val bpp = ModelElementLists.getDirectElementsFromType(pkgName + "BDDPortsPort").asInstanceOf[List[BDDPortsPort]]
    val btt = ModelElementLists.getDirectElementsFromType(pkgName + "BDDTreeTree").asInstanceOf[List[BDDTreeTree]]
    val laa = ModelElementLists.getDirectElementsFromType(pkgName + "LeafAssignmentsAssignment").asInstanceOf[List[LeafAssignmentsAssignment]]
    val spi = ModelElementLists.getDirectElementsFromType(pkgName + "SubtreePortInputPort").asInstanceOf[List[SubtreePortInputPort]]
    val szt = ModelElementLists.getDirectElementsFromType(pkgName + "SubtreeTreeForZeroTree").asInstanceOf[List[SubtreeTreeForZeroTree]]
    val sot = ModelElementLists.getDirectElementsFromType(pkgName + "SubtreeTreeForOneTree").asInstanceOf[List[SubtreeTreeForOneTree]]
    
    /*println(bd)
    println(in)
    println(ou)
    println(as)
    println(le)
    println(su)
    
    println(apo)
    println(bpp)
    println(btt)
    println(laa)
    println(spi)
    println(szt)
    println(sot)*/
    
    //add normal instances
    bd.foreach(o => {
      creation.createBDD(o, o.getName())
    })
    in.foreach(o => {
      creation.createInputPort(o, o.getName())
    })
    ou.foreach(o => {
      creation.createOutputPort(o, o.getName())
    })
    as.foreach(o => {
      creation.createAssignment(o, o.getValue())
    })
    le.foreach(o => {
      creation.createLeaf(o)
    })
    su.foreach(o => {
      creation.createSubtree(o)
    })    
    
    var setLeafs: Set[Leaf] = Set.empty
    var setAssignment: Set[Assignment] = Set.empty
    
    //add connections    
    bpp.foreach(o => {
      creation.addBDDPortConnection(o.getTargetIns())
    })
    btt.foreach(o => {
      creation.addBDDTreeConnection(o.getTargetIns())
      if (o.getTargetIns().isInstanceOf[Leaf]) {
        setLeafs += o.getTargetIns().asInstanceOf[Leaf]
      }
    })
    spi.foreach(o => {
      creation.addInputSubtreeConnection(o.getTargetIns(), o.getSourceIns())
    })
    szt.foreach(o => {
      creation.addSubtreeZeroTreeConnection(o.getTargetIns(), o.getSourceIns())
      if (o.getTargetIns().isInstanceOf[Leaf]) {
        setLeafs += o.getTargetIns().asInstanceOf[Leaf]
      }
    })
    sot.foreach(o => {
      creation.addSubtreeOneTreeConnection(o.getTargetIns(), o.getSourceIns())
      if (o.getTargetIns().isInstanceOf[Leaf]) {
        setLeafs += o.getTargetIns().asInstanceOf[Leaf]
      }
    })
    laa.filter(o => setLeafs.contains(o.getSourceIns())).foreach(o => {
      creation.addLeafAssignmentConnection(o.getSourceIns(), o.getTargetIns())
      setAssignment += o.getTargetIns()
    })
    //critical only push assignments that are connected
    apo.filter(o => setAssignment.contains(o.getSourceIns())).foreach(o => {
      creation.addOutputAssignmentConnection(o.getTargetIns(), o.getSourceIns())
    })
    
    //generate output file
    creation.generate(outputFile)
  }
}