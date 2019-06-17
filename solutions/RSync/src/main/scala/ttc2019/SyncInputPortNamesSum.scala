package ttc2019

import org.rosi_project.model_management.sync.ISyncCompartment
import org.rosi_project.model_management.sync.roles.ISyncRole

/**
  * Synchronization compartment for full name split with space.
  */
class SyncInputPortNamesSum() extends ISyncCompartment {

  def getNextRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sum.bdd.InputPort] || classname.isInstanceOf[sum.bddg.InputPort] || classname.isInstanceOf[sum.tt.InputPort])
      return new Sync()
    return null
  }

  def getFirstRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sum.bdd.InputPort] || classname.isInstanceOf[sum.bddg.InputPort] || classname.isInstanceOf[sum.tt.InputPort])
      return new Sync()
    return null
  }

  def isNextIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sum.bdd.InputPort] || classname.isInstanceOf[sum.bddg.InputPort] || classname.isInstanceOf[sum.tt.InputPort])
      return true
    return false
  }

  def isFirstIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sum.bdd.InputPort] || classname.isInstanceOf[sum.bddg.InputPort] || classname.isInstanceOf[sum.tt.InputPort])
      return true
    return false
  }

  def getNewInstance(): ISyncCompartment = new SyncInputPortNamesSum

  def getRuleName(): String = "SyncInputPortNamesSum"

  class Sync() extends ISyncRole {

    def getOuterCompartment(): ISyncCompartment = SyncInputPortNamesSum.this

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