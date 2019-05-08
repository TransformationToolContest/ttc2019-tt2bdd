package ttc2019.model.generator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

/**
 * Tests for the {@link TruthTableInputsIterable} class.
 */
public class TruthTableInputsIterableTest {

	@Test
	public void noInputs() {
		assertResultsEquals(new boolean[][]{}, new TruthTableInputsIterable(0));
	}

	@Test
	public void oneInput() {
		assertResultsEquals(new boolean[][] {
			{ false },
			{ true }
		}, new TruthTableInputsIterable(1));
	}

	@Test
	public void twoInputs() {
		assertResultsEquals(new boolean[][] {
			{ false, false },
			{ true, false },
			{ false, true },
			{ true, true },
		}, new TruthTableInputsIterable(2));
	}

	private void assertResultsEquals(final boolean[][] results, TruthTableInputsIterable iterable) {
		final Iterator<boolean[]> it = iterable.iterator();
		for (int iResult = 0; iResult < results.length; iResult++) {
			assertTrue("Element " + iResult + " exists", it.hasNext());
			assertArrayEquals("Element " + iResult + " has the expected value", results[iResult], it.next());
		}
		assertFalse("Exactly " + results.length + " elements are returned", it.hasNext());
	}
}
