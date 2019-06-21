package ttc2019.worksync

import org.rosi_project.model_management.core.PlayerSync
import org.rosi_project.model_management.core.SynchronizationCompartment
import org.rosi_project.model_management.sync.IIntegrationCompartment
import org.rosi_project.model_management.sync.roles.IIntegrator
import org.rosi_project.model_management.sync.roles.IRoleManager

import org.rosi_project.model_management.sync.helper.IntegrationContainer
import scala.collection.mutable.ListBuffer
import org.rosi_project.model_management.core.ModelElementLists

object BdtAndBddSyncIntegration extends IIntegrationCompartment {

  //private var createdObjects: Set[PlayerSync] = Set.empty
  private var leafNodes: Map[Set[String], sync.bddg.Leaf] = Map.empty
  private var createdBdds: Set[sync.bddg.BDD] = Set.empty
  private var createdBdts: Set[sync.bdd.BDD] = Set.empty

  def printManager(): Unit = {
    ModelElementLists.getElementsFromType("sync.bddg.Leaf").asInstanceOf[List[sync.bddg.Leaf]].foreach(obj => {
      +obj printAllManager ()
    })
    ModelElementLists.getElementsFromType("sync.bdd.Leaf").asInstanceOf[List[sync.bdd.Leaf]].foreach(obj => {
      +obj printAllManager ()
    })
  }

  def getRelationalIntegratorsForClassName(classname: Object): IIntegrator = {
    return null
  }

  def getIntegratorForClassName(classname: Object): IIntegrator = {
    if (classname.isInstanceOf[sync.tt.TruthTable])
      return new TruthTableConstruct()
    return null
  }

  def getNextIntegratorForClassName(classname: Object): IIntegrator = {
    if (classname.isInstanceOf[sync.tt.OutputPort])
      return new OutputPortConstruct()
    if (classname.isInstanceOf[sync.tt.InputPort])
      return new InputPortConstruct()
    return null
  }

  def finalEditFunction(): Unit = {
    var ttNode: sync.tt.TruthTable = null
    
    createdBdds.foreach(bddNode => {
      val oppBDD: PlayerSync = +bddNode getRelatedClassFromName ("TruthTable")
      if (oppBDD != null) {
        ttNode = oppBDD.asInstanceOf[sync.tt.TruthTable]
      }
      val rows = ttNode.getRows()

      val tree = createOutputLookSubtree(bddNode, ttNode, rows, Set.empty)
      bddNode.setRoot(tree)
      connectTargetElementWithSourceElementes(tree, rows.asInstanceOf[Set[PlayerSync]])
    })

    //printManager()
  }

  def createOutputLookSubtree(bdd: sync.bddg.BDD, truthTable: sync.tt.TruthTable, rows: Set[sync.tt.Row], finishPorts: Set[sync.tt.Port]): sync.bddg.Tree = {
    var numberTrue = -1
    var numberFalse = -1
    var max = 0
    var portTT: sync.tt.Port = null
    var cellis: Set[sync.tt.Cell] = Set.empty

    //find next inputport for subtree
    truthTable.getPorts().filter(p => p.isInstanceOf[sync.tt.InputPort] && !finishPorts.contains(p)).foreach(ttip => {
      val newCells = ttip.getCells().filter(c => rows.contains(c.getOwner()))
      if (newCells.size >= max) {
        var setTrue: Set[Set[String]] = Set.empty
        var setFalse: Set[Set[String]] = Set.empty
        newCells.foreach(cell => {
          if (cell.getValue()) {
            var setPortValue: Set[String] = Set.empty
            cell.getOwner().getCells().filter(_.getPort().isInstanceOf[sync.tt.OutputPort]).foreach(ocell => {
              setPortValue += s"${ocell.getPort().getName()} ${ocell.getValue()}"
            })
            setTrue += setPortValue
          } else {
            var setPortValue: Set[String] = Set.empty
            cell.getOwner().getCells().filter(_.getPort().isInstanceOf[sync.tt.OutputPort]).foreach(ocell => {
              setPortValue += s"${ocell.getPort().getName()} ${ocell.getValue()}"
            })
            setFalse += setPortValue
          }
        })
        val p1 = setTrue.size
        val p2 = setFalse.size
        if (p1 + p2 < numberFalse + numberTrue && p1 > 0 && p2 > 0 || numberTrue < 0) {
          numberTrue = p1
          numberFalse = p2
          cellis = newCells
          portTT = ttip
          max = newCells.size
        }
      }
    })

    var portBDD: sync.bddg.InputPort = null
    val oppo: PlayerSync = +portTT getRelatedClassFromName ("InputPort")
    if (oppo != null) {
      portBDD = oppo.asInstanceOf[sync.bddg.InputPort]
    }

    val newPorts = finishPorts + portTT
    val subtree = new sync.bddg.Subtree(null, null, portBDD, Set.empty, Set.empty, bdd)
    bdd.addTrees(subtree)
    portBDD.addSubtrees(subtree)

    val rowsOne = cellis.filter(_.getValue()).map(_.getOwner())
    val rowsZero = cellis.filter(!_.getValue()).map(_.getOwner())

    var treeZero: sync.bddg.Tree = null
    var treeOne: sync.bddg.Tree = null
    if (numberFalse == 1) {
      //create leaf from rows
      treeZero = createLeafFromRows(bdd, rowsZero)
    } else {
      //create new subtree from rows
      treeZero = createOutputLookSubtree(bdd, truthTable, rowsZero, newPorts)
    }
    if (numberTrue == 1) {
      //create leaf from rows
      treeOne = createLeafFromRows(bdd, rowsOne)
    } else {
      //create new subtree from rows
      treeOne = createOutputLookSubtree(bdd, truthTable, rowsOne, newPorts)
    }

    treeOne.addOwnerSubtreeForOne(subtree)
    subtree.setTreeForOne(treeOne)
    //connect to rows
    connectTargetElementWithSourceElementes(treeOne, rowsOne.asInstanceOf[Set[PlayerSync]])

    treeZero.addOwnerSubtreeForZero(subtree)
    subtree.setTreeForZero(treeZero)
    //connect to rows
    connectTargetElementWithSourceElementes(treeZero, rowsZero.asInstanceOf[Set[PlayerSync]])

    subtree
  }

