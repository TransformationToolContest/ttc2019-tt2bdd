package sync.bdd

import org.rosi_project.model_management.core.PlayerSync

abstract class Tree(protected var ownerSubtreeForOne: Subtree, protected var ownerSubtreeForZero: Subtree, protected var ownerBDD: BDD) extends PlayerSync {

  def getOwnerSubtreeForOne(): Subtree = {
    ownerSubtreeForOne
  }

  def setOwnerSubtreeForOne(o: Subtree): Unit = {
    ownerSubtreeForOne = o
    +this changeOwnerSubtreeForOne ()
  }

  def getOwnerSubtreeForZero(): Subtree = {
    ownerSubtreeForZero
  }

  def setOwnerSubtreeForZero(o: Subtree): Unit = {
    ownerSubtreeForZero = o
    +this changeOwnerSubtreeForZero ()
  }

  def getOwnerBDD(): BDD = {
    ownerBDD
  }

  def setOwnerBDD(o: BDD): Unit = {
    ownerBDD = o
    +this changeOwnerBDD ()
  }
  
  def getStringDeep(): String = {
    var result = ""
    for(i <- 0 to this.getDeep()) {
      result = result + "\t"
    }
    result
  }
  
  def getDeep(): Int = {
    if (ownerBDD != null) {
      return 1
    } 
    if (ownerSubtreeForOne != null) {
      return 1 + ownerSubtreeForOne.getDeep()
    }
    if (ownerSubtreeForZero != null) {
      return 1 + ownerSubtreeForZero.getDeep()
    }
    0
  }

  override def toString(): String = {
    "Tree:"
  }

}



    