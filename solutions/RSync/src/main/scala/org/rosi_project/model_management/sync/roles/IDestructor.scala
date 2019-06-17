package org.rosi_project.model_management.sync.roles

/**
  * Interface for the destructor roles.
  */
trait IDestructor {
  
  /**
    * General destruction function for external call.
    */
  def deleteRoleFunction(): Unit
}
