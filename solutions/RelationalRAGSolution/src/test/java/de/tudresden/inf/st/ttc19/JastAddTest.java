package de.tudresden.inf.st.ttc19;

import de.tudresden.inf.st.ttc19.jastadd.model.BDD;
import de.tudresden.inf.st.ttc19.jastadd.model.TruthTable;
import de.tudresden.inf.st.ttc19.parser.TruthTableParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class JastAddTest {

  private static Logger logger = LogManager.getLogger(Test.class);

  TruthTable loadTruthTable(String name) {

    TruthTable tt = null;

    TruthTableParser parser = new TruthTableParser();

    try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(name)) {
      Assertions.assertNotNull(stream, "unable to load resource '" + name + "'");
      tt = parser.parse(stream);
    } catch (IOException e) {
      Assertions.fail(e);
    }

    Assertions.assertNotNull(tt);

    return tt;
  }

  @Test
  void testTruthTableRoundtrip() {
    String file = "test.ttmodel";

    String originalTT = null;

    try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(file)) {
      originalTT = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      Assertions.fail(e);
    }

    Assertions.assertNotNull(originalTT);

    StringBuilder roundtripBuilder = new StringBuilder();
    loadTruthTable(file).writeXMI(roundtripBuilder);
    String roundtripTT = roundtripBuilder.toString();
    Assertions.assertNotNull(roundtripTT);

    Assertions.assertEquals(originalTT, roundtripTT);
  }

  @Test
  void testSimpleBDD() throws IOException {
    TruthTable tt = loadTruthTable("test.ttmodel");

    BDD simpleBDD = tt.simpleBDD();

    StringBuilder simpleBuilder = new StringBuilder();
    simpleBDD.writeXMI(simpleBuilder);

    Path outputPath = Paths.get("relrag-test-simple.bddmodel");
    try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
      writer.write(simpleBuilder.toString());
    }

  }

  @Test
  void testCaseBDT() throws IOException {
    TruthTable tt = loadTruthTable("test.ttmodel");

    BDD caseBdd = tt.caseBDD();

    StringBuilder bddBuilder = new StringBuilder();
    caseBdd.writeXMI(bddBuilder);
    Path outputPath = Paths.get("relrag-test-case.bddmodel");
    try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
      writer.write(bddBuilder.toString());
    }

  }

}
