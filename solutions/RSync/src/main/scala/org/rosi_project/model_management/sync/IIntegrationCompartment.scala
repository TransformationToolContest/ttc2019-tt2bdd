package org.rosi_project.model_management.sync

import scroll.internal.Compartment
import scala.collection.mutable.ListBuffer
import org.rosi_project.model_management.sync.roles.ISyncRole
import org.rosi_project.model_management.sync.roles.IIntegrator
import org.rosi_project.model_management.sync.helper.IntegrationContainer
import org.rosi_project.model_management.core._
import org.rosi_project.model_management.sync.roles.IRoleManager

/**
 * Interface for each integration rule.
 */
trait IIntegrationCompartment extends Compartment {

  /**
   * Return a role instance that handles the integration process for a new model to this instance.
   */
  def getIntegratorForClassName(classname: Object): IIntegrator

  /**
   * Return a role instance that handles the integration process for a new relational compartment.
   */
  def getRelationalIntegratorsForClassName(classname: Object): IIntegrator

  def finalEditFunction(): Unit
  
  protected def connectTargetElementWithSourceElementes(target: PlayerSync, sourceList: Set[PlayerSync]): Unit = {
    var containers: ListBuffer[IntegrationContainer] = ListBuffer.empty
    //Create Containers
    sourceList.foreach(e => {
      containers += new IntegrationContainer(target, e)
    })
    //Finish Creation
    makeCompleteIntegrationProcess(containers)
  }
  
  protected def connectTargetElementWithSourceElemente(target: PlayerSync, source: PlayerSync): Unit = {
    var containers: ListBuffer[IntegrationContainer] = ListBuffer.empty
    //Create Container
    containers += new IntegrationContainer(target, source)    
    //Finish Creation
    makeCompleteIntegrationProcess(containers)
  }

  private def addExtensionRoles(containers: ListBuffer[IntegrationContainer]): Unit = {
    containers.foreach { cc =>
      SynchronizationCompartment.getExtensions().foreach { e =>
        var role = e.getExtensionForClassName(cc.getNewPlayerInstance())
        if (role != null) {
          cc.getNewManagerInstance() play role
        }
      }
    }
  }

  private def notifyExtensionRoles(containers: ListBuffer[IntegrationContainer]): Unit = {
    containers.foreach { cc =>
      var playerInstance = cc.getNewPlayerInstance()
      +playerInstance insertNotification ()
    }
  }

  /**
   * Add Manager roles to all constructed elements.
   */
  private def addManagerRoles(containers: ListBuffer[IntegrationContainer]): Unit = {
    containers.filter(_.newManagerConnection).foreach { cc =>
      cc.getNewPlayerInstance() play cc.getNewManagerInstance()
    }
  }

  /**
   * Add the delete roles for the elements in the IntegrationContainer.
   */
  private def addDeleteRoles(containers: ListBuffer[IntegrationContainer]): Unit = {
    containers.filter(_.newManagerConnection).foreach { cc =>
      cc.getNewManagerInstance() play SynchronizationCompartment.getDestructionRule().getDestructorForClassName(cc.getNewPlayerInstance())
    }
  }

  /**
   * Add the related RoleManagers for the elements in the IntegrationContainer.
   */
  private def addRelatedRoleManager(containers: ListBuffer[IntegrationContainer]): Unit = {
    containers.foreach { cc =>
      if (cc.simpleRelatedManagerConnection) {
        val oldPlayer = cc.getOldPlayerInstance()
        val manager: IRoleManager = +oldPlayer getManager ()
        if (manager != null) {
          manager.addRelatedManager(cc.getNewManagerInstance())
          cc.getNewManagerInstance().addRelatedManager(manager)
        }
      } else {
        val oldPlayer = cc.getOldPlayerInstance()
        val allManager: Set[IRoleManager] = +oldPlayer getAllManager ()
        if (allManager != null) {
          allManager.foreach { r =>
            r.addRelatedManager(cc.getNewManagerInstance())
            cc.getNewManagerInstance().addRelatedManager(r)
          }
        }
      }
    }
  }

