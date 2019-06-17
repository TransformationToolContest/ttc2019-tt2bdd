package org.rosi_project.model_management.sum.join

trait IJoinInfo {
  
  private var allPlayers: Set[Object] = Set.empty
  
  def addObject(obj: Object): Unit = {
    allPlayers = allPlayers + obj
  }
  
  def removeObject(obj: Object): Unit = {
    allPlayers = allPlayers - obj
  }
  
  protected def objectInitialize(join: IJoinCompartment, base: Object, other: Object): Unit = {
    join.objectInitialization(base, other)
  }
  
  def containsObject(obj: Object): Boolean = allPlayers.contains(obj)
  
  def getJoinType(): RsumJoinType.Value  
  
  private def canInJoin(obj: Object): Boolean = (isInstanceBaseModel(obj) || isInstanceOtherModel(obj)) && !allPlayers.contains(obj)
  
  def canMatchWithNewJoin(obj: Object): Boolean = canInJoin(obj) && !containsObject(obj)
  
  def isInstanceBaseModel(obj: Object): Boolean
  
  def isInstanceOtherModel(obj: Object): Boolean
  
  def matchTwoObjects(b: Object, o: Object): Boolean
  
  def getNewInstance(b: Object, o: Object): IJoinCompartment
  
  def isInstanceOf(obj: Object): Boolean
}