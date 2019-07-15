package org.fulib.tables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class FulibTable
{
   private ArrayList<ArrayList> table = new ArrayList<>();

   public ArrayList<ArrayList > getTable()
   {
      return table;
   }

   public void setTable(ArrayList<ArrayList > table)
   {
      this.table = table;
   }

   private String columnName = null;

   public String getColumnName()
   {
      return columnName;
   }

   public void setColumnName(String columnName)
   {
      this.columnName = columnName;
   }

   private LinkedHashMap<String, Integer> columnMap = new LinkedHashMap<>();

   public LinkedHashMap<String, Integer> getColumnMap()
   {
      return columnMap;
   }

   public void setColumnMap(LinkedHashMap<String, Integer> columnMap)
   {
      this.columnMap = columnMap;
   }

   public FulibTable(String... columnNames)
   {
      for (String name : columnNames)
      {
         addColumn(name);
      }
   }

   public void addColumn(String name)
   {
      columnMap.put(name, columnMap.size());

      for (ArrayList<Object> row : table)
      {
         row.add(null);
      }

      setColumnName(name);
   }

   public void addRow(ArrayList row)
   {
      this.table.add(row);
   }


   @Override
   public String toString()
   {
      StringBuilder buf = new StringBuilder();
      for (String key : columnMap.keySet())
      {
         buf.append(key).append(" \t");
      }
      buf.append("\n");
      for (ArrayList<Object> row : table)
      {
         for (Object cell : row)
         {
            buf.append(cell).append(" \t");
         }
         buf.append("\n");
      }
      buf.append("\n");
      return buf.toString();
   }

   public Object getValue(String colName, int rowNumber)
   {
      Integer index = getColumnMap().get(colName);
      ArrayList row = table.get(rowNumber);
      return row.get(index);
   }
}



