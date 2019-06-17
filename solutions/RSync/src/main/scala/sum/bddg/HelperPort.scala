package sum.bddg

import org.rosi_project.model_management.sum.query.QueryHelper

class HelperPort(p_Name: String) extends Port(p_Name) with QueryHelper {

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Port]
  }

  override def toString(): String = {
    "HelperPort:" + " name=" + name
  }

}



    