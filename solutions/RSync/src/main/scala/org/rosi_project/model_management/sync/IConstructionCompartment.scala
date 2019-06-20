package org.rosi_project.model_management.sync

import scroll.internal.Compartment

import scala.collection.mutable.ListBuffer
import org.rosi_project.model_management.core._
import org.rosi_project.model_management.sync.helper.ConstructionContainer
import org.rosi_project.model_management.sync.roles.IConstructor


/**
  * Interface for each construction rule.
  */
trait IConstructionCompartment extends Compartment {

  /**
    * Return a role instance that handles the construction process for the object.
    */
  def getConstructorForClassName(classname: Object): IConstructor

  def getRuleName: String

  private def addExtensionRoles(containers: ListBuffer[ConstructionContainer]): Unit = {
    containers.foreach { cc =>
      if (cc.isConstructed) {
        SynchronizationCompartment.getExtensions().foreach { e =>
          var role = e.getExtensionForClassName(cc.getPlayerInstance())
          if (role != null) {
            cc.getManagerInstance() play role
          }
        }
      }
    }
  }

  private def notifyExtensionRoles(containers: ListBuffer[ConstructionContainer]): Unit = {
    containers.foreach { cc =>
      if (cc.isConstructed) {
        var playerInstance = cc.getPlayerInstance()
        +playerInstance insertNotification()
      }
    }
  }

  /**
   * Add the RoleManager roles from the synchronization compartment if necessary
   */
  protected def addManagerRoles(containers: ListBuffer[ConstructionContainer]): Unit = {
    containers.foreach { cc =>
      if (cc.isConstructed && !cc.isStartElement) {
        cc.getPlayerInstance play cc.getManagerInstance
      }
    }
  }

  /**
   * Add the delete roles for the elements in the ConstructionContainers.
   */
  protected def addDeleteRoles(containers: ListBuffer[ConstructionContainer]): Unit = {
    containers.foreach { cc =>
      if (cc.isConstructed) {
        cc.getManagerInstance() play SynchronizationCompartment.getDestructionRule().getDestructorForClassName(cc.getPlayerInstance())
      }
    }
  }

  /**
   * Add the related RoleManagers for the elements in the ConstructionContainers.
   */
  protected def addRelatedRoleManager(containers: ListBuffer[ConstructionContainer]): Unit = {
    containers.foreach { cc =>
      containers.foreach { inner =>
        cc.getManagerInstance.addRelatedManager(inner.getManagerInstance)
      }
    }
  }

  /**
   * Combine the SynchronizationCompartment with all Players from the ConstructionContainers.
   */
  protected def synchronizeCompartments(containers: ListBuffer[ConstructionContainer]): Unit = {
    containers.foreach { cc =>
      if (cc.isConstructed() && !cc.isStartElement()) {        
        SynchronizationCompartment combine cc.getPlayerInstance
      }
    }
  }

  /**
   * Create the Synchronization mechanisms for the elements in the ConstructionContainers.
   */
  protected def bindSynchronizationRules(containers: ListBuffer[ConstructionContainer]): Unit = {
    SynchronizationCompartment.getSyncRules().foreach { s =>
      var sync: ISyncCompartment = null
      //Proof all container for integration
      containers.foreach { cc =>
        if (s.isNextIntegration(cc.getPlayerInstance)) {
          if (cc.isConstructed && sync == null) {
            sync = s.getNewInstance
          }
          if (sync != null) {
            cc.getManagerInstance() play sync.getNextIntegrationRole(cc.getPlayerInstance())
          }
        }
      }
      if (sync != null)
        SynchronizationCompartment combine sync
    }
  }

  /**
   * Fill the test lists with all Players from the ConstructionContainers.
   */
  protected def fillTestLists(containers: ListBuffer[ConstructionContainer]): Unit = {
    containers.foreach { cc =>
      ModelElementLists.addElement(cc.getPlayerInstance)
    }
  }

  protected def makeCompleteConstructionProcess(containers: ListBuffer[ConstructionContainer]): Unit = {
    //first synchronize new compartments
    //var t1 = System.nanoTime()
    this.synchronizeCompartments(containers)
    //var t2 = System.nanoTime()
    
    //add role manager and relations
    this.addManagerRoles(containers)
    //var t3 = System.nanoTime()
    this.addRelatedRoleManager(containers)    
    //var t4 = System.nanoTime()
    
    //binding of roles
    //this.addDeleteRoles(containers)
    this.bindSynchronizationRules(containers)
    //var t5 = System.nanoTime()
    this.addExtensionRoles(containers)
    //var t6 = System.nanoTime()
    
    //notify extensions
    this.notifyExtensionRoles(containers)
    //var t7 = System.nanoTime()
    
    //fill test list
    this.fillTestLists(containers)
    /*var t8 = System.nanoTime()
    println("1: " + (t2 - t1))
    println("2: " + (t3 - t2))
    println("3: " + (t4 - t3))
    println("4: " + (t5 - t4))
    println("5: " + (t6 - t5))
    println("6: " + (t7 - t6))
    println("7: " + (t8 - t7))*/
    
    /*println("Construction ++++++++++++++++++++++++++++++++++++++++++++------------------------++++++++++++++++++++++++++++++++++++++++++++++++++++")
    containers.foreach { cc =>
      println((cc.getPlayerInstance).roles())
    }
    println("Construction ++++++++++++++++++++++++++++++++++++++++++++------------------------++++++++++++++++++++++++++++++++++++++++++++++++++++")*/
  }

}