  /**
   * Combine the SynchronizationCompartment with all Players from the IntegrationContainer.
   */
  private def synchronizeCompartments(containers: ListBuffer[IntegrationContainer]): Unit = {
    containers.foreach { cc =>
      SynchronizationCompartment combine cc.getNewPlayerInstance()
    }
  }

  /**
   * Create the Synchronization mechanisms for the elements in the IntegrationContainer.
   */
  private def bindSynchronizationRules(containers: ListBuffer[IntegrationContainer]): Unit = {
    containers.foreach { cc =>
      val oldPlayer = cc.getOldPlayerInstance()
      val allManager: Set[IRoleManager] = +oldPlayer getAllManager ()
      if (allManager != null) {
        allManager.foreach { rm =>
          val roles = rm.roles()
          //println("Player: " + rm.player + "Roles: " + roles)
          roles.foreach { r =>
            if (r.isInstanceOf[ISyncRole]) {
              val syncRole: ISyncRole = r.asInstanceOf[ISyncRole]
              val syncComp: ISyncCompartment = syncRole.getOuterCompartment
              //println("+~~~Sync: " + syncRole + " " + syncComp)
              if (!syncComp.containsSyncer(cc.getNewPlayerInstance()) && syncComp.isFirstIntegration(r.player.right.get)) {
                val newRole = syncComp.getNextIntegrationRole(cc.getNewPlayerInstance())
                //println("+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~New Role: " + newRole)
                if (newRole != null) {
                  cc.getNewManagerInstance() play newRole
                  allManager.foreach { internalSync =>
                    val playerSync = internalSync.player.right.get
                    if (syncComp.isNextIntegration(playerSync)) {
                      if (!syncComp.containsSyncer(playerSync)) {
                        internalSync play syncComp.getNextIntegrationRole(playerSync)
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * Fill the test lists with all Players from the IntegrationContainer.
   */
  private def fillTestLists(containers: ListBuffer[IntegrationContainer]): Unit = {
    containers.foreach { cc =>
      ModelElementLists.addElement(cc.getNewPlayerInstance())
    }
  }

  /**
   * Do the integration process automatically.
   */
  protected def makeCompleteIntegrationProcess(containers: ListBuffer[IntegrationContainer]): Unit = {
    containers.foreach(cc => {
      if (cc.getNewManagerInstance() == null) {
        val newPlayer = cc.getNewPlayerInstance()
        val manager = +newPlayer getManager ()
        if (manager.isRight && manager.right.get != null) {
          cc.newManagerConnection = false
          cc.newManagerInstance = manager.right.get
        } else {
          cc.newManagerConnection = true
          cc.newManagerInstance = SynchronizationCompartment.createRoleManager()
        }
      }
    })

    //add new role managers to the new players
    this.addManagerRoles(containers)
    this.addDeleteRoles(containers)
    this.addRelatedRoleManager(containers)
    //combines the new compartments with the existing ones
    this.synchronizeCompartments(containers)
    this.bindSynchronizationRules(containers)
    //add extension roles and notify them because of creation
    this.addExtensionRoles(containers)
    this.notifyExtensionRoles(containers)
    //add the new model element to the elements list
    this.fillTestLists(containers)
    /*println("Integrate +++++++++++++++++++++++++++++++++++++++++++++---------------------------------------------+++++++++++++++++++++++++++++++++++++++++++++++++++")
    containers.foreach { cc =>
      println((cc.getNewPlayerInstance()).roles())
      println((cc.getOldPlayerInstance()).roles())
    }
    println("Integrate ++++++++++++++++++++++++++++++++++++++++++++------------------------++++++++++++++++++++++++++++++++++++++++++++++++++++")*/
  }
}
