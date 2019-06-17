package ttc2019

import sync.bdd._

import ttc2019.metamodels.create.BdtCreationHelper
import org.rosi_project.model_management.core.ModelElementLists

object WriteSyncBdtOutput extends IWriteOutputModel {
  
  def generateEverything(outputFile: String): Unit = {
    val creation = new BdtCreationHelper()
    
    val pkgName = "sync.bdd."
    
    val bd = ModelElementLists.getDirectElementsFromType(pkgName + "BDD").asInstanceOf[List[BDD]]
    val in = ModelElementLists.getDirectElementsFromType(pkgName + "InputPort").asInstanceOf[List[InputPort]]
    val ou = ModelElementLists.getDirectElementsFromType(pkgName + "OutputPort").asInstanceOf[List[OutputPort]]
    val as = ModelElementLists.getDirectElementsFromType(pkgName + "Assignment").asInstanceOf[List[Assignment]]
    val le = ModelElementLists.getDirectElementsFromType(pkgName + "Leaf").asInstanceOf[List[Leaf]]
    val su = ModelElementLists.getDirectElementsFromType(pkgName + "Subtree").asInstanceOf[List[Subtree]]
    
    /*println(bd)
    println(in)
    println(ou)
    println(as)
    println(le)
    println(su)*/
    
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
    bd.foreach(o => {
      o.getPorts().foreach(p => {
        creation.addBDDPortConnection(p)
      })
      creation.addBDDTreeConnection(o.getTree())
    })
    su.foreach(o => {
      creation.addSubtreeOneTreeConnection(o.getTreeForOne(), o)
      creation.addSubtreeZeroTreeConnection(o.getTreeForZero(), o)
      creation.addInputSubtreeConnection(o.getPort(), o)
      if (o.getTreeForOne().isInstanceOf[Leaf]) {
        setLeafs += o.getTreeForOne().asInstanceOf[Leaf]
      }
      if (o.getTreeForZero().isInstanceOf[Leaf]) {
        setLeafs += o.getTreeForZero().asInstanceOf[Leaf]
      }
    }) 
    as.foreach(o => {
      if (setLeafs.contains(o.getOwner())) {
        creation.addLeafAssignmentConnection(o.getOwner(), o)
        creation.addOutputAssignmentConnection(o.getPort(), o)
      }
    })   
    
    //generate output file
    creation.generate(outputFile)
  }
}