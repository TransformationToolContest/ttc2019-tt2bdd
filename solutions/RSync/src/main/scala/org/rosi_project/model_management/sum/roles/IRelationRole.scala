package org.rosi_project.model_management.sum.roles

import org.rosi_project.model_management.sum.compartments.IRelationCompartment

trait IRelationRole extends IRsumRole {
  
  //TODO: look if this method needs different visibility
  def getOuterCompartment() : IRelationCompartment
}