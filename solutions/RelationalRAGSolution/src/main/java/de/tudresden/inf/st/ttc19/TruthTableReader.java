package de.tudresden.inf.st.ttc19;

import de.tudresden.inf.st.ttc19.jastadd.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Testing heuristic port order.
 *
 * @author rschoene - Initial contribution
 */
public class TruthTableReader {

  private static final Logger logger = LogManager.getLogger(TruthTableReader.class);

  public static void main(String[] args) {
    TruthTable tt = new TruthTable();
    System.out.println(Paths.get(".").toAbsolutePath().toString());
    try (BufferedReader reader = Files.newBufferedReader(Paths.get("src", "main", "resources", "one.tt"))) {
//    try (BufferedReader reader = new BufferedReader(new InputStreamReader(TruthTableReader.class.getResourceAsStream("one.tt")))) {
      String header = reader.readLine();
      String[] ports = header.split(" ");
      boolean isInput = true;
      for (String port : ports) {
        if (port.equals("->")) {
          isInput = false;
          continue;
        }
        Port newPort = isInput ? new InputPort("", port) : new OutputPort("", port);
        tt.addPort(newPort);
      }
      String line;
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(" ");
        Row row = new Row();
        for (int i = 0; i < values.length; i++) {
          String value = values[i];
          if (value.equals("-")) continue;
          Cell cell = new Cell();
          cell.setValue(value.equals("1"));
          cell.setPort(tt.getPort(i));
          row.addCell(cell);
        }
        tt.addRow(row);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    StringBuilder sb = new StringBuilder();
    tt.writeTruthTable(sb);
    System.out.println(sb);

//    tt.setPortOrder(tt.getHeuristicPortOrder());
    tt.setPortOrder(tt.getNaturalPortOrder());

    tt.reducedOBDD();
    BDD bdd = tt.BDD();
    logger.info("BDD has {} nodes.", bdd.getNumTree());
  }
}
