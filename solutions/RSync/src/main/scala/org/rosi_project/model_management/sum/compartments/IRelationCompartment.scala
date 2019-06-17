package org.rosi_project.model_management.sum.compartments

import scroll.internal.Compartment
import org.rosi_project.model_management.sum.roles.IRelationRole
import org.rosi_project.model_management.core.PlayerSync

trait IRelationCompartment extends PlayerSync {
  
  protected var source: ISource = null;
  protected var target: ITarget = null;
  
  //TODO: change again to protected
  def internalInitialize(): Unit
  
  def getTarget(): ITarget = target
  
  def getSource(): ISource = source
  
  def initialize(): Unit = {
    if (source == null && target == null)
      internalInitialize()
  }
  
  def deleteCompartment () : Unit = {  
    //println("DeletCompartment IRelationCompartment")
    if (source != null)
    {
      source.remove()
      source = null;
    }
    if (target != null)
    {
      target.remove()
      target = null;
    }
    //+this deleteEverything()
  }
  
  trait ISource extends IRelationRole {    
    def getTarget (): ITarget = IRelationCompartment.this.target
    private[model_management] def removeRole(): Unit = +IRelationCompartment.this deleteEverything()
    override def getOuterCompartment(): IRelationCompartment = IRelationCompartment.this
  }
  
  trait ITarget extends IRelationRole {    
    private[model_management] def removeRole(): Unit = +IRelationCompartment.this deleteEverything()
    override def getOuterCompartment(): IRelationCompartment = IRelationCompartment.this
  }
}