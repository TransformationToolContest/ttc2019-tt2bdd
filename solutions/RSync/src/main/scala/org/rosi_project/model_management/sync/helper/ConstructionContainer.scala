package org.rosi_project.model_management.sync.helper

import org.rosi_project.model_management.sync.roles.IRoleManager
import org.rosi_project.model_management.core.PlayerSync

/**
  * Helper class for all construction processes to manage standard work loads.
  */
class ConstructionContainer(val startElement: Boolean, val constructed: Boolean, val player: PlayerSync, val manager: IRoleManager) {
  /**
    * Returns true if it is the start construction element.
    */
  def isStartElement(): Boolean = startElement

  /**
    * Return true if it is new constructed.
    */
  def isConstructed(): Boolean = constructed

  /**
    * Get the PlayerSync instance of this element.
    */
  def getPlayerInstance(): PlayerSync = player

  /**
    * Get the RoleManager instance of this element
    */
  def getManagerInstance(): IRoleManager = manager
}
