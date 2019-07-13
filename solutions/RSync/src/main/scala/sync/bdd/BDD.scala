package sync.bdd

import org.rosi_project.model_management.core.PlayerSync

class BDD(protected var name: String, protected var tree: Tree, protected var ports: Set[Port]) extends PlayerSync {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    require(n != null)
    name = n
    +this syncSetName ()
  }

  def getTree(): Tree = {
    tree
  }

  def setTree(t: Tree): Unit = {
    require(t != null)
    tree = t
    +this syncSetTree ()
  }

  def getPorts(): Set[Port] = {
    ports
  }

  def addPorts(p: Port): Unit = {
    require(p != null)
    require(!ports.contains(p))
    ports += p
    +this syncAddPorts (p)
  }

  def removePorts(p: Port): Unit = {
    require(p != null)
    require(ports.contains(p))
    ports -= p
    +this syncRemovePorts (p)
  }

  override def toString(): String = {
    "BDD:" + " name=" + name
  }

}



    