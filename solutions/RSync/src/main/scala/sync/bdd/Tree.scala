package sync.bdd

import org.rosi_project.model_management.core.PlayerSync

abstract class Tree(protected var ownerSubtreeForOne: Subtree, protected var ownerSubtreeForZero: Subtree, protected var ownerBDD: BDD) extends PlayerSync {

  def getOwnerSubtreeForOne(): Subtree = {
    ownerSubtreeForOne
  }

  def setOwnerSubtreeForOne(o: Subtree): Unit = {
    ownerSubtreeForOne = o
    +this syncSetOwnerSubtreeForOne ()
  }

  def getOwnerSubtreeForZero(): Subtree = {
    ownerSubtreeForZero
  }

  def setOwnerSubtreeForZero(o: Subtree): Unit = {
    ownerSubtreeForZero = o
    +this syncSetOwnerSubtreeForZero ()
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



    