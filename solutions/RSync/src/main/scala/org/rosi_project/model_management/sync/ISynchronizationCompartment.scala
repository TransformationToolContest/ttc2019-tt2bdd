package org.rosi_project.model_management.sync

import org.rosi_project.model_management.sync.roles.IRoleManager
import scroll.internal.MultiCompartment

/**
  * Interface for the synchronization management compartment.
  */
trait ISynchronizationCompartment extends MultiCompartment {

  protected var underConstruction: Boolean = false

  /**
    * Is currently in a process where new elements could be created.
    */
  def isUnderConstruction(): Boolean = underConstruction

  /**
    * Get a new RoleManager instance.
    */
  def createRoleManager(): IRoleManager
}
