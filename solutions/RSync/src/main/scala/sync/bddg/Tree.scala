package sync.bddg

import org.rosi_project.model_management.core.PlayerSync

abstract class Tree(protected var ownerSubtreeForOne: Set[Subtree], protected var ownerSubtreeForZero: Set[Subtree], protected var ownerBDD: BDD) extends PlayerSync {

  def getOwnerSubtreeForOne(): Set[Subtree] = {
    ownerSubtreeForOne
  }

  def addOwnerSubtreeForOne(o: Subtree): Unit = {
    require(o != null)
    require(!ownerSubtreeForOne.contains(o))
    ownerSubtreeForOne += o
    +this syncAddOwnerSubtreeForOne (o)
  }

  def removeOwnerSubtreeForOne(o: Subtree): Unit = {
    require(o != null)
    require(ownerSubtreeForOne.contains(o))
    ownerSubtreeForOne -= o
    +this syncRemoveOwnerSubtreeForOne (o)
  }

  def getOwnerSubtreeForZero(): Set[Subtree] = {
    ownerSubtreeForZero
  }

  def addOwnerSubtreeForZero(o: Subtree): Unit = {
    require(o != null)
    require(!ownerSubtreeForZero.contains(o))
    ownerSubtreeForZero += o
    +this syncAddOwnerSubtreeForZero (o)
  }

  def removeOwnerSubtreeForZero(o: Subtree): Unit = {
    require(o != null)
    require(ownerSubtreeForZero.contains(o))
    ownerSubtreeForZero -= o
    +this syncRemoveOwnerSubtreeForZero (o)
  }

  def getOwnerBDD(): BDD = {
    ownerBDD
  }

  def setOwnerBDD(o: BDD): Unit = {
    require(o != null)
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



    