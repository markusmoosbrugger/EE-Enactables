package at.uibk.dps.ee.enactables.local.calculation;

import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalAbstract;
import net.sf.opendse.model.Task;

/**
 * Processes a number collection by adding up all the numbers.
 * 
 * @author Fedor Smirnov
 */
public class SumCollection extends LocalAbstract {

	/**
	 * Identical to the parent constructor.
	 * 
	 * @param stateListeners
	 * @param inputKeys
	 * @param functionNode
	 */
	public SumCollection(final Set<EnactableStateListener> stateListeners, final Task functionNode) {
		super(stateListeners, functionNode);
	}

	@Override
	protected void myPlay() throws StopException {
		final JsonArray jsonArray = readCollectionInput(ConstantsCalculation.inputSumCollection);
		final int waitTime = readIntInput(ConstantsCalculation.inputWaitTime);

		int result = 0;
		for (final JsonElement jsonElement : jsonArray) {
			result += jsonElement.getAsInt();
		}

		final JsonObject jsonResult = new JsonObject();
		jsonResult.add(ConstantsCalculation.outputSumCollection, new JsonPrimitive(result));
		this.jsonResult = jsonResult;
		waitMilliseconds(waitTime);
	}

	@Override
	protected void myPause() {
		// Nothing here
	}
}
