package ttc2019.metamodels.create;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import sync.tt.Cell;
import sync.tt.Port;
import sync.tt.Row;
import sync.tt.TruthTable;
import ttc2019.TTFactory;

public class CreateTTinJava {

	private TruthTable truthttable = null;
	private Map<EObject, Row> rows = new HashMap<EObject, Row>();
	private Map<EObject, Port> ports = new HashMap<EObject, Port>();
	private Map<EObject, Cell> cells = new HashMap<EObject, Cell>();

	public void createTruthTable(String name, EObject id) {
	    truthttable = TTFactory.getTruthTable(name);
	  }

	public void createInputPort(String name, EObject id) {
	    //mapping += (id -> new InputPort(name, Set.empty, null, null))
	    ports.put(id, TTFactory.getInputPort(name));
	  }

	public void createOutputPort(String name, EObject id) {
	    //mapping += (id -> new OutputPort(name, Set.empty, null, null))
	    ports.put(id, TTFactory.getOutputPort(name));
	  }

	public void createRow(EObject id) {
	    //mapping += (id -> new Row(Set.empty, null, null))
	    rows.put(id, TTFactory.getRow());
	  }

	public void createCell(Boolean value, EObject id) {
	    //mapping += (id -> new Cell(value, null, null, null))
	    cells.put(id, TTFactory.getCell(value));
	  }

	public void createTruthTableRowsRow(EObject tt, EObject row) {
	    //val t = mapping.get(tt).get.asInstanceOf[TruthTable]
	    //val r = mapping.get(row).get.asInstanceOf[Row]
	    Row r = rows.get(row);
	    truthttable.addRows(r);
	    r.setOwner(truthttable);
	  }

	public void createTruthTablePortsPort(EObject tt, EObject port) {
	    //val t = mapping.get(tt).get.asInstanceOf[TruthTable]
	    //val p = mapping.get(port).get.asInstanceOf[Port]
	    Port p = ports.get(port);
	    truthttable.addPorts(p);
	    p.setOwner(truthttable);
	  }

	public void createRowCellsCell(EObject row, EObject cell) {
	    //val c = mapping.get(cell).get.asInstanceOf[Cell]
	    //val r = mapping.get(row).get.asInstanceOf[Row]
	    Cell c = cells.get(cell);
	    Row r = rows.get(row);
	    c.setOwner(r);
	    r.addCells(c);
	  }

	public void createCellPortPort(EObject cell, EObject port) {
	    //val c = mapping.get(cell).get.asInstanceOf[Cell]
	    //val p = mapping.get(port).get.asInstanceOf[Port]
	    Cell c = cells.get(cell);
	    Port p = ports.get(port);
	    c.setPort(p);
	    p.addCells(c);
	  }
}
