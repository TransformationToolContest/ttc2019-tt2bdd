package de.tudresden.inf.st.ttc19;

import de.tudresden.inf.st.ttc19.jastadd.model.BDT;
import de.tudresden.inf.st.ttc19.jastadd.model.TruthTable;
import de.tudresden.inf.st.ttc19.parser.TruthTableParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Driver {

  private static String RunIndex;
  private static String Model;
  private static String Tool;
  private static long stopwatch;
  private static TruthTable truthTable;
  private static String ModelPath;

  private static Logger logger = LogManager.getLogger(Driver.class);

  public static void main(String[] args) {
    try {
      initialize();
      load();
      run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void load() {
    stopwatch = System.nanoTime();

    try (final FileInputStream stream = new FileInputStream(ModelPath)) {

      truthTable = new TruthTableParser().parse(stream);
    } catch (IOException e) {
      logger.error("Unable to load model from '" + ModelPath + "'", e);
    }

    stopwatch = System.nanoTime() - stopwatch;
    report(BenchmarkPhase.Load);
  }

  private static void initialize() {
    stopwatch = System.nanoTime();

    Model = System.getenv("Model");
    ModelPath = System.getenv("ModelPath");
    RunIndex = System.getenv("RunIndex");
    Tool = System.getenv("Tool");

    stopwatch = System.nanoTime() - stopwatch;
    report(BenchmarkPhase.Initialization);
  }

  private static void run() throws IOException {
    stopwatch = System.nanoTime();

    BDT solution = truthTable.caseBDT();
    stopwatch = System.nanoTime() - stopwatch;
    report(BenchmarkPhase.Run);
    StringBuilder bddBuilder = new StringBuilder();
    solution.writeBDT(bddBuilder);
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.xmi"))) {
      writer.write(bddBuilder.toString());
    }
  }

  private static void report(BenchmarkPhase phase) {
    System.out.println(String.format("%s;%s;%s;%s;Time;%s", Tool, Model, RunIndex, phase.toString(), Long.toString(stopwatch)));

    if (!"true".equals(System.getenv("NoGC"))) {
      Runtime.getRuntime().gc();
      Runtime.getRuntime().gc();
      Runtime.getRuntime().gc();
      Runtime.getRuntime().gc();
      Runtime.getRuntime().gc();
    }

    final long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    System.out.println(String.format("%s;%s;%s;%s;Memory;%s", Tool, Model, RunIndex, phase.toString(), Long.toString(memoryUsed)));
  }

  enum BenchmarkPhase {
    Initialization, Load, Run
  }
}
