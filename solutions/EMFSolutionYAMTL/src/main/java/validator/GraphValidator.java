package validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import bddg.Assignment;
import bddg.BDD;
import bddg.InputPort;
import bddg.Leaf;
import bddg.Subtree;
import bddg.Tree;
import tt.Cell;
import tt.Port;
import tt.Row;
import tt.TTFactory;
import tt.TruthTable;

/**
 * Validates that a given graph-style BDD model corresponds to a given truth
 * table: it gives the same outputs for the same inputs. We essentially "run"
 * the BDD on the same inputs as the row, and we check that assignments match
 * those of the TT.
 */
public class GraphValidator {

	public boolean validate(TruthTable tt, BDD bdd) {
		if (tt.getPorts().size() != bdd.getPorts().size()) {
			System.err.println(String.format(
				"TT and BDD have different number of ports (TT = %d, BDD = %d)",
				tt.getPorts().size(), bdd.getPorts().size()));
			return false;
		}

		int iRow = 0;
		for (Row ttRow : tt.getRows()) {
			for (Row completedRow : completeRows(tt, ttRow)) {
				for (Tree tree : bdd.getRoot()) { // <-- Artur Boronat: checking forest of DAGs
					if (!validate(iRow, completedRow, tree)) {
						return false;
					}
				}
			}
			iRow++;
		}
		return true;
	}


	/**
	 * Returns a collection of rows that define all the available input ports. Some
	 * rows may only define some of their values. The output port assignments will
	 * not be changed.
	 */
	private List<Row> completeRows(TruthTable tt, Row row) {
		Set<tt.InputPort> ttInputs = getTruthTableInputs(tt);
		Set<tt.InputPort> rowInputs = getRowInputs(row);

		// If there are no unspecified inputs, the row is complete and can be returned as-is
		ttInputs.removeAll(rowInputs);
		if (ttInputs.isEmpty()) {
			return Collections.singletonList(row);
		}

		// There are unspecified inputs: use recursion to build up copies of the row
		// that add the missing values.
		List<Row> completedRows = new ArrayList<>();
		completeRows(tt, row, ttInputs, completedRows);
		return completedRows;
	}

	private void completeRows(TruthTable tt, Row row, Set<tt.InputPort> remainingInputs, List<Row> completed) {
		if (remainingInputs.isEmpty()) {
			completed.add(row);
		} else {
			final Set<tt.InputPort> newRemaining = new HashSet<>(remainingInputs);
			final Iterator<tt.InputPort> itNewRemaining = newRemaining.iterator();
			final tt.InputPort port = itNewRemaining.next();
			itNewRemaining.remove();
			completeRows(tt, row, completed, newRemaining, port, true);
			completeRows(tt, row, completed, newRemaining, port, false);
		}
	}

	private void completeRows(TruthTable tt, Row row, List<Row> completed, final Set<tt.InputPort> newRemaining, final tt.InputPort port, boolean value) {
		Row copyRow = TTFactory.eINSTANCE.createRow();
		for (Cell cOriginal : row.getCells()) {
			Cell cCopy = TTFactory.eINSTANCE.createCell();
			cCopy.setPort(cOriginal.getPort());
			cCopy.setValue(cOriginal.isValue());
			copyRow.getCells().add(cCopy);
		}

		Cell cNew = TTFactory.eINSTANCE.createCell();
		cNew.setPort(port);
		cNew.setValue(value);
		copyRow.getCells().add(cNew);

		completeRows(tt, copyRow, newRemaining, completed);
	}

	private Set<tt.InputPort> getRowInputs(Row row) {
		Set<tt.InputPort> rowInputs = new HashSet<>();
		for (Cell c : row.getCells()) {
			if (c.getPort() instanceof tt.InputPort) {
				rowInputs.add((tt.InputPort) c.getPort());
			}
		}
		return rowInputs;
	}

	private Set<tt.InputPort> getTruthTableInputs(TruthTable tt) {
		Set<tt.InputPort> ttInputs = new HashSet<>();
		for (Port p : tt.getPorts()) {
			if (p instanceof tt.InputPort) {
				ttInputs.add((tt.InputPort) p);
			}
		}
		return ttInputs;
	}

	private boolean validate(int iRow, Row ttRow, Tree tree) {
		final List<Assignment> assignments = findAssignments(ttRow, tree); 
		if (assignments.isEmpty()) {
			System.err.println(String.format("Row %d of TT did not produce any assignments in BDD", ttRow, iRow));
		}

		for (Assignment a : assignments) {
			final String oPortName = a.getPort().getName();
			final boolean expectedResult = getExpectedResult(ttRow, oPortName);
			if (expectedResult != a.isValue()) {
				System.err.println(String.format("Row %d of TT (%s) produces unexpected result O_%s = %s in BDD", iRow, formatRow(ttRow), oPortName, a.isValue()));
				return false;
			}
		}

		// No mismatches found!
		return true;
	}

	private Object formatRow(Row ttRow) {
		final List<String> inputs = new ArrayList<>(ttRow.getCells().size());
		for (Cell c : ttRow.getCells()) {
			inputs.add(
				((c.getPort() instanceof tt.InputPort) ? "I_" : "O_")
				+ c.getPort().getName()
				+ " = "
				+ c.isValue()
			);
		}
		return String.join(", ", inputs);
	}

	private boolean getExpectedResult(Row ttRow, String oPortName) {
		for (Cell c : ttRow.getCells()) {
			if (c.getPort() instanceof tt.OutputPort && c.getPort().getName().equals(oPortName)) {
				return c.isValue();
			}
		}
		throw new NoSuchElementException("Could not find output port " + oPortName + " in the cells of the truth table");
	}

	private List<Assignment> findAssignments(Row ttRow, Tree tree) {
		if (tree instanceof Leaf) {
			Leaf leaf = (Leaf) tree;
			return leaf.getAssignments();
		} else if (tree instanceof Subtree) {
			Subtree sb = (Subtree) tree;
			InputPort sbInputPort = sb.getPort();
			for (Cell c : ttRow.getCells()) {
				// Port must be an input and have the same name
				if (c.getPort() instanceof tt.InputPort && c.getPort().getName().equals(sbInputPort.getName())) {
					return findAssignments(ttRow, c.isValue() ? sb.getTreeForOne() : sb.getTreeForZero());
				}
			}

			// NOTE: since each row with unspecified input variables is completed into
			// multiple fully specified rows, the original fix from Artur Boronat is no
			// longer required.
			throw new NoSuchElementException("Could not find a matching TT cell for the BDD port " + sbInputPort.getName());
		} else {
			throw new IllegalArgumentException("Tree must be a subtree or a leaf");
		}
	}

}
