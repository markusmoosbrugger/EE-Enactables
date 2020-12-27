package at.uibk.dps.ee.enactables.local;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import net.sf.opendse.model.Task;

/**
 * Simple Addition of 2 inputs and an option to wait for a given number of
 * seconds.
 * 
 * @author Fedor Smirnov
 *
 */
public class LocalAddition extends EnactableAtomic {

	protected LocalAddition(Set<EnactableStateListener> stateListeners, Map<String, JsonElement> inputMap,
			Task functionNode) {
		super(stateListeners, inputMap, functionNode);
	}

	@Override
	protected void myPlay() throws StopException {

		int firstSummand = jsonInput.get(ConstantsLocalEnactables.inputSumFirst).getAsInt();
		int secondSummand = jsonInput.get(ConstantsLocalEnactables.inputSumSecond).getAsInt();
		int waitTime = jsonInput.get(ConstantsLocalEnactables.inputSumWaitTime).getAsInt();

		int sum = firstSummand + secondSummand;

		JsonObject result = new JsonObject();
		result.addProperty(ConstantsLocalEnactables.outputSumResult, sum);
		result.addProperty(ConstantsLocalEnactables.outputWaitTime, waitTime);
		this.jsonResult = result;

		try {
			TimeUnit.SECONDS.sleep(waitTime);
		} catch (InterruptedException exc) {
			throw new IllegalStateException("Interrupted while sleeping.");
		}

	}

	@Override
	protected void myPause() {
		// Nothing here
	}
}
