package org.rosi_project.model_management.sum.query

import scroll.internal.Compartment
import org.rosi_project.model_management.core.RsumCompartment
import org.rosi_project.model_management.sum.compartments.IRelationCompartment
import org.rosi_project.model_management.sum.roles.IRelationRole

/**
 * Special query implementation for the query interface that works on the RSUM.
 */
class Query(_name: String) extends AQuery(_name) {
  
  RsumCompartment.combine(this)
  
  private var queryRelRoles: Set[QueryRelObject] = Set.empty
  private var queryNatRoles: Set[QueryNatObject] = Set.empty
  
  private var queryRelResults: Set[QueryRelResult] = Set.empty
  private var queryNatResults: Set[QueryNatResult] = Set.empty
    
  private def getQueryNatRole(obj: Object): QueryNatObject = {
    //queryNatRoles.filter(+_ == +obj).foreach(return _)
    queryNatRoles.foreach(q => {
      if (+obj == +q) {
        return q
      }
    })
    return null
  }
    
  private def getQueryRelRole(obj: Object): QueryRelObject = {
    //queryRelRoles.filter(+_ == +obj).foreach(return _)
    queryRelRoles.foreach(q => {
      if (+obj == +q) {
        return q
      }
    })
    return null
  }
  
  private def getNatResultRole(player: Object, queryObject: QueryNatObject): QueryNatResult = {
    //queryNatResults.filter(r => r.connectedQueryObject == queryObject && +player == +r).foreach(return _)
    queryNatResults.foreach(r => {
      if (r.connectedQueryObject == queryObject && +player == +r) {
        return r
      }
    })
    return null
  }
  
  def deleteQueryResults(): Unit = {
    queryRelResults.foreach(_.remove())
    queryRelResults = Set.empty
    queryNatResults.foreach(_.remove())
    queryNatResults = Set.empty
  }  
    
  def deleteQueryObjects(): Unit = {
    queryRelRoles.foreach(_.remove())
    queryRelRoles = Set.empty
    queryNatRoles.foreach(_.remove())
    queryNatRoles = Set.empty
  }
  
  def isQueryCorrect(): Boolean = {
    //check if the query is correct and does not contain more close islands
    queryRelRoles.foreach(qo => {
      return qo.overallConnectionUnit() == (queryRelRoles.size + queryNatRoles.size)
    })
    queryNatRoles.foreach(qo => {
      return qo.overallConnectionUnit() == (queryRelRoles.size + queryNatRoles.size)
    })
    return true
  }
  
  def removeQueryRole(role: AQuery#QueryObject): Unit = {
    if (role.isInstanceOf[QueryNatObject]) {
      queryNatRoles -= role.asInstanceOf[QueryNatObject]
    }
    if (role.isInstanceOf[QueryRelObject]) {
      queryRelRoles -= role.asInstanceOf[QueryRelObject]
    }
  }
  
