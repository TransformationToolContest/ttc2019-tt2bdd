package org.rosi_project.model_management.sync

import org.rosi_project.model_management.sync.roles.ISyncRole
import scroll.internal.Compartment

import scala.collection.immutable.Set

/**
  * Interface for each synchronization rule.
  */
trait ISyncCompartment extends Compartment {

  /**
    * Variable to proof if he is actual in a sync process.
    */
  protected var doSync = false
  /**
    * All sync roles of this synchronization rule.
    */
  private var syncer: Set[ISyncRole] = Set.empty

  /**
    * Get roles for all integration classes.
    */
  protected def getNextRole(classname: Object) : ISyncRole = {
    getFirstRole(classname)
  }
  
  /**
    * Get roles for integration classes. Should give less roles than getNextRole.
    */
  protected def getFirstRole(classname: Object) : ISyncRole
  
  def containsSyncer(value: Object): Boolean = {
    syncer.foreach { s =>
      if (+s == +value) {
      //if (s.player.equals(value.player)) {
        return true;
      }
    }
    return false
  }

  def addSyncer(sync: ISyncRole): Unit = {
    syncer += sync
  }

  /**
    * Get the list of all sync roles.
    */
  def getSyncer(): Set[ISyncRole] = syncer

  /**
    * Clear the list of all sync roles.
    */
  def clearSyncer(): Unit = {
    syncer = Set.empty
  }

  /**
    * Get roles for integration classes. Should give less roles than getNextRole.
    */
  def getFirstIntegrationRole(classname: Object) : ISyncRole = {
    val role: ISyncRole = this.getFirstRole(classname)
    if (role != null)
      this.addSyncer(role)
    role
  }

  /**
    * Get all roles for integration classes.
    */
  def getNextIntegrationRole(classname: Object) : ISyncRole = {
    val role: ISyncRole = this.getNextRole(classname)
    if (role != null)
      this.addSyncer(role)
    role
  }

  /**
    * Get roles for all integration classes.
    */
  def isFirstIntegration(classname: Object): Boolean

  /**
    * Get boolean if next integration
    */
  def isNextIntegration(classname: Object): Boolean = {
    isFirstIntegration(classname)
  }

  /**
    * Create a new instance of this class.
    */
  def getNewInstance: ISyncCompartment

  /**
    * Get the name of this rule.
    */
  def getRuleName: String
}
