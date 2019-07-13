package org.rosi_project.model_management.sync.roles

import org.rosi_project.model_management.core.PlayerSync

import scala.collection.mutable.ListBuffer

/**
 * Interface for the manager roles.
 */
trait IRoleManager {

  private var relatedManager: Set[IRoleManager] = Set.empty

  /**
   * Add a related manager to the list.
   */
  def addRelatedManager(related: IRoleManager): Unit = {
    if (related == null || related.equals(this))
      return
    relatedManager += related
  }

  /**
   * Get the list of related managers.
   */
  def getRelatedManager(): Set[IRoleManager] = relatedManager

  /**
   * Get this manager.
   */
  def getManager(): IRoleManager = this

  /**
   * Get this manager plus related manager.
   */
  def getAllManager(): Set[IRoleManager] = relatedManager + this

  /**
   * Remove a related manager from the list.
   */
  def removeRelatedManager(related: IRoleManager): Unit = {
    if (related != null)
      relatedManager -= related
  }

  /**
   * Remove this manager from the lists of all related managers.
   */
  def removeThisManager(): Unit = {
    relatedManager.foreach { m =>
      m.removeRelatedManager(this)
    }
  }

  /**
   * Clear the lists of all related managers,
   */
  def clearListsOfRelatedManager(): Unit = {
    relatedManager.foreach { m =>
      m.clearRelatedManager()
    }
  }

  /**
   * Clear the list of this role manager.
   */
  def clearRelatedManager(): Unit = {
    relatedManager = Set.empty
  }

  /**
   * Create a relation between two IRoleManager instances.
   */
  def makeRelated(relate: IRoleManager): Unit = {
    this.addRelatedManager(relate)
    relate.addRelatedManager(this)
  }

  /**
   * General manage function for external call.
   */
  def manage(value: PlayerSync): Unit

  /**
   * Function to manage the deletion.
   */
  def deleteManage(value: PlayerSync): Unit

  /**
   * Get related PlayerSync with the specific name.
   */
  def getRelatedClassFromName(name: String): PlayerSync

  /**
   * Create a relation between two IRoleManager and RoleManager of other PlayerSync instances.
   */
  def makePlayerSyncRelated(playerSync: PlayerSync): Unit

  /**
   * Print all Manager only for debug.
   */
  def printAllManager(): Unit
}
