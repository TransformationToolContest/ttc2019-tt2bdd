package org.rosi_project.model_management.core

import scroll.internal.MultiCompartment

trait PlayerSync extends MultiCompartment {
  
  buildClass()

  var deleted: Boolean = false

  def isDeleted: Boolean = deleted

  def buildClass(): Unit = {
    //println("Create New Class")
    if (!SynchronizationCompartment.isUnderConstruction()) {
      this play SynchronizationCompartment.createRoleManager()
      SynchronizationCompartment combine this
      +this manage this
    }
  }

  def deleteObjectFromSynchro(): Unit = {
    println("Delete Object")
    +this deleteRoleFunction()
    deleted = true
  }
}
