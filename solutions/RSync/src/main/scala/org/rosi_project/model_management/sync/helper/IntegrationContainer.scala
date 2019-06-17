package org.rosi_project.model_management.sync.helper

import org.rosi_project.model_management.sync.roles.IRoleManager
import org.rosi_project.model_management.core.PlayerSync

/**
  * Helper class for all integration processes to manage standard work loads.
  */
class IntegrationContainer (val newPlayerInstance: PlayerSync, val oldPlayerInstance: PlayerSync) {
  
  var simpleRelatedManagerConnection: Boolean = true
  var newManagerConnection: Boolean = true
  var newManagerInstance: IRoleManager = null
  
  /**
   * Get the new PlayerSync instance of this element.
   */
  def getNewPlayerInstance(): PlayerSync = newPlayerInstance
  
  /**
   * Get the new RoleManager instance of this element
   */
  def getNewManagerInstance(): IRoleManager = newManagerInstance
  
  /**
   * Get the old PlayerSync instance of this element.
   */
  def getOldPlayerInstance(): PlayerSync = oldPlayerInstance  
}