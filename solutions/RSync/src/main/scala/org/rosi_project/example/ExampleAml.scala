package org.rosi_project.example


object ExampleAml extends App {
  
  /*val amlView = AmlanguageView.getNewView()
  
  //Prototypes
  val sucStack = amlView.createSystemUnitClass("Stack", "9")
  val sucCrane = amlView.createSystemUnitClass("Crane", "10")
  val sucRamp = amlView.createSystemUnitClass("Rampe", "11")
  
  //System model
  val ihPpu = amlView.createInstanceHierarchy("PPU", "1")
  val ieStack1 = amlView.createInternalElement("Stack1", "2")
  val ieCrane2 = amlView.createInternalElement("Crane2", "7")
  val ieRamp3 = amlView.createInternalElement("Ramp3", "8")
  val ieConveyor1 = amlView.createInternalElement("Conveyor1", "4")
  val ieStore1 = amlView.createInternalElement("Store1", "5")
  val ieSensor1 = amlView.createInternalElement("Sensor1", "6")
  val attWeigth = amlView.createAttribute("50", "weigth", "3")
  
  ihPpu.addInternalElements(ieStack1)
  ihPpu.addInternalElements(ieCrane2)
  ihPpu.addInternalElements(ieRamp3)  
  ieStack1.addInternalElements(ieConveyor1)
  ieStack1.addInternalElements(ieStore1)
  ieStack1.addAttributes(attWeigth)  
  ieStore1.addInternalElements(ieSensor1)
  
  //connect prototyp and system model
  ieStack1.setBaseSystemUnit(sucStack)
  ieCrane2.setBaseSystemUnit(sucCrane)
  ieRamp3.setBaseSystemUnit(sucRamp)
    
  //amlView.printViewRoles()
  
  //val ieSensor2 = amlView.createInternalElement("Sensor2", "12")
  //ieStack1.addInternalElements(ieSensor2)
  
  //runAllQueries()
  runAllViewQueries()
  
  def runAllViewQueries() {
    //Query testing
    query_1()
    query_2()
    query_3()
    query_4()
    query_5()
    query_6()
    query_7()
    query_8()
  }
  
  def runAllQueries() {
    //Query testing
    query1()
    query2()
    query3()
    query4()
    query5()
    query6()
    query7()
    query8()
  }
  
  /**
   * Search for all objects that inherit from the CAEXObject.
   */
  def query1(): Unit = {
    //Query Objects
    val q1 = new Query("Q1")
    val co = new HelperCAEXObject("", "") //laufe über alle naturals im RSUM und suche, die die davon instanzen sind
    
    //Query Roles & Settings
    val r0 = q1.addQueryRole(co)
    r0.label = "CO"
    
    println(q1)
    println(q1.runQuery())
  }
  
  def query1Dot1(): Unit = {
    //Query Objects
    val q11 = new Query("Q1.1")
    val suc = new SystemUnitClass("SUC", "13")
    
    //Query Roles & Settings
    val r0 = q11.addQueryRole(suc)
    r0.label = "SUC"    
    
    println(q11)
    println(q11.runQuery())
  }
  
  /**
   * Search for internal elements with special attribute properties.
   */
  def query2(): Unit = {
    //Query Objects
    val q2 = new Query("Q2")
    val ie = new InternalElement("", "")
    val att = new Attribute("50", "weigth", "")
    val ieRatt = new SystemUnitClassAttributesAttribute(ie, att)
    ieRatt.internalInitialize()
    
    //Query Roles & Settings
    val r0 = q2.addQueryRole(ie)
    r0.label = "IE"
    val r1 = q2.addQueryRole(att)
    r1.addAttributeFilter("name", "weigth", CheckingOption.equalsCheck)
    r1.addAttributeFilter("value", "49", CheckingOption.biggerThan)
    r1.label = "ATT"
    val r2 = q2.addQueryRole(ieRatt)
    
    println(q2)
    println(q2.runQuery())
  }
  
  /**
   * Searches for all childs of the "PPU" element
   */
  def query3(): Unit = {
    //Query Objects
    val q3 = new Query("Q3")
    val ih = new InstanceHierarchy("PPU","")
    val ie = new InternalElement("", "")
    val ihRie = new InstanceHierarchyInternalElementsInternalElement(ih, ie)
    ihRie.internalInitialize()
    
    //Query Roles & Settings
    val r0 = q3.addQueryRole(ie)
    r0.label = "IE"
    val r1 = q3.addQueryRole(ih)
    r1.addAttributeFilter("name", "PPU", CheckingOption.equalsCheck)
    r1.label = "IH"
    r1.returned = false
    val r2 = q3.addQueryRole(ihRie)
    
    println(q3)
    println(q3.runQuery())
  }  
  
