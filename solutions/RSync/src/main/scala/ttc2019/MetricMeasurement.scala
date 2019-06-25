package ttc2019

import org.rosi_project.model_management.core.ModelElementLists
import ttc2019.benchmark.Metrics

/**
 * Object to create the metric values from the created target models.
 */
object MetricMeasurement {

  var printDirectly = false

  def printMetricsBDT(directly: Boolean = false): Metrics = {
    printDirectly = directly
    val bdd = ModelElementLists.getElementsFromType("sync.bdd.BDD").head.asInstanceOf[_root_.sync.bdd.BDD]
    val numberSubtrees = ModelElementLists.getElementsFromType("sync.bdd.Subtree").size
    val numberLeafs = ModelElementLists.getElementsFromType("sync.bdd.Leaf").size
    
    val maxPath = bdd.getTree().getMaxPath()
    val minPath = bdd.getTree().getMinPath()
    val averagePath = bdd.getTree().getAvgPath()

    if (directly) {
      println("Number Subtrees: " + numberSubtrees)
      println("Number Leaf: " + numberLeafs)
      println("Max Path Length: " + maxPath)
      println("Min Path Length: " + minPath)
      println("Average Path Length: " + averagePath)
    }
    Metrics(nLeafs =  numberLeafs, nSubTrees = numberSubtrees, maxPathLength = maxPath, minPathLength = minPath, avgPathLength = averagePath)
  }

  def printMetricsBDD(directly: Boolean = false): Metrics = {
    printDirectly = directly
    val bdd = ModelElementLists.getElementsFromType("sync.bddg.BDD").head.asInstanceOf[_root_.sync.bddg.BDD]
    val numberSubtrees = ModelElementLists.getElementsFromType("sync.bddg.Subtree").size
    val numberLeafs = ModelElementLists.getElementsFromType("sync.bddg.Leaf").size
    
    val maxPath = bdd.getRoot().getMaxPath()
    val minPath = bdd.getRoot().getMinPath()
    val averagePath = bdd.getRoot().getAvgPath()  

    if (directly) {
      println("Number Subtrees: " + numberSubtrees)
      println("Number Leaf: " + numberLeafs)
      println("Max Path Length: " + maxPath)
      println("Min Path Length: " + minPath)
      println("Average Path Length: " + averagePath)
    }
    Metrics(nLeafs =  numberLeafs, nSubTrees = numberSubtrees, maxPathLength = maxPath, minPathLength = minPath, avgPathLength = averagePath)
  }
}