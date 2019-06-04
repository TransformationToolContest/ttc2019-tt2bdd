package de.tudresden.inf.st.ttc19;

import de.tudresden.inf.st.ttc19.jastadd.model.BDD;
import de.tudresden.inf.st.ttc19.jastadd.model.TruthTable;
import de.tudresden.inf.st.ttc19.parser.TruthTableParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

  private static Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("Starting Relational RAG solution.");

    TruthTableParser parser = new TruthTableParser();
    TruthTable tt = parser.parse("test.ttmodel");

    StringBuilder builder = new StringBuilder();
    tt.writeXMI(builder);
    logger.info("Printed XMI:\n{}", builder.toString());

    BDD bdd = tt.simpleBDD();

    StringBuilder bddBuilder = new StringBuilder();
    try {
      bdd.writeXMI(bddBuilder);
      logger.info("BDD XMI:\n{}", bddBuilder.toString());
      Path outputPath = Paths.get("relrag-test.bddmodel");
      try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
        writer.write(bddBuilder.toString());
      }
    } catch (Exception e) {
      logger.error("Problems in writing bdd xmi");
      logger.catching(e);
    }
  }

}