  /**
   * Searches all "PPU" elements with 2 deep childs.
   */
  def query4(): Unit = {
    //Query Objects
    val q4 = new Query("Q4")
    val ih = new InstanceHierarchy("PPU","") //wie kann ich auf die eigenschaften und werte prüfen
    val ie1 = new InternalElement("", "")
    val ie2 = new InternalElement("", "")
    val ihRie = new InstanceHierarchyInternalElementsInternalElement(ih, ie1)
    val ieRie = new SystemUnitClassInternalElementsInternalElement(ie1, ie2)
    ihRie.internalInitialize()
    ieRie.internalInitialize()
    
    //Query Roles & Settings
    val r0 = q4.addQueryRole(ie1)
    r0.label = "IE1"
    val r1 = q4.addQueryRole(ie2)
    r1.label = "IE2"
    val r2 = q4.addQueryRole(ih)
    r2.addAttributeFilter("name", "PPU", CheckingOption.equalsCheck)
    r2.label = "IH"
    r2.returned = false
    val r3 = q4.addQueryRole(ihRie)
    val r4 = q4.addQueryRole(ieRie)
    
    println(q4)
    println(q4.runQuery())
  }
  
  /**
   * Searches for all internal elements of the
   * PPU instance hierarchy. Make transitive closure.
   */
  def query5(): Unit = {
    //Query Objects
    val q5 = new Query("Q5")
    val ih = new InstanceHierarchy("PPU","") //wie kann ich auf die eigenschaften und werte prüfen
    val ie1 = new InternalElement("", "")
    val ie2 = new InternalElement("", "")
    val ihRie = new InstanceHierarchyInternalElementsInternalElement(ih, ie1)
    val ieRie = new SystemUnitClassInternalElementsInternalElement(ie1, ie2)
    ihRie.internalInitialize()
    ieRie.internalInitialize()
    
    //Query Roles & Settings
    val r0 = q5.addQueryRole(ie1)
    r0.label = "IE1"
    val r1 = q5.addQueryRole(ie2)
    r1.label = "IE2"
    r1.transitive = true
    val r2 = q5.addQueryRole(ih)
    r2.addAttributeFilter("name", "PPU", CheckingOption.equalsCheck)
    r2.label = "IH"
    r2.returned = false
    val r3 = q5.addQueryRole(ihRie)
    val r4 = q5.addQueryRole(ieRie)
    
    println(q5)
    println(q5.runQuery())
  }
  
  /**
   * Get all leave nodes.
   */
  def query6(): Unit = {
    //Query Objects
    val q6 = new Query("Q6")
    val ie1 = new InternalElement("", "")
    val ie2 = new InternalElement("", "")
    val ieRie = new SystemUnitClassInternalElementsInternalElement(ie1, ie2)
    ieRie.internalInitialize()
    
    //Query Roles & Settings
    val r0 = q6.addQueryRole(ie1)
    r0.label = "IE1"
    val r1 = q6.addQueryRole(ie2)
    r1.label = "IE2"
    r1.negated = true
    val r2 = q6.addQueryRole(ieRie)
    
    println(q6)
    println(q6.runQuery())
  }
  
  /**
   * Selects all internal elements that refer 
   * to a system unit class (SUC) named “Stack”.
   */
  def query7(): Unit = {
    //Query Objects
    val q7 = new Query("Q7")
    val suc = new SystemUnitClass("Stack", "")
    val ie = new InternalElement("", "")
    val ieRsuc = new  InternalElementBaseSystemUnitSystemUnitClass(ie, suc)
    ieRsuc.internalInitialize()
    
    //Query Roles & Settings
    val r0 = q7.addQueryRole(ie)
    r0.label = "IE"
    val r1 = q7.addQueryRole(suc)
    r1.addAttributeFilter("name", "Stack", CheckingOption.equalsCheck)
    r1.label = "SUC"
    r1.returned = false
    val r2 = q7.addQueryRole(ieRsuc)
    
    println(q7)
    println(q7.runQuery())
  }
  
  /**
   * Searches for all internal elements, 
   * which have more than two internal element as direct child
   */
  def query8(): Unit = {
    //Query Objects
    val q8 = new Query("Q8")
    val ie1 = new InternalElement("", "")
    val ie2 = new InternalElement("", "")
    val ieRie = new SystemUnitClassInternalElementsInternalElement(ie1, ie2)
    ieRie.internalInitialize()
    
    //Query Roles & Settings
    val r0 = q8.addQueryRole(ie1)
    r0.label = "IE1"
    val r1 = q8.addQueryRole(ie2)
    r1.label = "IE2"
    r1.multi = 3 //means >= 3
    val r2 = q8.addQueryRole(ieRie)
    
    println(q8)
    println(q8.runQuery())
  }
  
