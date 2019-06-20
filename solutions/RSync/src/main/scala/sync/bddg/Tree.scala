package sync.bddg

import org.rosi_project.model_management.core.PlayerSync

abstract class Tree(protected var ownerSubtreeForOne: Set[Subtree], protected var ownerSubtreeForZero: Set[Subtree], protected var ownerBDD: BDD) extends PlayerSync {

  def getOwnerSubtreeForOne(): Set[Subtree] = {
    ownerSubtreeForOne
  }

  def addOwnerSubtreeForOne(o: Subtree): Unit = {
    ownerSubtreeForOne += o
    +this addOwnerSubtreeForOne ()
  }

  def removeOwnerSubtreeForOne(o: Subtree): Unit = {
    ownerSubtreeForOne -= o
    +this removeOwnerSubtreeForOne ()
  }

  def getOwnerSubtreeForZero(): Set[Subtree] = {
    ownerSubtreeForZero
  }

  def addOwnerSubtreeForZero(o: Subtree): Unit = {
    ownerSubtreeForZero += o
    +this addOwnerSubtreeForZero ()
  }

  def removeOwnerSubtreeForZero(o: Subtree): Unit = {
    ownerSubtreeForZero -= o
    +this removeOwnerSubtreeForZero ()
  }

  def getOwnerBDD(): BDD = {
    ownerBDD
  }

  def setOwnerBDD(o: BDD): Unit = {
    ownerBDD = o
    +this setOwnerBDD ()
  }

  override def toString(): String = {
    "Tree:"
  }
  
  def getAvgPath(): Double
  
  def getMinPath(): Int
  
  def getMaxPath(): Int

}



    