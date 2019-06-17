package ttc2019.metamodels.create;

import java.util.List;
import java.util.NoSuchElementException;

import ttc2019.metamodels.bddg.Assignment;
import ttc2019.metamodels.bddg.BDD;
import ttc2019.metamodels.bddg.InputPort;
import ttc2019.metamodels.bddg.Leaf;
import ttc2019.metamodels.bddg.Subtree;
import ttc2019.metamodels.bddg.Tree;
import ttc2019.metamodels.tt.Cell;
import ttc2019.metamodels.tt.Row;
import ttc2019.metamodels.tt.TruthTable;

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
			if (!validate(iRow, ttRow, bdd.getRoot())) {
				return false;
			}
			iRow++;
		}
		return true;
	}

	private boolean validate(int iRow, Row ttRow, Tree tree) {
		final List<Assignment> assignments = findAssignments(ttRow, tree); 
		if (assignments.isEmpty()) {
			System.err.println(String.format("Row %d of TT did not produce any assignments in BDD", iRow));
		}

		for (Assignment a : assignments) {
			final String oPortName = a.getPort().getName();
			final boolean expectedResult = getExpectedResult(ttRow, oPortName);
			if (expectedResult != a.isValue()) {
				System.err.println(String.format("Row %d of TT produces unexpected result %s in BDD", iRow, a.isValue()));
				return false;
			}
		}

		// No mismatches found!
		return true;
	}

	private boolean getExpectedResult(Row ttRow, String oPortName) {
		for (Cell c : ttRow.getCells()) {
			if (c.getPort() instanceof ttc2019.metamodels.tt.OutputPort && c.getPort().getName().equals(oPortName)) {
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
				if (c.getPort() instanceof ttc2019.metamodels.tt.InputPort && c.getPort().getName().equals(sbInputPort.getName())) {
					return findAssignments(ttRow, c.isValue() ? sb.getTreeForOne() : sb.getTreeForZero());
				}
			}

			/*
			 * (2019-05-23 Artur Boronat) If the row in the table does not require a
			 * specific value for the port checked in this subtree, try both values.
			 */
			List<Assignment> zeroList = findAssignments(ttRow, sb.getTreeForZero());
			if (zeroList.isEmpty()) {
				return findAssignments(ttRow, sb.getTreeForOne());
			} else {
				return zeroList;
			}
		} else {
			throw new IllegalArgumentException("Tree must be a subtree or a leaf");
		}
	}

}
