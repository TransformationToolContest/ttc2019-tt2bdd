package org.rosi_project.model_management.sum

import scroll.internal.Compartment
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Set
import org.rosi_project.model_management.core.RsumCompartment
import org.rosi_project.model_management.core.RsumCompartment.RsumManager
import org.rosi_project.model_management.sum.compartments.IRelationCompartment
import org.rosi_project.model_management.sum.roles.IViewRole
import org.rosi_project.model_management.sum.join.IJoinCompartment

trait IViewCompartment extends Compartment {

  private var initRelationRole: Boolean = false
  private var naturalRoles = ListBuffer[AViewRole]()
  private var relationalRoles = ListBuffer[AViewRole]()

  /**
   * Get a map with the simple names of all elements
   * and the connected elements in the view.
   */
  def getMapOfElements(): Map[String, Set[AViewRole]] = {
    var result: Map[String, Set[AViewRole]] = Map.empty
    naturalRoles.foreach(n => {
      if (result.contains(n.getClass.getSimpleName)) {
        result(n.getClass.getSimpleName) += n
      } else {
        result = result + (n.getClass.getSimpleName -> Set(n))
      }
    })
    result
  }

  /**
   * Get all elements with the specific simple name.
   */
  def getElementsWithClassName(s: String): Set[AViewRole] = {
    var result: Set[AViewRole] = Set.empty
    naturalRoles.foreach(n => {
      if (n.getClass.getSimpleName == s) {
        result += n
      }
    })
    result
  }

  /**
   * Get all elements with the same type from the view.
   */
  def getElementsWithExample[A <: IViewRole](a: A): Set[A] = {
    var result: Set[A] = Set.empty
    naturalRoles.foreach(n => {
      if (n.getClass.getName == a.getClass.getName) {
        result += n.asInstanceOf[A]
      }
    })
    result
  }

  /**
   * Get a new view role for the object. Adds automatically the play relation.
   */
  private[model_management] def getViewRole(classname: Object): IViewRole = {
    if (!RsumCompartment.isActiveView(this)) {
      return null
    }

    if (!isViewable(classname)) {
      return null
    }

    //look if a role from this elements exists in this view
    var role: AViewRole = getRoleFromList(classname)
    //YES Return this role
    if (role != null) {
      return role
    }

    //it can not be possible that relations are created without their connection parts before
    //decide if source or target need a join role than we must look if they are also connected with such a role
    //NO create a new one
    if (classname.isInstanceOf[IRelationCompartment]) {
      //1. Handle the Relational Compartment
      var comp: IRelationCompartment = classname.asInstanceOf[IRelationCompartment]
      var source = comp.getSource()
      var target = comp.getTarget()
      var sourcePlayer = source.player.right.get
      var targetPlayer = target.player.right.get
      var sourceRole: AViewRole = getRoleFromList(source) //should work now with join objects
      var targetRole: AViewRole = getRoleFromList(target) //should work now with join objects 
      //requirement that a relational compartment cannot play a role in a relational compartment
      //if one source does not exists then do not create the new relation
      if (sourceRole != null && targetRole != null) {
        initRelationRole = true
        role = getRelationalRole(classname, sourceRole, targetRole)
        classname play role
        initRelationRole = false
      }      
    } else {
      //2. Handle the natural and join types
      initRelationRole = true
      role = getNaturalRole(classname)
      classname play role
      initRelationRole = false
    }
    return role
  }

  protected def isViewable(classname: Object): Boolean
  protected def getNaturalRole(classname: Object): AViewRole
  protected def getRelationalRole(classname: Object, sourceRole: AViewRole, targetRole: AViewRole): AViewRole

  protected def getRoleFromList(classname: Object): AViewRole = {
    relationalRoles.foreach(r => {
      if (+r == +classname) {
        return r
      }
    })
    naturalRoles.foreach(r => {
      if (r.player.right.get.isInstanceOf[IJoinCompartment] && !classname.isInstanceOf[IJoinCompartment]) {
        if (+r joinEquals(classname)) {
          return r
        }
      } else {
        if (+r == +classname) {
          return r
        }
      }
    })
    return null
  }

  def printViewRoles(): Unit = {
    println("-------------------------------------------------------------------")
    println("View: " + this.getViewName() + " #Roles: " + naturalRoles.size)
    naturalRoles.foreach { r =>
      var player = r.player.right.get
      var playerRoles = player.roles()
      println("* Player: " + player + " - Role: " + r + " - Size: " + playerRoles.size)
    }
    relationalRoles.foreach { r =>
      var player = r.player.right.get
      var playerRoles = player.roles()
      println("# Player: " + player + " - Role: " + r + " - Size: " + playerRoles.size)
    }
    println("-------------------------------------------------------------------")
  }

  def getAllViewElements(): ListBuffer[AViewRole] = naturalRoles

  def containsRole(role: AViewRole): Boolean = naturalRoles.contains(role)

  def getViewName(): String

  private[model_management] def deleteAllRoles(): Unit = {
    naturalRoles.foreach { r =>
      r.remove()
    }
    relationalRoles.foreach { r =>
      r.remove()
    }
  }

  protected def removeRoleFromList(role: AViewRole): Unit = {
    if (naturalRoles.contains(role))
      naturalRoles -= role
    if (relationalRoles.contains(role))
      relationalRoles -= role
  }

  private def initElement(element: Object, relational: Boolean, role: AViewRole): Unit = {
    //proof if the actual view is in
    if (!RsumCompartment.isActiveView(this)) {
      return
    }
    if (relational)
      relationalRoles = relationalRoles :+ role
    else
      naturalRoles = naturalRoles :+ role

    //do this only if element is created in the view and not somewhere else  
    if (!initRelationRole) {
      var rsumManager: RsumManager = new RsumManager()
      element play rsumManager
      element play role
      rsumManager.manageRsum(element)
    }
  }

  private def isActive(): Boolean = RsumCompartment.isActiveView(this)

  abstract class AViewRole extends IViewRole {

    initializeElement()

    protected def isRelational(): Boolean

    protected def getCreationObject(): Object

    private def initializeElement(): Unit = {
      if (isActive) {
        if (isRelational) {
          relationalRoles = relationalRoles :+ this
        } else {
          naturalRoles = naturalRoles :+ this
        }
        if (!initRelationRole) {
          var rsumManager: RsumManager = new RsumManager()
          var obj = getCreationObject
          obj play rsumManager
          obj play this
          rsumManager.manageRsum(obj)
        }
      }
    }
    
    protected def getPlayerOfType(obj: Object, t: String): Object = {
      val source: Object = obj.player.right.get
      if (source.isInstanceOf[IJoinCompartment]) {
        val joinNats = source.asInstanceOf[IJoinCompartment].getAllJoinNaturals()
        joinNats.filter(_.getClass.getSimpleName == t).foreach(n => return n)
      } else {
        return source
      }
      source
    }

    protected def deleteElement(): Unit = {
      +this deleteEverything ()
    }

    private[model_management] def removeRole(): Unit = {
      this.remove()
      IViewCompartment.this.removeRoleFromList(this)
    }
  }
}