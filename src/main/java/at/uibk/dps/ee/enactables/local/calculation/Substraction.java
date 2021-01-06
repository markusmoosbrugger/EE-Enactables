package at.uibk.dps.ee.enactables.local.calculation;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.ConstantsLocalEnactables;
import at.uibk.dps.ee.enactables.local.LocalAbstract;
import net.sf.opendse.model.Task;

/**
 * Simple substraction of 2 inputs with an option to wait for a given number of
 * milliseconds.
 * 
 * @author Fedor Smirnov
 *
 */
public class Substraction extends LocalAbstract{

	/**
	 * Same constructor as for the parent class.
	 * 
	 * @param stateListeners
	 * @param inputMap
	 * @param functionNode
	 */
	public Substraction(Set<EnactableStateListener> stateListeners, Map<String, JsonElement> inputMap,
			Task functionNode) {
		super(stateListeners, inputMap, functionNode);
	}

	@Override
	protected void atomicPlay() throws StopException {
		final int minuend = readIntInput(ConstantsLocalEnactables.inputMinuend);
		final int subtrahend = readIntInput(ConstantsLocalEnactables.inputSubtrahend);
		final int waitTime = readIntInput(ConstantsLocalEnactables.inputWaitTime);
		final int difference = minuend - subtrahend;
		final JsonObject result = new JsonObject();
		result.addProperty(ConstantsLocalEnactables.outputSubstractionResult, difference);
		this.jsonResult = result;
		waitMilliseconds(waitTime);
	}

	@Override
	protected void myPause() {
		// Nothing
	}
}
