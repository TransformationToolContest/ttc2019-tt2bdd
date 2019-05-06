package ttc2019;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.m2m.atl.emftvm.EmftvmFactory;
import org.eclipse.m2m.atl.emftvm.ExecEnv;
import org.eclipse.m2m.atl.emftvm.Metamodel;
import org.eclipse.m2m.atl.emftvm.Model;
import org.eclipse.m2m.atl.emftvm.impl.resource.EMFTVMResourceFactoryImpl;
import org.eclipse.m2m.atl.emftvm.util.ClassModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.ModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.TimingData;

import ttc2019.bdd.BDDPackage;
import ttc2019.tt.TTPackage;
import ttc2019.tt.TruthTable;

public class Solution {

	private TruthTable truthTable;
	private Resource outputResource;

	public TruthTable getTruthTable() {
		return truthTable;
	}

	public void setTruthTable(TruthTable tt) {
		this.truthTable = tt;
	}

	public void run(String moduleName) {
		  ExecEnv env = EmftvmFactory.eINSTANCE.createExecEnv();
          ResourceSet rs = new ResourceSetImpl();
  
          Metamodel ttMetamodel = EmftvmFactory.eINSTANCE.createMetamodel();
          ttMetamodel.setResource(TTPackage.eINSTANCE.getCell().eResource());
          env.registerMetaModel("TT", ttMetamodel);

          Metamodel bddMetamodel = EmftvmFactory.eINSTANCE.createMetamodel();
          bddMetamodel.setResource(BDDPackage.eINSTANCE.getTree().eResource());
          env.registerMetaModel("BDD", bddMetamodel);

          // loading models
          rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("emftvm", new EMFTVMResourceFactoryImpl());

          Model inModel = EmftvmFactory.eINSTANCE.createModel();
          inModel.setResource(truthTable.eResource());
          env.registerInputModel("IN", inModel);

          Model outModel = EmftvmFactory.eINSTANCE.createModel();
          outModel.setResource(outputResource);
          env.registerOutputModel("OUT", outModel);

          ModuleResolver mr = new ClassModuleResolver(Solution.class);
          TimingData td = new TimingData();
          env.loadModule(mr, moduleName);
          td.finishLoading();
          env.run(td);
          td.finish();
	}

	public void save() throws IOException {
		outputResource.save(null);
	}

	public void setOutputResource(Resource outputResource) {
		this.outputResource = outputResource;
	}

	public Resource getOutputResource() {
		return outputResource;
	}
}
