package at.uibk.dps.ee.enactables.local;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonElement;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.core.exception.StopException.StoppingReason;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import net.sf.opendse.model.Task;

/**
 * Parent of all local function classes.
 * 
 * @author Fedor Smirnov
 */
public abstract class LocalAbstract extends EnactableAtomic {

	/**
	 * Same constructor as {@link EnactableAtomic}.
	 * 
	 * @param stateListeners
	 * @param inputMap
	 * @param functionNode
	 */
	protected LocalAbstract(final Set<EnactableStateListener> stateListeners, final Set<String> inputKeys,
			final Task functionNode) {
		super(stateListeners, inputKeys, functionNode);
	}

	/**
	 * Reads the int input with the provided member name. Throws an exception if no
	 * such member exists.
	 * 
	 * @param memberName the String key for the json int element
	 * @return the integer value stored with the provided key
	 */
	protected int readIntInput(final String memberName) throws StopException {
		checkInputEntry(memberName);
		return jsonInput.get(memberName).getAsInt();
	}

	/**
	 * Waits for the given number of milliseconds.
	 * 
	 * @param milliseconds the wait time in milliseconds
	 */
	protected void waitMilliseconds(final int milliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		} catch (InterruptedException exc) {
			throw new IllegalStateException("Interrupted while sleeping.", exc);
		}
	}

	/**
	 * Reads and returns the Json entry specified by the given key.
	 * 
	 * @param key the given key
	 * @return the Json entry specified by the given key
	 * @throws StopException thrown if the entry is not found
	 */
	protected JsonElement readEntry(final String key) throws StopException {
		checkInputEntry(key);
		return jsonInput.get(key);
	}

	/**
	 * Checks that an entry with the given key is present in the input object.
	 * Throws an exception if this is not the case.
	 * 
	 * @param key the key to check
	 */
	protected void checkInputEntry(final String key) throws StopException {
		if (jsonInput.get(key) == null) {
			throw new StopException(StoppingReason.ERROR);
		}
	}
}
