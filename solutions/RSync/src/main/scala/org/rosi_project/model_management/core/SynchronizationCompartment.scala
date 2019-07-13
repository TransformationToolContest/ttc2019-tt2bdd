package org.rosi_project.model_management.core

import scala.collection.Seq
import scala.collection.mutable.ListBuffer
import scala.collection.immutable.Set
import scala.util.control.Breaks._

import org.rosi_project.model_management.sync._
import org.rosi_project.model_management.sync.roles._
import org.rosi_project.model_management.sync.compartments._

object SynchronizationCompartment extends ISynchronizationCompartment {

  def createRoleManager(): IRoleManager = new RoleManager()

  private var activeConstructionCompartment: IConstructionCompartment = null //object
  private var activeDestructionCompartment: IDestructionCompartment = null //object
  private var activeSyncCompartmentInstances = Set.empty[ISyncCompartment] //classes
  private var activeExtensionCompartments = Set.empty[IExtenstionCompartment] //objects

  private var availableExtensionCompartments: List[IExtenstionCompartment] = List[IExtenstionCompartment]()
  private var availableConstructionCompartments: List[IConstructionCompartment] = List[IConstructionCompartment]()
  private var availableDestructionCompartments: List[IDestructionCompartment] = List[IDestructionCompartment]()
  private var availableSyncCompartments: List[ISyncCompartment] = List[ISyncCompartment]()

  changeConstructionRule(SuppressingConstructionCompartment)
  changeDestructionRule(GeneralDestructor)

  def getConstructionRule(): IConstructionCompartment = activeConstructionCompartment

  def getDestructionRule(): IDestructionCompartment = activeDestructionCompartment

  def getSyncRules(): Set[ISyncCompartment] = activeSyncCompartmentInstances

  def getExtensions(): Set[IExtenstionCompartment] = activeExtensionCompartments

  def getAvailableConstructionRule(): List[IConstructionCompartment] = availableConstructionCompartments

  def getAvailableDestructionRule(): List[IDestructionCompartment] = availableDestructionCompartments

  def getAvailableSyncRules(): List[ISyncCompartment] = availableSyncCompartments

  def getAvailableExtensions(): List[IExtenstionCompartment] = availableExtensionCompartments

  /**
   * Method for Debug Output.
   */
  private def debugCompleteRoleGraphOutput(): Unit = {
    println("")
    val nodes = this.allPlayers
    nodes.foreach { n =>
      println("Output N: " + n + " Player: " + n.player)
    }
    println("")
  }

  /**
   * Method for Debug Output.
   */
  private def debugSyncRoleGraphOutput(): Unit = {
    println("")
    val nodes = this.allPlayers
    nodes.foreach {
      case n: ISyncRole =>
        val role: ISyncRole = n.asInstanceOf[ISyncRole]
        val compart: ISyncCompartment = role.getOuterCompartment
        println("Output N: " + n + " Player: " + n.player + " Comp: " + compart + " RN: " + compart.getRuleName)
      case _ =>
    }
    println("")
  }

  /**
   * Method for Debug Output.
   */
  private def debugPlayerRolesOutput(): Unit = {
    println("")
    val nodes = this.allPlayers
    nodes.foreach {
      case player: PlayerSync =>
        println("Output N: " + player.roles())
      case _ =>
    }
    println("")
  }

  def registerConstructionCompartment(compartment: IConstructionCompartment): Unit = {
    if (compartment == null)
      return
    if (!availableConstructionCompartments.contains(compartment)) {
      availableConstructionCompartments = availableConstructionCompartments :+ compartment
      this combine compartment
    }
  }

  def registerDestructionCompartment(compartment: IDestructionCompartment): Unit = {
    if (compartment == null)
      return
    if (!availableDestructionCompartments.contains(compartment)) {
      availableDestructionCompartments = availableDestructionCompartments :+ compartment
      this combine compartment
    }
  }

  def registerSyncRule(compartment: ISyncCompartment): Unit = {
    if (compartment == null)
      return
    if (!availableSyncCompartments.contains(compartment)) {
      availableSyncCompartments = availableSyncCompartments :+ compartment
    }
  }

  def registerExtensionCompartment(compartment: IExtenstionCompartment): Unit = {
    if (compartment == null)
      return
    if (!availableExtensionCompartments.contains(compartment)) {
      availableExtensionCompartments = availableExtensionCompartments :+ compartment
      this combine compartment
    }
  }

  def activateExtensionCompartment(compartment: IExtenstionCompartment): Unit = {
    if (compartment == null)
      return
    activeExtensionCompartments += compartment
    registerExtensionCompartment(compartment)
  }

  def deactivateExtensionCompartment(compartment: IExtenstionCompartment): Unit = {
    if (compartment == null)
      return
    activeExtensionCompartments -= compartment
  }

