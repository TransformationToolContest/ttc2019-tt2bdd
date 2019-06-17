package sync.bddg

import org.rosi_project.model_management.core.PlayerSync

abstract class Tree(protected var ownerSubtreeForOne: Set[Subtree], protected var ownerSubtreeForZero: Set[Subtree], protected var ownerBDD: BDD) extends PlayerSync {

  def getOwnerSubtreeForOne(): Set[Subtree] = {
    ownerSubtreeForOne
  }

  def setOwnerSubtreeForOne(o: Set[Subtree]): Unit = {
    ownerSubtreeForOne = o
    +this changeOwnerSubtreeForOne ()
  }

  def addOwnerSubtreeForOne(o: Subtree): Unit = {
    ownerSubtreeForOne += o
    +this changeOwnerSubtreeForOne ()
  }

  def getOwnerSubtreeForZero(): Set[Subtree] = {
    ownerSubtreeForZero
  }

  def setOwnerSubtreeForZero(o: Set[Subtree]): Unit = {
    ownerSubtreeForZero = o
    +this changeOwnerSubtreeForZero ()
  }

  def addOwnerSubtreeForZero(o: Subtree): Unit = {
    ownerSubtreeForZero += o
    +this changeOwnerSubtreeForZero ()
  }

  def getOwnerBDD(): BDD = {
    ownerBDD
  }

  def setOwnerBDD(o: BDD): Unit = {
    ownerBDD = o
    +this changeOwnerBDD ()
  }

  override def toString(): String = {
    "Tree:"
  }

}



    