package de.tudresden.inf.st.ttc19.util;


import de.tudresden.inf.st.ttc19.jastadd.model.*;

import java.util.NoSuchElementException;

public class Validator {

  public boolean validateBDT(TruthTable tt, BDT bdt) {
    if (tt.getNumPort() != bdt.getNumPort()) {
      System.err.println(String.format(
          "TT and BDT have different number of ports (TT = %d, BDT = %d)",
          tt.getNumPort(), bdt.getNumPort()));
      return false;
    }

    int iRow = 0;
    for (Row ttRow : tt.getRows()) {
      if (!validateBDT(iRow, ttRow, bdt.getTree())) {
        return false;
      }
      iRow++;
    }
    return true;
  }

  private boolean validateBDT(int iRow, Row ttRow, BDT_Tree tree) {
    final BDT_Leaf leaf = findAssignmentsInTree(ttRow, tree);

    // check if the row referenced by the leaf
    if (!leaf.getRowList().contains(ttRow)) {
      System.err.println(String.format("Row %d is not referenced by target leaf!", iRow));
      return false;
    }

    if (leaf.getNumChild() == 0) {
      System.err.println(String.format("Row %d of TT did not produce any assignments in BDT", iRow));
    }

    for (BDT_Assignment a : leaf.getAssignmentList()) {
      final String oPortName = a.getPort().getName();
      final boolean expectedResult = getExpectedResult(ttRow, oPortName);
      if (expectedResult != a.getValue()) {
        System.err.println(String.format("Row %d of TT produces unexpected result %s in BDT", iRow, a.getValue()));
        return false;
      }
    }

    // No mismatches found!
    return true;
  }


  private BDT_Leaf findAssignmentsInTree(Row ttRow, BDT_Tree tree) {
    if (tree instanceof BDT_Leaf) {
      BDT_Leaf leaf = (BDT_Leaf) tree;
      return leaf;
    } else if (tree instanceof BDT_Subtree) {
      BDT_Subtree sb = (BDT_Subtree) tree;
      InputPort sbInputPort = sb.getPort().getTruthTableInputPort();
      for (Cell c : ttRow.getCells()) {
        // Port must be an input and have the same name
        if (c.getPort() instanceof InputPort && c.getPort().getName().equals(sbInputPort.getName())) {
          return findAssignmentsInTree(ttRow, c.getValue() ? sb.getTreeForOne() : sb.getTreeForZero());
        }
      }

      /*
       * (2019-05-23 Artur Boronat) If the row in the table does not require a
       * specific value for the port checked in this subtree, try both values.
       */
      BDT_Leaf zeroList = findAssignmentsInTree(ttRow, sb.getTreeForZero());
      if (zeroList.getNumAssignment() == 0) {
        return findAssignmentsInTree(ttRow, sb.getTreeForOne());
      } else {
        return zeroList;
      }
    } else {
      throw new IllegalArgumentException("Tree must be a subtree or a leaf");
    }
  }

  public boolean validateBDD(TruthTable tt, BDD bdd) {
    if (tt.getNumPort() != bdd.getNumPort()) {
      System.err.println(String.format(
          "TT and BDD have different number of ports (TT = %d, BDD = %d)",
          tt.getNumPort(), bdd.getNumPort()));
      return false;
    }

    int iRow = 0;
    for (Row ttRow : tt.getRows()) {
      if (!validateBDD(iRow, ttRow, bdd.getRoot())) {
        return false;
      }
      iRow++;
    }
    return true;
  }

  private boolean validateBDD(int iRow, Row ttRow, BDD_Tree tree) {
    final BDD_Leaf leaf = findAssignmentsInGraph(ttRow, tree);
    if (leaf.getNumAssignment() == 0) {
      System.err.println(String.format("Row %d of TT did not produce any assignments in BDD", iRow));
    }

    // check if the row referenced by the leaf
    if (!leaf.getRowList().contains(ttRow)) {
      System.err.println(String.format("Row %d is not referenced by target leaf!", iRow));
      return false;
    }

    for (BDD_Assignment a : leaf.getAssignmentList()) {
      final String oPortName = a.getPort().getName();
      final boolean expectedResult = getExpectedResult(ttRow, oPortName);
      if (expectedResult != a.getValue()) {
        System.err.println(String.format("Row %s of TT produces unexpected result %s in BDD", ttRow.rowString(), a.getValue()));
        return false;
      }
    }

    // No mismatches found!
    return true;
  }

  private boolean getExpectedResult(Row ttRow, String oPortName) {
    for (Cell c : ttRow.getCells()) {
      if (c.getPort() instanceof OutputPort && c.getPort().getName().equals(oPortName)) {
        return c.getValue();
      }
    }
    throw new NoSuchElementException("Could not find output port " + oPortName + " in the cells of the truth table");
  }

  private BDD_Leaf findAssignmentsInGraph(Row ttRow, BDD_Tree tree) {
    if (tree instanceof BDD_Leaf) {
      BDD_Leaf leaf = (BDD_Leaf) tree;
      return leaf;
    } else if (tree instanceof BDD_Subtree) {
      BDD_Subtree sb = (BDD_Subtree) tree;
      InputPort sbInputPort = sb.getPort().getTruthTableInputPort();
      for (Cell c : ttRow.getCells()) {
        // Port must be an input and have the same name
        if (c.getPort() instanceof InputPort && c.getPort().getName().equals(sbInputPort.getName())) {
          return findAssignmentsInGraph(ttRow, c.getValue() ? sb.getTreeForOne() : sb.getTreeForZero());
        }
      }

      /*
       * (2019-05-23 Artur Boronat) If the row in the table does not require a
       * specific value for the port checked in this subtree, try both values.
       */
      BDD_Leaf zeroList = findAssignmentsInGraph(ttRow, sb.getTreeForZero());
      if (zeroList.getNumAssignment() == 0) {
        return findAssignmentsInGraph(ttRow, sb.getTreeForOne());
      } else {
        return zeroList;
      }
    } else if (tree == null) {
      throw new IllegalArgumentException("Tree must not be null");
    } else {
      throw new IllegalArgumentException("Tree must be a subtree or a leaf");
    }
  }

}
