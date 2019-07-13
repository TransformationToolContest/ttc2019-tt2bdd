package org.rosi_project.model_management.sync.compartments

import org.rosi_project.model_management.core.{PlayerSync, SynchronizationCompartment}
import org.rosi_project.model_management.sync.IConstructionCompartment
import org.rosi_project.model_management.sync.roles.{IConstructor, IRoleManager}

/** An [[IConstructionCompartment]] which will not create any related instances in other models
  *
  * @author Rico Bergmann
  */
object SuppressingConstructionCompartment extends IConstructionCompartment {

  override def getConstructorForClassName(classname: Object): IConstructor = new Suppressor

  override def getRuleName: String = "SuppressingConstructionCompartment"
  
  /** The constructor will only create the necessary `plays` relationships with the synchronization
    * services
    */
  class Suppressor extends IConstructor {

    override def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      // just set up the player
      createContainerElement(start=true, con=true, comp, man)
      makeCompleteConstructionProcess(containers)
    }

  }

}
