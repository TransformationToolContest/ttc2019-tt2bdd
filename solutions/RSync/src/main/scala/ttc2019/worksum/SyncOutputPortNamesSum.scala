package ttc2019.worksum

import org.rosi_project.model_management.sync.ISyncCompartment
import org.rosi_project.model_management.sync.roles.ISyncRole

/**
  * Synchronization compartment for full name split with space.
  */
class SyncOutputPortNamesSum() extends ISyncCompartment {

  def getNextRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sum.bdd.OutputPort] || classname.isInstanceOf[sum.bddg.OutputPort] || classname.isInstanceOf[sum.tt.OutputPort])
      return new Sync()
    return null
  }

  def getFirstRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sum.bdd.OutputPort] || classname.isInstanceOf[sum.bddg.OutputPort] || classname.isInstanceOf[sum.tt.OutputPort])
      return new Sync()
    return null
  }

  def isNextIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sum.bdd.OutputPort] || classname.isInstanceOf[sum.bddg.OutputPort] || classname.isInstanceOf[sum.tt.OutputPort])
      return true
    return false
  }

  def isFirstIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sum.bdd.OutputPort] || classname.isInstanceOf[sum.bddg.OutputPort] || classname.isInstanceOf[sum.tt.OutputPort])
      return true
    return false
  }

  def getNewInstance(): ISyncCompartment = new SyncOutputPortNamesSum

  def getRuleName(): String = "SyncOutputPortNamesSum"

  class Sync() extends ISyncRole {

    def getOuterCompartment(): ISyncCompartment = SyncOutputPortNamesSum.this

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