package ttc2019

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.{ EObject, EPackage }
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl
import scala.collection.JavaConverters._
import java.io.File
import org.eclipse.emf.ecore.EStructuralFeature

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
  def loadEcore(pathMeta: String, pathInstance: String): TTCEmfSaver = {
    require(null != pathMeta && pathMeta.nonEmpty && null != pathInstance && pathInstance.nonEmpty)
    
    val resourceSet = new ResourceSetImpl()
    resourceSet.getResourceFactoryRegistry.getExtensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl())

    val res = resourceSet.getResource(URI.createFileURI(pathMeta), true)

    require(null != res)
    require(!res.getContents.isEmpty)

    val univEPackage = res.getContents().get(0);
    resourceSet.getPackageRegistry().put("https://www.transformation-tool-contest.eu/2019/tt", univEPackage);
    val myModel = resourceSet.getResource(URI.createURI(pathInstance), true);

    return new TTCEmfSaver(res.getContents.toArray(new Array[EObject](0)).toList.find(_.isInstanceOf[EPackage]).map((p: EObject) => p.asInstanceOf[EPackage]).orNull,
      myModel.getContents().toArray(new Array[EObject](0)).toList.head)
  }

  /**
   * Create the input TruthTable instance from the *.ttmodel file.
   */
  def createTruthTableInstance(clsins: TTCEmfSaver, ctts: ICreateTruthTable): Unit = {
    //println("++++++++++++++++++++++++++++++++++++++++++++++")
    val contents = clsins.obj.eAllContents().asScala
    var counter = 0
    var ecoreMap: Map[EObject, Int] = Map.empty
    var objName = clsins.obj.eClass.getName

    //println(objName)
    objName match {
      case "TruthTable" => ctts.createTruthTable(clsins.obj.eGet(clsins.obj.eClass().getEStructuralFeature("name")).toString(), counter)
      case "InputPort"  => ctts.createInputPort(clsins.obj.eGet(clsins.obj.eClass().getEStructuralFeature("name")).toString(), counter)
      case "OutputPort" => ctts.createOutputPort(clsins.obj.eGet(clsins.obj.eClass().getEStructuralFeature("name")).toString(), counter)
      case "Row"        => ctts.createRow(counter)
      case "Cell"       => ctts.createCell(clsins.obj.eGet(clsins.obj.eClass().getEStructuralFeature("value")).asInstanceOf[Boolean], counter)
      case _            =>
    }
    ecoreMap += (clsins.obj -> counter)
    counter += 1

    contents.foreach(o => {
      var objName = o.eClass().getName
      //println(objName)
      objName match {
        case "TruthTable" => {
          /*var structs: Seq[EStructuralFeature] = o.eClass().getEAllStructuralFeatures.asScala
          structs.foreach(a => {
            println("AName: " + a.getName)
          })*/
          ctts.createTruthTable(o.eGet(o.eClass().getEStructuralFeature("name")).toString(), counter)
        }
        case "InputPort"  => {
          /*var structs: Seq[EStructuralFeature] = o.eClass().getEAllStructuralFeatures.asScala
          structs.foreach(a => {
            println("AName: " + a.getName)
          })*/
          ctts.createInputPort(o.eGet(o.eClass().getEStructuralFeature("name")).toString(), counter)
        }
        case "OutputPort" => {
          /*var structs: Seq[EStructuralFeature] = o.eClass().getEAllStructuralFeatures.asScala
          structs.foreach(a => {
            println("AName: " + a.getName)
          })*/
          ctts.createOutputPort(o.eGet(o.eClass().getEStructuralFeature("name")).toString(), counter)
        }
        case "Row"        => {
          /*var structs: Seq[EStructuralFeature] = o.eClass().getEAllStructuralFeatures.asScala
          structs.foreach(a => {
            println("AName: " + a.getName)
          })*/
          ctts.createRow(counter)
        }
        case "Cell"       => {
          /*var structs: Seq[EStructuralFeature] = o.eClass().getEAllStructuralFeatures.asScala
          structs.foreach(a => {
            println("AName: " + a.getName)
          })*/
          ctts.createCell(o.eGet(o.eClass().getEStructuralFeature("value")).asInstanceOf[Boolean], counter)
        }
        case _            =>
      }
      ecoreMap += (o -> counter)
      counter += 1
    })    
    //add values for instances
    ecoreMap.keySet.foreach(o1 => {
      o1.eClass().getEAllReferences.forEach(sf => {        
        if (sf.getName == "port" || sf.getName == "owner") {
          val o2 = o1.eGet(sf).asInstanceOf[EObject]
          //println("++ " + o1.eClass().getName + " " + sf.getName + " " + o2.eClass().getName)
          if (o1.eClass().getName == "Cell" && sf.getName == "port" && o2.eClass().getName.contains("Port")) {
            ctts.createCellPortPort(ecoreMap.get(o1).get, ecoreMap.get(o2).get)
          }
          if (o1.eClass().getName == "Cell" && sf.getName == "owner" && o2.eClass().getName == "Row") {
            ctts.createRowCellsCell(ecoreMap.get(o2).get, ecoreMap.get(o1).get)
          }
          if (o1.eClass().getName.contains("Port") && sf.getName == "owner" && o2.eClass().getName == "TruthTable") {
            ctts.createTruthTablePortsPort(ecoreMap.get(o2).get, ecoreMap.get(o1).get)
          }
          if (o1.eClass().getName == "Row" && sf.getName == "owner" && o2.eClass().getName == "TruthTable") {
            ctts.createTruthTableRowsRow(ecoreMap.get(o2).get, ecoreMap.get(o1).get)
          }
        }
      })
    })
  }

}
