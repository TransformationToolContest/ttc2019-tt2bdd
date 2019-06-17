package sync.bdd

import org.rosi_project.model_management.core.PlayerSync

class BDD(protected var name: String, protected var tree: Tree, protected var ports: Set[Port]) extends PlayerSync {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this changeName ()
  }

  def getTree(): Tree = {
    tree
  }

  def setTree(t: Tree): Unit = {
    tree = t
    +this changeTree ()
  }

  def getPorts(): Set[Port] = {
    ports
  }

  def setPorts(p: Set[Port]): Unit = {
    ports = p
    +this changePorts ()
  }

  def addPorts(p: Port): Unit = {
    ports += p
    +this changePorts ()
  }

  override def toString(): String = {
    "BDD:" + " name=" + name + " ports=" + ports + " tree=\n" + tree
  }

}



    