  def addQueryRole(obj: Object): AQuery#QueryObject = {
    val relResult = getQueryRelRole(obj)
    if (relResult != null) return relResult
    val natResult = getQueryNatRole(obj)
    if (natResult != null) return natResult
    //get player
    val playerObj = obj.player.getOrElse(obj)
    //difference between relation and natural
    if (playerObj.isInstanceOf[IRelationCompartment]) {
      val refComp = playerObj.asInstanceOf[IRelationCompartment]
      this.combine(refComp)
      val sourceRole: QueryNatObject = getQueryNatRole(refComp.getSource())
      val targetRole: QueryNatObject = getQueryNatRole(refComp.getTarget())
      if (sourceRole == null || targetRole == null) {
        println("--- Error: Relational compartment can only be added if source and target are added before")
        return null
      }
      val newRole = new QueryRelObject(sourceRole, targetRole)
      queryRelRoles += newRole    
      playerObj play newRole
      return newRole
    } else {
      val newRole = new QueryNatObject()
      queryNatRoles += newRole    
      playerObj play newRole
      return newRole
    }  
  }  
  
  def runQuery(): Set[Set[Object]] = {
    //delete at the end
    var forDeletionQueryResults: Set[QueryNatResult] = Set.empty
    //immediate values
    var deleteNatResults: Set[QueryNatResult] = Set.empty   
    var doSomething = true
    //output value
    var result: Set[Set[Object]] = Set.empty
    
    //###add query results to all elements that can be results because of connection properties
    //...iterate over non negated query relations and add result roles if necessary 
    queryRelRoles.filter(!_.isNegated()).foreach(queryRel => {
      val playerQueryRel: IRelationCompartment = queryRel.player.right.get.asInstanceOf[IRelationCompartment]
      RsumCompartment.getRelations().foreach(rsumRel => {
        if (playerQueryRel.getClass.isInstance(rsumRel)) {
          //search if their are existing results if not create new ones
          val rsumNatSource: Object = rsumRel.getSource().player.right.get
          val rsumNatTarget: Object = rsumRel.getTarget().player.right.get
          var queryNatResultSource: QueryNatResult = getNatResultRole(rsumNatSource, queryRel.source)
          if (queryNatResultSource == null) {
            queryNatResultSource = new QueryNatResult(queryRel.source)  
            rsumNatSource play queryNatResultSource
            queryNatResults += queryNatResultSource
          }          
          var queryNatResultTarget: QueryNatResult = getNatResultRole(rsumNatTarget, queryRel.target)
          if (queryNatResultTarget == null) {
            queryNatResultTarget = new QueryNatResult(queryRel.target)  
            rsumNatTarget play queryNatResultTarget
            queryNatResults += queryNatResultTarget
          }
          //connect them in a new relation result
          val queryRelResult: QueryRelResult = new QueryRelResult(queryRel, queryNatResultSource, queryNatResultTarget, false) 
          rsumRel play queryRelResult     
          queryRelResults += queryRelResult
          
          //if transitive add new relation to separate list to add them at the end
          if (queryRel.isTransitive) {
            //set only source new because target must be old one, transitive query means always target same
            queryNatResultSource = getNatResultRole(rsumNatSource, queryRel.target)
            if (queryNatResultSource == null) {
              queryNatResultSource = new QueryNatResult(queryRel.target)  
              rsumNatSource play queryNatResultSource
              queryNatResults += queryNatResultSource
            }
            val transitiveQueryRelResult: QueryRelResult = new QueryRelResult(queryRel, queryNatResultSource, queryNatResultTarget, true) 
            rsumRel play transitiveQueryRelResult     
            queryRelResults += transitiveQueryRelResult
          }
        }
      })      
    })
    
    //...iterate over negated related roles and search all opposite naturals that do not implement this rel
    queryRelRoles.filter(_.isNegated()).foreach(queryRel => {
      val playerQueryRel: IRelationCompartment = queryRel.player.right.get.asInstanceOf[IRelationCompartment]
      var playerQueryNat: Object = null
      if (queryRel.source.negated) {
        playerQueryNat = playerQueryRel.getSource.player.right.get 
      } else {
        playerQueryNat = playerQueryRel.getTarget.player.right.get 
      }
      //source or target negated, search for negated class
      RsumCompartment.getNaturals().foreach(rsumNat => {
        if (playerQueryNat.getClass.isInstance(rsumNat))
        {
          var goodElement: Boolean = true;
          //filtered roles of the natural type
          var roles: Set[IRelationRole] = Set.empty 
          if (queryRel.source.negated) {
            roles = rsumNat.roles().filter(_.isInstanceOf[IRelationCompartment#ITarget]).toSet.asInstanceOf[Set[IRelationRole]] 
          } else {
            roles = rsumNat.roles().filter(_.isInstanceOf[IRelationCompartment#ISource]).toSet.asInstanceOf[Set[IRelationRole]] 
          }
          //if we find a role that works than we can not add this natural to the results
          roles.foreach(r => {            
            val outerRelComp = r.getOuterCompartment()
            //println("COMP: " + outerRelComp + " " + playerQueryRel + " " + playerQueryRel.getClass.isInstance(outerRelComp))
            if (playerQueryRel.getClass.isInstance(outerRelComp)) {
              //we can not use this element
              goodElement = false
            }
          })
          //if we do not find a compatible element in this natural role we can add it
          if (goodElement) {
            var queryNatRole: QueryNatObject = null
            if (queryRel.source.negated) {
              queryNatRole = queryRel.target            
            } else {
              queryNatRole = queryRel.source
            }
            var queryNatResult: QueryNatResult = getNatResultRole(rsumNat, queryNatRole)
            if (queryNatResult == null) {
              queryNatResult = new QueryNatResult(queryNatRole) 
              rsumNat play queryNatResult
              queryNatResults += queryNatResult
            }
            if (queryRel.source.negated) {
              queryNatResult.increaseTargetNegated()
            } else {
              queryNatResult.increaseSourceNegated()
            }          
          }
        }
      })
    })    
    
    //...iterate over all query naturals without a connection and add result roles if necessary
    queryNatRoles.filter(_.connectionSize == 0).foreach(queryNat => {
      //connected one are visited in the part before
      val playerQueryNat: Object = queryNat.player.right.get
      RsumCompartment.getNaturals().foreach(rsumNat => {
        if (playerQueryNat.isInstanceOf[QueryHelper]) {
          if (playerQueryNat.equals(rsumNat)) {
            var queryNatResult: QueryNatResult = getNatResultRole(rsumNat, queryNat)
            if (queryNatResult == null) {
              queryNatResult = new QueryNatResult(queryNat)  
              rsumNat play queryNatResult
              queryNatResults += queryNatResult
            }
          }
        } else {
          if (playerQueryNat.getClass.isInstance(rsumNat)) {
            var queryNatResult: QueryNatResult = getNatResultRole(rsumNat, queryNat)
            if (queryNatResult == null) {
              queryNatResult = new QueryNatResult(queryNat)  
              rsumNat play queryNatResult
              queryNatResults += queryNatResult
            }
          }
        }        
      })
    })
    
    //###proof correctness of results
    //...proof attribute correctness once
    queryNatResults.filter(!_.proofAttributes).foreach(deleteNatResults += _)
    //delete them
    deleteNatResults.foreach(queryResultNat => {
      queryNatResults -= queryResultNat
      forDeletionQueryResults += queryResultNat
      queryResultNat.removeConnections()
    })
    
    //...proof the correctness of the query results and delete non correct ones
    while (doSomething) {
      deleteNatResults = Set.empty      
      //proof the connection correctness
      queryNatResults.filter(!_.proofConnections).foreach(deleteNatResults += _)
      
      //do as long as everything is correct
      doSomething = !deleteNatResults.isEmpty
      //delete them
      deleteNatResults.foreach(queryResultNat => {
        queryNatResults -= queryResultNat
        forDeletionQueryResults += queryResultNat
        queryResultNat.removeConnections()   
      })
    }
    
    //###make good outputs from the current query results
    //...only correct elements should now play result roles and are in the list    
    var combinedResult: Set[QueryCombinedResult] = queryRelResults.filter(!_.transitive).map(_.createCombinedResult())
    
    //...combine the result elements to good output units
    doSomething = true
    while (doSomething) {
      doSomething = false
      combinedResult.foreach(combined => {
        queryRelResults.foreach(rel => {
          if (rel.source == combined.target && !combined.contains(rel.target)) {
            //add in the back
            combined.addBack(rel.target)
            doSomething = true
          } else {
            if (rel.target == combined.source && !combined.contains(rel.source)) {
              //add in the front
              combined.addFront(rel.source)
              doSomething = true
            }            
          }
        })    
      })  
      //delete duplicated elements
      combinedResult = combinedResult.groupBy(_.getBetween).map(_._2.head).toSet
      //combinedResult.distinct
    }
    
    //create only results with tuples
    /*queryRelResults.foreach(rel => {
      var rSet: Set[Object] = Set.empty
      if (rel.source.connectedQueryObject.returned) {
        rSet += rel.source.player.right.get
      }
      if (rel.target.connectedQueryObject.returned) {
        rSet += rel.target.player.right.get
      }
      result += rSet
    })*/
    
    //...bring the output format to the reals one
    combinedResult.foreach(combined => {
      var rSet: Set[Object] = Set.empty
      combined.getBetween.foreach(queryNat => {
        if (queryNat.connectedQueryObject.returned) {
          rSet += queryNat.player.right.get
        }
      })
      result += rSet
    })
    
    //...add also single results
    queryNatResults.foreach(queryResultNat => {
      if (!queryResultNat.isConnected() && queryResultNat.connectedQueryObject.returned) {
        result += Set(queryResultNat.player.right.get)
      }
    })
    
    //...delete now really all not needed result roles 
    forDeletionQueryResults.foreach(_.remove())
    
    return result
  }
  
  override def toString(): String = name + ": " + isQueryCorrect + " Rs: " + queryNatRoles + " " + queryRelRoles
  
  /**
   * Query object specialized for naturals in the RSUM.
   */
  class QueryNatObject() extends QueryObject() {
    private var sourceRelations: Set[QueryRelObject] = Set.empty
    private var targetRelations: Set[QueryRelObject] = Set.empty    
    
    def addSourceRelation(rel: QueryRelObject): Unit = {
      sourceRelations += rel
    }    
    
    def removeSourceRelation(rel: QueryRelObject): Unit = {
      sourceRelations -= rel
    }
    
    def getSourceRelationsSize(): Int = sourceRelations.filter(!_.isNegated()).map(_.multiTarget).sum //sum up multi value not use size
    def getNegatedSourceRelationsSize(): Int = sourceRelations.filter(_.isNegated()).size
    
    def addTargetRelation(rel: QueryRelObject): Unit = {
      targetRelations += rel
    }
    
    def removeTargetRelation(rel: QueryRelObject): Unit = {
      targetRelations -= rel
    }
    
    def getTargetRelationsSize(): Int = targetRelations.filter(!_.isNegated()).map(_.multiSource).sum
    def getNegatedTargetRelationsSize(): Int = targetRelations.filter(_.isNegated()).size
    
    def connectionSize(): Int = targetRelations.size + sourceRelations.size
    
    def overallConnectionUnit(): Int = {
      var visitedRel: Set[QueryRelObject] = Set.empty
      var visitedNat: Set[QueryNatObject] = Set(this)
      var unVisitedRel: Set[QueryRelObject] = targetRelations ++ sourceRelations
      var unVisitedNat: Set[QueryNatObject] = Set.empty
      while (!unVisitedRel.isEmpty || !unVisitedNat.isEmpty) {
        //run over all unvisited relations 
        unVisitedRel.foreach(rel => {
          if (!visitedNat.contains(rel.source))
            unVisitedNat += rel.source 
          if (!visitedNat.contains(rel.target))
            unVisitedNat += rel.target
        })
        visitedRel ++= unVisitedRel
        unVisitedRel = Set.empty
        //run over all unvisited naturals 
        unVisitedNat.foreach(nat => {
          nat.sourceRelations.filter(!visitedRel.contains(_)).foreach(unVisitedRel += _)
          nat.targetRelations.filter(!visitedRel.contains(_)).foreach(unVisitedRel += _)
        })
        visitedNat ++= unVisitedNat
        unVisitedNat = Set.empty        
      }
      return visitedRel.size + visitedNat.size
    }
    
    override def toString: String = "QON: " + label + " C: " + connectionSize
  }
    
  /**
   * Query object specialized for relational compartments in the RSUM.
   */
  class QueryRelObject(val source: QueryNatObject, val target: QueryNatObject) extends QueryObject() {

    def isNegated(): Boolean = source.negated || target.negated
    def isTransitive(): Boolean = target.transitive
    def multiTarget(): Int = target.multi
    def multiSource(): Int = source.multi
    
    source.addSourceRelation(this)
    target.addTargetRelation(this)
    
    def overallConnectionUnit(): Int = source.overallConnectionUnit()
    
    override def toString: String = "QOR: " + source.label + "-" + target.label
  }
    
  /**
   * Query result roles for natural query roles.
   */
  class QueryNatResult(val connectedQueryObject: QueryNatObject) extends QueryResult(connectedQueryObject) {
    private var sourceRelations: Set[QueryRelResult] = Set.empty
    private var targetRelations: Set[QueryRelResult] = Set.empty
    
    private var negatedSources: Int = 0
    private var negatedTargets: Int = 0
    
    def increaseSourceNegated(): Unit = { negatedSources += 1 }
    def decreaseSourceNegated(): Unit = { negatedSources -= 1 }
    
    def increaseTargetNegated(): Unit = { negatedTargets += 1 }
    def decreaseTargetNegated(): Unit = { negatedTargets -= 1 }
    
    /**
     * Proof if the player has all the necessary attributes with its values.
     */
    def proofAttributes(): Boolean = {
      val player: Object = this.player.right.get   
      var returnValue = true 
      connectedQueryObject.getAttributeFilters.foreach(a => {
        try {
          //player.getClass.getMethods.foreach(f => println(f.toString))
          //println(player.getClass.getMethod("name").getReturnType) //getName
          val met = player.getClass.getMethod(a.attributeName)        
          val result: String = met.invoke(player).asInstanceOf[String]
          a.checking match {
            case CheckingOption.equalsCheck => if (result != a.value) returnValue = false
            case CheckingOption.biggerThan => if (result <= a.value) returnValue = false
            case CheckingOption.smallerThan => if (result >= a.value) returnValue = false
            case _ =>
          }
        } catch {
          case _: Throwable => { 
            println("!!! No attribute with this name!!!") 
            return false
          }
        }
      })
      return returnValue
    }
    
    /**
     * Proof if the number of connections is equals to the related query object.
     */
    def proofConnections(): Boolean = {
      //TODO: proof also the number of types and not only the numbers
      //println("S: " + _connectedQueryObject.getSourceRelationsSize() + " T: " + _connectedQueryObject.getTargetRelationsSize() + " NS: " + _connectedQueryObject.getNegatedSourceRelationsSize() + " NT: " + _connectedQueryObject.getNegatedTargetRelationsSize() + " Con: " + _connectedQueryObject)
      //println("S: " + getSourceRelationsSize() + " T: " + getTargetRelationsSize() + " NS: " + negatedSources + " NT: " + negatedTargets + " This: " + this)
      if (getSourceRelationsSize >= connectedQueryObject.getSourceRelationsSize() 
          && getTargetRelationsSize >= connectedQueryObject.getTargetRelationsSize()
          && negatedSources >= connectedQueryObject.getNegatedSourceRelationsSize()
          && negatedTargets >= connectedQueryObject.getNegatedTargetRelationsSize()) {
        return true
      }
      return false
    }
    
    def addSourceRelation(rel: QueryRelResult): Unit = {
      sourceRelations += rel
    }
    
    def removeSourceRelation(rel: QueryRelResult): Unit = {
      sourceRelations -= rel
    }
    
    def getSourceRelationsSize(): Int = sourceRelations.size
    
    def addTargetRelation(rel: QueryRelResult): Unit = {
      targetRelations += rel
    }
    
    def removeTargetRelation(rel: QueryRelResult): Unit = {
      targetRelations -= rel
    }
    
    def getTargetRelationsSize(): Int = targetRelations.size
        
    def isConnected(): Boolean = (targetRelations.size + sourceRelations.size) > 0
    
    def removeConnections(): Unit = {
      sourceRelations.foreach(rel => {        
        rel.target.removeTargetRelation(rel)
        queryRelResults -= rel
        rel.remove()
      })      
      targetRelations.foreach(rel => {        
        rel.source.removeSourceRelation(rel)
        queryRelResults -= rel
        rel.remove()
      })
      sourceRelations = Set.empty
      targetRelations = Set.empty    
      negatedSources = 0
      negatedTargets = 0
    }
  }
  
  /**
   * Query result roles for relation query roles.
   */
  class QueryRelResult(val connectedQueryObject: QueryRelObject, val source: QueryNatResult, val target: QueryNatResult, val transitive: Boolean) extends QueryResult(connectedQueryObject) {
    //only add this relation to the connect results if it is not transitive
    if (!transitive) {
      source.addSourceRelation(this)
      target.addTargetRelation(this)
    }
    
    /**
     * Create a new combined result which is a copy of this one.
     */
    def createCombinedResult(): QueryCombinedResult = new QueryCombinedResult(source, target)
  }
  
  /**
   * Helper class to connect the results to a chain.
   */
  class QueryCombinedResult(var source: QueryNatResult, var target: QueryNatResult) {
    
    //list of all query results that are connected
    private var between: Seq[QueryNatResult] = Seq(source, target)
    
    def getBetween(): Seq[QueryNatResult] = between
    
    def addFront(newIn: QueryNatResult): Unit = {
      between = newIn +: between
      source = newIn
    }
    
    def addBack(newIn: QueryNatResult): Unit = {
      between = between :+ newIn
      target = newIn
    }
    
    def contains(result: QueryNatResult): Boolean = ( between.contains(result) )
    
    private def getSize(): Int = between.size    
    
    protected def canEqual(other: Any): Boolean = other.isInstanceOf[QueryCombinedResult]
    
    override def equals(other: Any): Boolean = other match {
      case that: QueryCombinedResult => {
        if (that canEqual this) {
          between.foreach(b => {
            if (!that.contains(b))
              return false
          })
          return that.getSize == this.getSize()
        } else {
          return false
        }
      }
      case _ => return false
    }
    
    override def toString(): String = "QCR: (" + between + ")"
  }  
}