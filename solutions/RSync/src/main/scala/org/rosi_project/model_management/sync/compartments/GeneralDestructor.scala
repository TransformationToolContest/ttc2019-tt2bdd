package org.rosi_project.model_management.sync.compartments

import org.rosi_project.model_management.core.PlayerSync
import org.rosi_project.model_management.sync.IDestructionCompartment
import org.rosi_project.model_management.sync.roles.{ IDestructor, IRoleManager }

import scala.collection.mutable.ListBuffer

/**
 * Calls the destruction method from all related RoleManagers and then deletes all roles from this player.
 */
object GeneralDestructor extends IDestructionCompartment {

  override def getDestructorForClassName(classname: Object): IDestructor = new DeleteRole

  override def getRuleName: String = "GeneralDestructor"

  class DeleteRole() extends IDestructor {

    def deleteRoleFunction(): Unit = {
      println("##Delete roles and related from Player: " + this.player)

      var relatedManagers: Set[IRoleManager] = (+this).getRelatedManager()
      (+this).clearListsOfRelatedManager()

      relatedManagers.foreach { m =>
        (+m).deleteObjectFromSynchro()
      }

      (+this).deletionNotification()

      //clear now the related manager list
      (+this).clearRelatedManager()
      //delete all roles this element has
      val player = this.player
      if (player.isRight) {
        val test: PlayerSync = player.right.get.asInstanceOf[PlayerSync]
        val roles = test.roles()
        roles.foreach { r =>
          r.remove()
        }
      }
    }
  }
}
