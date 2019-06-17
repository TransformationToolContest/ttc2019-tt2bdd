package org.rosi_project.model_management.sum.compartments

trait IDirectComposition extends IRelationCompartment {
  
  override def deleteCompartment () : Unit = {
    //println("DeleteCompartment IDirectComposition")
    if (source != null)
    {
      source.remove()
      source = null;
    }
    if (target != null)
    {
      var player = target.player
      if (player.isRight) {
        var realPlayer = player.right.get
        target.remove()
        target = null;
        +realPlayer deleteEverything()
      }      
    }
    //+this deleteEverything()
  }
  
  trait IDirectCompositionSource extends ISource {
    override private[model_management] def removeRole(): Unit = +IDirectComposition.this deleteEverything()
  }
  
  trait IDirectCompositionTarget extends ITarget {
    override private[model_management] def removeRole(): Unit = +IDirectComposition.this deleteEverything()
  }
}