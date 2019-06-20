package sync.tt

//import scala.collection.mutable.Set

class OutputPort(p_Name: String, p_Cells: Set[Cell] = Set.empty, p_Owner: TruthTable = null, p_Location: String = null) extends Port(p_Name, p_Cells, p_Owner, p_Location) {

  override def toString(): String = {
    "OutputPort:" + " name=" + name + " location=" + location
  }

}



    