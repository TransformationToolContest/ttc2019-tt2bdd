package de.tudresden.inf.st.ttc19;

import de.tudresden.inf.st.ttc19.jastadd.model.BDD;
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
import java.util.Arrays;

public class Driver {

  private static String RunIndex;
  private static String Model;
  private static String Tool;
  private static String Computation;
  private static String PortOrderType;
  private static long stopwatch;
  private static SolutionHandler solutionHandler;
  private static TruthTable truthTable;
  private static String ModelPath;

  private static Logger logger = LogManager.getLogger(Driver.class);

  private static abstract class SolutionHandler {
    void computeSolution(TruthTable tt) {
      stopwatch = System.nanoTime();
      computeSolution0(tt);
      stopwatch = System.nanoTime() - stopwatch;
      report(BenchmarkPhase.Run);
    }

    protected abstract void computeSolution0(TruthTable tt);

    void writeResult() throws IOException {
      String result = getResultAsString();
      try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.xmi"))) {
        writer.write(result);
      }
    }

    protected abstract String getResultAsString();
  }

  private static class BDTSolutionHandler extends SolutionHandler {

    BDT lastResult;

    @Override
    protected void computeSolution0(TruthTable tt) {
      switch (Computation) {
        case "simple":
          lastResult = tt.simpleBDT();
          break;
        case "case":
          lastResult = tt.caseBDT();
          break;
        default:
          System.err.println("Invalid computation type for BDT: " + Computation);
      }
    }

    @Override
    protected String getResultAsString() {
      StringBuilder sb = new StringBuilder();
      lastResult.writeBDT(sb);
      return sb.toString();
    }
  }

  private static class BDDSolutionHandler extends SolutionHandler {

    BDD lastResult;

    @Override
    protected void computeSolution0(TruthTable tt) {
      switch (Computation) {
        case "case":
          lastResult = tt.caseBDD();
          break;
        case "reduction":
          lastResult = tt.reductionOBDD();
          break;
        default:
          System.err.println("Invalid computation type for BDD: " + Computation);
      }
    }

    @Override
    protected String getResultAsString() {
      StringBuilder sb = new StringBuilder();
      lastResult.writeBDD(sb);
      return sb.toString();
    }
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println("Usage: java -jar Driver RESULT_TYPE COMPUTATION PORT_ORDER");
      System.err.println("RESULT_TYPE = bdd|bdt");
      System.err.println("COMPUTATION for bdt: simple|case. for bdd: case|reduction");
      System.err.println("PORT_ORDER = natural|heuristic");
      System.exit(1);
    }
    args = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
    switch (args[0]) {
      case "bdt":
        solutionHandler = new BDTSolutionHandler();
        break;
      case "bdd":
        solutionHandler = new BDDSolutionHandler();
        break;
      default:
        System.err.println("Invalid result type: " + args[0]);
        System.exit(1);
    }
    Computation = args[1];
    PortOrderType = args[2];
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
      switch (PortOrderType) {
        case "natural":
          truthTable.setPortOrder(truthTable.getNaturalPortOrder());
          break;
        case "heuristic":
          truthTable.setPortOrder(truthTable.getHeuristicPortOrder());
          break;
        default:
          System.err.println("Invalid port order type: " + PortOrderType);
          System.exit(1);
      }
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
    solutionHandler.computeSolution(truthTable);
    solutionHandler.writeResult();
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
