package ttc2019

import org.rosi_project.model_management.sync.ISyncCompartment
import org.rosi_project.model_management.sync.roles.ISyncRole

/**
  * Synchronization compartment for full name split with space.
  */
class SyncHeadNamesSum() extends ISyncCompartment {

  def getNextRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sum.bdd.BDD] || classname.isInstanceOf[sum.bddg.BDD] || classname.isInstanceOf[sum.tt.TruthTable])
      return new Sync()
    return null
  }

  def getFirstRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sum.bdd.BDD] || classname.isInstanceOf[sum.bddg.BDD] || classname.isInstanceOf[sum.tt.TruthTable])
      return new Sync()
    return null
  }

  def isNextIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sum.bdd.BDD] || classname.isInstanceOf[sum.bddg.BDD] || classname.isInstanceOf[sum.tt.TruthTable])
      return true
    return false
  }

  def isFirstIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sum.bdd.BDD] || classname.isInstanceOf[sum.bddg.BDD] || classname.isInstanceOf[sum.tt.TruthTable])
      return true
    return false
  }

  def getNewInstance(): ISyncCompartment = new SyncHeadNamesSum

  def getRuleName(): String = "SyncHeadNamesSum"

  class Sync() extends ISyncRole {

    def getOuterCompartment(): ISyncCompartment = SyncHeadNamesSum.this

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