package ttc2019.metamodels.create;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import ttc2019.metamodels.bdd.Assignment;
import ttc2019.metamodels.bdd.BDD;
import ttc2019.metamodels.bdd.BDDFactory;
import ttc2019.metamodels.bdd.InputPort;
import ttc2019.metamodels.bdd.Leaf;
import ttc2019.metamodels.bdd.OutputPort;
import ttc2019.metamodels.bdd.Port;
import ttc2019.metamodels.bdd.Subtree;
import ttc2019.metamodels.bdd.Tree;

public class BdtCreationHelper {
	
	private BDD bdd;
	private Map<Object, Object> mapping = new HashMap<Object, Object>();
	
	public void generate(String fileName) throws IOException {
		File file = new File(fileName);
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		final Resource rImpl = rs.createResource(URI.createFileURI(file.getCanonicalPath()));
		rImpl.getContents().add(bdd);
		rImpl.save(null);
	}
	
	public void addLeafAssignmentConnection(Object leafScObj, Object assScObj) {
		Leaf l = (Leaf)mapping.get(leafScObj);
		Assignment a = (Assignment)mapping.get(assScObj);
		a.setOwner(l);
		l.getAssignments().add(a);
	}
	
	public void addOutputAssignmentConnection(Object outScObj, Object assScObj) {
		OutputPort o = (OutputPort)mapping.get(outScObj);
		Assignment a = (Assignment)mapping.get(assScObj);
		o.getAssignments().add(a);
		a.setPort(o);
	}
	
	public void addInputSubtreeConnection(Object inScObj, Object subScObj) {
		InputPort i = (InputPort)mapping.get(inScObj);
		Subtree s = (Subtree)mapping.get(subScObj);
		s.setPort(i);
		i.getSubtrees().add(s);
	}
	
	public void addSubtreeZeroTreeConnection(Object treeScObj, Object subScObj) {
		Tree t = (Tree)mapping.get(treeScObj);
		Subtree s = (Subtree)mapping.get(subScObj);
		t.setOwnerSubtreeForZero(s);
		s.setTreeForZero(t);
	}
	
	public void addSubtreeOneTreeConnection(Object treeScObj, Object subScObj) {
		Tree t = (Tree)mapping.get(treeScObj);
		Subtree s = (Subtree)mapping.get(subScObj);
		t.setOwnerSubtreeForOne(s);
		s.setTreeForOne(t);
	}	
	
	public void addBDDPortConnection(Object portScObj) {
		Port p = (Port)mapping.get(portScObj);
		p.setOwner(bdd);		
		bdd.getPorts().add(p);
	}
	
	public void addBDDTreeConnection(Object treeScObj) {
		Tree t = (Tree)mapping.get(treeScObj);
		t.setOwnerBDD(bdd);		
		bdd.setTree(t);
	}
	
	public void createOutputPort(Object scalaObj, String name) {
		OutputPort o = BDDFactory.eINSTANCE.createOutputPort();
		o.setName(name);
		mapping.put(scalaObj, o);
	}
	
	public void createInputPort(Object scalaObj, String name) {
		InputPort i = BDDFactory.eINSTANCE.createInputPort();
		i.setName(name);
		mapping.put(scalaObj, i);
	}
	
	public void createAssignment(Object scalaObj, boolean value) {
		Assignment a = BDDFactory.eINSTANCE.createAssignment();
		a.setValue(value);
		mapping.put(scalaObj, a);
	}
	
	public void createSubtree(Object scalaObj) {
		Subtree sub = BDDFactory.eINSTANCE.createSubtree();
		mapping.put(scalaObj, sub);
	}
	
	public void createLeaf(Object scalaObj) {
		Leaf l = BDDFactory.eINSTANCE.createLeaf();
		mapping.put(scalaObj, l);
	}

	public void createBDD(Object scalaObj, String name) {		
		bdd = BDDFactory.eINSTANCE.createBDD();
		bdd.setName(name);
		mapping.put(scalaObj, bdd);
	}
}