  def createLeafFromRows(bdd: sync.bddg.BDD, rows: Set[sync.tt.Row]): sync.bddg.Leaf = {
    val mapping = rows.head.getCells().filter(c => c.getPort().isInstanceOf[sync.tt.OutputPort]).map(c => s"${c.getPort().getName()} ${c.getValue()}")

    val mapped = leafNodes.get(mapping)
    var leaf: sync.bddg.Leaf = null

    if (!mapped.isEmpty) {
      leaf = mapped.get
    } else {
      leaf = new sync.bddg.Leaf(Set.empty, Set.empty, Set.empty, bdd)
      bdd.addTrees(leaf)
      leafNodes += (mapping -> leaf)

      rows.head.getCells().filter(c => c.getPort().isInstanceOf[sync.tt.OutputPort]).foreach(cellout => {
        //Create new assignment and search all cells for it
        val assignment = new sync.bddg.Assignment(cellout.getValue(), null, null)
        assignment.setOwner(leaf)
        leaf.addAssignments(assignment)

        val ttport: PlayerSync = +(cellout.getPort()) getRelatedClassFromName ("OutputPort")
        if (ttport != null) {
          val o_port = ttport.asInstanceOf[sync.bddg.OutputPort]
          o_port.addAssignments(assignment)
          assignment.setPort(o_port)
        }
      })
    }

    //connect assignments to cells
    leaf.getAssignments().foreach(a => {
      val cellList = rows.map(_.getCells().filter(_.getPort().getName() == a.getPort().getName()).head)

      //connect them
      connectTargetElementWithSourceElementes(a, cellList.asInstanceOf[Set[PlayerSync]])
    })
    leaf
  }

  class OutputPortConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): PlayerSync = {
      //Step 1: Get construction values
      val name: String = +this getName ()

      //Step 2: Create the object in the other models
      val oD_port = new sync.bddg.OutputPort(Set.empty, name, null)
      val oT_port = new sync.bdd.OutputPort(Set.empty, name, null)

      //Step 3: Make Connection
      connectTargetElementWithSourceElemente(oD_port, comp)
      connectTargetElementWithSourceElemente(oT_port, comp)
      connectTargetElementWithSourceElemente(oD_port, oT_port)

      oD_port
    }
  }

  class InputPortConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): PlayerSync = {
      //Step 1: Get construction values
      val name: String = +this getName ()

      //Step 2: Create the object in the other models
      val iD_port = new sync.bddg.InputPort(Set.empty, name, null)
      val iT_port = new sync.bdd.InputPort(Set.empty, name, null)

      //Step 3: Make Connection
      connectTargetElementWithSourceElemente(iD_port, comp)
      connectTargetElementWithSourceElemente(iT_port, comp)
      connectTargetElementWithSourceElemente(iD_port, iT_port)

      iD_port
    }
  }

  class TruthTableConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): PlayerSync = {
      //Step 1: Get construction values
      val name: String = +this getName ()
      val ports: Set[sync.tt.Port] = +this getPorts ()

      //Step 2: Create the object in the other models
      val bdd = new sync.bddg.BDD(name, Set.empty, null, Set.empty)
      val bdt = new sync.bdd.BDD(name, null, Set.empty)

      //Step 3: Make Connection
      connectTargetElementWithSourceElemente(bdt, comp)
      connectTargetElementWithSourceElemente(bdd, comp)
      connectTargetElementWithSourceElemente(bdd, bdt)

      ports.foreach(p => {
        val integrator = getNextIntegratorForClassName(p)
        val manager: IRoleManager = +p getManager ()
        if (manager != null) {
          manager play integrator
          val obj = integrator.integrate(p).asInstanceOf[sync.bddg.Port]
          integrator.remove()
          obj.setOwner(bdd)
          bdd.addPorts(obj)
        }
      })

      createdBdds += bdd

      bdd
    }
  }

}