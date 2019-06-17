package ttc2019

import org.rosi_project.model_management.sync.ISyncCompartment
import org.rosi_project.model_management.sync.roles.ISyncRole

/**
  * Synchronization compartment for full name split with space.
  */
class SyncHeadNamesSync() extends ISyncCompartment {

  def getNextRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sync.bdd.BDD] || classname.isInstanceOf[sync.bddg.BDD] || classname.isInstanceOf[sync.tt.TruthTable])
      return new Sync()
    return null
  }

  def getFirstRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sync.bdd.BDD] || classname.isInstanceOf[sync.bddg.BDD] || classname.isInstanceOf[sync.tt.TruthTable])
      return new Sync()
    return null
  }

  def isNextIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sync.bdd.BDD] || classname.isInstanceOf[sync.bddg.BDD] || classname.isInstanceOf[sync.tt.TruthTable])
      return true
    return false
  }

  def isFirstIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sync.bdd.BDD] || classname.isInstanceOf[sync.bddg.BDD] || classname.isInstanceOf[sync.tt.TruthTable])
      return true
    return false
  }

  def getNewInstance(): ISyncCompartment = new SyncHeadNamesSync

  def getRuleName(): String = "SyncHeadNamesSync"

  class Sync() extends ISyncRole {

    def getOuterCompartment(): ISyncCompartment = SyncHeadNamesSync.this

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