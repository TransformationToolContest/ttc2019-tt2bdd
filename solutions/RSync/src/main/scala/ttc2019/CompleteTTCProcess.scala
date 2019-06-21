package ttc2019

import org.rosi_project.model_management.core.SynchronizationCompartment
import org.rosi_project.model_management.core.RsumCompartment
import org.rosi_project.model_management.core.ModelElementLists
import org.rosi_project.model_management.sync.IIntegrationCompartment
import ttc2019.metamodels.tt.TruthTable
import ttc2019.worksync._
import ttc2019.worksum._
import ttc2019.metamodels.create.Launcher

/** The `CompleteTTCProcess` executes the entire transformation workflow. Its methods are inspired
  * by the different phases that the benchmark is expecting.
  */
object CompleteTTCProcess extends App {

  SynchronizationCompartment combine RsumCompartment

  var ctts: ICreateTruthTable = _
  var integrate: IIntegrationCompartment = _
  var writeOut: IWriteOutputModel = _
  var loader: TTCLoader = _
  var validator: Launcher = _
  var saver: TruthTable = _
  var processConfig: TTCProcessConfiguration = _
  var bdt: Boolean = _

  /** Performs necessary setup instructions such as loading the ecore meta-model.
    *
    * @param processConfig contains the necessary file locations
    */
  def initialize(processConfig: TTCProcessConfiguration): Unit = {
    val sync = true
    bdt = processConfig.processMode == ProcessMode.BDT || processConfig.processMode == ProcessMode.BDTU
    loader = new TTCLoader
    validator = new Launcher
    if (sync) {
      ctts = new CreateTruthTableSync()
      if (bdt) {
        integrate = if (processConfig.processMode == ProcessMode.BDT) BdtSyncIntegration else BdtSyncIntegrationWithoutOrder
        writeOut = WriteSyncBdtOutput
      } else {
        integrate = if (processConfig.processMode == ProcessMode.BDD) BddSyncIntegration else BddSyncIntegrationWithoutOrder
        writeOut = WriteSyncBddOutput
      }
    } else {
      ctts = new CreateTruthTableSum()
      integrate = BdtSumIntegration
      writeOut = WriteSumBdtOutput
    }
    this.processConfig = processConfig
    saver = loader.javaOptimizedTTJavaEcore(processConfig.ttEcoreName, processConfig.ttFileName)
  }

  /** Loads the truth table.
    */
  def load(): Unit = loader.createTruthTableRSYNCInstance(saver, ctts)

  /** Transforms the truth table instance to a binary decision diagram.
    */
  def run(): Unit = SynchronizationCompartment.integrateNewModel(integrate)

  /** Shows all created TT and BDD elements '''after transformation'''.
    */
  def printModelElements(): Unit = ModelElementLists.printFromPackage("sync.bdd.BDD")

  /** Persists the BDD in the File system (according to the
    * [[TTCProcessConfiguration process configuration]] specified during
    * [[initialize() initialization]] '''after transformation'''.
    */
  def doWriteOut(): Unit = writeOut.generateEverything(processConfig.bddFileName)


  /** Checks, whether the generated BDD and the original TT work as expected (after
    * transformation!).
    */
  def validateModelEquality(): Unit = validator.launch(processConfig.ttFileName, processConfig.bddFileName)

  /** Runs the entire transformation process at once.
    *
    * That is initialization, loading and running as the core part, as well as printing the model,
    * writing the generated BDD and validating as extensions of the minimal workflow will be
    * executed.
    */
  def executeEntireProcess(processConfig: TTCProcessConfiguration): Unit = {
    initialize(processConfig)
    load()
    run()
    printModelElements()
    doWriteOut()
    validateModelEquality()
  }

  override def main(args: Array[String]): Unit = {
    val processConfig = TTCProcessConfiguration(ttFileName = "TT.ttmodel", bddFileName = "Generated.bddmodel", processMode = ProcessMode.BDT)
    executeEntireProcess(processConfig)
  }

}
