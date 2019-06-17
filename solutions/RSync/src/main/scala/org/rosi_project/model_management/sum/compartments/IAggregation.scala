package org.rosi_project.model_management.sum.compartments

trait IAggregation extends IDirectAggregation {
  
  trait IAggregationSource extends IDirectAggregationSource {
  }
  
  trait IAggregationTarget extends IDirectAggregationTarget {
    def getSource (): ISource = IAggregation.this.source
  }
}