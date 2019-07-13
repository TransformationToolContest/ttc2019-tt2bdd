package sync.bddg

import org.rosi_project.model_management.core.PlayerSync

class Assignment(protected var value: Boolean, protected var owner: Leaf, protected var port: OutputPort) extends PlayerSync {

  def getValue(): Boolean = {
    value
  }

  def setValue(v: Boolean): Unit = {
    value = v
    +this syncSetValue ()
  }

  def getOwner(): Leaf = {
    owner
  }

  def setOwner(o: Leaf): Unit = {
    require(o != null)
    owner = o
    +this syncSetOwner ()
  }

  def getPort(): OutputPort = {
    port
  }

  def setPort(p: OutputPort): Unit = {
    require(p != null)
    port = p
    +this syncSetPort ()
  }

  override def toString(): String = {
    "Assignment:" + " value=" + value
  }

}



    