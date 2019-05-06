package ttc2019;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import ttc2019.bdd.BDD;
import ttc2019.bdd.BDDFactory;
import ttc2019.bdd.BDDPackage;
import ttc2019.tt.TTPackage;
import ttc2019.tt.TruthTable;

public class Driver {

	public static void main(String[] args) {
		try {
			initialize();
			load();
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ResourceSet repository;
	private static String RunIndex;
	private static String Model;
	private static String Tool;
	private static String Query;

	private static long stopwatch;

	private static Solution solution;
	private static String ModelPath;

	private static Object loadFile(String path) {
		Resource mRes;
		try {
			mRes = repository.getResource(URI.createFileURI(new File(path).getCanonicalPath()),	true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return mRes.getContents().get(0);
	}

	static void load() throws IOException {
		stopwatch = System.nanoTime();
		solution.setTruthTable((TruthTable) loadFile(ModelPath));

		URI uri = URI.createFileURI(new File("output.xmi").getCanonicalPath());
		Resource outputResource = repository.createResource(uri);
		outputResource.getContents().clear();
		BDD bdd = BDDFactory.eINSTANCE.createBDD();
		outputResource.getContents().add(bdd);
		solution.setBDD(bdd);

		stopwatch = System.nanoTime() - stopwatch;
		report(BenchmarkPhase.Load);
	}

	static void initialize() throws Exception {
		stopwatch = System.nanoTime();

		repository = new ResourceSetImpl();
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		repository.getPackageRegistry().put(TTPackage.eINSTANCE.getNsURI(), TTPackage.eINSTANCE);
		repository.getPackageRegistry().put(BDDPackage.eINSTANCE.getNsURI(), BDDPackage.eINSTANCE);

		Model = System.getenv("Model");
		ModelPath = System.getenv("ModelPath");
		RunIndex = System.getenv("RunIndex");
		Tool = System.getenv("Tool");
		Query = System.getenv("Query");

		solution = new Solution();

		stopwatch = System.nanoTime() - stopwatch;
		report(BenchmarkPhase.Initialization);
	}

	static void run() throws IOException {
		stopwatch = System.nanoTime();
		solution.run("TT2BDD");
		stopwatch = System.nanoTime() - stopwatch;
		report(BenchmarkPhase.Run);
		solution.save();
	}

	static void report(BenchmarkPhase phase) {
		System.out.println(String.format("%s;%s;%s;%s;%s;Time;%s", Tool, Query, Model, RunIndex, phase.toString(), Long.toString(stopwatch)));

		if ("true".equals(System.getenv("NoGC"))) {
			// nothing to do
		} else {
			Runtime.getRuntime().gc();
			Runtime.getRuntime().gc();
			Runtime.getRuntime().gc();
			Runtime.getRuntime().gc();
			Runtime.getRuntime().gc();
		}

		final long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println(String.format("%s;%s;%s;%s;%s;Memory;%s", Tool, Query, Model, RunIndex, phase.toString(), Long.toString(memoryUsed)));
	}

	enum BenchmarkPhase {
		Initialization, Load, Run
	}
}
