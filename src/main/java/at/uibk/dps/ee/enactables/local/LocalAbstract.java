package at.uibk.dps.ee.enactables.local;

import java.util.Map;
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
	protected LocalAbstract(final Set<EnactableStateListener> stateListeners, final Map<String, JsonElement> inputMap,
			final Task functionNode) {
		super(stateListeners, inputMap, functionNode);
	}

	/**
	 * Reads the int input with the provided member name. Throws an exception if no
	 * such member exists.
	 * 
	 * @param memberName the String key for the json int element
	 * @return the integer value stored with the provided key
	 */
	protected int readIntInput(final String memberName) throws StopException {
		if (jsonInput.get(memberName) == null) {
			throw new StopException(StoppingReason.ERROR);
		}
		return jsonInput.get(memberName).getAsInt();
	}

	/**
	 * Waits for the given number of milliseconds.
	 * 
	 * @param milliseconds the wait time in milliseconds
	 */
	protected void waitMilliseconds(int milliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		} catch (InterruptedException exc) {
			throw new IllegalStateException("Interrupted while sleeping.", exc);
		}
	}
}
