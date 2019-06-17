package ttc2019

import org.rosi_project.model_management.sync.IConstructionCompartment
import org.rosi_project.model_management.sync.roles.IConstructor
import org.rosi_project.model_management.core.PlayerSync
import org.rosi_project.model_management.sync.roles.IRoleManager
import org.rosi_project.model_management.core.SynchronizationCompartment
import org.rosi_project.model_management.sum.roles.IRelationRole



/**
  * Construction Process for Model  BDD and TT.
  */
object TTandBDTandBDDSumConstruction extends IConstructionCompartment {

  def getConstructorForClassName(classname: Object): IConstructor = {
    if (classname.isInstanceOf[sum.tt.TruthTable])
      return new TTTruthTableConstruct()
    if (classname.isInstanceOf[sum.bddg.BDD])
      return new BDDBddConstruct()
    if (classname.isInstanceOf[sum.bdd.BDD])
      return new BDTBddConstruct()
    
    if (classname.isInstanceOf[sum.tt.InputPort])
      return new TTInputPortConstruct()
    if (classname.isInstanceOf[sum.bddg.InputPort])
      return new BDDInputPortConstruct()
    if (classname.isInstanceOf[sum.bdd.InputPort])
      return new BDTInputPortConstruct()
    
    if (classname.isInstanceOf[sum.tt.OutputPort])
      return new TTOutputPortConstruct()
    if (classname.isInstanceOf[sum.bddg.OutputPort])
      return new BDDOutputPortConstruct()
    if (classname.isInstanceOf[sum.bdd.OutputPort])
      return new BDTOutputPortConstruct()
    
    if (classname.isInstanceOf[sum.tt.TruthTablePortsPort])
      return new TTPortsConnectionConstruct()
    if (classname.isInstanceOf[sum.bddg.BDDPortsPort])
      return new BDDPortsConnectionConstruct()
    if (classname.isInstanceOf[sum.bdd.BDDPortsPort])
      return new BDTPortsConnectionConstruct()
    return null
  }
  
  def getRuleName: String = "TTandBDTandBDDSyncConstruction"

  class TTTruthTableConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val name: String = +this getName()

      //Step 2: Create the object in the other models
      val dBdd = new sum.bddg.BDD(name)
      val tBdd = new sum.bdd.BDD(name)

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
      val tt = new sum.tt.TruthTable(name, null)
      val tBdd = new sum.bdd.BDD(name)

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
      val dBdd = new sum.bddg.BDD(name)
      val tt = new sum.tt.TruthTable(name, null)

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
      val dIn = new sum.bddg.InputPort(name)
      val tIn = new sum.bdd.InputPort(name)

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
      val ttIn = new sum.tt.InputPort(name, null)
      val tIn = new sum.bdd.InputPort(name)

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
      val dIn = new sum.bddg.InputPort(name)
      val ttIn = new sum.tt.InputPort(name, null)

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
      val dOut = new sum.bddg.OutputPort(name)
      val tOut = new sum.bdd.OutputPort(name)

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
      val ttOut = new sum.tt.OutputPort(name, null)
      val tOut = new sum.bdd.OutputPort(name)

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
      val dOut = new sum.bddg.OutputPort(name)
      val ttOut = new sum.tt.OutputPort(name, null)

      //Step 3: Create Containers 
      createContainerElement(true, true, comp, man)
      createContainerElement(false, true, dOut, SynchronizationCompartment.createRoleManager())
      createContainerElement(false, true, ttOut, SynchronizationCompartment.createRoleManager())

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class TTPortsConnectionConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val source: IRelationRole = +this getSource ()
      val target: IRelationRole = +this getTarget ()
      
      createContainerElement(true, true, comp, man)

      val oppSoBDT: PlayerSync = +source getRelatedClassFromName ("bdd.BDD")
      val oppTaBDT: PlayerSync = +target getRelatedClassFromName ("bdd.Port")
      
      //Step 2 and 3
      if (oppSoBDT != null && oppTaBDT != null) {
        val s = oppSoBDT.asInstanceOf[sum.bdd.BDD]
        val t = oppTaBDT.asInstanceOf[sum.bdd.Port]

        //Step 2: Create the object in the other models
        val rel = new sum.bdd.BDDPortsPort(s, t)
        rel.initialize()

        //Step 3: Make Connection
        createContainerElement(false, true, rel, SynchronizationCompartment.createRoleManager())
      }
      
