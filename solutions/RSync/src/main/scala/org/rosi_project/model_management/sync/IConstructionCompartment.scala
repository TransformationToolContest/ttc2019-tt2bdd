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

  /*private def addNotificationRoles(containers: ListBuffer[ConstructionContainer]): Unit = {
    containers.foreach { cc =>
      if (cc.isConstructed) {
        // add the notification role for the sync UI
        val clazz = cc.getPlayerInstance.getClass
        ModelRegistry.getModelForInstanceClass(clazz) match {
          case Some(model) =>
            //val notifier = SyncUpdateNotifier(model)
            cc.getManagerInstance play SyncUpdateNotifier(model)
          case None =>
            println(s"No model found: $clazz")
        }
      }
    }
  }*/

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
      if (cc.isConstructed() && !cc.isStartElement())
        SynchronizationCompartment combine cc.getPlayerInstance
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
    this.addManagerRoles(containers)
    //this.addNotificationRoles(containers)
    this.addDeleteRoles(containers)
    this.addRelatedRoleManager(containers)
    this.synchronizeCompartments(containers)
    this.bindSynchronizationRules(containers)
    this.addExtensionRoles(containers)
    this.notifyExtensionRoles(containers)
    this.fillTestLists(containers)
    /*println("Construction ++++++++++++++++++++++++++++++++++++++++++++------------------------++++++++++++++++++++++++++++++++++++++++++++++++++++")
    containers.foreach { cc =>
      println((cc.getPlayerInstance).roles())
    }
    println("Construction ++++++++++++++++++++++++++++++++++++++++++++------------------------++++++++++++++++++++++++++++++++++++++++++++++++++++")*/
  }

}
