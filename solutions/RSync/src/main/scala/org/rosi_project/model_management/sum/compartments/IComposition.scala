package org.rosi_project.model_management.sum.compartments

trait IComposition extends IDirectComposition {
  
  trait ICompositionSource extends IDirectCompositionSource {    
  }
  
  trait ICompositionTarget extends IDirectCompositionTarget {    
    def getSource (): ISource = IComposition.this.source
  }
}