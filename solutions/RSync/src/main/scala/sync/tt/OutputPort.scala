package sync.tt

class OutputPort(p_Name: String, p_Cells: Set[Cell], p_Owner: TruthTable, p_Location: String) extends Port(p_Name, p_Cells, p_Owner, p_Location) {

  override def toString(): String = {
    "OutputPort:" + " name=" + name + " location=" + location
  }

}



    