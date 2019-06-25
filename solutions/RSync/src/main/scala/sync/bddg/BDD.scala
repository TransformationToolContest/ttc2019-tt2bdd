package sync.bddg

import org.rosi_project.model_management.core.PlayerSync

class BDD(protected var name: String, protected var trees: Set[Tree], protected var root: Tree, protected var ports: Set[Port]) extends PlayerSync {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this syncSetName ()
  }

  def getTrees(): Set[Tree] = {
    trees
  }

  def addTrees(t: Tree): Unit = {
    trees += t
    +this syncAddTrees (t)
  }

  def removeTrees(t: Tree): Unit = {
    trees -= t
    +this syncRemoveTrees (t)
  }

  def getRoot(): Tree = {
    root
  }

  def setRoot(r: Tree): Unit = {
    root = r
    +this syncSetRoot ()
  }

  def getPorts(): Set[Port] = {
    ports
  }

  def addPorts(p: Port): Unit = {
    ports += p
    +this syncAddPorts (p)
  }

  def removePorts(p: Port): Unit = {
    ports -= p
    +this syncRemovePorts (p)
  }

  override def toString(): String = {
    "BDD:" + " name=" + name
  }

}



    