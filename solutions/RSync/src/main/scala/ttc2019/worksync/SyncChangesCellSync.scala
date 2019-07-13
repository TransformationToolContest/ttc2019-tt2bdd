package ttc2019.worksync

import org.rosi_project.model_management.sync.ISyncCompartment
import org.rosi_project.model_management.sync.roles.ISyncRole
import org.rosi_project.model_management.core.PlayerSync

class SyncChangesCellSync() extends ISyncCompartment {

  def getFirstRole(classname: Object): ISyncRole = {
    if (classname.isInstanceOf[sync.tt.Cell])
      return new Sync()
    return null
  }

  def isFirstIntegration(classname: Object): Boolean = {
    if (classname.isInstanceOf[sync.tt.Cell])
      return true
    return false
  }

  def getNewInstance(): ISyncCompartment = new SyncChangesCellSync

  def getRuleName(): String = "SyncChangesCellSync"

  class Sync() extends ISyncRole {

    def getOuterCompartment(): ISyncCompartment = SyncChangesCellSync.this

    /**
     * Rule which add ports to its root in each model.
     */
    def syncSetPort(): Unit = {
      val port: sync.tt.Port = +this getPort ()
      if (port.isInstanceOf[sync.tt.OutputPort] && !doSync) {
        doSync = true;
        //get value from cell
        val value: Boolean = +this getValue ()
        //get needed ports
        val opTreePort: PlayerSync = +port getRelatedClassFromName ("sync.bdd.Port")
        //val opDiaPort: PlayerSync = +port getRelatedClassFromName ("sync.bddg.Port")
        //create assignments
        if (opTreePort != null) {
          val o_port = opTreePort.asInstanceOf[sync.bdd.OutputPort]
          val assignment = new sync.bdd.Assignment(value, null, null)

          o_port.addAssignments(assignment)
          assignment.setPort(o_port)
          //trace link
          +this makePlayerSyncRelated (assignment)
        }
        /*if (opDiaPort != null) {
          val o_port = opDiaPort.asInstanceOf[sync.bddg.OutputPort]
          val assignment = new sync.bddg.Assignment(value, null, null)

          o_port.addAssignments(assignment)
          assignment.setPort(o_port)
          //trace link
          +this makePlayerSyncRelated (assignment)
        }*/
        doSync = false;
      }
    }
    
    /*def syncSetValue(): Unit = {
      if (!doSync) {
        doSync = true;
        var value: Boolean = +this getValue ();
        syncer.foreach { a =>
          if (!a.equals(this)) {
            (+a).setValue(value);
          }
        }
        doSync = false;
      }
    }*/
  }

}