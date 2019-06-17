package sum.tt

import org.rosi_project.model_management.core.PlayerSync

class LocatedElement(protected var location: String) extends PlayerSync {

  def getLocation(): String = {
    location
  }

  def setLocation(l: String): Unit = {
    location = l
    +this changeLocation ()
  }

  override def toString(): String = {
    "LocatedElement:" + " location=" + location
  }

}



    