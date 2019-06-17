package org.rosi_project.model_management.sync.roles

import org.rosi_project.model_management.core.PlayerSync
import org.rosi_project.model_management.sync.helper.ConstructionContainer
import scala.collection.mutable.ListBuffer

/**
  * Interface for the constructor roles.
  */
trait IConstructor {

  /**
    * Container list for the construction process.
    */
  protected var containers: ListBuffer[ConstructionContainer] = ListBuffer[ConstructionContainer]()

  /**
    * Create a container element with the incoming configuration.
    */
  protected def createContainerElement(start: Boolean, con: Boolean, play: PlayerSync, man: IRoleManager): Unit = {
    if (play == null)
      return
    var cc = new ConstructionContainer(start, con, play, man)
    containers += cc
  }

  /**
    * General construction function for external call.
    */
  def construct(comp: PlayerSync, man: IRoleManager): Unit
}
