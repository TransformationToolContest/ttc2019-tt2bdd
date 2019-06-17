package sync.bdd

import org.rosi_project.model_management.core.PlayerSync

class Assignment(protected var value: Boolean, protected var owner: Leaf, protected var port: OutputPort) extends PlayerSync {

  def getValue(): Boolean = {
    value
  }

  def setValue(v: Boolean): Unit = {
    value = v
    +this changeValue ()
  }

  def getOwner(): Leaf = {
    owner
  }

  def setOwner(o: Leaf): Unit = {
    owner = o
    +this changeOwner ()
  }

  def getPort(): OutputPort = {
    port
  }

  def setPort(p: OutputPort): Unit = {
    port = p
    +this changePort ()
  }

  override def toString(): String = {
    "Assignment:" + " value=" + value + " out=" + port
  }

}



    