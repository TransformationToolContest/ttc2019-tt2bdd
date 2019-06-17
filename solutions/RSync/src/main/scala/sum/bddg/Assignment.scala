package sum.bddg

import org.rosi_project.model_management.core.PlayerSync

class Assignment(protected var value: Boolean) extends PlayerSync {

  def getValue(): Boolean = {
    value
  }

  def setValue(v: Boolean): Unit = {
    value = v
    +this changeValue ()
  }

  override def toString(): String = {
    "Assignment:" + " value=" + value
  }

}



    