
package fulib;

import org.fulib.tables.ObjectTable;
import org.fulib.tables.StringTable;
import org.junit.Test;
import ttc2019.metamodels.bdd.BDD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BasicScenario
{
   int count = 0;

   @Test
   public void testFulibTables() throws IOException
   {
      BDD bdd = FulibSolution.doTransform("models/GeneratedI4O2Seed42.ttmodel", "models/GeneratedI4O2Seed42.ttmodel", "0", "Fulib", false);

      {
         ObjectTable bddTable = new ObjectTable(bdd);
         ObjectTable portTable = bddTable.expandLink("Ports", "ports");
         ObjectTable treeTable = bddTable.expandLink("Tree", "tree");
         // System.out.println(bddTable);
         treeTable.hasLink("port", portTable);
         // System.out.println(bddTable);
      }

      ObjectTable rootTable = new ObjectTable(bdd);
      StringTable nameTable = rootTable
            .expandLink("P", "ports")
            .expandString("Ports", "name");
      rootTable.selectColumns("Ports");
      // System.out.println(rootTable);
      ArrayList<String> nameList = nameTable.toList();
      // System.out.println(nameList) ;

      ObjectTable bddTable = new ObjectTable("BDD", bdd);
      ObjectTable portTable = bddTable.expandLink("ports", "ports");
      ObjectTable assignsTable = portTable.expandLink("assignments", "assignments");
      ObjectTable leafTable = assignsTable.expandLink("Leaf", "owner");
      leafTable.selectColumns("Leaf");
      leafTable.addColumn("LeafNo", map -> "L" + (count++));
      assignsTable = leafTable.expandLink("Assigns", "assignments");
      portTable = assignsTable.expandLink("Port", "port");
      portTable.expandString("Port Name", "name");
      assignsTable.expandInt("Value", "value");
      assignsTable.selectColumns("LeafNo", "Port Name", "Value");

      // System.out.println(bddTable);

   }

   @Test
   public void testRunGeneratedModels() throws IOException
   {
      File modelsDir = new File("models");

      String[] fileList = modelsDir.list();

      for (String fileName : fileList)
      {
         long startTime = System.nanoTime();
         FulibSolution.doTransform("models/" + fileName, "models/" + fileName, "0", "Fulib", false);
         FulibSolution.report(startTime, fileName, "Run", "0", "Fulib");
      }
   }

}
