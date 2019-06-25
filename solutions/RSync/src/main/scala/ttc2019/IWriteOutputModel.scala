package ttc2019

/**
 * Interface to write out the target models.
 */
trait IWriteOutputModel {
  
  def generateEverything(outputFile: String): Unit
}