package org.rosi_project.model_management.sum.join

import scroll.internal.Compartment
import org.rosi_project.model_management.sum.roles.IRsumRole
import org.rosi_project.model_management.core.RsumCompartment.RsumManager
import org.rosi_project.model_management.core.RsumCompartment

trait IJoinCompartment extends Compartment {
  var baseObj: Object = null
  var otherObj: Object = null
    
  protected val baseRole: BaseRole = new BaseRole
  protected val otherRole: OtherRole = new OtherRole
  
  def getJoinInfo(): IJoinInfo
  
  def getAllJoinNaturals(): Set[Object] = {
    var bases: Set[Object] = Set(baseObj)
    if (baseObj.isInstanceOf[IJoinCompartment]) {
      bases = baseObj.asInstanceOf[IJoinCompartment].getAllJoinNaturals()
    }
    if (otherObj != null) {
      var others: Set[Object] = Set(otherObj)
      if (otherObj.isInstanceOf[IJoinCompartment]) {
        others = otherObj.asInstanceOf[IJoinCompartment].getAllJoinNaturals()
      }
      return others ++ bases
    }
    return bases
  }
  
  def hasEmptyOther(): Boolean = otherObj == null
  
  def joinEquals(obj: Object): Boolean = {
    var baseEquals: Boolean = false
    var otherEquals: Boolean = false
    if (baseObj.isInstanceOf[IJoinCompartment]) {
      baseEquals = baseObj.asInstanceOf[IJoinCompartment].joinEquals(obj)
    } else {
      baseEquals = +obj == +baseObj
    }
    if (otherObj.isInstanceOf[IJoinCompartment]) {
      otherEquals = otherObj.asInstanceOf[IJoinCompartment].joinEquals(obj)
    } else {
      otherEquals = +obj == +otherObj
    }
    baseEquals || otherEquals
  }
  
  def addOther(obj: Object): Unit = {
    if (obj != null && otherObj == null) {
      getJoinInfo.addObject(obj)
      otherObj = obj
      otherObj play otherRole
    }
  }
  
  //if deletion comes from down than is stop in roles
  def deleteCompartment(): Unit = {
    if (baseObj != null) {
      getJoinInfo.removeObject(baseObj)
      baseRole.remove()
      +baseObj deleteEverything()
    }
    if (otherObj != null) {
      getJoinInfo.removeObject(otherObj)
      otherRole.remove()
      +otherObj deleteEverything()
    }
  }
  
  private[join] def objectInitialization(base: Object, other: Object): Unit = {
    if (base != null) {
      getJoinInfo.addObject(base)
      baseObj = base
      base play baseRole
    }
    if (other != null) {
      getJoinInfo.addObject(other)
      otherObj = other
      other play otherRole      
    }
  }
  
  protected def initialize(base: Object, other: Object): Unit = {
    RsumCompartment.combine(this)
    var rsumManager: RsumManager = null
    if (base != null) {
      getJoinInfo.addObject(base)
      baseObj = base
      rsumManager = new RsumManager()
      base play rsumManager
      base play baseRole
      rsumManager.manageRsum(base)
    }
    if (other != null) {
      getJoinInfo.addObject(other)
      otherObj = other
      rsumManager = new RsumManager()
      other play rsumManager
      other play otherRole
      rsumManager.manageRsum(other)      
    }
  } 
  
  class BaseRole extends IRsumRole {
    //deletion comes from down
    private[model_management] def removeRole(): Unit = {
      baseRole.remove()
      otherRole.remove()
      +IJoinCompartment.this deleteEverything()
    }
  }
  
  class OtherRole extends IRsumRole {
    private[model_management] def removeRole(): Unit = {
      baseRole.remove()
      otherRole.remove()
      +IJoinCompartment.this deleteEverything()
    }
  }
  
}