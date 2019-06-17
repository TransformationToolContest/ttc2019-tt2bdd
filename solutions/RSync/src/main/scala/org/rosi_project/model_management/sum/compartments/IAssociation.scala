package org.rosi_project.model_management.sum.compartments

trait IAssociation extends IDirectAssoziation {
  
  trait IAssociationSource extends IDirectAssoziationSource {
  }
  
  trait IAssociationTarget extends IDirectAssoziationTarget {    
    def getSource (): ISource = IAssociation.this.source
  }
}