      val oppSoBDD: PlayerSync = +source getRelatedClassFromName ("bddg.BDD")
      val oppTaBDD: PlayerSync = +target getRelatedClassFromName ("bddg.Port")
      
      //Step 2 and 3
      if (oppSoBDD != null && oppTaBDD != null) {
        val s = oppSoBDD.asInstanceOf[sum.bddg.BDD]
        val t = oppTaBDD.asInstanceOf[sum.bddg.Port]

        //Step 2: Create the object in the other models
        val rel = new sum.bddg.BDDPortsPort(s, t)
        rel.initialize()

        //Step 3: Make Connection
        createContainerElement(false, true, rel, SynchronizationCompartment.createRoleManager())
      }

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class BDDPortsConnectionConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val source: IRelationRole = +this getSource ()
      val target: IRelationRole = +this getTarget ()
      
      createContainerElement(true, true, comp, man)

      val oppSoBDT: PlayerSync = +source getRelatedClassFromName ("bdd.BDD")
      val oppTaBDT: PlayerSync = +target getRelatedClassFromName ("bdd.Port")
      
      //Step 2 and 3
      if (oppSoBDT != null && oppTaBDT != null) {
        val s = oppSoBDT.asInstanceOf[sum.bdd.BDD]
        val t = oppTaBDT.asInstanceOf[sum.bdd.Port]

        //Step 2: Create the object in the other models
        val rel = new sum.bdd.BDDPortsPort(s, t)
        rel.initialize()

        //Step 3: Make Connection
        createContainerElement(false, true, rel, SynchronizationCompartment.createRoleManager())
      }
      
      val oppSoTT: PlayerSync = +source getRelatedClassFromName ("tt.TruthTable")
      val oppTaTT: PlayerSync = +target getRelatedClassFromName ("tt.Port")
      
      //Step 2 and 3
      if (oppSoTT != null && oppTaTT != null) {
        val s = oppSoTT.asInstanceOf[sum.tt.TruthTable]
        val t = oppTaTT.asInstanceOf[sum.tt.Port]

        //Step 2: Create the object in the other models
        val rel = new sum.tt.TruthTablePortsPort(s, t)
        rel.initialize()

        //Step 3: Make Connection
        createContainerElement(false, true, rel, SynchronizationCompartment.createRoleManager())
      }

      //Step 4: Finish Creation
      makeCompleteConstructionProcess(containers)
    }
  }
  
  class BDTPortsConnectionConstruct() extends IConstructor {

    def construct(comp: PlayerSync, man: IRoleManager): Unit = {
      //Step 1: Get construction values
      val source: IRelationRole = +this getSource ()
      val target: IRelationRole = +this getTarget ()
      
      createContainerElement(true, true, comp, man)

      val oppSoTT: PlayerSync = +source getRelatedClassFromName ("tt.TruthTable")
      val oppTaTT: PlayerSync = +target getRelatedClassFromName ("tt.Port")
      
      //Step 2 and 3
      if (oppSoTT != null && oppTaTT != null) {
        val s = oppSoTT.asInstanceOf[sum.tt.TruthTable]
        val t = oppTaTT.asInstanceOf[sum.tt.Port]

        //Step 2: Create the object in the other models
        val rel = new sum.tt.TruthTablePortsPort(s, t)
        rel.initialize()

        //Step 3: Make Connection
        createContainerElement(false, true, rel, SynchronizationCompartment.createRoleManager())
      }
      
      val oppSoBDD: PlayerSync = +source getRelatedClassFromName ("bddg.BDD")
      val oppTaBDD: PlayerSync = +target getRelatedClassFromName ("bddg.Port")
      
      //Step 2 and 3
      if (oppSoBDD != null && oppTaBDD != null) {
        val s = oppSoBDD.asInstanceOf[sum.bddg.BDD]
        val t = oppTaBDD.asInstanceOf[sum.bddg.Port]

        //Step 2: Create the object in the other models
        val rel = new sum.bddg.BDDPortsPort(s, t)
        rel.initialize()

        //Step 3: Make Connection
        createContainerElement(false, true, rel, SynchronizationCompartment.createRoleManager())
      }

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