  def query_1(): Unit = {
    //Query Objects
    val q = new AmlanguageQuery
    val co = q.createCAEXObject()
    
    //Settings
    co.getQueryObject.label = "CO"    
        
    println(q.getQuery())
    println(q.getQuery().runQuery())
  }
  
  def query_2(): Unit = {
    //Query Objects
    val q = new AmlanguageQuery
    val ie = q.createInternalElement()
    val att = q.createAttribute()
    ie.addAttributes(att)
    
    //Settings
    ie.getQueryObject.label = "IE"
    att.getQueryObject.label = "ATT"
    att.setValueView("49", CheckingOption.biggerThan)
    att.setNameView("weigth", CheckingOption.equalsCheck)
    //att.getQueryObject().addAttributeFilter("name", "weigth", CheckingOption.equalsCheck)
    //att.getQueryObject().addAttributeFilter("value", "49", CheckingOption.biggerThan)
    
    println(q.getQuery())
    println(q.getQuery().runQuery())
  }
  
  def query_3(): Unit = {
    //Query Objects
    val q = new AmlanguageQuery
    val ih = q.createInstanceHierarchy()
    val ie = q.createInternalElement()
    ih.addInternalElements(ie)
    
    //Settings
    ie.getQueryObject.label = "IE"
    ih.getQueryObject.label = "IH"
    ih.setNameView("PPU", CheckingOption.equalsCheck)
    //ih.getQueryObject().addAttributeFilter("name", "PPU", CheckingOption.equalsCheck)
    ih.getQueryObject.returned = false
    
    println(q.getQuery())
    println(q.getQuery().runQuery())
  }  
  
  def query_4(): Unit = {
    //Query Objects
    val q = new AmlanguageQuery
    val ih = q.createInstanceHierarchy()
    val ie1 = q.createInternalElement()
    val ie2 = q.createInternalElement()
    ih.addInternalElements(ie1)
    ie1.addInternalElements(ie2)
    
    //Settings
    ie1.getQueryObject.label = "IE1"
    ie2.getQueryObject.label = "IE2"
    ih.getQueryObject.label = "IH"
    ih.setNameView("PPU", CheckingOption.equalsCheck)
    //ih.getQueryObject().addAttributeFilter("name", "PPU", CheckingOption.equalsCheck)
    ih.getQueryObject.returned = false
    
    println(q.getQuery())
    println(q.getQuery().runQuery())
  }
  
  def query_5(): Unit = {
    //Query Objects
    val q = new AmlanguageQuery
    val ih = q.createInstanceHierarchy()
    val ie1 = q.createInternalElement()
    val ie2 = q.createInternalElement()
    ih.addInternalElements(ie1)
    ie1.addInternalElements(ie2)
    
    //Settings
    ie1.getQueryObject.label = "IE1"
    ie2.getQueryObject.label = "IE2"
    ie2.getQueryObject.transitive = true
    ih.getQueryObject.label = "IH"
    ih.setNameView("PPU", CheckingOption.equalsCheck)
    //ih.getQueryObject().addAttributeFilter("name", "PPU", CheckingOption.equalsCheck)
    ih.getQueryObject.returned = false
    
    println(q.getQuery())
    println(q.getQuery().runQuery())
  }
  
  def query_6(): Unit = {
    //Query Objects
    val q = new AmlanguageQuery
    val ie1 = q.createInternalElement()
    val ie2 = q.createInternalElement()
    ie1.addInternalElements(ie2)
    
    //Settings
    ie1.getQueryObject.label = "IE1"
    ie2.getQueryObject.label = "IE2"
    ie2.getQueryObject.negated = true
    
    println(q.getQuery())
    println(q.getQuery().runQuery())
  }
  
  def query_7(): Unit = {
    //Query Objects
    val q = new AmlanguageQuery
    val suc = q.createSystemUnitClass()
    val ie = q.createInternalElement()
    ie.setBaseSystemUnit(suc)
    
    //Settings
    ie.getQueryObject.label = "IE"
    suc.getQueryObject.label = "SUC"
    suc.setNameView("Stack", CheckingOption.equalsCheck)
    //suc.getQueryObject().addAttributeFilter("name", "Stack", CheckingOption.equalsCheck)
    suc.getQueryObject.returned = false
    
    println(q.getQuery())
    println(q.getQuery().runQuery())
  }
  
  def query_8(): Unit = {
    //Query Objects
    val q = new AmlanguageQuery
    val ie1 = q.createInternalElement()
    val ie2 = q.createInternalElement()
    ie1.addInternalElements(ie2)
    
    //Settings
    ie1.getQueryObject.label = "IE1"
    ie2.getQueryObject.label = "IE2"
    ie2.getQueryObject.multi = 3 //means >= 3
    
    println(q.getQuery())
    println(q.getQuery().runQuery())
  }*/
  
}