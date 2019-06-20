package org.rosi_project.model_management.core

import scroll.internal.MultiCompartment
import scala.collection.mutable.ListBuffer
import org.rosi_project.model_management.sum._
import org.rosi_project.model_management.sum.roles._
import org.rosi_project.model_management.sum.join._
import org.rosi_project.model_management.sum.compartments.IRelationCompartment

object RsumCompartment extends MultiCompartment {

  //all active extensions
  private var activeExtensions = ListBuffer[IExtensionCompartment]()
  //all active views
  private var activeViews = ListBuffer[IViewCompartment]()
  
  //all registered views
  private var viewInfos = ListBuffer[IViewTypeInfo]()
  private var joinInfos = ListBuffer[IJoinInfo]()

  //elements in the RSUM
  private var relations = ListBuffer[IRelationCompartment]()
  private var naturals = ListBuffer[Object]()
  private var joins = ListBuffer[IJoinCompartment]()
  
  //TODO: look if this method needs different visibility
  def getNaturals(): ListBuffer[Object] = naturals
  def getRelations(): ListBuffer[IRelationCompartment] = relations
  def getJoins(): ListBuffer[IJoinCompartment] = joins  
  
  private[model_management] def addJoinCompartment(joinInfo: IJoinInfo): Unit = {
    if (joinInfo == null || joinInfos.contains(joinInfo)) {
      return
    }
    joinInfos += joinInfo
    
    val bases = RsumCompartment.getNaturals().filter(joinInfo.isInstanceBaseModel(_))
    val others = RsumCompartment.getNaturals().filter(joinInfo.isInstanceOtherModel(_))
    //b can only match with one o TODO: uses last one if more than one matches
    //TODO: also iterate over joins to allow deep joins
    bases.foreach(b => {
      var other: Object = null
      others.foreach(o => {
        if (joinInfo.matchTwoObjects(b, o)) {
          other = o
        }
      })
      if (other != null || joinInfo.getJoinType() == RsumJoinType.outer) {
        //join should be added before a view that needs it
        val j = joinInfo.getNewInstance(b, other)
        val rsumManager = new RsumManager
        j play rsumManager
        rsumManager.manageRsum(j)
      }      
    })
  }
    
  def printStatus (): Unit = {
    println("****************************************")
    println("Naturals: " + naturals.size + " N: " + naturals)
    println("Relations: " + relations.size + " R: " + relations)
    println("Joins: " + joins.size + " J: " + joins)
    println("****************************************")
  }
  
  def isViewTypeInfo(info: IViewTypeInfo): Boolean = viewInfos.contains(info)
  
  def isActiveExtension(extension: IExtensionCompartment): Boolean = activeExtensions.contains(extension)
  
  def isActiveView(view: IViewCompartment): Boolean = activeViews.contains(view)
  
  def isActiveViewFromName(name: String): Boolean = {
    activeViews.filter(_.getViewName() == name).foreach(
        v => return true)
    return false
  }

  def getActiveViewFromName(name: String): IViewCompartment = {
    activeViews.filter(_.getViewName() == name).foreach(
        v => return v)
    return null;
  }
  
  def addViewTypeInfo(view: IViewTypeInfo): IViewTypeInfo = {
    //look if there is a view with this name
    viewInfos.filter(_.getViewName() == view.getViewName()).foreach(v => {
      println("View with name " + v.getViewName() + " is already known!")
      return v
    })
    viewInfos = viewInfos :+ view
    view.getJoinInfos().foreach(this.addJoinCompartment(_))
    return view
  }
  
  def createActiveViewFromName(name: String): IViewCompartment = {
    //look if there is a view with this name
    viewInfos.filter(_.getViewName() == name).foreach(
        v => return this.createView(v))
    println("View with name %s is not known!", name)
    return null
  }    

  def createActiveView(view: IViewTypeInfo): IViewCompartment = {
    return this.createView(this.addViewTypeInfo(view))
  }

  def deleteActiveView(view: IViewCompartment): Boolean = {
    if (view == null || !activeViews.contains(view))
      return false    
    view.deleteAllRoles()
    activeViews -= view
    return true
  }
  
  private def createView(view: IViewTypeInfo): IViewCompartment = {
    return addActiveView(view.getViewRsumInstance())
  }
  
  private[model_management] def addActiveView(view: IViewCompartment): IViewCompartment = {
    //look if the view is registered
    if (viewInfos.filter(_.getViewName() == view.getViewName()).isEmpty) {
      return null
    }
    //look if their exists a view with the same name
    //TODO: should be possible to create 2 views with same name
    activeViews.filter(_.getViewName() == view.getViewName()).foreach(v => {
      println("Two views with the same name can not be created, return old view")
      return v
    })
    activeViews = activeViews :+ view
    //combine the view with the rsum compartment   
    this combine view
    //add the view roles
    naturals.foreach(n => view.getViewRole(n))
    joins.foreach(j => view.getViewRole(j))
    relations.foreach(r => view.getViewRole(r))
    return view
  }  

