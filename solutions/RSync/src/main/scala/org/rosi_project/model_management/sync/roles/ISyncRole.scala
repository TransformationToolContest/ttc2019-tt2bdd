package org.rosi_project.model_management.sync.roles

import org.rosi_project.model_management.sync.ISyncCompartment

/**
  * Interface for the synchronization roles.
  */
trait ISyncRole {
  
  /**
    * Function to get the synchronization compartment from a role instance.
    */
  def getOuterCompartment: ISyncCompartment
}
