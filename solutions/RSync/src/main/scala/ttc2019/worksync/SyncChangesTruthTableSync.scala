package ttc2019.worksync

import org.rosi_project.model_management.sync.ISyncCompartment
import org.rosi_project.model_management.sync.roles.ISyncRole
import org.rosi_project.model_management.core.PlayerSync
import org.rosi_project.model_management.sync.roles.IRoleManager

/**
 * Synchronization compartment between truth table and BDD to react on changing (adding or removing) a row.
 */
class SyncChangesTruthTableSync() extends ISyncCompartment {

  private var leafNodes: Map[Set[String], sync.bddg.Leaf] = Map.empty

  def getFirstRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sync.tt.TruthTable])
      return new Sync()
    return null
  }

  def isFirstIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sync.tt.TruthTable])
      return true
    return false
  }

  def getNewInstance(): ISyncCompartment = new SyncChangesTruthTableSync

  def getRuleName(): String = "SyncChangesTruthTableSync"

  class Sync() extends ISyncRole {

    def getOuterCompartment(): ISyncCompartment = SyncChangesTruthTableSync.this

    /**
     * Rule which add ports to its root in each model.
     */
    def syncAddPorts(port: PlayerSync): Unit = {
      if (!doSync) {
        doSync = true;
        //get connected bdds & tt & ports
        //add the ports to the bdds or tts
        val opTreeBDD: PlayerSync = +this getRelatedClassFromName ("sync.bdd.BDD")
        val opTreePort: PlayerSync = +port getRelatedClassFromName ("sync.bdd.Port")
        if (opTreeBDD != null && opTreePort != null) {
          val reTreeBdd = opTreeBDD.asInstanceOf[sync.bdd.BDD]
          val reTreePort = opTreePort.asInstanceOf[sync.bdd.Port]
          reTreeBdd.addPorts(reTreePort)
          reTreePort.setOwner(reTreeBdd)
        }

        val opDiaBDD: PlayerSync = +this getRelatedClassFromName ("sync.bddg.BDD")
        val opDiaPort: PlayerSync = +port getRelatedClassFromName ("sync.bddg.Port")
        if (opDiaBDD != null && opDiaPort != null) {
          val reDiaBdd = opDiaBDD.asInstanceOf[sync.bddg.BDD]
          val reDiaPort = opDiaPort.asInstanceOf[sync.bddg.Port]
          reDiaBdd.addPorts(reDiaPort)
          reDiaPort.setOwner(reDiaBdd)
        }

        val opTtBDD: PlayerSync = +this getRelatedClassFromName ("sync.tt.TruthTable")
        val opTtPort: PlayerSync = +port getRelatedClassFromName ("sync.tt.Port")
        if (opTtBDD != null && opTtPort != null) {
          val reTtBdd = opTtBDD.asInstanceOf[sync.tt.TruthTable]
          val reTtPort = opTtPort.asInstanceOf[sync.tt.Port]
          reTtBdd.addPorts(reTtPort)
          reTtPort.setOwner(reTtBdd)
        }

        doSync = false;
      }
    }

    /**
     * Removes a row from the truth table and from the other the leafs and subtrees.
     */
    def syncRemoveRows(rowPS: PlayerSync): Unit = {
      val row = rowPS.asInstanceOf[sync.tt.Row]
      if (!doSync) {
        doSync = true
        val opTreLeaf: PlayerSync = +row getRelatedClassFromName ("sync.bdd.Leaf")
        val opDiaLeaf: PlayerSync = +row getRelatedClassFromName ("sync.bddg.Leaf")

        if (opTreLeaf != null) {
          //exists connected BDT
          val reTreLeaf = opTreLeaf.asInstanceOf[sync.bdd.Leaf]
          var relManagerLeaf: Set[IRoleManager] = +reTreLeaf getRelatedManager ()
          //println(relManagerLeaf)

          //do something if there is only one related manager than minimize the tree 
          if (relManagerLeaf.size == 1) {
            if (reTreLeaf.getOwnerBDD() != null) {
              reTreLeaf.getOwnerBDD().setTree(null)
            } else {
              var parent: sync.bdd.Subtree = null
              var otherChild: sync.bdd.Tree = null
              if (reTreLeaf.getOwnerSubtreeForOne() == null) {
                parent = reTreLeaf.getOwnerSubtreeForZero()
                otherChild = parent.getTreeForOne()
              } else {
                parent = reTreLeaf.getOwnerSubtreeForOne()
                otherChild = parent.getTreeForZero()
              }
              if (parent.getOwnerBDD() != null) {
                parent.getOwnerBDD().setTree(otherChild)
                otherChild.setOwnerBDD(parent.getOwnerBDD())
                otherChild.setOwnerSubtreeForOne(null)
                otherChild.setOwnerSubtreeForZero(null)
              } else {
                if (parent.getOwnerSubtreeForOne() != null) {
                  parent.getOwnerSubtreeForOne().setTreeForOne(otherChild)
                  otherChild.setOwnerSubtreeForOne(parent.getOwnerSubtreeForOne())
                  otherChild.setOwnerSubtreeForZero(null)
                } else {
                  parent.getOwnerSubtreeForZero().setTreeForZero(otherChild)
                  otherChild.setOwnerSubtreeForZero(parent.getOwnerSubtreeForZero())
                  otherChild.setOwnerSubtreeForOne(null)
                }
              }
              //delete parent from synchro
              parent.deleteObjectFromSynchro()
            }

            //delete leaf
            reTreLeaf.deleteObjectFromSynchro()
            reTreLeaf.getAssignments().foreach(a => {
              a.deleteObjectFromSynchro()
            })

          }
          row.deleteObjectFromSynchro()
          row.setOwner(null)
          row.getCells().foreach(c => {
            c.getPort().removeCells(c)
            c.setPort(null)
            c.setOwner(null)
            c.deleteObjectFromSynchro()
          })
          //TODO: row remove all cells
        }
        doSync = false;
      }
    }

    /**
     * Rule which add rows to the truth table and create subtrees and leafs in other models.
     */
    def syncAddRows(rowPS: PlayerSync): Unit = {
      //println("In Sync Add Row")
      val row = rowPS.asInstanceOf[sync.tt.Row]
      if (!doSync) {
        doSync = true;
        val opTreeBDD: PlayerSync = +this getRelatedClassFromName ("sync.bdd.BDD")
        val opDiaBDD: PlayerSync = +this getRelatedClassFromName ("sync.bddg.BDD")
        if (opTreeBDD != null) {
          val reBdd = opTreeBDD.asInstanceOf[sync.bdd.BDD]
          addNewRowCompleteTree(reBdd, row)
          //addNewRow(reTreeBdd.getTree(), Set.empty, row)
        }
        if (opDiaBDD != null) {
          val reBdd = opDiaBDD.asInstanceOf[sync.bddg.BDD]
          addNewRowCompleteTree(reBdd, row)
        }
        doSync = false;
      }
    }

    /**
     * Function to add a new row for the sync.bddg model.
     */
    private def addNewRowCompleteTree(bdd: sync.bddg.BDD, row: sync.tt.Row): Unit = {
      var portList: Set[sync.tt.Port] = Set.empty
      var oldValue = false
      var lastSubtree: sync.bddg.Subtree = null
      var newTree: sync.bddg.Tree = bdd.getRoot()

      //iterate over tree to find next empty subtree
      while (newTree != null) {
        if (newTree.isInstanceOf[sync.bddg.Subtree]) {
          lastSubtree = newTree.asInstanceOf[sync.bddg.Subtree]
          row.getCells().foreach(c => {
            if (c.getPort().getName() == lastSubtree.getPort().getName()) {
              //goto next subtree
              if (c.getValue()) {
                newTree = lastSubtree.getTreeForOne()
              } else {
                newTree = lastSubtree.getTreeForZero()
              }
              portList += c.getPort()
              oldValue = c.getValue()
              //trace link
              +row makePlayerSyncRelated (lastSubtree)
            }
          })
        }
      }

      //add subtrees for all missing cells with input ports
      row.getCells().filter(c => !portList.contains(c.getPort()) && c.getPort().isInstanceOf[sync.tt.InputPort]).foreach(c => {
        val opTreePort: PlayerSync = +(c.getPort()) getRelatedClassFromName ("sync.bddg.InputPort")
        if (opTreePort != null) {
          val inputPort = opTreePort.asInstanceOf[sync.bddg.InputPort]
          var subtree = new sync.bddg.Subtree(null, null, inputPort, Set.empty, Set.empty, null)
          bdd.addTrees(subtree)
          subtree.setOwnerBDD(bdd)
          if (lastSubtree == null) {
            bdd.setRoot(subtree)
          } else {
            if (oldValue) {
              lastSubtree.setTreeForOne(subtree)
              subtree.addOwnerSubtreeForOne(lastSubtree)
            } else {
              lastSubtree.setTreeForZero(subtree)
              subtree.addOwnerSubtreeForZero(lastSubtree)
            }
          }
          oldValue = c.getValue()
          lastSubtree = subtree
          //trace link
          +row makePlayerSyncRelated (lastSubtree)
        }
      })

      //Create new assignment and search all cells for it
      val mapping = row.getCells().filter(c => c.getPort().isInstanceOf[sync.tt.OutputPort]).map(c => s"${c.getPort().getName()} ${c.getValue()}")

      val mapped = leafNodes.get(mapping)
      var leaf: sync.bddg.Leaf = null

      if (!mapped.isEmpty) {
        leaf = mapped.get
      } else {
        leaf = new sync.bddg.Leaf(Set.empty, Set.empty, Set.empty, bdd)
        bdd.addTrees(leaf)
        leafNodes += (mapping -> leaf)

        row.getCells().filter(c => c.getPort().isInstanceOf[sync.tt.OutputPort]).foreach(cellout => {
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
        val cell = row.getCells().filter(_.getPort().getName() == a.getPort().getName()).head

        //trace link
        +cell makePlayerSyncRelated (a)
      })

      if (oldValue) {
        lastSubtree.setTreeForOne(leaf)
        leaf.addOwnerSubtreeForOne(lastSubtree)
      } else {
        lastSubtree.setTreeForZero(leaf)
        leaf.addOwnerSubtreeForZero(lastSubtree)
      }
      //trace link
      +row makePlayerSyncRelated (leaf)

      //+row printAllManager ()
    }

    /**
     * Function to add a new row for the sync.bdd model.
     */
    private def addNewRowCompleteTree(bdd: sync.bdd.BDD, row: sync.tt.Row): Unit = {
      var portList: Set[sync.tt.Port] = Set.empty
      var oldValue = false
      var lastSubtree: sync.bdd.Subtree = null
      var newTree: sync.bdd.Tree = bdd.getTree()

      //iterate over tree to find next empty subtree
      while (newTree != null) {
        if (newTree.isInstanceOf[sync.bdd.Subtree]) {
          lastSubtree = newTree.asInstanceOf[sync.bdd.Subtree]
          row.getCells().foreach(c => {
            if (c.getPort().getName() == lastSubtree.getPort().getName()) {
              //goto next subtree
              if (c.getValue()) {
                newTree = lastSubtree.getTreeForOne()
              } else {
                newTree = lastSubtree.getTreeForZero()
              }
              portList += c.getPort()
              oldValue = c.getValue()
              //trace link
              +row makePlayerSyncRelated (lastSubtree)
            }
          })
        }
      }

      //add subtrees for all missing cells with input ports
      row.getCells().filter(c => !portList.contains(c.getPort()) && c.getPort().isInstanceOf[sync.tt.InputPort]).foreach(c => {
        val opTreePort: PlayerSync = +(c.getPort()) getRelatedClassFromName ("sync.bdd.InputPort")
        if (opTreePort != null) {
          val inputPort = opTreePort.asInstanceOf[sync.bdd.InputPort]
          var subtree = new sync.bdd.Subtree(null, null, inputPort, null, null, null)
          if (lastSubtree == null) {
            bdd.setTree(subtree)
            subtree.setOwnerBDD(bdd)
          } else {
            if (oldValue) {
              lastSubtree.setTreeForOne(subtree)
              subtree.setOwnerSubtreeForOne(lastSubtree)
            } else {
              lastSubtree.setTreeForZero(subtree)
              subtree.setOwnerSubtreeForZero(lastSubtree)
            }
          }
          oldValue = c.getValue()
          lastSubtree = subtree
          //trace link
          +row makePlayerSyncRelated (lastSubtree)
        }
      })

      //add leaf for each row with cells with output ports
      val leaf = new sync.bdd.Leaf(Set.empty, null, null, null)
      row.getCells().filter(c => c.getPort().isInstanceOf[sync.tt.OutputPort]).foreach(cellout => {
        //Search connected assignement from cell
        val oppAssign: PlayerSync = +cellout getRelatedClassFromName ("sync.bdd.Assignment")
        if (oppAssign != null) {
          val assignment = oppAssign.asInstanceOf[sync.bdd.Assignment]
          assignment.setOwner(leaf)
          leaf.addAssignments(assignment)
        }

        /*//Create new assignment and search all cells for it
        val assignment = new sync.bdd.Assignment(cellout.getValue(), null, null)
        assignment.setOwner(leaf)
        leaf.addAssignments(assignment)

        val ttport: PlayerSync = +(cellout.getPort()) getRelatedClassFromName ("sync.bdd.OutputPort")
        if (ttport != null) {
          val o_port = ttport.asInstanceOf[sync.bdd.OutputPort]
          o_port.addAssignments(assignment)
          assignment.setPort(o_port)
        }
        //trace link
        +cellout makePlayerSyncRelated(assignment) 
        
        +cellout printAllManager()*/
      })

      if (oldValue) {
        lastSubtree.setTreeForOne(leaf)
        leaf.setOwnerSubtreeForOne(lastSubtree)
      } else {
        lastSubtree.setTreeForZero(leaf)
        leaf.setOwnerSubtreeForZero(lastSubtree)
      }
      //trace link
      +row makePlayerSyncRelated (leaf)
    }

    /**
     * Old Method to complex because of special tree structure.
     */
    private def addNewRow(tree: sync.bdd.Tree, portList: Set[sync.tt.Port], row: sync.tt.Row): Unit = {
      if (tree.isInstanceOf[sync.bdd.Leaf]) {
        val oldLeaf = tree.asInstanceOf[sync.bdd.Leaf]
        var cellValue: Set[String] = Set.empty
        //proof if assignments are correct to the ones of the new row
        row.getCells().filter(_.getPort().isInstanceOf[sync.tt.OutputPort]).foreach(c => {
          cellValue += s"${c.getPort().getName()} ${c.getValue()}"
        })
        val firstCount = cellValue.size
        oldLeaf.getAssignments().foreach(a => {
          cellValue += s"${a.getPort().getName()} ${a.getValue()}"
        })
        if (firstCount < cellValue.size) {
          //you must split and add new stuff 
          //TODO problem with more than one connected row
          val opRowTT: PlayerSync = +this getRelatedClassFromName ("sync.tt.Row")
          if (opRowTT != null) {
            val reRowTT = opRowTT.asInstanceOf[sync.tt.Row]
            //compare opponent row with this row
            reRowTT.getCells().filter(c => !portList.contains(c.getPort())).foreach(cO => {
              row.getCells().filter(_.getPort() == cO.getPort()).foreach(cN => {
                if (cN.getValue() != cO.getValue()) {
                  //use this port for new one and create now all stuff
                  val newLeaf = new sync.bdd.Leaf(Set.empty, null, null, null)

                  row.getCells().filter(cl => cl.getPort().isInstanceOf[sync.tt.OutputPort]).foreach(cellout => {
                    //Create new assignment and search all cells for it
                    val assignment = new sync.bdd.Assignment(cellout.getValue(), null, null)
                    assignment.setOwner(newLeaf)
                    newLeaf.addAssignments(assignment)

                    val ttport: PlayerSync = +(cellout.getPort()) getRelatedClassFromName ("OutputPort")
                    if (ttport != null) {
                      val o_port = ttport.asInstanceOf[sync.bdd.OutputPort]
                      o_port.addAssignments(assignment)
                      assignment.setPort(o_port)
                    }

                    //trace link
                    +cellout makePlayerSyncRelated (assignment)
                  })

                  val bdtInputPort: PlayerSync = +(cO.getPort()) getRelatedClassFromName ("InputPort")
                  if (bdtInputPort != null) {
                    val i_port = bdtInputPort.asInstanceOf[sync.bdd.InputPort]
                    var subtree: sync.bdd.Subtree = null
                    if (oldLeaf.getOwnerSubtreeForOne() != null) {
                      subtree = new sync.bdd.Subtree(null, null, i_port, oldLeaf.getOwnerSubtreeForOne(), null, null)
                      oldLeaf.getOwnerSubtreeForOne().setTreeForOne(subtree)
                    } else {
                      subtree = new sync.bdd.Subtree(null, null, i_port, null, oldLeaf.getOwnerSubtreeForZero(), null)
                      oldLeaf.getOwnerSubtreeForZero().setTreeForZero(subtree)
                    }
                    i_port.addSubtrees(subtree)
                    if (cN.getValue()) {
                      subtree.setTreeForOne(newLeaf)
                      newLeaf.setOwnerSubtreeForOne(subtree)
                      subtree.setTreeForZero(oldLeaf)
                      oldLeaf.setOwnerSubtreeForOne(null)
                      oldLeaf.setOwnerSubtreeForZero(subtree)
                    } else {
                      subtree.setTreeForOne(oldLeaf)
                      oldLeaf.setOwnerSubtreeForOne(subtree)
                      oldLeaf.setOwnerSubtreeForZero(null)
                      subtree.setTreeForZero(newLeaf)
                      newLeaf.setOwnerSubtreeForZero(subtree)
                    }
                  }
                }
              })
            })
          }
        } else {
          val rmSubtree: IRoleManager = +oldLeaf getManager ()
          val rmRow: IRoleManager = +row getManager ()
          if (rmSubtree != null && rmRow != null) {
            rmSubtree.makeRelated(rmRow)
          }
        }
      } else {
        //is instance of subtree
        val subtree = tree.asInstanceOf[sync.bdd.Subtree]
        var port: sync.tt.Port = null
        var value = false
        row.getCells().foreach(c => {
          if (c.getPort().getName() == subtree.getPort().getName()) {
            port = c.getPort()
            value = c.getValue()
          }
        })
        val rmSubtree: IRoleManager = +subtree getManager ()
        val rmRow: IRoleManager = +row getManager ()
        if (rmSubtree != null && rmRow != null) {
          rmSubtree.makeRelated(rmRow)
        }
        if (value) {
          addNewRow(subtree.getTreeForOne(), portList + port, row)
        } else {
          addNewRow(subtree.getTreeForZero(), portList + port, row)
        }
      }
    }

    /**
     * Change the names of TruthTable and BDDs.
     */
    def changeName(): Unit = {
      if (!doSync) {
        doSync = true;
        var name: String = +this getName ();
        getSyncer().foreach { a =>
          if (!a.equals(this)) {
            (+a).setName(name);
          }
        }
        doSync = false;
      }
    }
  }

}