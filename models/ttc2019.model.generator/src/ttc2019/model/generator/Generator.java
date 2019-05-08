package ttc2019.model.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

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
import ttc2019.metamodels.tt.TTFactory;
import ttc2019.metamodels.tt.TruthTable;

/**
 * Generates a truth table model with a given number of inputs, outputs and a
 * random seed, and saves it into a file.
 */
public class Generator {

	private final int nInputs, nOutputs, seed;
	private final Random rnd;

	public Generator(int nInputs, int nOutputs, int seed) {
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		this.seed = seed;
		this.rnd = new Random(seed);
	}

	private void generate(File file) throws IOException {
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		final Resource rImpl = rs.createResource(URI.createFileURI(file.getCanonicalPath()));
		TruthTable tt = generate();
		rImpl.getContents().add(tt);
		rImpl.save(null);
	}

	private TruthTable generate() {
		final TruthTable tt = TTFactory.eINSTANCE.createTruthTable();
		tt.setName(String.format("TableI%dO%dSeed%d", nInputs, nOutputs, seed));

		final List<InputPort> inputs = createPorts("I", TTFactory.eINSTANCE::createInputPort, nInputs);
		tt.getPorts().addAll(inputs);

		final List<OutputPort> outputs = createPorts("O", TTFactory.eINSTANCE::createOutputPort, nOutputs);
		tt.getPorts().addAll(outputs);

		for (boolean[] rowInputs : new TruthTableInputsIterable(inputs.size())) {
			final Row ttRow = TTFactory.eINSTANCE.createRow();

			for (int i = 0 ; i < inputs.size(); i++) {
				final Cell ttInputCell = TTFactory.eINSTANCE.createCell();
				ttInputCell.setPort(inputs.get(i));
				ttInputCell.setValue(rowInputs[i]);
				ttRow.getCells().add(ttInputCell);
			}

			for (int i = 0; i < outputs.size(); i++) {
				final Cell ttOutputCell = TTFactory.eINSTANCE.createCell();
				ttOutputCell.setPort(outputs.get(i));
				ttOutputCell.setValue(rnd.nextBoolean());
				ttRow.getCells().add(ttOutputCell);
			}

			tt.getRows().add(ttRow);
		}
 
		return tt;
	}

	private static <T extends Port>  List<T> createPorts(String prefix, Supplier<T> generator, int nEntries) {
		final List<T> outputs = new ArrayList<>();
		for (int iPort = 0; iPort < nEntries; iPort++) {
			final T port =  generator.get();
			port.setName(prefix + iPort);
			outputs.add(port);
		}
		return outputs;
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			System.err.println("Usage: java -jar generator.jar nInputs nOutputs seed [output.xmi]");
			System.exit(1);
		}

		final int nInputs  = Integer.parseInt(args[0]);
		final int nOutputs = Integer.parseInt(args[1]);
		final int seed = Integer.parseInt(args[2]);
		final File outputFile = new File(
			args.length >= 4
			? args[3]
			: String.format("GeneratedI%dO%dSeed%d.ttmodel", nInputs, nOutputs, seed)
		);

		try {
			final Generator gen = new Generator(nInputs, nOutputs, seed);
			gen.generate(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
