package ttc2019.worksum

import org.rosi_project.model_management.core._
import org.rosi_project.model_management.sync.IIntegrationCompartment
import org.rosi_project.model_management.sync.roles.IIntegrator
import org.rosi_project.model_management.sync.roles.IRoleManager
import org.rosi_project.model_management.sum.compartments.IRelationCompartment
import org.rosi_project.model_management.sum.roles.IRelationRole

import sum.tt.TruthTable
import sum.tt.Row
import sum.tt.Cell
import sum.tt.CellPortPort
import sum.tt.RowCellsCell
import sum.tt.TruthTablePortsPort
import sum.tt.TruthTableRowsRow
import sum.bdd.BDD
import sum.bdd.Leaf
import sum.bdd.Assignment
import sum.bdd.Subtree
import sum.bdd.Tree
import sum.bdd.BDDPortsPort
import sum.bdd.AssignmentPortOutputPort
import sum.bdd.LeafAssignmentsAssignment
import sum.bdd.BDDTreeTree
import sum.bdd.SubtreePortInputPort
import sum.bdd.SubtreeTreeForOneTree
import sum.bdd.SubtreeTreeForZeroTree

object BdtSumIntegration extends IIntegrationCompartment {

  private var createdObjects: Set[PlayerSync] = Set.empty

  def getRelationalIntegratorsForClassName(classname: Object): IIntegrator = {
    if (classname.isInstanceOf[TruthTablePortsPort])
      return new TruthTablePortsPortConstruct()
    //if (classname.isInstanceOf[CellPortPort])
    //  return new CellPortPortConstruct()
    return null
  }

  def getIntegratorForClassName(classname: Object): IIntegrator = {
    if (classname.isInstanceOf[TruthTable])
      return new TruthTableConstruct()
    if (classname.isInstanceOf[sum.tt.OutputPort])
      return new OutputPortConstruct()
    if (classname.isInstanceOf[sum.tt.InputPort])
      return new InputPortConstruct()
    return null
  }

  def printPretty(indent: String, tree: Tree, number: String, last: Boolean) {
    var name = ""
    ModelElementLists.getElementsFromType("SubtreePortInputPort").filter(spi => spi.asInstanceOf[SubtreePortInputPort].getSourceIns() == tree).foreach(spi => {
      name = spi.asInstanceOf[SubtreePortInputPort].getTargetIns().getName()
    })
    if (name == "") {
      ModelElementLists.getElementsFromType("LeafAssignmentsAssignment").filter(spi => spi.asInstanceOf[LeafAssignmentsAssignment].getSourceIns() == tree).foreach(spi => {
        name += spi.asInstanceOf[LeafAssignmentsAssignment].getTargetIns().getValue() + " "
      })
    }
    var ini = indent
    print(ini);
    if (last) {
      print("\\-")
      ini += "  "
    } else {
      print("|-");
      ini += "| ";
    }
    println(number + " " + name);

    ModelElementLists.getElementsFromType("SubtreeTreeForZeroTree").filter(spi => spi.asInstanceOf[SubtreeTreeForZeroTree].getSourceIns() == tree).foreach(
      spi => printPretty(ini, spi.asInstanceOf[SubtreeTreeForZeroTree].getTargetIns(), "0", false))
    ModelElementLists.getElementsFromType("SubtreeTreeForOneTree").filter(spi => spi.asInstanceOf[SubtreeTreeForOneTree].getSourceIns() == tree).foreach(
      spi => printPretty(ini, spi.asInstanceOf[SubtreeTreeForOneTree].getTargetIns(), "1", true))
  }

  def printManager(): Unit = {
    createdObjects.foreach(obj => {
      println(obj.getClass.getName)
      //if (obj.isInstanceOf[sync.bdd.Assignment]) {
      +obj printAllManager ()
      //}
    })
  }

