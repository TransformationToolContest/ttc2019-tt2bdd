package org.rosi_project.model_management.core

/**
 * Provides convenient access to all instances of the synchronized models.
 *
 * New models may be registered by name to simplify retrieving their instances.
 */
object ModelElementLists {

  var elements: Map[Class[_ <: AnyRef], Set[AnyRef]] = Map()
  var model2Class: Map[String, Class[_ <: AnyRef]] = Map()

  /**
   * Inserts a new instance.
   *
   * The appropriate model will be inferred automatically
   *
   * @param obj the instance. May never be `null`
   */
  def addElement(obj: Object): Unit = {
    if (obj == null) {
      return
    }

    var elementsWithClass = elements.get(obj.getClass)
    
    if (elementsWithClass.isEmpty) {
      elements += (obj.getClass -> Set(obj))
    } else {
      elements += (obj.getClass -> (elementsWithClass.get ++ Set(obj)))
    }
  }

  /**
   * Queries for all instances of the given class.
   *
   * @param clazz the class
   * @return all matching instances. If none were found or the class is not yet part of the
   *         repository, an empty list will be returned
   */
  def getElementsWithClass(clazz: Class[_ <: AnyRef], excludeSubclasses: Boolean = false): Set[AnyRef] = {
    var matchingElems: Set[AnyRef] = elements.getOrElse(clazz, Set())

    /*if (!excludeSubclasses) {
      matchingElems ++= elements
        .filter(elem => elem._1 != clazz && clazz.isAssignableFrom(elem._1))
        .values
        .fold(List[AnyRef]())((l1, l2) => l1 ++ l2)
    }*/

    matchingElems
  }

  /**
   * Queries for all instances of a given model
   *
   * @param name the name of the model
   * @return if the model was found, all its instances will be wrapped in an [[Option]], otherwise
   *         [[None]] will be returned
   */
  def getElementsForModel(name: String): Option[Set[AnyRef]] = {

    if (!model2Class.contains(name)) {
      None
    } else {
      Some(elements.getOrElse(model2Class(name), Set()))
    }

  }

  def removeElement(obj: AnyRef): Unit = {
    for { elementsWithClass <- elements.get(obj.getClass) } {
      val filtered = elementsWithClass.filter(_ != obj)

      elements += (obj.getClass -> filtered)
    }
  }

  /**
   * Informs the repository about what objects belong to which model
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

  def printFromPackage(s: String): Unit = {
    for (model <- model2Class) {
      println(s"Model: ${model._1}:")

      elements.getOrElse(model._2, Set()).foreach(e => println(s"++ $e"))

    }

    for { clazz <- elements.keys if !model2Class.exists(t => t._2 == clazz) } {
      if (clazz.toString().contains(s)) {
        println(s"Model: $clazz:")

        elements.getOrElse(clazz, Set()).foreach(e => println(s"** $e"))
      }
    }
  }

  def getElementsFromType(s: String): Set[AnyRef] = {
    for { clazz <- elements.keys if !model2Class.exists(t => t._2 == clazz) } {
      if (clazz.toString().contains(s)) {
        return elements.getOrElse(clazz, Set())
      }
    }
    return Set()
  }
  
  def getDirectElementsFromType(s: String): Set[AnyRef] = {
    for { clazz <- elements.keys if !model2Class.exists(t => t._2 == clazz) } {
      if (clazz.getName == s) {
        return elements.getOrElse(clazz, Set())
      }
    }
    return Set()
  }

  def printAll(): Unit = {
    for (model <- model2Class) {
      println(s"Model: ${model._1}:")

      elements.getOrElse(model._2, Set()).foreach(e => println(s"++ $e"))

    }

    for { clazz <- elements.keys if !model2Class.exists(t => t._2 == clazz) } {
      println(s"Model: $clazz:")

      elements.getOrElse(clazz, Set()).foreach(e => println(s"** $e"))
    }
  }

}