  /**
   * Change the actual construction role.
   */
  def changeConstructionRule(construct: IConstructionCompartment): Unit = {
    if (construct == null) {
      return
    }
    activeConstructionCompartment = construct
    registerConstructionCompartment(construct)
  }

  /**
   * Change the destruction role.
   * Set the new one and remove old roles and add new ones.
   */
  def changeDestructionRule(destruct: IDestructionCompartment): Unit = {
    if (destruct == null) {
      return
    }
    if (activeDestructionCompartment == null) {
      activeDestructionCompartment = destruct
      registerDestructionCompartment(destruct)
      return
    }
    //debugCompleteRoleGraphOutput()
    var nodes = this.allPlayers; //get all nodes
    //delete all destruction roles
    nodes.foreach { n =>
      if (n.isInstanceOf[IDestructor])
        n.remove()
    }
    //debugCompleteRoleGraphOutput()
    //add all new ones
    nodes = this.allPlayers
    nodes.foreach {
      case n: IRoleManager =>
        //add new role here
        val player = n.player
        if (player.isRight) {
          val realPlayer = player.right.get
          val newRole = destruct.getDestructorForClassName(realPlayer)
          n play newRole
        }
      case _ =>
    }
    //debugCompleteRoleGraphOutput()
    activeDestructionCompartment = destruct

    registerDestructionCompartment(destruct)
  }

  /**
   * Integration of a new Model with an integration compartment.
   */
  def integrateNewModel(integrationRule: IIntegrationCompartment): Unit = {
    this combine integrationRule
    val nodes = this.allPlayers
    nodes.foreach {
      case player: PlayerSync =>
        val role = integrationRule.getIntegratorForClassName(player)
        //println("RM: " + n + " Role: " + role)
        if (role != null) {
          player play role
          underConstruction = true;
          (+player).integrate(player)
          underConstruction = false;
          role.remove()
        }
      case _ =>
    }
    nodes.foreach {
      case player: PlayerSync =>
        val role = integrationRule.getRelationalIntegratorsForClassName(player)
        //println("RM: " + n + " Role: " + role)
        if (role != null) {
          player play role
          underConstruction = true;
          (+player).integrate(player)
          underConstruction = false;
          role.remove()
        }
      case _ =>
    }
    integrationRule.finalEditFunction()
  }

  /**
   * Add a new synchronization rule to the synchronization process.
   */
  def addSynchronizationRule(newRule: ISyncCompartment): Unit = {
    if (newRule == null) {
      return
    }
    //if the rule is in the list stop
    activeSyncCompartmentInstances.foreach { s =>
      if (s.getRuleName == newRule.getRuleName)
        return
    }
    activeSyncCompartmentInstances += newRule

    var running = true
    var nodes = Seq[AnyRef]()
    //debugSyncRoleGraphOutput()
    while (running) {
      breakable {
        running = false
        nodes = this.allPlayers; //get all nodes
        nodes.foreach {
          case n: RoleManager =>
            //proof if the role manager does not play this rule
            var proof = true
            val player = n.player
            if (player.isRight) {
              val realPlayer = player.right.get
              val relatedRoles = n.roles()
              //println(realPlayer + "-----" + relatedRoles)
              relatedRoles.foreach {
                case syncRole: ISyncRole =>
                  val syncComp: ISyncCompartment = syncRole.getOuterCompartment
                  if (syncComp.getRuleName == newRule.getRuleName || !newRule.isFirstIntegration(realPlayer))
                    proof = false
                case _ =>
              }

              //if synchronization compartment was not integrated before then integrate now
              if (proof) {
                //add new role to the player
                //the new compartment
                val newComp: ISyncCompartment = newRule.getNewInstance
                val newRole = newComp.getNextIntegrationRole(realPlayer)
                //println("**" + newComp + " " + newRole + " " + n + " " + realPlayer)
                if (newRole != null)
                  n play newRole
                else
                  proof = false

                if (proof) {
                  //add roles to related role manager because on is added to this one
                  val related = n.getRelatedManager
                  related.foreach { r =>
                    val player = r.player
                    if (player.isRight) {
                      val realPlayer = player.right.get
                      if (newComp.isNextIntegration(realPlayer)) {
                        val newRole = newComp.getNextIntegrationRole(realPlayer)
                        r play newRole
                      }
                    }
                  }
                  this combine newComp
                  running = true
                  break
                }
              }
            }
          case _ =>

        }
      }
    }

    registerSyncRule(newRule)
    //debugPlayerRolesOutput()
    //debugSyncRoleGraphOutput()
  }

  def hasSynchronizationRule(ruleName: String): Boolean = {
    activeSyncCompartmentInstances.map(_.getRuleName).contains(ruleName)
  }

