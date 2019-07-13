package ttc2019.worksync

import org.rosi_project.model_management.sync.ISyncCompartment
import org.rosi_project.model_management.sync.roles.ISyncRole

/**
  * Synchronization compartment for port names.
  */
class SyncPortNamesSync extends ISyncCompartment {

  override def getNextRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sync.bdd.Port] || classname.isInstanceOf[sync.bddg.Port] || classname.isInstanceOf[sync.tt.Port])
      return new Sync()
    return null
  }

  def getFirstRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sync.tt.Port])
      return new Sync()
    return null
  }

  override def isNextIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sync.bdd.Port] || classname.isInstanceOf[sync.bddg.Port] || classname.isInstanceOf[sync.tt.Port])
      return true
    return false
  }

  def isFirstIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sync.tt.Port])
      return true
    return false
  }

  def getNewInstance(): ISyncCompartment = new SyncPortNamesSync

  def getRuleName(): String = "SyncPortNamesSync"

  class Sync() extends ISyncRole {

    def getOuterCompartment(): ISyncCompartment = SyncPortNamesSync.this

    def changeName(): Unit = {
      if (!doSync) {
        doSync = true;
        var name: String = +this getName();
        getSyncer().foreach { a =>
          if (!a.equals(this)) {
            (+a).setName(name);
          }
        }
        doSync = false;
      }
    }    
  }

}