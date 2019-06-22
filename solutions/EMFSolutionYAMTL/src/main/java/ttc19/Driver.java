package ttc19;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import bddg.BddgPackage;
import tt.TTPackage;
import tt.TruthTable;

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

	private static long stopwatch;

	private static Solution solution;
	private static String ModelPath;

//	private static Object loadFile(String path) {
//		Resource mRes;
//		try {
//			mRes = repository.getResource(URI.createFileURI(new File(path).getCanonicalPath()),	true);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//		return mRes.getContents().get(0);
//	}
	private static Resource loadFile(String path) {
		Resource mRes;
		try {
			mRes = repository.getResource(URI.createFileURI(new File(path).getCanonicalPath()),	true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return mRes;
	}

	static void load() throws IOException {
		stopwatch = System.nanoTime();
		Resource mRes = loadFile(ModelPath);
		solution.setTruthTable((TruthTable) mRes.getContents().get(0));

//		URI uri = URI.createFileURI(new File("output.xmi").getCanonicalPath());
//		Resource outputResource = repository.createResource(uri);
//		outputResource.getContents().clear();
//		solution.setOutputResource(outputResource);
		
		solution.load(mRes);
//		solution.load(ModelPath);

		stopwatch = System.nanoTime() - stopwatch;
		report(BenchmarkPhase.Load);
	}

	static void initialize() throws Exception {
		stopwatch = System.nanoTime();

		repository = new ResourceSetImpl();
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		repository.getPackageRegistry().put(TTPackage.eINSTANCE.getNsURI(), TTPackage.eINSTANCE);
		repository.getPackageRegistry().put(BddgPackage.eINSTANCE.getNsURI(), BddgPackage.eINSTANCE);

		Model = System.getenv("Model");
		ModelPath = System.getenv("ModelPath");
		RunIndex = System.getenv("RunIndex");
		Tool = System.getenv("Tool");

		solution = new Solution();
		solution.init();

		stopwatch = System.nanoTime() - stopwatch;
		report(BenchmarkPhase.Initialization);
	}

	static void run() throws IOException {
		stopwatch = System.nanoTime();
		solution.run();
		stopwatch = System.nanoTime() - stopwatch;
		report(BenchmarkPhase.Run);
		solution.save(ModelPath);
	}

	static void report(BenchmarkPhase phase) {
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
