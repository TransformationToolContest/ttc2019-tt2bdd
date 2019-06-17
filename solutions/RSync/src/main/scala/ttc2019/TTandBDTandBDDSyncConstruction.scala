package ttc2019

import org.rosi_project.model_management.sync.IConstructionCompartment
import org.rosi_project.model_management.sync.roles.IConstructor
import org.rosi_project.model_management.core.PlayerSync
import org.rosi_project.model_management.sync.roles.IRoleManager
import org.rosi_project.model_management.core.SynchronizationCompartment



/**
  * Construction Process for Model  BDD and TT.
  */
object TTandBDTandBDDSyncConstruction extends IConstructionCompartment {

  def getConstructorForClassName(classname: Object): IConstructor = {
    if (classname.isInstanceOf[sync.tt.TruthTable])
      return new TTTruthTableConstruct()
    if (classname.isInstanceOf[sync.bddg.BDD])
      return new BDDBddConstruct()
    if (classname.isInstanceOf[sync.bdd.BDD])
      return new BDTBddConstruct()
    if (classname.isInstanceOf[sync.tt.InputPort])
      return new TTInputPortConstruct()
    if (classname.isInstanceOf[sync.bddg.InputPort])
      return new BDDInputPortConstruct()
    if (classname.isInstanceOf[sync.bdd.InputPort])
      return new BDTInputPortConstruct()
    if (classname.isInstanceOf[sync.tt.OutputPort])
      return new TTOutputPortConstruct()
    if (classname.isInstanceOf[sync.bddg.OutputPort])
      return new BDDOutputPortConstruct()
    if (classname.isInstanceOf[sync.bdd.OutputPort])
      return new BDTOutputPortConstruct()
    return null
  }
  
  def getRuleName: String = "TTandBDTandBDDSyncConstruction"

  class TTTruthTableConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val name: String = +this getName()

      //Step 2: Create the object in the other models
      val dBdd = new sync.bddg.BDD(name, Set.empty, null, Set.empty)
      val tBdd = new sync.bdd.BDD(name, null, Set.empty)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, dBdd, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, tBdd, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class BDDBddConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val name: String = +this getName()

      //Step 2: Create the object in the other models
      val tt = new sync.tt.TruthTable(name, Set.empty, Set.empty, null)
      val tBdd = new sync.bdd.BDD(name, null, Set.empty)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, tt, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, tBdd, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class BDTBddConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val name: String = +this getName()

      //Step 2: Create the object in the other models
      val dBdd = new sync.bddg.BDD(name, Set.empty, null, Set.empty)
      val tt = new sync.tt.TruthTable(name, Set.empty, Set.empty, null)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, dBdd, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, tt, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class TTInputPortConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val name: String = +this getName()

      //Step 2: Create the object in the other models
      val dIn = new sync.bddg.InputPort(Set.empty, name, null)
      val tIn = new sync.bdd.InputPort(Set.empty, name, null)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, dIn, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, tIn, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class BDDInputPortConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val name: String = +this getName()

      //Step 2: Create the object in the other models
      val ttIn = new sync.tt.InputPort(name, Set.empty, null, null)
      val tIn = new sync.bdd.InputPort(Set.empty, name, null)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, ttIn, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, tIn, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class BDTInputPortConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val name: String = +this getName()

      //Step 2: Create the object in the other models
      val dIn = new sync.bddg.InputPort(Set.empty, name, null)
      val ttIn = new sync.tt.InputPort(name, Set.empty, null, null)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, dIn, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, ttIn, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class TTOutputPortConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val name: String = +this getName()

      //Step 2: Create the object in the other models
      val dOut = new sync.bddg.OutputPort(Set.empty, name, null)
      val tOut = new sync.bdd.OutputPort(Set.empty, name, null)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, dOut, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, tOut, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class BDDOutputPortConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val name: String = +this getName()

      //Step 2: Create the object in the other models
      val ttOut = new sync.tt.OutputPort(name, Set.empty, null, null)
      val tOut = new sync.bdd.OutputPort(Set.empty, name, null)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, ttOut, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, tOut, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class BDTOutputPortConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val name: String = +this getName()

      //Step 2: Create the object in the other models
      val dOut = new sync.bddg.OutputPort(Set.empty, name, null)
      val ttOut = new sync.tt.OutputPort(name, Set.empty, null, null)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, dOut, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, ttOut, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class TTRowConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //TODO: modify the whole tree
      //TODO: Cell construct does not make sense directly
    }
  }

}