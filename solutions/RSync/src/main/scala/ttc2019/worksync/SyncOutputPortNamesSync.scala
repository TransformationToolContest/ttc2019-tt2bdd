package ttc2019.worksync

import org.rosi_project.model_management.sync.ISyncCompartment
import org.rosi_project.model_management.sync.roles.ISyncRole

/**
  * Synchronization compartment for full name split with space.
  */
class SyncOutputPortNamesSync() extends ISyncCompartment {

  def getNextRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sync.bdd.OutputPort] || classname.isInstanceOf[sync.bddg.OutputPort] || classname.isInstanceOf[sync.tt.OutputPort])
      return new Sync()
    return null
  }

  def getFirstRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sync.bdd.OutputPort] || classname.isInstanceOf[sync.bddg.OutputPort] || classname.isInstanceOf[sync.tt.OutputPort])
      return new Sync()
    return null
  }

  def isNextIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sync.bdd.OutputPort] || classname.isInstanceOf[sync.bddg.OutputPort] || classname.isInstanceOf[sync.tt.OutputPort])
      return true
    return false
  }

  def isFirstIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sync.bdd.OutputPort] || classname.isInstanceOf[sync.bddg.OutputPort] || classname.isInstanceOf[sync.tt.OutputPort])
      return true
    return false
  }

  def getNewInstance(): ISyncCompartment = new SyncOutputPortNamesSync

  def getRuleName(): String = "SyncOutputPortNamesSync"

  class Sync() extends ISyncRole {

    def getOuterCompartment(): ISyncCompartment = SyncOutputPortNamesSync.this

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