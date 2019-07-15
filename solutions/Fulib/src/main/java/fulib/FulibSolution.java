package fulib;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.fulib.tables.FulibTable;
import ttc2019.metamodels.bdd.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class FulibSolution
{
   private static int numberOfBDDNodes = 0;
   private static String model;

   public static void main(String[] args) throws IOException
   {
      String model = System.getenv("Model");
      String modelPath = System.getenv("ModelPath");
      String runIndex = System.getenv("RunIndex");
      String tool = System.getenv("Tool");

      doTransform(model, modelPath, runIndex, tool, true);
   }

   public static BDD doTransform(String myModel, String modelPath, String runIndex, String tool, boolean doSave) throws IOException
   {
      model = myModel;

      long startTime = System.nanoTime();

      Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
      Map<String, Object> m = reg.getExtensionToFactoryMap();
      m.put("xmi", new XMIResourceFactoryImpl());

      // Obtain a new resource set
      ResourceSet resSet = new ResourceSetImpl();

      // create a resource
      Resource resource = resSet.createResource(URI
            .createURI(model + ".xmi"));

      report(startTime, model, "Initialization", runIndex, tool);

      // read truth table
      startTime = System.nanoTime();
      byte[] bytes = Files.readAllBytes(Paths.get(modelPath));
      String body = new String(bytes);
      String[] lines = body.split("\n");
      FulibTable truthTable = new FulibTable();

      readTruthTable(lines, truthTable);

      report(startTime, model, "Load", runIndex, tool);

      // run
      startTime = System.nanoTime();

      BDD bdd = BDDFactory.eINSTANCE.createBDD();

      for (String key : truthTable.getColumnMap().keySet())
      {
         if (key.startsWith("I")) {
            InputPort inputPort = BDDFactory.eINSTANCE.createInputPort();
            inputPort.setName(key);
            bdd.getPorts().add(inputPort);
         }
         else {
            OutputPort outputPort = BDDFactory.eINSTANCE.createOutputPort();
            outputPort.setName(key);
            bdd.getPorts().add(outputPort);
         }
      }

      Tree subtree = doOneBDDLevel(bdd, truthTable);

      bdd.setTree(subtree);

      report(startTime, model, "Run", runIndex, tool);

      if (doSave) {
         saveModel(bdd);
      }

      return bdd;
   }


   private static void saveModel(BDD bdd)
   {
      Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
      Map<String, Object> m = reg.getExtensionToFactoryMap();
      m.put("xmi", new XMIResourceFactoryImpl());

      // Obtain a new resource set
      ResourceSet resSet = new ResourceSetImpl();

      // create a resource
      Resource resource = resSet.createResource(URI
            .createURI(model + ".xmi"));
      // Get the first model element and cast it to the right type, in my
      // example everything is hierarchical included in this first node
      resource.getContents().add(bdd);

      // now save the content.
      try {
         resource.save(Collections.EMPTY_MAP);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   private static Tree doOneBDDLevel(BDD bdd, FulibTable truthTable)
   {
      if (truthTable.getTable().size() == 1)
      {
         Leaf leaf = BDDFactory.eINSTANCE.createLeaf();
         numberOfBDDNodes++;

         for (String key : truthTable.getColumnMap().keySet())
         {
            if (key.startsWith("O")) {
               int value = (int) truthTable.getValue(key, 0);
               Assignment assignment = BDDFactory.eINSTANCE.createAssignment();
               OutputPort port = getOutputPort(bdd, key);
               assignment.setPort(port);
               assignment.setValue(value == 1);
               leaf.getAssignments().add(assignment);
            }
         }
         return leaf;
      }
      else
      {
         String varName = chooseVariable(truthTable);

         ArrayList<FulibTable> kidTables = splitTable(truthTable, varName);

         Subtree subtree = BDDFactory.eINSTANCE.createSubtree();
         InputPort inputPort = getInputPort(bdd, varName);
         subtree.setPort(inputPort);
         numberOfBDDNodes++;

         Tree falseSubTree = doOneBDDLevel(bdd, kidTables.get(0));
         Tree trueSubTree = doOneBDDLevel(bdd, kidTables.get(1));

         subtree.setTreeForZero(falseSubTree);
         subtree.setTreeForOne(trueSubTree);

         return subtree;
      }
   }

   private static InputPort getInputPort(BDD bdd, String varName)
   {
      for (Port port : bdd.getPorts())
      {
         if (port.getName().equals(varName)) {
            return (InputPort) port;
         }
      }

      InputPort inputPort = BDDFactory.eINSTANCE.createInputPort();
      inputPort.setName(varName);
      bdd.getPorts().add(inputPort);

      return inputPort;
   }

   private static OutputPort getOutputPort(BDD bdd, String varName)
   {
      for (Port port : bdd.getPorts())
      {
         if (port.getName().equals(varName)) {
            return (OutputPort) port;
         }
      }

      OutputPort outputPort = BDDFactory.eINSTANCE.createOutputPort();
      outputPort.setName(varName);
      bdd.getPorts().add(outputPort);

      return outputPort;
   }


   private static ArrayList<FulibTable> splitTable(FulibTable truthTable, String varName)
   {
      FulibTable falseTable = new FulibTable();
      FulibTable trueTable = new FulibTable();

      for (String key : truthTable.getColumnMap().keySet())
      {
         if (! key.equals(varName)) {
            falseTable.addColumn(key);
            trueTable.addColumn(key);
         }
      }

      int varIndex = truthTable.getColumnMap().get(varName);

      for (ArrayList row : truthTable.getTable())
      {
         ArrayList<Integer> clone = (ArrayList<Integer>) row.clone();
         clone.remove(varIndex);
         int value = (Integer) row.get(varIndex);
         if (value == 1) {
            trueTable.addRow(clone);
         }
         else {
            falseTable.addRow(clone);
         }
      }

      ArrayList<FulibTable> result = new ArrayList<>();
      result.add(falseTable);
      result.add(trueTable);
      return result;
   }

   private static String chooseVariable(FulibTable truthTable)
   {
      LinkedHashMap<String, Integer> columnMap = truthTable.getColumnMap();

      String firstColumn = columnMap.keySet().iterator().next();

      return firstColumn;
   }


   private static void readTruthTable(String[] lines, FulibTable truthTable)
   {
      int lineNumber = 0;
      String line;

      // read ports
      while (lineNumber < lines.length)
      {
         line = lines[lineNumber];
         lineNumber++;

         String[] tokens = line.split(" +");

         if (tokens.length > 0 && tokens[1].startsWith("<rows>")) {
            lineNumber--;
            break;
         }

         if (tokens.length > 3 && tokens[1].equals("<ports")) {
            String nameToken = tokens[3];
            String[] pair = nameToken.split("=");
            String name = pair[1].replaceAll("\"", "");
            truthTable.addColumn(name);
         }
      }

      // read rows
      while (lineNumber < lines.length)
      {
         line = lines[lineNumber];
         lineNumber++;
         String[] tokens = line.split(" +");

         if (tokens.length > 1 && tokens[1].startsWith("<rows>")) {
            // read values
            ArrayList<Integer> row = new ArrayList<>();
            while (lineNumber < lines.length)
            {
               line = lines[lineNumber];
               lineNumber++;
               tokens = line.split(" +");

               if (tokens.length > 0 && tokens[1].startsWith("</rows>")) {
                  break;
               }

               if (tokens.length == 3) {
                  row.add(0);
               } else {
                  row.add(1);
               }

            }
            truthTable.addRow(row);
         }
      }
   }

   public static long report(long startTime, String model, String phase, String runIndex, String tool)
   {
      long diffTime = System.nanoTime() - startTime;
      System.out.println(String.format("%s;%s;%s;%s;Time;%s;%s", tool, model, runIndex, phase, Long.toString(diffTime), numberOfBDDNodes));
      long memory = getMemory();
      System.out.println(String.format("%s;%s;%s;%s;Memory;%s;%s", tool, model, runIndex, phase, Long.toString(memory), numberOfBDDNodes));

      return System.nanoTime();
   }

   private static long getMemory()
   {
      Runtime.getRuntime().gc();
      Runtime.getRuntime().gc();
      Runtime.getRuntime().gc();
      Runtime.getRuntime().gc();
      Runtime.getRuntime().gc();

      final long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

      return memoryUsed;
   }
}
