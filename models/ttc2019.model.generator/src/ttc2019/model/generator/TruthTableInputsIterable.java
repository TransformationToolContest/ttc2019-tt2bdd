package ttc2019.model.generator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterable which goes through all the possible truth value combinations for a
 * number of ports.
 */
class TruthTableInputsIterable implements Iterable<boolean[]> {
	private final int nPorts;

	public TruthTableInputsIterable(int nPorts) {
		this.nPorts = nPorts;
	}

	@Override
	public Iterator<boolean[]> iterator() {
		if (nPorts == 0) {
			return Collections.emptyIterator();
		}

		final boolean[] values = new boolean[nPorts];
		return new Iterator<boolean[]>() {
			private boolean hasNext = true;

			@Override
			public boolean hasNext() {
				return hasNext;
			}

			@Override
			public boolean[] next() {
				if (!hasNext()) throw new NoSuchElementException();
				final boolean[] retValue = Arrays.copyOf(values, values.length);

				// Compute next value by logically adding "one" to the array
				boolean carry = false;
				for (int i = 0; i < values.length; ++i) {
					if (values[i]) {
						// We go back to zero and continue looping to act as addition carry
						values[i] = false;
						carry = true;
					} else {
						// We use the carry/original addition here and stop
						values[i] = true;
						carry = false;
						break;
					}
				}

				if (carry) {
					// the carry has gone beyond the last bit in the array (overflow): we are done
					hasNext = false;
				}

				return retValue;
			}
		};
	}
	
}