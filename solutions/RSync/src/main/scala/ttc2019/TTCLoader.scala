package ttc2019

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.{ EObject, EPackage }
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import scala.collection.JavaConverters._
import java.io.File
import org.eclipse.emf.ecore.EStructuralFeature
import ttc2019.metamodels.create._
import ttc2019.metamodels.tt._

/**
 * Simple service to load an ECORE meta and instance model from a file.
 *
 * @author Christopher Werner
 */
class TTCLoader {

  /**
   * Fetches an ecore model from XML.
   *
   * @param path where to find the model
   * @return the model described by the XML
   */  
  def javaOptimizedTTJavaEcore(pathMeta: String, pathInstance: String): TruthTable = {
    require(null != pathMeta && pathMeta.nonEmpty && null != pathInstance && pathInstance.nonEmpty)
    val loader = new LoadEObject
    return loader.loadOptimizedTruthTable(pathMeta, pathInstance)
  }
  
  def loadOptimizedJavaEcore(pathMeta: String, pathInstance: String): EObject = {
    require(null != pathMeta && pathMeta.nonEmpty && null != pathInstance && pathInstance.nonEmpty)
    val loader = new LoadEObject
    return loader.loadOptimized(pathMeta, pathInstance)
  }
  
  def loadSimpleJavaEcore(pathMeta: String, pathInstance: String): EObject = {
    require(null != pathMeta && pathMeta.nonEmpty && null != pathInstance && pathInstance.nonEmpty)
    val loader = new LoadEObject
    return loader.loadSimple(pathMeta, pathInstance)
  }
  
  def loadScalaEcore(pathMeta: String, pathInstance: String): EObject = {
    require(null != pathMeta && pathMeta.nonEmpty && null != pathInstance && pathInstance.nonEmpty)

    val resourceSet = new ResourceSetImpl();
    resourceSet.getResourceFactoryRegistry.getExtensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl())

    
    val ressourceMeta = resourceSet.getResource(URI.createFileURI(pathMeta), true)
    val packageMeta = ressourceMeta.getContents().get(0)

    require(null != ressourceMeta)
    require(!ressourceMeta.getContents.isEmpty)
    
    resourceSet.getPackageRegistry().put("https://www.transformation-tool-contest.eu/2019/tt", packageMeta);
    val ressourceModel = resourceSet.getResource(URI.createURI(pathInstance), true);

    return ressourceModel.getContents().get(0)
  }

  private def createObj(obj: EObject, ctts: ICreateTruthTable): Unit = {
    var objName = obj.eClass.getName

    //println(objName)
    objName match {
      case "TruthTable" => ctts.createTruthTable(obj.eGet(obj.eClass().getEStructuralFeature("name")).toString(), obj)
      case "InputPort"  => ctts.createInputPort(obj.eGet(obj.eClass().getEStructuralFeature("name")).toString(), obj)
      case "OutputPort" => ctts.createOutputPort(obj.eGet(obj.eClass().getEStructuralFeature("name")).toString(), obj)
      case "Row"        => ctts.createRow(obj)
      case "Cell"       => ctts.createCell(obj.eGet(obj.eClass().getEStructuralFeature("value")).asInstanceOf[Boolean], obj)
      case _            =>
    }
  }

  private def createReferences(o1: EObject, ctts: ICreateTruthTable): Unit = {
    o1.eClass().getEAllReferences.forEach(sf => {
      if (sf.getName == "port" || sf.getName == "owner") {
        val o2 = o1.eGet(sf).asInstanceOf[EObject]
        //println("++ " + o1.eClass().getName + " " + sf.getName + " " + o2.eClass().getName)
        if (o1.eClass().getName == "Cell" && sf.getName == "port" && o2.eClass().getName.contains("Port")) {
          ctts.createCellPortPort(o1, o2)
        } else if (o1.eClass().getName == "Cell" && sf.getName == "owner" && o2.eClass().getName == "Row") {
          ctts.createRowCellsCell(o2, o1)
        } else if (o1.eClass().getName.contains("Port") && sf.getName == "owner" && o2.eClass().getName == "TruthTable") {
          ctts.createTruthTablePortsPort(o2, o1)
        } else if (o1.eClass().getName == "Row" && sf.getName == "owner" && o2.eClass().getName == "TruthTable") {
          ctts.createTruthTableRowsRow(o2, o1)
        }
      }
    })
  }

  /**
   * Create the input TruthTable instance from the *.ttmodel file.
   */
  def createTruthTableInstance(obj: EObject, ctts: ICreateTruthTable): Unit = {
    createObj(obj, ctts)
    obj.eAllContents().asScala.foreach(o => {
      createObj(o, ctts)
    })

    //add values for instances
    createReferences(obj, ctts)
    obj.eAllContents().asScala.foreach(o1 => {
      createReferences(o1, ctts)
    })
  }
  
  def createTruthTableRSYNCInstance(tt: TruthTable, cttss: ICreateTruthTable): Unit = {    
    val ctts = new CreateTTinJava()    
    ctts.createTruthTable(tt.getName, tt)
    
    tt.getPorts.forEach(p => {
      if (p.isInstanceOf[InputPort]) {
        ctts.createInputPort(p.getName, p)
      } else {
        ctts.createOutputPort(p.getName, p)
      }
      ctts.createTruthTablePortsPort(tt, p)      
    })
    
    tt.getRows.forEach(r => {
      ctts.createRow(r)
      ctts.createTruthTableRowsRow(tt, r)
      r.getCells.forEach(c => {
        ctts.createCell(c.isValue(), c)
        ctts.createRowCellsCell(r, c)
        ctts.createCellPortPort(c, c.getPort)
      })
    })
  }

}
