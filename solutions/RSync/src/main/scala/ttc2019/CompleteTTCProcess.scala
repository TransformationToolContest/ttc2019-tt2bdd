package ttc2019

import ttc2019.metamodels.create.Validator
import org.rosi_project.model_management.core.SynchronizationCompartment
import org.rosi_project.model_management.core.RsumCompartment
import org.rosi_project.model_management.core.ModelElementLists
import org.rosi_project.model_management.sync.IIntegrationCompartment

/** The `CompleteTTCProcess` executes the entire transformation workflow. Its methods are inspired
  * by the different phases that the benchmark is expecting.
  */
object CompleteTTCProcess extends App {

  SynchronizationCompartment combine RsumCompartment

  var ctts: ICreateTruthTable = _
  var integrate: IIntegrationCompartment = _
  var writeOut: IWriteOutputModel = _
  var loader: TTCLoader = _
  var validator: Validator = _
  var saver: TTCEmfSaver = _
  var processConfig: TTCProcessConfiguration = _

  /** Performs necessary setup instructions such as loading the ecore meta-model.
    *
    * @param processConfig contains the necessary file locations
    */
  def initialize(processConfig: TTCProcessConfiguration): Unit = {
    val sync = true
    loader = new TTCLoader
    validator = new Validator
    if (sync) {
      ctts = new CreateTruthTableSync()
      integrate = BdtSyncIntegration
      writeOut = WriteSyncBdtOutput
    } else {
      ctts = new CreateTruthTableSum()
      integrate = BdtSumIntegration
      writeOut = WriteSumBdtOutput
    }
    this.processConfig = processConfig
    saver = loader.loadEcore(processConfig.ttEcoreName, processConfig.ttFileName)
  }

  /** Loads the truth table.
    */
  def load(): Unit = loader.createTruthTableInstance(saver, ctts)

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
  def writeBdd(): Unit = writeOut.generateEverything(processConfig.bddFileName)

  /** Checks, whether the generated BDD and the original TT work as expected (after
    * transformation!).
    */
  def validateModelEquality(): Unit = validator.validate(processConfig.ttFileName, processConfig.bddFileName)

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
    writeBdd()
    validateModelEquality()
  }

  override def main(args: Array[String]): Unit = {
    val processConfig = TTCProcessConfiguration(ttFileName = "TT.ttmodel", bddFileName = "Generated.bddmodel")
    executeEntireProcess(processConfig)
  }

}
