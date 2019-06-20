package sync.bdd

import org.rosi_project.model_management.core.PlayerSync

abstract class Tree(protected var ownerSubtreeForOne: Subtree, protected var ownerSubtreeForZero: Subtree, protected var ownerBDD: BDD) extends PlayerSync {

  def getOwnerSubtreeForOne(): Subtree = {
    ownerSubtreeForOne
  }

  def setOwnerSubtreeForOne(o: Subtree): Unit = {
    ownerSubtreeForOne = o
    +this setOwnerSubtreeForOne ()
  }

  def getOwnerSubtreeForZero(): Subtree = {
    ownerSubtreeForZero
  }

  def setOwnerSubtreeForZero(o: Subtree): Unit = {
    ownerSubtreeForZero = o
    +this setOwnerSubtreeForZero ()
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



    