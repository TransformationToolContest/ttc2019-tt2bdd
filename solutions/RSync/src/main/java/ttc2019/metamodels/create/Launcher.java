package ttc2019.metamodels.create;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import ttc2019.metamodels.bdd.BDDPackage;
import ttc2019.metamodels.bddg.BDDGPackage;
import ttc2019.metamodels.tt.TTPackage;
import ttc2019.metamodels.tt.TruthTable;

/**
 * Opens the truth table and BDD model, and chooses the right validator on the
 * fly. Easier for users than asking them to pick the right one each time.
 */
public class Launcher {
	
	public void launch(String ttName, String bddName) {
		final File fInputTT = new File(ttName);
		final File fOutputBDD = new File(bddName);

		try {
			// We only need to get these metamodels loaded
			TTPackage.eINSTANCE.getName();
			BDDPackage.eINSTANCE.getName();
			BDDGPackage.eINSTANCE.getName();

			ResourceSet rs = new ResourceSetImpl();
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
			Resource rTT = rs.getResource(URI.createFileURI(fInputTT.getCanonicalPath()), true);
			Resource rBDD = rs.getResource(URI.createFileURI(fOutputBDD.getCanonicalPath()), true);

			TruthTable tt = (TruthTable) rTT.getContents().get(0);
			EObject bdd = rBDD.getContents().get(0);

			boolean valid;
			if (bdd instanceof ttc2019.metamodels.bdd.BDD) {
				valid = new TreeValidator().validate(tt, (ttc2019.metamodels.bdd.BDD) bdd);
			} else {
				valid = new GraphValidator().validate(tt, (ttc2019.metamodels.bddg.BDD) bdd);
			}

			if (valid) {
				System.out.println(String.format("BDD %2$s matches TT %1$s: all OK", fInputTT.getName(), fOutputBDD.getName()));
			} else {
				System.err.println(String.format("BDD %2$s does not match TT %1$s: see issues above", fInputTT.getName(), fOutputBDD.getName()));
				System.exit(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(255);
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java -jar validator.jar ttmodel bddmodel");
			System.exit(1);
		}
		final File fInputTT = new File(args[0]);
		final File fOutputBDD = new File(args[1]);

		try {
			// We only need to get these metamodels loaded
			TTPackage.eINSTANCE.getName();
			BDDPackage.eINSTANCE.getName();
			BDDGPackage.eINSTANCE.getName();

			ResourceSet rs = new ResourceSetImpl();
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
			Resource rTT = rs.getResource(URI.createFileURI(fInputTT.getCanonicalPath()), true);
			Resource rBDD = rs.getResource(URI.createFileURI(fOutputBDD.getCanonicalPath()), true);

			TruthTable tt = (TruthTable) rTT.getContents().get(0);
			EObject bdd = rBDD.getContents().get(0);

			boolean valid;
			if (bdd instanceof ttc2019.metamodels.bdd.BDD) {
				valid = new TreeValidator().validate(tt, (ttc2019.metamodels.bdd.BDD) bdd);
			} else {
				valid = new GraphValidator().validate(tt, (ttc2019.metamodels.bddg.BDD) bdd);
			}

			if (valid) {
				System.out.println(String.format("BDD %2$s matches TT %1$s: all OK", fInputTT.getName(), fOutputBDD.getName()));
			} else {
				System.err.println(String.format("BDD %2$s does not match TT %1$s: see issues above", fInputTT.getName(), fOutputBDD.getName()));
				System.exit(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(255);
		}
	}

}
