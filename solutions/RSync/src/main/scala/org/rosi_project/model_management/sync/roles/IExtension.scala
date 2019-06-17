package org.rosi_project.model_management.sync.roles

/**
  * Interface for the extension roles.
  */
trait IExtension {
  
  /**
   * Function to react on insertion behavior.
   */
  def notifyInsertion(): Unit
  
  /**
   * Function to react on deletion behavior.
   */
  def notifyDeletion(): Unit
  
  /**
   * Function to react on update behavior.
   */
  def notifyUpdate(): Unit
}