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
 * Simple Addition of 2 inputs and an option to wait for a given number of
 * milliseconds.
 * 
 * @author Fedor Smirnov
 *
 */
public class Addition extends LocalAbstract {

	/**
	 * Identical to the constructor of the parent class
	 * 
	 * @param stateListeners
	 * @param inputMap
	 * @param functionNode
	 */
	public Addition(final Set<EnactableStateListener> stateListeners, final Map<String, JsonElement> inputMap,
			final Task functionNode) {
		super(stateListeners, inputMap, functionNode);
	}

	@Override
	protected void atomicPlay() throws StopException {
		final int firstSummand = readIntInput(ConstantsLocalEnactables.inputSumFirst);
		final int secondSummand = readIntInput(ConstantsLocalEnactables.inputSumSecond);
		final int waitTime = readIntInput(ConstantsLocalEnactables.inputWaitTime);
		final int sum = firstSummand + secondSummand;
		final JsonObject result = new JsonObject();
		result.addProperty(ConstantsLocalEnactables.outputAdditionResult, sum);
		this.jsonResult = result;
		waitMilliseconds(waitTime);
	}

	@Override
	protected void myPause() {
		// Nothing here
	}
}