  /**
   * Delete all rules with this name.
   */
  def deleteRule(ruleName: String): Unit = {
    val nodes = this.allPlayers //get all nodes
    nodes.foreach {
      case n: ISyncRole =>
        val role: ISyncRole = n.asInstanceOf[ISyncRole]
        val compart: ISyncCompartment = role.getOuterCompartment
        //println("Destruct1: " + n.isInstanceOf[ISyncRole] + " N: " + n + " Player: " + n.player + " Comp: " + compart + " RN: " + compart.getRuleName() + " From: " + from)
        if (compart.getRuleName == ruleName) {
          compart.clearSyncer()
          n.remove()
        }
      case _ =>

    }

    // rule names should be unique (in theory)
    // but we're better save than sorry
    val ruleCompartments = activeSyncCompartmentInstances.filter(_.getRuleName == ruleName)
    for (comp <- ruleCompartments) {
      activeSyncCompartmentInstances -= comp
    }

    //debugCompleteRoleGraphOutput()
  }

  /**
   * Change rule with this name to new rule.
   */
  def changeRuleFromTo(from: String, to: ISyncCompartment): Unit = {
    var running = true
    var nodes = Seq[AnyRef]()
    while (running) {
      breakable {
        running = false
        nodes = this.allPlayers //get all nodes
        nodes.foreach {
          case role: ISyncRole =>
            val compart: ISyncCompartment = role.getOuterCompartment
            //println("Destruct1: " + n.isInstanceOf[ISyncRole] + " N: " + n + " Player: " + n.player + " Comp: " + compart + " RN: " + compart.getRuleName() + " From: " + from)
            if (compart.getRuleName == from) {
              //exchange this with a new compartment
              val newComp: ISyncCompartment = to.getNewInstance
              compart.getSyncer.foreach { r =>
                val manager = (+r).getManager()
                if (manager.isRight) {
                  val realManager: RoleManager = manager.right.get(0).right.get
                  val player = r.player
                  if (player.isRight) {
                    val realPlayer = player.right.get
                    val newRole = newComp.getNextIntegrationRole(realPlayer)
                    r.remove()
                    realManager play newRole
                  }
                }
              }
              //role graph combination
              this combine newComp
              //delete compartment
              compart.clearSyncer()
              running = true
              break
            }
          case _ =>

        }
      }
    }

    // rule names should be unique (in theory)
    // but we're better save than sorry
    val oldRuleCompartments = activeSyncCompartmentInstances.filter(_.getRuleName == from)
    for (comp <- oldRuleCompartments) {
      activeSyncCompartmentInstances -= comp
    }
    activeSyncCompartmentInstances += to

    registerSyncRule(to)
    //debugSyncRoleGraphOutput()
  }

  class RoleManager() extends IRoleManager {

    def getRelatedClassFromName(name: String): PlayerSync = {
      getRelatedManager.foreach(rm => {
        val realPlayer = rm.player.right.get
        //TODO: look on more superclasses
        //println(realPlayer.getClass.getSimpleName + " " + realPlayer.getClass.getName + " " + realPlayer.getClass.getCanonicalName)
        if (realPlayer.getClass.getName.contains(name) || realPlayer.getClass.getSuperclass.getName.contains(name)) {
          return realPlayer.asInstanceOf[PlayerSync]
        }
      })
      null
    }

    def getSetRelatedClassesFromName(name: String): Set[PlayerSync] = {
      var resultSet: Set[PlayerSync] = Set.empty
      getRelatedManager.foreach(rm => {
        val realPlayer = rm.player.right.get
        if (realPlayer.getClass.getName.contains(name) || realPlayer.getClass.getSuperclass.getName.contains(name)) {
          resultSet += realPlayer.asInstanceOf[PlayerSync]
        }
      })
      resultSet
    }

    def insertNotification(): Unit = {
      //println("Insert Notification")
      +this notifyInsertion ()
    }

    def deletionNotification(): Unit = {
      //println("Deletion Notification")
      +this notifyDeletion ()
    }

    def updateNotification(): Unit = {
      //println("Update Notification")
      +this notifyUpdate ()
    }

    def printAllManager(): Unit = {
      println("++ Ma => Pl: " + this + " | " + this.player.right.get)
      getRelatedManager().foreach(m => {
        println("-- Ma => Pl: " + m + " | " + m.player.right.get)
      })
    }

    def deleteManage(value: PlayerSync): Unit = {
      val delete = activeDestructionCompartment.getDestructorForClassName(value)
      if (delete != null) {
        this play delete
        +this deleteRoleFunction ()
      }
    }

    def manage(value: PlayerSync): Unit = {
      val construct = activeConstructionCompartment.getConstructorForClassName(value)
      if (construct != null) {
        this play construct
        underConstruction = true;
        val _ = +this construct (value, this)
        underConstruction = false;
        construct.remove()
      }
    }

    /**
     * Create a relation between two IRoleManager and RoleManager of other PlayerSync instances.
     */
    def makePlayerSyncRelated(playerSync: PlayerSync): Unit = {
      +playerSync makeRelated (this)
    }
  }

}
