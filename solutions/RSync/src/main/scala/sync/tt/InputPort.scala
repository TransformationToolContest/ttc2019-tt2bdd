package sync.tt

class InputPort(p_Name: String, p_Cells: Set[Cell], p_Owner: TruthTable, p_Location: String) extends Port(p_Name, p_Cells, p_Owner, p_Location) {

  override def toString(): String = {
    "InputPort:" + " name=" + name + " location=" + location
  }

}



    