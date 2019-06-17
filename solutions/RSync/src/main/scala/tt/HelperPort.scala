package tt

import org.rosi_project.model_management.sum.query.QueryHelper

class HelperPort(p_Name: String, p_Location: String) extends Port(p_Name, p_Location) with QueryHelper {

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Port]
  }

  override def toString(): String = {
    "HelperPort:" + " name=" + name + " location=" + location
  }

}



    