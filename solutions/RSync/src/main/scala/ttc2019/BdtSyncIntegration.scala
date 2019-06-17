package ttc2019

import org.rosi_project.model_management.core.PlayerSync
import org.rosi_project.model_management.core.SynchronizationCompartment
import org.rosi_project.model_management.sync.IIntegrationCompartment
import org.rosi_project.model_management.sync.roles.IIntegrator
import org.rosi_project.model_management.sync.roles.IRoleManager

import org.rosi_project.model_management.sync.helper.IntegrationContainer
import scala.collection.mutable.ListBuffer
import org.rosi_project.model_management.core.ModelElementLists

object BdtSyncIntegration extends IIntegrationCompartment {

  private var leafNodes: Map[Set[String], sync.bdd.Leaf] = Map.empty
  private var createdBdds: Set[sync.bdd.BDD] = Set.empty

  def printManager(): Unit = {
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
    //println("FINISH FUNCTION")
    var ttNode: sync.tt.TruthTable = null

    createdBdds.foreach(bddNode => {
      val oppBDD: PlayerSync = +bddNode getRelatedClassFromName ("TruthTable")
      if (oppBDD != null) {
        ttNode = oppBDD.asInstanceOf[sync.tt.TruthTable]
      }
      val rows = ttNode.getRows()

      //val tree = createInputLookSubtree(ttNode, rows, Set.empty)
      val tree = createOutputLookSubtree(bddNode, ttNode, rows, Set.empty)
      tree.setOwnerBDD(bddNode)
      bddNode.setTree(tree)
      connectTargetElementWithSourceElementes(tree, rows.asInstanceOf[Set[PlayerSync]])
    })
    
    //printManager()
    //println("END FINISH FUNCTION")
  }

  def createOutputLookSubtree(bdd: sync.bdd.BDD, truthTable: sync.tt.TruthTable, rows: Set[sync.tt.Row], finishPorts: Set[sync.tt.Port]): sync.bdd.Tree = {
    var numberTrue = -1
    var numberFalse = -1
    var max = 0
    var portTT: sync.tt.Port = null
    var cellis: Set[sync.tt.Cell] = Set.empty

    truthTable.getPorts().filter(p => p.isInstanceOf[sync.tt.InputPort] && !finishPorts.contains(p)).foreach(ttip => {
      val newCells = ttip.getCells().filter(c => rows.contains(c.getOwner()))
      //println("Looking Port: " + ttip.getName() + " S: " + newCells.size)
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
        //println("############################## " + newCells.size + " || T: " + p1 + " F: " + p2)
      }
    })

    //println("Used Port: " + portTT)
    var portBDD: sync.bdd.InputPort = null
    val oppo: PlayerSync = +portTT getRelatedClassFromName ("InputPort")
    if (oppo != null) {
      portBDD = oppo.asInstanceOf[sync.bdd.InputPort]
    }

    val newPorts = finishPorts + portTT
    val subtree = new sync.bdd.Subtree(null, null, portBDD, null, null, null)
    portBDD.addSubtrees(subtree)

    val rowsOne = cellis.filter(_.getValue()).map(_.getOwner())
    val rowsZero = cellis.filter(!_.getValue()).map(_.getOwner())
    //println("Rows (1) " + rowsOne.size + " (2) " + rowsZero.size)

    var treeZero: sync.bdd.Tree = null
    var treeOne: sync.bdd.Tree = null
    if (numberFalse == 1) {
      //create leaf from rows
      treeZero = createLeafFromRows(rowsZero)
    } else {
      //create new subtree from rows
      treeZero = createOutputLookSubtree(bdd, truthTable, rowsZero, newPorts)
    }
    if (numberTrue == 1) {
      //create leaf from rows
      treeOne = createLeafFromRows(rowsOne)
    } else {
      //create new subtree from rows
      treeOne = createOutputLookSubtree(bdd, truthTable, rowsOne, newPorts)
    }

    treeOne.setOwnerSubtreeForOne(subtree)
    subtree.setTreeForOne(treeOne)
    //connect to rows
    connectTargetElementWithSourceElementes(treeOne, rowsOne.asInstanceOf[Set[PlayerSync]])

    treeZero.setOwnerSubtreeForZero(subtree)
    subtree.setTreeForZero(treeZero)
    //connect to rows
    connectTargetElementWithSourceElementes(treeZero, rowsZero.asInstanceOf[Set[PlayerSync]])

