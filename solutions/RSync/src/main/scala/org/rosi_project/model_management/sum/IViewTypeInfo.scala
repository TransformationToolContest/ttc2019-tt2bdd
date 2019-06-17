package org.rosi_project.model_management.sum

import org.rosi_project.model_management.sum.join.IJoinInfo
import org.rosi_project.model_management.core.RsumCompartment

trait IViewTypeInfo {
  
  def getViewName(): String
  
  protected def getNewViewTypeInstance(): IViewCompartment = {
    RsumCompartment.addViewTypeInfo(this)
    var view = RsumCompartment.getActiveViewFromName(this.getViewName())
    if (view == null) {
      return RsumCompartment.addActiveView(this.getNewInstance())
    }
    else {
      return view
    }
  }
  
  def getJoinInfos(): Set[IJoinInfo]
  
  private[model_management] def getViewRsumInstance(): IViewCompartment = getNewInstance
  
  protected def getNewInstance(): IViewCompartment
}