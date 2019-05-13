package ttc2019;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import ttc2019.metamodels.bdd.BDDPackage;
import ttc2019.metamodels.tt.TTPackage;
import ttc2019.metamodels.tt.TruthTable;

public class Driver {

	public static void main(final String[] args) {
		try {
			initialize();
			load();
			run();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static ResourceSet repository;
	private static String RunIndex;
	private static String Model;
	private static String Tool;

	private static long stopwatch;

	private static Solution solution;
	private static String ModelPath;

	private static Object loadFile(final String path) {
		Resource mRes;
		try {
			mRes = repository.getResource(URI.createFileURI(new File(path).getCanonicalPath()),	true);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return mRes.getContents().get(0);
	}

	static void load() throws IOException {
		stopwatch = System.nanoTime();
		solution.setTruthTable((TruthTable) loadFile(ModelPath));

		final URI uri = URI.createFileURI(new File("output.xmi").getCanonicalPath());
		final Resource outputResource = repository.createResource(uri);
		outputResource.getContents().clear();
		solution.setOutputResource(outputResource);

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

		solution = new Solution();
		solution.load("TT2BDD");

		stopwatch = System.nanoTime() - stopwatch;
		report(BenchmarkPhase.Initialization);
	}

	static void run() throws IOException {
		stopwatch = System.nanoTime();
		solution.run();
		stopwatch = System.nanoTime() - stopwatch;
		report(BenchmarkPhase.Run);
		solution.save();
	}

	static void report(final BenchmarkPhase phase) {
		System.out.println(String.format("%s;%s;%s;%s;Time;%s", Tool, Model, RunIndex, phase.toString(), Long.toString(stopwatch)));

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
		System.out.println(String.format("%s;%s;%s;%s;Memory;%s", Tool, Model, RunIndex, phase.toString(), Long.toString(memoryUsed)));
	}

	enum BenchmarkPhase {
		Initialization, Load, Run
	}
}
