package sum.bdd

import org.rosi_project.model_management.sum.query.QueryHelper

class HelperTree extends Tree() with QueryHelper {

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Tree]
  }

  override def toString(): String = {
    "HelperTree:"
  }

}



    