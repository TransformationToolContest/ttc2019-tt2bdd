package org.rosi_project.model_management.sync.roles

import org.rosi_project.model_management.core.PlayerSync
import org.rosi_project.model_management.sync.helper.ConstructionContainer

/**
  * Interface for the constructor roles.
  */
trait IConstructor {

  /**
    * Container list for the construction process.
    */
  protected var containers: Set[ConstructionContainer] = Set.empty

  /**
    * Create a container element with the incoming configuration.
    */
  protected def createContainerElement(start: Boolean, con: Boolean, play: PlayerSync, man: IRoleManager): Unit = {
    if (play == null)
      return
    containers += new ConstructionContainer(start, con, play, man)
  }

  /**
    * General construction function for external call.
    */
  def construct(comp: PlayerSync, man: IRoleManager): Unit
}
