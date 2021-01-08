package at.uibk.dps.ee.enactables.local.calculation;

import java.util.Set;

import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
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
	 * @param inputKeys
	 * @param functionNode
	 */
	public Addition(final Set<EnactableStateListener> stateListeners, final Set<String> inputKeys,
			final Task functionNode) {
		super(stateListeners, inputKeys, functionNode);
	}

	@Override
	protected void atomicPlay() throws StopException {
		final int firstSummand = readIntInput(ConstantsCalculation.inputSumFirst);
		final int secondSummand = readIntInput(ConstantsCalculation.inputSumSecond);
		final int waitTime = readIntInput(ConstantsCalculation.inputWaitTime);
		final int sum = firstSummand + secondSummand;
		final JsonObject result = new JsonObject();
		result.addProperty(ConstantsCalculation.outputAdditionResult, sum);
		this.jsonResult = result;
		waitMilliseconds(waitTime);
	}

	@Override
	protected void myPause() {
		// Nothing here
	}
}
