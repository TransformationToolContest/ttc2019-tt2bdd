package org.rosi_project.model_management.sum.query

import scroll.internal.Compartment

/**
 * Describes the abstract query compartment with the roles that each query must implement.
 */
abstract class AQuery(val name: String) extends Compartment {
  
  def deleteQueryResults(): Unit
  def deleteQueryObjects(): Unit
  
  /**
   * Add a query role to the underlying object.
   */
  def addQueryRole(obj: Object): AQuery#QueryObject
  
  /**
   * Remove a query role from the query.
   */
  def removeQueryRole(role: AQuery#QueryObject): Unit
  
  /**
   * Proof if the query has a correct form.
   * Not more than one connection island.
   */
  def isQueryCorrect(): Boolean
  
  /**
   * Running the query and getting a set of sets of result objects.
   */
  def runQuery(): Set[Set[Object]]
  
  /**
   * Abstract query object to create the query from example.
   */
  abstract class QueryObject() {
    
    /**
     * Label to identify a query object.
     */
    var label: String = ""
    
    /**
     * Describes is necessary to be in the results or to be not.
     */
    var negated: Boolean = false
    
    /**
     * Important to describe if a transitive closure should be created.
     */
    var transitive: Boolean = false
    
    /**
     * Describes how often a connection must exists.
     */
    var multi: Int = 1
    
    /**
     * Describes if the elements should be returned in the results or not.
     */
    var returned: Boolean = true
    
    private var attributeFilters: Set[AttributeFilter] = Set.empty
      
    /**
  	 * Create and add an attribute filter object to a query object.
   	 */        
    def addAttributeFilter(attributeName: String, value: String, bigger: CheckingOption.Value): Boolean = {
      if (attributeName == null || attributeName == "" ||
        bigger == null || value == null || value == "") {
        return false
      }
      attributeFilters += new AttributeFilter(attributeName, value, bigger)
      return true
    }
    
    def getAttributeFilters(): Set[AttributeFilter] = attributeFilters
    
    def overallConnectionUnit(): Int
    
    override def toString(): String = "QO: " + label
  }  
  
  /**
   * Abstract query results that each element which matches the query gets.
   */
  abstract class QueryResult(val matchedQueryObject: QueryObject) {
    
    override def toString(): String = "QR: (" + this.player.right.get.toString() + " " + matchedQueryObject + ")"
  }
  
  /**
   * Should collect information about the attributes a query element must have
   */
  class AttributeFilter(val attributeName: String, val value: String, val checking: CheckingOption.Value) {}
}