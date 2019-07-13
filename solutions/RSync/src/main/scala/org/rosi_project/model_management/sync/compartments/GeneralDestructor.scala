package org.rosi_project.model_management.sync.compartments

import org.rosi_project.model_management.core.PlayerSync
import org.rosi_project.model_management.sync.IDestructionCompartment
import org.rosi_project.model_management.sync.roles.{ IDestructor, IRoleManager }

import scala.collection.mutable.ListBuffer
import org.rosi_project.model_management.core.ModelElementLists

/**
 * Calls the destruction method from all related RoleManagers and then deletes all roles from this player.
 */
object GeneralDestructor extends IDestructionCompartment {

  override def getDestructorForClassName(classname: Object): IDestructor = new DeleteRole

  override def getRuleName: String = "GeneralDestructor"
  
  class DeleteRole() extends IDestructor {

    def deleteRoleFunction(): Unit = {
      //remove this manager from all related ones
      +this removeThisManager()
      //clear list of related manager
      +this clearRelatedManager()
      //send notification about deletion
      +this deletionNotification()

      //delete all roles this element has
      val player = this.player
      if (player.isRight) {
        val test: PlayerSync = player.right.get.asInstanceOf[PlayerSync]
        ModelElementLists.removeElement(test)
        val roles = test.roles()
        roles.foreach { r =>
          r.remove()
        }
      }
    }
  }

  class DeleteRoleAndConnections() extends IDestructor {

    def deleteRoleFunction(): Unit = {
      //get the list of related manager
      var relatedManagers: Set[IRoleManager] = (+this).getRelatedManager()
      
      //clear all lists from the related managers
      (+this).clearListsOfRelatedManager()

      //delete also all related elements
      relatedManagers.foreach { m =>
        (+m).deleteObjectFromSynchro()
      }

      //send notification about deletion
      (+this).deletionNotification()

      //clear now the related manager list
      (+this).clearRelatedManager()
      //delete all roles this element has
      val player = this.player
      if (player.isRight) {
        val test: PlayerSync = player.right.get.asInstanceOf[PlayerSync]
        ModelElementLists.removeElement(test)
        val roles = test.roles()
        roles.foreach { r =>
          r.remove()
        }
      }
    }
  }
}
