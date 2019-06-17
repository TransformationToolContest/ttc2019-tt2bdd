package org.rosi_project.model_management.sync.compartments

import org.rosi_project.model_management.core.ModelElementLists
import org.rosi_project.model_management.sync.IExtenstionCompartment
import org.rosi_project.model_management.sync.roles.IExtension
import scroll.internal.errors.SCROLLErrors.TypeError

/** Extension to delete instances which are removed from the synchronization context from the
  * [[ModelElementLists]]. This extension therefore functions as some kind of garbage collector,
  * hence the name.
  *
  * @author Rico Bergmann
  */
object ModelElementsListGCExtension extends IExtenstionCompartment {

  /**
    * Return a role instance that handles the extension process for the object.
    */
  override def getExtensionForClassName(classname: Object): IExtension = new GarbageCollector

  /**
    * Return a unique name that describes this extension.
    */
  override def getExtensionName(): String = "ModelElementsList GarbageCollector"

  /** The actual extension.
    */
  class GarbageCollector extends IExtension {

    /**
      * Function to react on insertion behavior.
      */
    override def notifyInsertion(): Unit = {
      ;
    }

    /**
      * Function to react on deletion behavior.
      */
    override def notifyDeletion(): Unit = {      
      val player: Either[TypeError, AnyRef] = this.player

      player.right.foreach { obj =>
        println(s"Removing $obj")
        ModelElementLists.removeElement(obj)
      }

    }

    /**
      * Function to react on update behavior.
      */
    override def notifyUpdate(): Unit = {
      ;
    }
  }

}
