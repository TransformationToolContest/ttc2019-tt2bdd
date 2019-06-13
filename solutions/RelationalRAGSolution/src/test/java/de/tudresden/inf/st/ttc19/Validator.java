package de.tudresden.inf.st.ttc19;


import de.tudresden.inf.st.ttc19.jastadd.model.*;

import java.util.NoSuchElementException;

public class Validator {

  public boolean validate(TruthTable tt, BDD bdd) {
    if (tt.getNumPort() != bdd.getPorts().size()) {
      System.err.println(String.format(
          "TT and BDD have different number of ports (TT = %d, BDD = %d)",
          tt.getNumPort(), bdd.getPorts().size()));
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

  private boolean validate(int iRow, Row ttRow, AbstractNode tree) {
    final JastAddList<Assignment> assignments = findAssignments(ttRow, tree);
    if (assignments.getNumChild() == 0) {
      System.err.println(String.format("Row %d of TT did not produce any assignments in BDD", iRow));
    }

    for (Assignment a : assignments) {
      final String oPortName = a.getPort().getName();
      final boolean expectedResult = getExpectedResult(ttRow, oPortName);
      if (expectedResult != a.getValue()) {
        System.err.println(String.format("Row %s of TT produces unexpected result %s in BDD", ttRow.rowString(), a.getValue()));
        //return false;
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

  private JastAddList<Assignment> findAssignments(Row ttRow, AbstractNode tree) {
    if (tree instanceof TerminalNode) {
      TerminalNode leaf = (TerminalNode) tree;
      return leaf.getAssignmentList();
    } else if (tree instanceof InnerNode) {
      InnerNode sb = (InnerNode) tree;
      InputPort sbInputPort = sb.getPort();
      for (Cell c : ttRow.getCells()) {
        // Port must be an input and have the same name
        if (c.getPort() instanceof InputPort && c.getPort().getName().equals(sbInputPort.getName())) {
          return findAssignments(ttRow, c.getValue() ? sb.getGraphForOne() : sb.getGraphForZero());
        }
      }

      /*
       * (2019-05-23 Artur Boronat) If the row in the table does not require a
       * specific value for the port checked in this subtree, try both values.
       */
      JastAddList<Assignment> zeroList = findAssignments(ttRow, sb.getGraphForZero());
      if (zeroList.getNumChild() == 0) {
        return findAssignments(ttRow, sb.getGraphForOne());
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
