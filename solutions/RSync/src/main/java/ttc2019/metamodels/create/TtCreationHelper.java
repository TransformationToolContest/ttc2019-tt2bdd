package ttc2019.metamodels.create;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import ttc2019.metamodels.tt.Cell;
import ttc2019.metamodels.tt.InputPort;
import ttc2019.metamodels.tt.OutputPort;
import ttc2019.metamodels.tt.Port;
import ttc2019.metamodels.tt.Row;
import ttc2019.metamodels.tt.TruthTable;
import ttc2019.metamodels.tt.TTFactory;

public class TtCreationHelper {

	private TruthTable tt;
	private Map<Object, Object> mapping = new HashMap<Object, Object>();
	
	public void generate(String fileName) throws IOException {
		File file = new File(fileName);
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		final Resource rImpl = rs.createResource(URI.createFileURI(file.getCanonicalPath()));
		rImpl.getContents().add(tt);
		rImpl.save(null);
	}
	
	public void addPortCellsConnection(Object portScObj, Object cellScObj) {
		Port p = (Port)mapping.get(portScObj);
		Cell c = (Cell)mapping.get(cellScObj);
		p.getCells().add(c);
		c.setPort(p);
	}
	
	public void addRowCellsConnection(Object rowScObj, Object cellScObj) {
		Row r = (Row)mapping.get(rowScObj);
		Cell c = (Cell)mapping.get(cellScObj);
		c.setOwner(r);
		r.getCells().add(c);
	}
	
	public void addTTPortConnection(Object portScObj) {
		Port p = (Port)mapping.get(portScObj);
		p.setOwner(tt);
		tt.getPorts().add(p);
	}
	
	public void addTTRowConnection(Object rowScObj) {
		Row r = (Row)mapping.get(rowScObj);
		r.setOwner(tt);
		tt.getRows().add(r);
	}
	
	public void createOutputPort(Object scalaObj, String name) {
		OutputPort o = TTFactory.eINSTANCE.createOutputPort();
		o.setName(name);
		mapping.put(scalaObj, o);
	}
	
	public void createInputPort(Object scalaObj, String name) {
		InputPort i = TTFactory.eINSTANCE.createInputPort();
		i.setName(name);
		mapping.put(scalaObj, i);
	}
	
	public void createRow(Object scalaObj) {
		Row r = TTFactory.eINSTANCE.createRow();
		mapping.put(scalaObj, r);
	}
	
	public void createCell(Object scalaObj, boolean value) {
		Cell c = TTFactory.eINSTANCE.createCell();
		c.setValue(value);
		mapping.put(scalaObj, c);
	}

	public void createTT(Object scalaObj, String name) {		
		tt = TTFactory.eINSTANCE.createTruthTable();
		tt.setName(name);
		mapping.put(scalaObj, tt);
	}
}
