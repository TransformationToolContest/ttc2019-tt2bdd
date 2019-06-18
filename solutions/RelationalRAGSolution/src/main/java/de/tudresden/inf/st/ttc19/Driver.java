package de.tudresden.inf.st.ttc19;

import de.tudresden.inf.st.ttc19.jastadd.model.BDD;
import de.tudresden.inf.st.ttc19.jastadd.model.BDT;
import de.tudresden.inf.st.ttc19.jastadd.model.PortOrder;
import de.tudresden.inf.st.ttc19.jastadd.model.TruthTable;
import de.tudresden.inf.st.ttc19.parser.TruthTableParser;
import de.tudresden.inf.st.ttc19.util.Validator;
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

  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println("Usage: java -jar Driver RESULT_TYPE COMPUTATION PORT_ORDER");
      System.err.println("RESULT_TYPE = bdd|bdt");
      System.err.println("COMPUTATION = dynamic|ordered|reduced");
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
      validate();
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

    solutionHandler.computeSolution(truthTable);
    solutionHandler.writeResult();
  }

  private static void validate() {
    boolean validationResult = solutionHandler.validate(truthTable);
    if (!validationResult) {
      logger.error("Generated model is invalid!");
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

    if (phase == BenchmarkPhase.Run) {
      System.out.println(String.format("%s;%s;%s;%s;DecisionNodes;%s", Tool, Model, RunIndex, phase.toString(), Integer.toString(solutionHandler.decisionNodeCount())));
      System.out.println(String.format("%s;%s;%s;%s;AssignmentNodes;%s", Tool, Model, RunIndex, phase.toString(), Integer.toString(solutionHandler.assignmentNodeCount())));
      System.out.println(String.format("%s;%s;%s;%s;MinPath;%s", Tool, Model, RunIndex, phase.toString(), Integer.toString(solutionHandler.minPathLength())));
      System.out.println(String.format("%s;%s;%s;%s;MaxPath;%s", Tool, Model, RunIndex, phase.toString(), Integer.toString(solutionHandler.maxPathLength())));
      System.out.println(String.format("%s;%s;%s;%s;AvgPath;%s", Tool, Model, RunIndex, phase.toString(), Double.toString(solutionHandler.avgPathLength())));

    }
  }

  enum BenchmarkPhase {
    Initialization, Load, Run
  }

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
      String directoryName = "../../output/solutions/" + solutionName();
      String fileName = directoryName + "/" + ModelPath.substring(ModelPath.lastIndexOf('/') + 1) + ".xmi";
      Files.createDirectories(Paths.get(directoryName));
      try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
        writer.write(result);
      }
    }

    protected abstract String getResultAsString();

    protected abstract boolean validate(TruthTable tt);

    protected abstract int decisionNodeCount();

    protected abstract int assignmentNodeCount();

    protected abstract int minPathLength();

    protected abstract int maxPathLength();

    protected abstract double avgPathLength();

    protected abstract String solutionName();
  }

  private static class BDTSolutionHandler extends SolutionHandler {

    BDT lastResult;

    @Override
    protected void computeSolution0(TruthTable tt) {
      switch (Computation) {
        case "dynamic":
          lastResult = tt.BDT();
          break;
        case "reduced":
          logger.warn("reduced BDT is not yet supported, using ordered BDT!");
        case "ordered":
          lastResult = tt.OBDT();
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

    @Override
    protected boolean validate(TruthTable tt) {
      return new Validator().validateBDT(tt, lastResult);
    }

    @Override
    protected int decisionNodeCount() {
      return lastResult.decisionNodeCount();
    }

    @Override
    protected int assignmentNodeCount() {
      return lastResult.assignmentNodeCount();
    }

    @Override
    protected int minPathLength() {
      return lastResult.minPathLength();
    }

    @Override
    protected int maxPathLength() {
      return lastResult.maxPathLength();
    }

    @Override
    protected double avgPathLength() {
      return lastResult.avgPathLength();
    }

    @Override
    protected String solutionName() {
      return "RelationalRAG-BDT-" + Computation + "-" + PortOrderType;
    }
  }

  private static class BDDSolutionHandler extends SolutionHandler {

    BDD lastResult;

    @Override
    protected void computeSolution0(TruthTable tt) {
      switch (Computation) {
        case "dynamic":
          lastResult = tt.BDD();
          break;
        case "ordered":
          lastResult = tt.fullOBDD();
          break;
        case "reduced":
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

    @Override
    protected boolean validate(TruthTable tt) {
      return new Validator().validateBDD(tt, lastResult);
    }

    @Override
    protected int decisionNodeCount() {
      return lastResult.decisionNodeCount();
    }

    @Override
    protected int assignmentNodeCount() {
      return lastResult.assignmentNodeCount();
    }

    @Override
    protected int minPathLength() {
      return lastResult.minPathLength();
    }

    @Override
    protected int maxPathLength() {
      return lastResult.maxPathLength();
    }

    @Override
    protected double avgPathLength() {
      return lastResult.avgPathLength();
    }

    @Override
    protected String solutionName() {
      return "RelationalRAG-BDD-" + Computation + "-" + PortOrderType;
    }
  }
}
