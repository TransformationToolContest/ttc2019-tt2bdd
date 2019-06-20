package org.rosi_project.model_management.core

import scroll.internal.MultiCompartment

trait PlayerSync extends MultiCompartment {
  
  buildClass()

  var deleted: Boolean = false

  def isDeleted: Boolean = deleted

  def buildClass(): Unit = {
    //println("Create New Class")
    if (!SynchronizationCompartment.isUnderConstruction()) {
      SynchronizationCompartment combine this
      val mani = SynchronizationCompartment.createRoleManager()
      this play mani
      mani.manage(this)
      //this play SynchronizationCompartment.createRoleManager()
      //+this manage this
    }
  }

  def deleteObjectFromSynchro(): Unit = {
    println("Delete Object")
    +this deleteManage this
    deleted = true
  }
}
