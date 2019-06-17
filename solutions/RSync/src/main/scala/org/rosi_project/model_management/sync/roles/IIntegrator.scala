package org.rosi_project.model_management.sync.roles

import scala.collection.mutable.ListBuffer
import org.rosi_project.model_management.sync.helper.IntegrationContainer
import org.rosi_project.model_management.core.PlayerSync
import org.rosi_project.model_management.core.SynchronizationCompartment

/**
  * Interface for the integration roles.
  */
trait IIntegrator {

  /**
   * Container list for the integration process.
   */
  protected var containers = ListBuffer[IntegrationContainer]()

  /**
   * Create a container element with the incoming configuration.
   */
  protected def createContainerElement(newPlayer: PlayerSync, oldPlayer: PlayerSync): Unit = {
    if (oldPlayer == null || oldPlayer == null)
      return
    var cc = new IntegrationContainer(newPlayer, oldPlayer)
    containers += cc
  }
  
  /**
   * General integration function for external call.
   */
  def integrate(comp: PlayerSync) : PlayerSync
}