package org.rosi_project.model_management.sum

import scroll.internal.Compartment
import scala.collection.mutable.ListBuffer
import org.rosi_project.model_management.sum.roles.IExtensionRole
import org.rosi_project.model_management.core.RsumCompartment

trait IExtensionCompartment extends Compartment {
  
  protected var roles = ListBuffer[IExtensionRole]()
  
  protected def getRole(classname: Object) : IExtensionRole
  
  private def getRoleFromList(classname: Object): IExtensionRole = {
    roles.foreach { r =>
      if (+r == +classname) {
        return r
      }
    }
    return null
  }
  
  protected def removeRoleFromList(role: IExtensionRole): Unit = {
    if (roles.contains(role))
      roles -= role
  }
  
  /**
   * Get a new extension role for the object. Adds automatically the play relation.
   */
  private[model_management] def getExtensionRole(classname: Object) : IExtensionRole = {
    if (!RsumCompartment.isActiveExtension(this)) {
      return null
    }
    
    //look if a role from this elements exists in this extension
    var role: IExtensionRole = getRoleFromList(classname)
    //YES Return this role
    if (role != null) {
      return role
    }
    
    role = this.getRole(classname)
    if (role != null) {
      classname play role
      roles = roles :+ role
    }
    return role
  }
  
  //TODO: do we need this function
  def getNewInstance() : IExtensionCompartment
  
  def getExtensionName() : String
  
  def deleteAllRoles() : Unit = {
    roles.foreach(_.remove())
  }
  
  abstract class AExtensionRole extends IExtensionRole {
    
    private[model_management] def removeRole(): Unit = {
      this.remove()
      IExtensionCompartment.this.removeRoleFromList(this)
    }
  }
}