  def finalEditFunction(): Unit = {
    println("FINISH FUNCTION")
    var bddNode: BDD = null
    var ttNode: TruthTable = null

    createdObjects.filter(_.isInstanceOf[BDD]).foreach(b => bddNode = b.asInstanceOf[BDD])
    val oppBDD: PlayerSync = +bddNode getRelatedClassFromName ("TruthTable")
    if (oppBDD != null) {
      ttNode = oppBDD.asInstanceOf[TruthTable]
    }
    val rows = ModelElementLists.getElementsFromType("TruthTableRowsRow").filter(ttr => ttr.asInstanceOf[TruthTableRowsRow].getSourceIns() == ttNode).map(ttr => ttr.asInstanceOf[TruthTableRowsRow].getTargetIns())

    val tree = createOutputSubtree(rows.toSet, Set.empty)
    //val tree = createInputSubtree(rows.toSet, Set.empty)
    val btt = new BDDTreeTree(bddNode, tree)
    btt.initialize()
    createdObjects += btt

    printPretty("", tree, "", true)
    printManager()

    println("END FINISH FUNCTION")
  }

  def createOutputSubtree(rows: Set[Row], finishPorts: Set[sum.tt.InputPort]): Tree = {
    var numberTrue = -1
    var numberFalse = -1
    var max = 0
    var portTT: sum.tt.InputPort = null
    var portBDD: sum.bdd.InputPort = null
    var cellis: Set[Cell] = Set.empty

    createdObjects.filter(_.isInstanceOf[sum.bdd.InputPort]).foreach(bddip => {
      val oppo: PlayerSync = +bddip getRelatedClassFromName ("InputPort")
      if (oppo != null) {
        val ttip = oppo.asInstanceOf[sum.tt.InputPort]
        if (!finishPorts.contains(ttip)) {
          val cellPorts = ModelElementLists.getElementsFromType("CellPortPort").filter(cpp => cpp.asInstanceOf[CellPortPort].getTargetIns() == ttip).asInstanceOf[List[CellPortPort]]
          val rowCells = ModelElementLists.getElementsFromType("RowCellsCell").filter(cpp => rows.contains(cpp.asInstanceOf[RowCellsCell].getSourceIns())).asInstanceOf[List[RowCellsCell]]
          var newCells: Set[Cell] = Set.empty
          cellPorts.foreach(cpp => {
            rowCells.foreach(rcc => {
              if (rcc.getTargetIns() == cpp.getSourceIns()) {
                newCells += rcc.getTargetIns()
              }
            })
          })
          println("Looking Port: " + ttip.getName() + " S: " + newCells.size)
          if (newCells.size >= max) {
            var setTrue: Set[Set[String]] = Set.empty
            var setFalse: Set[Set[String]] = Set.empty
            newCells.foreach(cell => {
              if (cell.getValue()) {
                var setPortValue: Set[String] = Set.empty
                var ownerRow: Row = null
                val rowCellsN = ModelElementLists.getElementsFromType("RowCellsCell").asInstanceOf[List[RowCellsCell]]
                rowCellsN.filter(_.getTargetIns() == cell).foreach(rcc => ownerRow = rcc.getSourceIns())
                rowCellsN.filter(_.getSourceIns() == ownerRow).foreach(rcc => {
                  var cell = rcc.getTargetIns()
                  val cellPortsN = ModelElementLists.getElementsFromType("CellPortPort").asInstanceOf[List[CellPortPort]]
                  cellPortsN.filter(cpp => cpp.getSourceIns() == cell && cpp.getTargetIns().isInstanceOf[sum.tt.OutputPort]).foreach(cpp => {
                    setPortValue += s"${cpp.getTargetIns().getName()} ${cpp.getSourceIns().getValue()}"
                  })
                })
                setTrue += setPortValue
              } else {
                var setPortValue: Set[String] = Set.empty
                var ownerRow: Row = null
                val rowCellsN = ModelElementLists.getElementsFromType("RowCellsCell").asInstanceOf[List[RowCellsCell]]
                rowCellsN.filter(_.getTargetIns() == cell).foreach(rcc => ownerRow = rcc.getSourceIns())
                rowCellsN.filter(_.getSourceIns() == ownerRow).foreach(rcc => {
                  var cell = rcc.getTargetIns()
                  val cellPortsN = ModelElementLists.getElementsFromType("CellPortPort").asInstanceOf[List[CellPortPort]]
                  cellPortsN.filter(cpp => cpp.getSourceIns() == cell && cpp.getTargetIns().isInstanceOf[sum.tt.OutputPort]).foreach(cpp => {
                    setPortValue += s"${cpp.getTargetIns().getName()} ${cpp.getSourceIns().getValue()}"
                  })
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
              portBDD = bddip.asInstanceOf[sum.bdd.InputPort]
            }
            println("############################## " + newCells.size + " || T: " + p1 + " F: " + p2)
          }
        }
      }
    })
    println("Used Port: " + portTT)
    var newPorts = finishPorts + portTT
    val subtree = new Subtree()
    val spi = new SubtreePortInputPort(subtree, portBDD)
    spi.initialize()
    createdObjects += subtree
    createdObjects += spi

    val rowsOne = cellis.filter(_.getValue()).map(c => {
      var row: Row = null
      ModelElementLists.getElementsFromType("RowCellsCell").foreach(rcc => {
        if (c == rcc.asInstanceOf[RowCellsCell].getTargetIns()) {
          row = rcc.asInstanceOf[RowCellsCell].getSourceIns()
        }
      })
      row
    })
    val rowsZero = cellis.filter(!_.getValue()).map(c => {
      var row: Row = null
      ModelElementLists.getElementsFromType("RowCellsCell").foreach(rcc => {
        if (c == rcc.asInstanceOf[RowCellsCell].getTargetIns()) {
          row = rcc.asInstanceOf[RowCellsCell].getSourceIns()
        }
      })
      row
    })
    println("Rows (1) " + rowsOne.size + " (2) " + rowsZero.size)

    var treeZero: Tree = null
    var treeOne: Tree = null
    if (numberFalse == 1) {
      treeZero = createLeafFromRows(rowsZero)
    } else {
      treeZero = createOutputSubtree(rowsZero, newPorts)
    }
    if (numberTrue == 1) {
      treeOne = createLeafFromRows(rowsOne)
    } else {
      treeOne = createOutputSubtree(rowsOne, newPorts)
    }

    val stf1 = new SubtreeTreeForOneTree(subtree, treeOne)
    stf1.initialize()
    createdObjects += stf1
    //connect to rows
    connectTargetElementWithSourceElementes(treeOne, rowsOne.asInstanceOf[Set[PlayerSync]])

    val stf0 = new SubtreeTreeForZeroTree(subtree, treeZero)
    stf0.initialize()
    createdObjects += stf0
    //connect to rows
    connectTargetElementWithSourceElementes(treeZero, rowsZero.asInstanceOf[Set[PlayerSync]])

    subtree
  }

  def createInputSubtree(rows: Set[Row], finishPorts: Set[sum.tt.InputPort]): Tree = {
    var max = 0
    var portTT: sum.tt.InputPort = null
    var portBDD: sum.bdd.InputPort = null
    var cellis: Set[Cell] = Set.empty

    createdObjects.filter(_.isInstanceOf[sum.bdd.InputPort]).foreach(bddip => {
      val oppo: PlayerSync = +bddip getRelatedClassFromName ("InputPort")
      if (oppo != null) {
        val ttip = oppo.asInstanceOf[sum.tt.InputPort]
        if (!finishPorts.contains(ttip)) {
          val cellPorts = ModelElementLists.getElementsFromType("CellPortPort").filter(cpp => cpp.asInstanceOf[CellPortPort].getTargetIns() == ttip).asInstanceOf[List[CellPortPort]]
          val rowCells = ModelElementLists.getElementsFromType("RowCellsCell").filter(cpp => rows.contains(cpp.asInstanceOf[RowCellsCell].getSourceIns())).asInstanceOf[List[RowCellsCell]]
          var newCells: Set[Cell] = Set.empty
          cellPorts.foreach(cpp => {
            rowCells.foreach(rcc => {
              if (rcc.getTargetIns() == cpp.getSourceIns()) {
                newCells += rcc.getTargetIns()
              }
            })
          })
          if (newCells.size > max) {
            max = newCells.size
            cellis = newCells
            portTT = ttip
            portBDD = bddip.asInstanceOf[sum.bdd.InputPort]
          } else if (newCells.size == max) {
            val p1 = newCells.count(_.getValue())
            val p2 = newCells.count(!_.getValue())
            if (Math.abs(max / 2 - p1) > Math.abs(max / 2 - p2)) {
              cellis = newCells
              portTT = ttip
              portBDD = bddip.asInstanceOf[sum.bdd.InputPort]
            }
          }
        }
      }
    })
    println("Used Port: " + portTT)
    var newPorts = finishPorts + portTT
    val subtree = new Subtree()
    val spi = new SubtreePortInputPort(subtree, portBDD)
    spi.initialize()
    createdObjects += subtree
    createdObjects += spi

    val rowsOne = cellis.filter(_.getValue()).map(c => {
      var row: Row = null
      ModelElementLists.getElementsFromType("RowCellsCell").foreach(rcc => {
        if (c == rcc.asInstanceOf[RowCellsCell].getTargetIns()) {
          row = rcc.asInstanceOf[RowCellsCell].getSourceIns()
        }
      })
      row
    })
    val rowsZero = cellis.filter(!_.getValue()).map(c => {
      var row: Row = null
      ModelElementLists.getElementsFromType("RowCellsCell").foreach(rcc => {
        if (c == rcc.asInstanceOf[RowCellsCell].getTargetIns()) {
          row = rcc.asInstanceOf[RowCellsCell].getSourceIns()
        }
      })
      row
    })
    println("Rows (1) " + rowsOne.size + " (2) " + rowsZero.size)

    var treeZero: Tree = null
    var treeOne: Tree = null
    if (rowsZero.size == 1) {
      treeZero = createLeafFromRows(rowsZero)
    } else {
      treeZero = createInputSubtree(rowsZero, newPorts)
    }
    if (rowsOne.size == 1) {
      treeOne = createLeafFromRows(rowsOne)
    } else {
      treeOne = createInputSubtree(rowsOne, newPorts)
    }
    //TODO: Assignment what to do for more than one output cell

    val stf1 = new SubtreeTreeForOneTree(subtree, treeOne)
    stf1.initialize()
    createdObjects += stf1
    //connect to rows
    connectTargetElementWithSourceElementes(treeOne, rowsOne.asInstanceOf[Set[PlayerSync]])

    val stf0 = new SubtreeTreeForZeroTree(subtree, treeZero)
    stf0.initialize()
    createdObjects += stf0
    //connect to rows
    connectTargetElementWithSourceElementes(treeZero, rowsZero.asInstanceOf[Set[PlayerSync]])

    subtree
  }

  def createLeafFromRows(rows: Set[Row]): Leaf = {
    var leaf: Leaf = new Leaf()
    createdObjects += leaf
    //cells of output port
    val cppsOutputPort = ModelElementLists.getElementsFromType("CellPortPort").filter(cpp => cpp.asInstanceOf[CellPortPort].getTargetIns().isInstanceOf[sum.tt.OutputPort]).asInstanceOf[List[CellPortPort]]
    //TODO: Function element.getList(Type: CellPortPort) Relational compartments need function for names of references
    //TODO: get list of elements from ref name
    //cells of first row
    val rccsFirstRow = ModelElementLists.getElementsFromType("RowCellsCell").filter(cpp => rows.head == cpp.asInstanceOf[RowCellsCell].getSourceIns()).asInstanceOf[List[RowCellsCell]]
    //cells from first row
    val cellsFirstRow = rccsFirstRow.map(_.getTargetIns())
    val allOutputCells = cppsOutputPort.map(_.getSourceIns())

    cellsFirstRow.filter(allOutputCells.contains(_)).foreach(c => {
      //Create new assignment and search all cells for it
      val assignment = new Assignment(c.getValue())
      val laa = new LeafAssignmentsAssignment(leaf, assignment)
      laa.initialize()
      createdObjects += assignment
      createdObjects += laa

      //get port from cell
      val oport = cppsOutputPort.filter(_.getSourceIns() == c).map(_.getTargetIns()).head
      var cellList: Set[Cell] = Set.empty

      rows.foreach(r => {
        val cellsThisRow = ModelElementLists.getElementsFromType("RowCellsCell").filter(cpp => r == cpp.asInstanceOf[RowCellsCell].getSourceIns()).map(_.asInstanceOf[RowCellsCell].getTargetIns())
        cellList += cppsOutputPort.filter(cpp => cpp.getTargetIns() == oport && cellsThisRow.contains(cpp.getSourceIns())).map(_.getSourceIns()).head
      })

      val ttport: PlayerSync = +oport getRelatedClassFromName ("OutputPort")
      if (ttport != null) {
        val o_port = ttport.asInstanceOf[sum.bdd.OutputPort]
        val apo = new AssignmentPortOutputPort(assignment, o_port)
        apo.initialize()
        createdObjects += apo
      }

      //connect them
      connectTargetElementWithSourceElementes(assignment, cellList.asInstanceOf[Set[PlayerSync]])

    })
    leaf
  }

  //---------------------------------------------------------------------------- Roles --------------------------------------------------------------------------------

  class OutputPortConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): PlayerSync = {
      println("OutputPort Integration " + comp);

      //Step 1: Get construction values
      val name: String = +this getName ()

      //Step 2: Create the object in the other models
      val o_port = new sum.bdd.OutputPort(name)

      //Step 3: Make Connection
      connectTargetElementWithSourceElemente(o_port, comp)

      createdObjects += o_port

      o_port
    }
  }

  class InputPortConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): PlayerSync = {
      println("InputPort Integration " + comp);

      //Step 1: Get construction values
      val name: String = +this getName ()

      //Step 2: Create the object in the other models
      val i_port = new sum.bdd.InputPort(name)

      //Step 3: Make Connection
      connectTargetElementWithSourceElemente(i_port, comp)

      createdObjects += i_port

      i_port
    }
  }

