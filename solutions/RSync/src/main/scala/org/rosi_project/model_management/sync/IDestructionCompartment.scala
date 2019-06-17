package org.rosi_project.model_management.sync

import org.rosi_project.model_management.sync.roles.IDestructor
import scroll.internal.Compartment

/**
  * Interface for each destruction rule.
  */
trait IDestructionCompartment extends Compartment {

  /**
    * Return a role instance that handles the destruction process for the object.
    */
  def getDestructorForClassName(classname: Object) : IDestructor
  
  def getRuleName: String
  
}
