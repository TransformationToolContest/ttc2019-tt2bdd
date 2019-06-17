package sum.bdd

import org.rosi_project.model_management.core.PlayerSync

class BDD(protected var name: String) extends PlayerSync {

  def getName(): String = {
    name
  }

  def setName(n: String): Unit = {
    name = n
    +this changeName ()
  }

  override def toString(): String = {
    "BDD:" + " name=" + name
  }

}



    