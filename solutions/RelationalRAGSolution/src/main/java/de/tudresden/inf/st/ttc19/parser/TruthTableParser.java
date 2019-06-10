package de.tudresden.inf.st.ttc19.parser;

import de.tudresden.inf.st.ttc19.jastadd.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;

public class TruthTableParser {

  private static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
  private static final String ELEMENT_TRUTH_TABLE = "TruthTable";
  private static final String ELEMENT_PORTS = "ports";
  private static final String ELEMENT_ROWS = "rows";
  private static final String ELEMENT_CELLS = "cells";
  private static final QName ATTRIBUTE_TYPE = new QName(XSI_NS, "type");
  private static final QName ATTRIBUTE_NAME = new QName("name");
  private static final QName ATTRIBUTE_CELLS = new QName("cells");
  private static final QName ATTRIBUTE_PORT = new QName("port");
  private static final QName ATTRIBUTE_VALUE = new QName("value");

  private static final String TYPE_INPUTPORT = "InputPort";
  private static final String TYPE_OUTPUTPORT = "OutputPort";
  private static final String TYPE_TT_INPUTPORT = "tt:InputPort";
  private static final String TYPE_TT_OUTPUTPORT = "tt:OutputPort";

  private static Logger logger = LogManager.getLogger(TruthTableParser.class);
  private final XMLInputFactory factory = XMLInputFactory.newInstance();

  public TruthTable parse(String file) {

    TruthTable truthTable = new TruthTable();
    try (final InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(file)) {
      final XMLEventReader reader = factory.createXMLEventReader(stream);
      while (reader.hasNext()) {
        final XMLEvent event = reader.nextEvent();
        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          switch (startElement.getName().getLocalPart()) {
            case ELEMENT_TRUTH_TABLE:
              truthTable.setName(startElement.getAttributeByName(ATTRIBUTE_NAME).getValue());
              break;
            case ELEMENT_PORTS:
              truthTable.addPort(parsePort(startElement));
              break;
            case ELEMENT_ROWS:
              truthTable.addRow(parseRow(reader));
              break;
          }
        }
      }
    } catch (XMLStreamException | IOException e) {
      e.printStackTrace();
    }

    return truthTable;
  }

  private Port parsePort(final StartElement portElement) throws XMLStreamException {
    Port port;

    // construct the port
    final String portType = portElement.getAttributeByName(ATTRIBUTE_TYPE).getValue();
    switch (portType) {
      case TYPE_INPUTPORT:
      case TYPE_TT_INPUTPORT:
        port = new InputPort();
        break;
      case TYPE_OUTPUTPORT:
      case TYPE_TT_OUTPUTPORT:
        port = new OutputPort();
        break;
      default:
        logger.error("Unknown port type {}", portType);
        throw new XMLStreamException("Port type " + portType + " is unknown.");
    }

    port.setName(portElement.getAttributeByName(ATTRIBUTE_NAME).getValue());

    for (String cell : portElement.getAttributeByName(ATTRIBUTE_CELLS).getValue().split(" ")) {
      port.addCell(Cell.createRefDirection(cell));
    }
    return port;
  }

  private Row parseRow(final XMLEventReader reader) throws XMLStreamException {
    Row row = new Row();

    while (reader.hasNext()) {
      final XMLEvent event = reader.nextEvent();
      if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(ELEMENT_CELLS)) {
        row.addCell(parseCell(event.asStartElement()));
      } else if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(ELEMENT_ROWS)) {
        return row;
      }
    }

    throw new XMLStreamException("Row element was not closed before the end of the file!");
  }

  private Cell parseCell(final StartElement cellElement) {
    Cell cell = new Cell();

    cell.setPort(Port.createRefDirection(cellElement.getAttributeByName(ATTRIBUTE_PORT).getValue()));
    if (cellElement.getAttributeByName(ATTRIBUTE_VALUE) != null && cellElement.getAttributeByName(ATTRIBUTE_VALUE).isSpecified()) {
      cell.setValue(cellElement.getAttributeByName(ATTRIBUTE_VALUE).getValue().equals("true"));
    } else {
      // the default value of a boolean in ecore is false
      cell.setValue(false);
    }

    return cell;
  }

}
