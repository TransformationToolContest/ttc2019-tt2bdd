package sync.bddg

import org.rosi_project.model_management.core.PlayerSync

abstract class Tree(protected var ownerSubtreeForOne: Set[Subtree], protected var ownerSubtreeForZero: Set[Subtree], protected var ownerBDD: BDD) extends PlayerSync {

  def getOwnerSubtreeForOne(): Set[Subtree] = {
    ownerSubtreeForOne
  }

  def addOwnerSubtreeForOne(o: Subtree): Unit = {
    ownerSubtreeForOne += o
    +this syncAddOwnerSubtreeForOne (o)
  }

  def removeOwnerSubtreeForOne(o: Subtree): Unit = {
    ownerSubtreeForOne -= o
    +this syncRemoveOwnerSubtreeForOne (o)
  }

  def getOwnerSubtreeForZero(): Set[Subtree] = {
    ownerSubtreeForZero
  }

  def addOwnerSubtreeForZero(o: Subtree): Unit = {
    ownerSubtreeForZero += o
    +this syncAddOwnerSubtreeForZero (o)
  }

  def removeOwnerSubtreeForZero(o: Subtree): Unit = {
    ownerSubtreeForZero -= o
    +this syncRemoveOwnerSubtreeForZero (o)
  }

  def getOwnerBDD(): BDD = {
    ownerBDD
  }

  def setOwnerBDD(o: BDD): Unit = {
    ownerBDD = o
    +this syncSetOwnerBDD ()
  }

  override def toString(): String = {
    "Tree:"
  }

  def getAvgPath(): Double = 0.0

  def getMinPath(): Int = 0

  def getMaxPath(): Int = 0

}



    