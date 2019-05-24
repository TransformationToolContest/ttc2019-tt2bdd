package de.tudresden.inf.st.ttc19;

import de.tudresden.inf.st.ttc19.jastadd.model.TruthTable;
import de.tudresden.inf.st.ttc19.parser.TruthTableParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

  private static Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("Starting Relational RAG solution.");

    TruthTableParser parser = new TruthTableParser();
    TruthTable tt = parser.parse("test.ttmodel");

    StringBuilder builder = new StringBuilder();
    tt.writeXMI(builder);
    logger.info("Printed XMI:\n{}", builder.toString());
  }

}
