package ttc2019.metamodels.create;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import ttc2019.metamodels.bdd.Assignment;
import ttc2019.metamodels.bdd.BDD;
import ttc2019.metamodels.bdd.BDDPackage;
import ttc2019.metamodels.bdd.InputPort;
import ttc2019.metamodels.bdd.Leaf;
import ttc2019.metamodels.bdd.Subtree;
import ttc2019.metamodels.bdd.Tree;
import ttc2019.metamodels.tt.Cell;
import ttc2019.metamodels.tt.Row;
import ttc2019.metamodels.tt.TTPackage;
import ttc2019.metamodels.tt.TruthTable;

/**
 * Validates that a given BDD model corresponds to a given truth table: it gives
 * the same outputs for the same inputs. We essentially "run" the BDD on the same
 * inputs as the row, and we check that assignments match those of the TT.
 */
public class Validator {

	private boolean validate(TruthTable tt, BDD bdd) {
		if (tt.getPorts().size() != bdd.getPorts().size()) {
			System.err.println(String.format(
				"TT and BDD have different number of ports (TT = %d, BDD = %d)",
				tt.getPorts().size(), bdd.getPorts().size()));
			return false;
		}

		int iRow = 0;
		for (Row ttRow : tt.getRows()) {
			if (!validate(iRow, ttRow, bdd.getTree())) {
				return false;
			}
			iRow++;
		}
		return true;
	}

	private boolean validate(int iRow, Row ttRow, Tree tree) {
		final List<Assignment> assignments = findAssignments(ttRow, tree); 

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
			throw new NoSuchElementException("Could not find input port " + sbInputPort.getName() + " in the cells of the truth table");
		} else {
			throw new IllegalArgumentException("Tree must be a subtree or a leaf");
		}
	}

	public void validate(String ttName, String bddName) {
		final File fInputTT = new File(ttName);
		final File fOutputBDD = new File(bddName);

		try {
			// We only need to get these two metamodels loaded
			TTPackage.eINSTANCE.getName();
			BDDPackage.eINSTANCE.getName();
			
			ResourceSet rs = new ResourceSetImpl();
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
			Resource rTT = rs.getResource(URI.createFileURI(fInputTT.getCanonicalPath()), true);
			Resource rBDD = rs.getResource(URI.createFileURI(fOutputBDD.getCanonicalPath()), true);

			TruthTable tt = (TruthTable) rTT.getContents().get(0);
			BDD bdd = (BDD) rBDD.getContents().get(0);

			final boolean valid = new Validator().validate(tt, bdd);
			if (valid) {
				System.out.println(String.format("BDD %2$s matches TT %1$s: all OK", fInputTT.getName(), fOutputBDD.getName()));
			} else {
				System.err.println(String.format("BDD %2$s does not match TT %1$s: see issues above", fInputTT.getName(), fOutputBDD.getName()));
				System.exit(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(255);
		}
	}
}