  def addExtension(comp: IExtensionCompartment): Boolean = {
    activeExtensions.foreach { e =>
      //extension already integrated (ignore)
      if (e.getExtensionName().equals(comp.getExtensionName())) {
        println("Their already exists an extension compartment with this name")
        return false
      }
    }
    activeExtensions = activeExtensions :+ comp
    //combine the extension with the rsum compartment
    this combine comp
    //adds the extension roles
    relations.foreach(r => comp.getExtensionRole(r))
    naturals.foreach(n => comp.getExtensionRole(n))
    joins.foreach(j => comp.getExtensionRole(j))
    return true
  }

  def removeExtension(name: String): Boolean = {
    var extension: IExtensionCompartment = null
    //find extension by name
    activeExtensions.filter(_.getExtensionName() == name).foreach(e => extension = e)
    //remove
    if (extension != null) {
      extension.deleteAllRoles()
      activeExtensions -= extension
      return true
    }    
    println("No extension with such a name!")
    return false
  }
  
  private def newObject(obj: Object): Unit = {   
    //TODO: add here implementation for deep joins
    //TODO: new function to get naturals and joins in one list
    //obj should not be in the join and must be a type of it
    joinInfos.filter(_.canMatchWithNewJoin(obj)).foreach(joinInfo => {      
      var other: Object = null
      var newJoin: IJoinCompartment = null
      if (joinInfo.isInstanceBaseModel(obj)) {
        //can be a new base instance, should not be possible that is is connect with one that is already connected
        RsumCompartment.getNaturals().filter(n => joinInfo.isInstanceOtherModel(n) && joinInfo.matchTwoObjects(obj, n) && !joinInfo.containsObject(n)).foreach(other = _)
        if (other != null || joinInfo.getJoinType() == RsumJoinType.outer) {
          newJoin = joinInfo.getNewInstance(obj, other)
        }
      } else {
        if (joinInfo.getJoinType() == RsumJoinType.outer) {
          //search alle join instances from this join for a base model without connection that mathes to this obj
          RsumCompartment.getJoins().filter(j => j.hasEmptyOther() && j.getJoinInfo().isInstanceOtherModel(obj) && j.getJoinInfo().matchTwoObjects(j.baseObj, obj)).foreach(_.addOther(obj))
        } else {
          //if its no outer join search for all naturals if one match that is currently not in the join
          RsumCompartment.getNaturals().filter(n => joinInfo.isInstanceBaseModel(n) && joinInfo.matchTwoObjects(n, obj) && !joinInfo.containsObject(n)).foreach(other = _)
          if (other != null) {
            newJoin = joinInfo.getNewInstance(other, obj)
          }
        }
      }
      if (newJoin != null) {
        var rsumManager: RsumManager = new RsumManager()
        newJoin play rsumManager
        rsumManager.manageRsum(newJoin)
      }
    })
  }

  class RsumManager() extends IRsumRole {

    def manageRsum(incommingPlayer: Object): Unit = {
      if (incommingPlayer == null)
        return
        
      ModelElementLists.addElement(incommingPlayer)

      incommingPlayer match {
        case r: IRelationCompartment =>
          r.initialize()
          RsumCompartment.this combine r
          //add to list with relations
          relations = relations :+ r
        case j: IJoinCompartment =>
          //TODO: implement here deep joins
          //newObject(j)
          //add to list with joins
          joins = joins :+ j
        case n: Object =>
          newObject(n)
          //add to list with naturals
          naturals = naturals :+ n
      }

      //add all roles from extension compartments
      activeExtensions.foreach(e => e.getExtensionRole(incommingPlayer))
      //create new elements in other views
      activeViews.foreach(v => v.getViewRole(incommingPlayer))      
    }

    def deleteEverything(): Unit = {
      val playerObj = this.player.right.get
      
      ModelElementLists.removeElement(playerObj)
      
      val roles = playerObj.roles()
      //iterate over all roles and call remove methods
      //remove methods from the rule also remove the role from the graph      
      roles.filter(_.isInstanceOf[IRsumRole]).foreach(r => {
        //println("Delete RsumRole (Extension, View, or Relation) Role: " + r)
        val rsumRole = r.asInstanceOf[IRsumRole]
        rsumRole.removeRole()
      })
      
      //remove element from internal element lists
      if (playerObj.isInstanceOf[IRelationCompartment]) {
        val relation = playerObj.asInstanceOf[IRelationCompartment]
        relation.deleteCompartment()
        relations -= relation
      } else if (playerObj.isInstanceOf[IJoinCompartment]) {
        val join = playerObj.asInstanceOf[IJoinCompartment]
        join.deleteCompartment()
        joins -= join
      } else {
        naturals -= playerObj
      }
      //println("All Count: " + plays.allPlayers.size)
    }

    def changeSomething(): Unit = {
      +this runExtension ()
    }
    
    private[model_management] def removeRole(): Unit = {
      this.remove()
    }
  }
}