  class TruthTableConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): PlayerSync = {
      println("TruthTable Integration " + comp);

      //Step 1: Get construction values
      val name: String = +this getName ()

      //Step 2: Create the object in the other models
      val bdd = new BDD(name)

      //Step 3: Make Connection
      connectTargetElementWithSourceElemente(bdd, comp)

      createdObjects += bdd

      bdd
    }
  }

  //---------------------------------------------------------------------------- Relational Roles --------------------------------------------------------------------------------

  class TruthTablePortsPortConstruct() extends IIntegrator {

    def integrate(comp: PlayerSync): PlayerSync = {
      println("TruthTablePortsPort Integration " + comp);

      //Step 1: Get construction values
      val source: IRelationRole = +this getSource ()
      val target: IRelationRole = +this getTarget ()

      val oppSource: PlayerSync = +source getRelatedClassFromName ("BDD")
      val oppTarget: PlayerSync = +target getRelatedClassFromName ("Port")

      if (oppSource != null && oppTarget != null) {
        val s = oppSource.asInstanceOf[BDD]
        val t = oppTarget.asInstanceOf[sum.bdd.Port]

        //Step 2: Create the object in the other models
        val rel = new BDDPortsPort(s, t)
        rel.initialize()

        //Step 3: Make Connection
        connectTargetElementWithSourceElemente(rel, comp)

        createdObjects += rel

        return rel
      }
      null
    }
  }

}