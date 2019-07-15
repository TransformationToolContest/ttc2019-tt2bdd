package ttc19

import java.io.IOException
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors
import tt.TruthTable
import yamtl.core.YAMTLModule.ExtentTypeModifier

class Solution {
	@Accessors
	TruthTable truthTable;
	@Accessors
	Resource outputResource;

	var YAMTLSolution xform

	def init() {
		xform = new YAMTLSolution
		xform.fromRoots = false
		xform.stageUpperBound = 1
		xform.extentTypeModifier = ExtentTypeModifier.LIST
	}

	def load(String modelPath) {
		xform.loadInputModels(#{'tt' -> modelPath})
	}

	def load(Resource mRes) {
		xform.loadInputResources(#{'tt' -> mRes})
	}

	def void run() {
		// EXECUTE TRAFO 
		xform.execute()
	}

	def void save(String modelPath) throws IOException {
		xform.saveOutputModels(#{'bdd' -> modelPath + '.output.xmi'})
	}
}