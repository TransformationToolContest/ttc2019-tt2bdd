package de.tudresden.inf.st.ttc19;

import de.tudresden.inf.st.ttc19.jastadd.model.BDD;
import de.tudresden.inf.st.ttc19.jastadd.model.BDT;
import de.tudresden.inf.st.ttc19.jastadd.model.TruthTable;
import de.tudresden.inf.st.ttc19.parser.TruthTableParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class JastAddTest {

  private static Logger logger = LogManager.getLogger(Test.class);

  static Stream<String> truthTableFiles() {
    return Stream.of(
        "../../models/Test.ttmodel",
        "../../models/GeneratedI4O2Seed42.ttmodel",
        "../../models/GeneratedI8O2Seed68.ttmodel",
        "../../models/GeneratedI8O4Seed68.ttmodel",
        "../../models/GeneratedI10O2Seed68.ttmodel");
  }

  static Stream<String> generatedTruthTableFiles() {
    return Stream.of(
        "../../models/GeneratedI4O2Seed42.ttmodel",
        "../../models/GeneratedI8O2Seed68.ttmodel",
        "../../models/GeneratedI8O4Seed68.ttmodel",
        "../../models/GeneratedI10O2Seed68.ttmodel");
  }

  private TruthTable loadTruthTable(String name) {

    TruthTable tt = null;

    TruthTableParser parser = new TruthTableParser();

    try (InputStream stream = new FileInputStream(name)) {
      Assertions.assertNotNull(stream, "unable to load resource '" + name + "'");
      tt = parser.parse(stream);
    } catch (IOException e) {
      Assertions.fail(e);
    }

    Assertions.assertNotNull(tt);

    return tt;
  }

  private void validate(String tt, String bdd) throws IOException {
    logger.debug("calling 'java -jar validator.jar {} {}'", tt, bdd);

    Process proc = Runtime.getRuntime().exec(new String[]{"java", "-jar", "../../models/validator.jar", tt, bdd});
    InputStream in = proc.getInputStream();
    InputStream err = proc.getErrorStream();
    try {
      proc.waitFor();
    } catch (InterruptedException e) {
      Assertions.fail(e);
    }
    byte[] b = new byte[in.available()];
    //noinspection ResultOfMethodCallIgnored
    in.read(b, 0, b.length);
    String output = new String(b);
    logger.debug("process returned {}", output.trim());

    byte[] berr = new byte[err.available()];
    //noinspection ResultOfMethodCallIgnored
    err.read(berr, 0, berr.length);
    String errorOutput = new String(berr);
    if (errorOutput.trim().length() > 0) {
      logger.debug("process returned errors\n{}", errorOutput);
    }

    Assertions.assertTrue(output.trim().endsWith("all OK"));
  }

  @ParameterizedTest
  @MethodSource("generatedTruthTableFiles")
  void testTruthTableRoundtrip(String file) {

    String originalTT = null;

    try (InputStream stream = new FileInputStream(file)) {
      originalTT = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      Assertions.fail(e);
    }

    Assertions.assertNotNull(originalTT);

    StringBuilder roundtripBuilder = new StringBuilder();
    loadTruthTable(file).writeTruthTable(roundtripBuilder);
    String roundtripTT = roundtripBuilder.toString();
    Assertions.assertNotNull(roundtripTT);

    Assertions.assertEquals(originalTT, roundtripTT);
  }

  @ParameterizedTest
  @MethodSource("truthTableFiles")
  void testSimpleBDT(String pathName) throws IOException {
    TruthTable tt = loadTruthTable(pathName);
    File inputFile = new File(pathName);

    BDT simpleBDT = tt.simpleBDT();

    StringBuilder simpleBuilder = new StringBuilder();
    simpleBDT.writeBDT(simpleBuilder);

    Path outputPath = Files.createTempFile("relrag-test-simple", ".bddmodel");
    outputPath.toFile().deleteOnExit();
    try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
      writer.write(simpleBuilder.toString());
    }

    validate(inputFile.getAbsolutePath(), outputPath.toString());
  }

  @ParameterizedTest
  @MethodSource("truthTableFiles")
  void testCaseBDT(String pathName) throws IOException {
    TruthTable tt = loadTruthTable(pathName);
    File inputFile = new File(pathName);

    BDT caseBdd = tt.caseBDT();

    StringBuilder bddBuilder = new StringBuilder();
    caseBdd.writeBDT(bddBuilder);
    Path outputPath = Files.createTempFile("relrag-test-case", ".bddmodel");
    outputPath.toFile().deleteOnExit();
    try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
      writer.write(bddBuilder.toString());
    }

    validate(inputFile.getAbsolutePath(), outputPath.toString());
  }

  @ParameterizedTest
  @MethodSource("truthTableFiles")
  void testCaseBDD(String pathName) throws IOException {
    TruthTable tt = loadTruthTable(pathName);
    File inputFile = new File(pathName);

    BDD caseBdd = tt.caseBDD();

    StringBuilder bddBuilder = new StringBuilder();
    caseBdd.writeBDD(bddBuilder);
    Path outputPath = Files.createTempFile("relrag-test-case-bdd", ".bddmodel");
    outputPath.toFile().deleteOnExit();
    try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
      writer.write(bddBuilder.toString());
    }

    validate(inputFile.getAbsolutePath(), outputPath.toString());
  }

}
