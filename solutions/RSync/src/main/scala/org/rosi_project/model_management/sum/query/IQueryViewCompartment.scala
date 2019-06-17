package org.rosi_project.model_management.sum.query

import scroll.internal.Compartment
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Set
import org.rosi_project.model_management.core.RsumCompartment
import org.rosi_project.model_management.sum.compartments.IRelationCompartment

trait IQueryViewCompartment extends Compartment {
  
  private var name: String = ""
  private var query: Query = null
  
  RsumCompartment.combine(this)
  
  protected def init(n: String): Unit = {
    name = n
    query = new Query(n)
  }
  
  private var naturalRoles = Set[AQueryViewRole]()
  private var relationalRoles = Set[AQueryViewRole]()
  
  def getName(): String = name
  
  def getQuery(): Query = query
  
  /**
   * Get a map with the simple names of all elements 
   * and the connected elements in the view.
   */
  def getMapOfElements(): Map[String, Set[AQueryViewRole]] = {
    var result: Map[String, Set[AQueryViewRole]] = Map.empty
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
  def getElementsWithClassName(s: String): Set[AQueryViewRole] = {
    var result: Set[AQueryViewRole] = Set.empty
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
  def getElementsWithExample[A <: AQueryViewRole](a: A): Set[A] = {
    var result: Set[A] = Set.empty
    naturalRoles.foreach(n => {
      if (n.getClass.getName == a.getClass.getName) {
        result += n.asInstanceOf[A]
      }
    })
    result
  }
  
  protected def getRoleFromList(classname: Object): AQueryViewRole = {
    relationalRoles.foreach { r =>
      if (+r == +classname) {
        return r
      }
    }
    naturalRoles.foreach { r =>
      if (+r == +classname) {
        return r
      }
    }
    return null
  }

  def printViewRoles(): Unit = {
    println("-------------------------------------------------------------------")
    println("QueryView: " + name + " #Roles: " + naturalRoles.size)
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
  
  def getAllViewElements(): Set[AQueryViewRole] = naturalRoles
  
  def containsRole(role: AQueryViewRole): Boolean = naturalRoles.contains(role)
  
  abstract class AQueryViewRole {  
    
    protected var connected: AQuery#QueryObject = null 
    
    initializeElement()
    
    protected def isRelational(): Boolean
    
    protected def getCreationObject(): Object
    
    def getQueryObject: AQuery#QueryObject = connected  

    private def initializeElement(): Unit = {
      val obj = getCreationObject
      if (isRelational) {
        relationalRoles += this
        val rel = obj.asInstanceOf[IRelationCompartment]
        RsumCompartment.combine(rel)
        rel.internalInitialize()
      } else {
        naturalRoles += this
      }
      connected = query.addQueryRole(obj)
      obj play this
    }      
  
    protected def deleteElement(): Unit = {
      if (isRelational) {
        +this deleteCompartment()
      }
      this.remove()
      query.removeQueryRole(connected)
    }
  }
}