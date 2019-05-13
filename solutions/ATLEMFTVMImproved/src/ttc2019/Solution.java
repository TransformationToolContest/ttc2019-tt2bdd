package ttc2019;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.m2m.atl.emftvm.EmftvmFactory;
import org.eclipse.m2m.atl.emftvm.ExecEnv;
import org.eclipse.m2m.atl.emftvm.Metamodel;
import org.eclipse.m2m.atl.emftvm.Model;
import org.eclipse.m2m.atl.emftvm.util.ClassModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.ModuleResolver;

import ttc2019.metamodels.bdd.BDDPackage;
import ttc2019.metamodels.tt.TTPackage;
import ttc2019.metamodels.tt.TruthTable;

public class Solution {

	private TruthTable truthTable;
	private Resource outputResource;
	private ExecEnv env;

	public TruthTable getTruthTable() {
		return truthTable;
	}

	public void setTruthTable(final TruthTable tt) {
		this.truthTable = tt;
		final Model inModel = EmftvmFactory.eINSTANCE.createModel();
		inModel.setResource(truthTable.eResource());
		env.registerInputModel("IN", inModel);
	}

	public void load(final String moduleName) {
		env = EmftvmFactory.eINSTANCE.createExecEnv();

		final Metamodel ttMetamodel = EmftvmFactory.eINSTANCE.createMetamodel();
		ttMetamodel.setResource(TTPackage.eINSTANCE.getCell().eResource());
		env.registerMetaModel("TT", ttMetamodel);

		final Metamodel bddMetamodel = EmftvmFactory.eINSTANCE.createMetamodel();
		bddMetamodel.setResource(BDDPackage.eINSTANCE.getTree().eResource());
		env.registerMetaModel("BDD", bddMetamodel);

		// loading module
		final ModuleResolver mr = new ClassModuleResolver(Solution.class);
		env.loadModule(mr, moduleName);
	}

	public void run() {
		env.run(null);
	}

	public void save() throws IOException {
		outputResource.save(null);
	}

	public void setOutputResource(final Resource outputResource) {
		this.outputResource = outputResource;
		final Model outModel = EmftvmFactory.eINSTANCE.createModel();
		outModel.setResource(outputResource);
		env.registerOutputModel("OUT", outModel);
	}

	public Resource getOutputResource() {
		return outputResource;
	}
}
