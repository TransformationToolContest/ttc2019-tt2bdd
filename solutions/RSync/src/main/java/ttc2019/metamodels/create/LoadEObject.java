package ttc2019.metamodels.create;

import java.io.File;
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

public class LoadEObject {
	
	public EObject loadSimple(String pathMeta, String pathInstance) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
			    
		Resource ressourceMeta = resourceSet.getResource(URI.createFileURI(pathMeta), true);
		EObject packageMeta = ressourceMeta.getContents().get(0);
			    
		resourceSet.getPackageRegistry().put("https://www.transformation-tool-contest.eu/2019/tt", packageMeta);
		Resource ressourceModel = resourceSet.getResource(URI.createURI(pathInstance), true);
		
		return ressourceModel.getContents().get(0);
	}

	public EObject load(String pathMeta, String pathInstance) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ttmodel", new IntrinsicIDXMIResourceFactoryImpl());
		//resourceSet.getResourceFactoryRegistry.getExtensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		//resourceSet.getLoadOptions().put(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
			    
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
	
	public TruthTable loadTT(String pathMeta, String pathInstance) throws IOException {
		TTPackage.eINSTANCE.getName();
		ResourceSet rs = new ResourceSetImpl();
		//rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
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
		
		
		/*ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ttmodel", new IntrinsicIDXMIResourceFactoryImpl());
		//resourceSet.getResourceFactoryRegistry.getExtensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		//resourceSet.getLoadOptions().put(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
			    
		Resource ressourceMeta = resourceSet.getResource(URI.createFileURI(pathMeta), true);
		EObject packageMeta = ressourceMeta.getContents().get(0);
			    
		resourceSet.getPackageRegistry().put("https://www.transformation-tool-contest.eu/2019/tt", packageMeta);
		Resource ressourceModel = resourceSet.getResource(URI.createURI(pathInstance), true);
		
		Map<String, Object> loadOptions = new HashMap<>();
		loadOptions.put(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
	    loadOptions.put(XMIResource.OPTION_USE_PARSER_POOL, new XMLParserPoolImpl());
	    loadOptions.put(XMIResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, new HashMap<>());
	    ressourceModel.load(loadOptions);
		return (TruthTable)ressourceModel.getContents().get(0);*/
	}
}
