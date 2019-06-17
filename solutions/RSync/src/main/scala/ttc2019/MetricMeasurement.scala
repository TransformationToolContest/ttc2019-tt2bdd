package ttc2019

import org.rosi_project.model_management.core.ModelElementLists
import ttc2019.benchmark.Metrics

object MetricMeasurement {

  var printDirectly = false

  def printMetricsBDT(directly: Boolean = false): Metrics = {
    printDirectly = directly
    val numberSubtrees = ModelElementLists.getElementsFromType("sync.bdd.Subtree").size
    val numberLeafs = ModelElementLists.getElementsFromType("sync.bdd.Leaf").size
    var maxPath: Int = -1
    var minPath: Int = -1

    var sumDepth: Int = 0
    ModelElementLists.getElementsFromType("sync.bdd.Leaf").asInstanceOf[List[sync.bdd.Leaf]].foreach(l => {
      var depth = l.getMinDeep()
      sumDepth += depth
      if (depth > maxPath) {
        maxPath = depth
      }
      if (depth < minPath || minPath == -1) {
        minPath = depth
      }
    })
    val averagePath: Double = sumDepth.toDouble / numberLeafs.toDouble

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
    val numberSubtrees = ModelElementLists.getElementsFromType("sync.bddg.Subtree").size
    val numberLeafs = ModelElementLists.getElementsFromType("sync.bddg.Leaf").size
    var maxPath: Int = -1
    var minPath: Int = -1

    var sumDepth: Int = 0
    var sumNodes: Int = 0
    ModelElementLists.getElementsFromType("sync.bddg.Leaf").asInstanceOf[List[sync.bddg.Leaf]].foreach(l => {
      var depth = l.getMinDeep()
      sumNodes += l.getOwnerSubtreeForOne().size + l.getOwnerSubtreeForZero().size
      sumDepth += l.getSumDeep()
      if (depth > maxPath) {
        maxPath = depth
      }
      if (depth < minPath || minPath == -1) {
        minPath = depth
      }
    })
    val averagePath: Double = sumDepth.toDouble / sumNodes.toDouble

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
