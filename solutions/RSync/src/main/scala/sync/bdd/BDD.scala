package sync.bdd

import org.rosi_project.model_management.core.PlayerSync

class BDD(protected var name: String, protected var tree: Tree, protected var ports: Set[Port]) extends PlayerSync {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this syncSetName ()
  }

  def getTree(): Tree = {
    tree
  }

  def setTree(t: Tree): Unit = {
    tree = t
    +this syncSetTree ()
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



    