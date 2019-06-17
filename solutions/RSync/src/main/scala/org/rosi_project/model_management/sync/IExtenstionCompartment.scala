package org.rosi_project.model_management.sync

import org.rosi_project.model_management.sync.roles.IExtension
import scroll.internal.Compartment

/**
  * Interface for each extension rule.
  */
trait IExtenstionCompartment extends Compartment {
  
  /**
    * Return a role instance that handles the extension process for the object.
    */
  def getExtensionForClassName(classname: Object) : IExtension
  
  /**
   * Return a unique name that describes this extension.
   */
  def getExtensionName() : String
}