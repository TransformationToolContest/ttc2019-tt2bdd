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
  
  def getMinDeep(): Int = {
    var minDepth = -1
    var depth = 0
    ownerSubtreeForOne.foreach(s => {
      depth = s.getMinDeep()
      if (1 + depth < minDepth || minDepth == -1) {
        minDepth = 1 + depth
      }
    })
    ownerSubtreeForZero.foreach(s => {
      depth = s.getMinDeep()
      if (1 + depth < minDepth || minDepth == -1) {
        minDepth = 1 + depth
      }
    })
    if (minDepth == -1) {
      minDepth = 0
    }
    minDepth
  }
  
  def getSumDeep(): Int = {
    var sumDepth = 0
    ownerSubtreeForOne.foreach(s => {
      sumDepth += s.getMinDeep() + 1
    })
    ownerSubtreeForZero.foreach(s => {
      sumDepth += s.getMinDeep() + 1
    })
    sumDepth
  }

  override def toString(): String = {
    "Tree:"
  }

}



    