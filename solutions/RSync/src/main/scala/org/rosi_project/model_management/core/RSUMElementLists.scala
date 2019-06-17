package org.rosi_project.model_management.core

/** Provides convenient access to all instances of the synchronized models.
  *
  * New models may be registered by name to simplify retrieving their instances.
  */
object RSUMElementLists {
  
  var elements: Map[Class[_ <: AnyRef], List[_ <: AnyRef]] = Map()
  var model2Class: Map[String, Class[_ <: AnyRef]] = Map()

  /** Inserts a new instance.
    *
    * The appropriate model will be inferred automatically
    *
    * @param obj the instance. May never be `null`
    */
  def addElement(obj: Object): Unit = {
    if (obj == null) {
      return
    }

    var elementsWithClass: List[AnyRef] = elements getOrElse (obj.getClass, List())

    if (elementsWithClass.contains(obj)) {
      return
    }

    elementsWithClass = obj :: elementsWithClass

    elements += (obj.getClass -> elementsWithClass)
  }

  /** Queries for all instances of the given class.
    *
    * @param clazz the class
    * @return all matching instances. If none were found or the class is not yet part of the
    *         repository, an empty list will be returned
    */
  def getElementsWithClass(clazz: Class[_ <: AnyRef], excludeSubclasses: Boolean = false): List[AnyRef] = {
    var matchingElems: List[AnyRef] = elements.getOrElse(clazz, List())

    if (!excludeSubclasses) {
      matchingElems ++= elements
        .filter(elem => elem._1 != clazz && clazz.isAssignableFrom(elem._1))
        .values
        .fold(List[AnyRef]())((l1, l2) => l1 ++ l2)
    }

    matchingElems
  }

  /** Queries for all instances of a given model
    *
    * @param name the name of the model
    * @return if the model was found, all its instances will be wrapped in an [[Option]], otherwise
    *         [[None]] will be returned
    */
  def getElementsForModel(name: String): Option[List[AnyRef]] = {

    if (!model2Class.contains(name)) {
      None
    } else {
      Some(elements.getOrElse(model2Class(name), List()))
    }

  }

  def removeElement(obj: AnyRef): Unit = {
    for {elementsWithClass <- elements.get(obj.getClass)} {
      val filtered = elementsWithClass.filter(_ != obj)

      elements += (obj.getClass -> filtered)
    }
  }


  /** Informs the repository about what objects belong to which model
    *
    * @param name the model's name
    * @param elemsType the class of the corresponding instances
    * @throws IllegalArgumentException if the name is already in use
    */
  def registerModel(name: String, elemsType: Class[_ <: AnyRef]): Unit = {
    if (model2Class.contains(name)) {
      throw new IllegalArgumentException(s"Model is already present: $name")
    }

    model2Class += (name -> elemsType)
  }

  def printAll(): Unit = {
    for (model <- model2Class) {
      println(s"Model: ${model._1}:")

      elements.getOrElse(model._2, List()).foreach(e => println(s"** $e"))

    }

    for { clazz <- elements.keys if !model2Class.exists(t => t._2 == clazz) } {
      println(s"Model: $clazz:")

      elements.getOrElse(clazz, List()).foreach(e => println(s"** $e"))
    }
  }
}