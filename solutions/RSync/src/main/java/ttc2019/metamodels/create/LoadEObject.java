package ttc2019.metamodels.create;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;

import ttc2019.metamodels.tt.TTPackage;
import ttc2019.metamodels.tt.TruthTable;

/**
 * Load the EObject of an incoming Model in two different ways.
 * Third time we load and EObject and cast it to a TruthTable.
 * 
 * @author Christopher Werner
 */
public class LoadEObject {
	
	/**
	 * Simple loading of an EObject without extra Optimization stuff.
	 * @param pathMeta Path of metamodel
	 * @param pathInstance Path of model
	 * @return Instance of EObject
	 * @throws IOException
	 */
	public EObject loadSimple(String pathMeta, String pathInstance) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
			    
		Resource ressourceMeta = resourceSet.getResource(URI.createFileURI(pathMeta), true);
		EObject packageMeta = ressourceMeta.getContents().get(0);
			    
		resourceSet.getPackageRegistry().put("https://www.transformation-tool-contest.eu/2019/tt", packageMeta);
		Resource ressourceModel = resourceSet.getResource(URI.createURI(pathInstance), true);
		
		return ressourceModel.getContents().get(0);
	}

	/**
	 * Loading an EObject with optimized settings.
	 * @param pathMeta Path of metamodel
	 * @param pathInstance Path of model
	 * @return Instance of EObject
	 * @throws IOException
	 */
	public EObject loadOptimized(String pathMeta, String pathInstance) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ttmodel", new IntrinsicIDXMIResourceFactoryImpl());
			    
		Resource ressourceMeta = resourceSet.getResource(URI.createFileURI(pathMeta), true);
		EObject packageMeta = ressourceMeta.getContents().get(0);
			    
		resourceSet.getPackageRegistry().put("https://www.transformation-tool-contest.eu/2019/tt", packageMeta);
		Resource ressourceModel = resourceSet.getResource(URI.createURI(pathInstance), true);
		
		Map<String, Object> loadOptions = new HashMap<>();
		loadOptions.put(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
	    loadOptions.put(XMIResource.OPTION_USE_PARSER_POOL, new XMLParserPoolImpl());
	    loadOptions.put(XMIResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, new HashMap<>());
	    ressourceModel.load(loadOptions);
		return ressourceModel.getContents().get(0);
	}
	
	/**
	 * Load directly a truth table from the Java representation.
	 * @param pathMeta Path of metamodel
	 * @param pathInstance Path of model
	 * @return Instance of EObject
	 * @throws IOException
	 */
	public TruthTable loadOptimizedTruthTable(String pathMeta, String pathInstance) throws IOException {
		TTPackage.eINSTANCE.getName();
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new IntrinsicIDXMIResourceFactoryImpl());
		rs.getLoadOptions().put(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
		Resource rTT = rs.getResource(URI.createFileURI(pathInstance), true);
		Map<String, Object> loadOptions = new HashMap<>();
		loadOptions.put(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
	    loadOptions.put(XMIResource.OPTION_USE_PARSER_POOL, new XMLParserPoolImpl());
	    loadOptions.put(XMIResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, new HashMap<>());
	    rTT.load(loadOptions);

		TruthTable tt = (TruthTable) rTT.getContents().get(0);
		return tt;
	}
}