    subtree
  }

  def createInputLookSubtree(bdd: sync.bdd.BDD, truthTable: sync.tt.TruthTable, rows: Set[sync.tt.Row], finishPorts: Set[sync.tt.Port]): sync.bdd.Tree = {
    var max = 0
    var portTT: sync.tt.Port = null
    var cellis: Set[sync.tt.Cell] = Set.empty

    truthTable.getPorts().filter(p => p.isInstanceOf[sync.tt.InputPort] && !finishPorts.contains(p)).foreach(ttip => {
      val newCells = ttip.getCells().filter(c => rows.contains(c.getOwner()))
      if (newCells.size > max) {
        max = newCells.size
        portTT = ttip
        cellis = newCells
      } else if (newCells.size == max) {
        val p1 = newCells.count(_.getValue())
        val p2 = newCells.count(!_.getValue())
        if (Math.abs(max / 2 - p1) > Math.abs(max / 2 - p2)) {
          portTT = ttip
          cellis = newCells
        }
      }
    })
    //println("Used Port: " + portTT)
    var portBDD: sync.bdd.InputPort = null
    val oppo: PlayerSync = +portTT getRelatedClassFromName ("InputPort")
    if (oppo != null) {
      portBDD = oppo.asInstanceOf[sync.bdd.InputPort]
    }

    val newPorts = finishPorts + portTT
    val subtree = new sync.bdd.Subtree(null, null, portBDD, null, null, null)
    portBDD.addSubtrees(subtree)

    val rowsOne = cellis.filter(_.getValue()).map(_.getOwner())
    val rowsZero = cellis.filter(!_.getValue()).map(_.getOwner())
    //println("Rows (1) " + rowsOne.size + " (2) " + rowsZero.size)

    var treeZero: sync.bdd.Tree = null
    var treeOne: sync.bdd.Tree = null
    if (rowsZero.size == 1) {
      //create leaf from rows
      treeZero = createLeafFromRows(rowsZero)
    } else {
      treeZero = createInputLookSubtree(bdd, truthTable, rowsZero, newPorts)
    }
    if (rowsOne.size == 1) {
      //create leaf from rows
      treeOne = createLeafFromRows(rowsOne)
    } else {
      treeOne = createInputLookSubtree(bdd, truthTable, rowsOne, newPorts)
    }
    //TODO: Assignment what to do for more than one output cell

    treeOne.setOwnerSubtreeForOne(subtree)
    subtree.setTreeForOne(treeOne)
    //connect to rows
    connectTargetElementWithSourceElementes(treeOne, rowsOne.asInstanceOf[Set[PlayerSync]])

    treeZero.setOwnerSubtreeForZero(subtree)
    subtree.setTreeForZero(treeZero)
    //connect to rows
    connectTargetElementWithSourceElementes(treeZero, rowsZero.asInstanceOf[Set[PlayerSync]])

    subtree
  }

  def createLeafFromRows(rows: Set[sync.tt.Row]): sync.bdd.Leaf = {
    val leaf = new sync.bdd.Leaf(Set.empty, null, null, null)

    rows.head.getCells().filter(c => c.getPort().isInstanceOf[sync.tt.OutputPort]).foreach(cellout => {

      //Create new assignment and search all cells for it
      val assignment = new sync.bdd.Assignment(cellout.getValue(), null, null)
      val cellList = rows.map(_.getCells().filter(_.getPort() == cellout.getPort()).head)
      assignment.setOwner(leaf)
      leaf.addAssignments(assignment)

      val ttport: PlayerSync = +(cellout.getPort()) getRelatedClassFromName ("OutputPort")
      if (ttport != null) {
        val o_port = ttport.asInstanceOf[sync.bdd.OutputPort]
        o_port.addAssignments(assignment)
        assignment.setPort(o_port)
      }

      //connect them
      connectTargetElementWithSourceElementes(assignment, cellList.asInstanceOf[Set[PlayerSync]])
    })
    leaf
  }

  class OutputPortConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): PlayerSync = {
      //Step 1: Get construction values
      val name: String = +this getName ()

      //Step 2: Create the object in the other models
      val o_port = new sync.bdd.OutputPort(Set.empty, name, null)

      //Step 3: Make Connection
      connectTargetElementWithSourceElemente(o_port, comp)

      o_port
    }
  }

  class InputPortConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): PlayerSync = {
      //Step 1: Get construction values
      val name: String = +this getName ()

      //Step 2: Create the object in the other models
      val i_port = new sync.bdd.InputPort(Set.empty, name, null)

      //Step 3: Make Connection
      connectTargetElementWithSourceElemente(i_port, comp)

      i_port
    }
  }

  class TruthTableConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): PlayerSync = {
      //Step 1: Get construction values
      val name: String = +this getName ()
      val ports: Set[sync.tt.Port] = +this getPorts ()

      //Step 2: Create the object in the other models
      val bdt = new sync.bdd.BDD(name, null, Set.empty)

      //Step 3: Make Connection
      connectTargetElementWithSourceElemente(bdt, comp)

      ports.foreach(p => {
        val integrator = getNextIntegratorForClassName(p)
        val manager: IRoleManager = +p getManager ()
        if (manager != null) {
          manager play integrator
          val obj = integrator.integrate(p).asInstanceOf[sync.bdd.Port]
          integrator.remove()
          obj.setOwner(bdt)
          bdt.addPorts(obj)
        }
      })

      createdBdds += bdt

      bdt
    }
  }

}