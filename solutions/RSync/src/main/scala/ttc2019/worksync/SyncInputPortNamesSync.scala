package ttc2019.worksync

import org.rosi_project.model_management.sync.ISyncCompartment
import org.rosi_project.model_management.sync.roles.ISyncRole

/**
  * Synchronization compartment for full name split with space.
  */
class SyncInputPortNamesSync() extends ISyncCompartment {

  def getNextRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sync.bdd.InputPort] || classname.isInstanceOf[sync.bddg.InputPort] || classname.isInstanceOf[sync.tt.InputPort])
      return new Sync()
    return null
  }

  def getFirstRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sync.bdd.InputPort] || classname.isInstanceOf[sync.bddg.InputPort] || classname.isInstanceOf[sync.tt.InputPort])
      return new Sync()
    return null
  }

  def isNextIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sync.bdd.InputPort] || classname.isInstanceOf[sync.bddg.InputPort] || classname.isInstanceOf[sync.tt.InputPort])
      return true
    return false
  }

  def isFirstIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sync.bdd.InputPort] || classname.isInstanceOf[sync.bddg.InputPort] || classname.isInstanceOf[sync.tt.InputPort])
      return true
    return false
  }

  def getNewInstance(): ISyncCompartment = new SyncInputPortNamesSync

  def getRuleName(): String = "SyncInputPortNamesSync"

  class Sync() extends ISyncRole {

    def getOuterCompartment(): ISyncCompartment = SyncInputPortNamesSync.this

    def changeName(): Unit = {
      if (!doSync) {
        doSync = true;
        var name: String = +this getName();
        syncer.foreach { a =>
          if (!a.equals(this)) {
            (+a).setName(name);
          }
        }
        doSync = false;
      }
    }    
  }

}