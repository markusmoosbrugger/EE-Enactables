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
public class SumCollection extends LocalAbstract{

	/**
	 * Identical to the parent constructor.
	 * 
	 * @param stateListeners
	 * @param inputKeys
	 * @param functionNode
	 */
	public SumCollection(Set<EnactableStateListener> stateListeners, Set<String> inputKeys, Task functionNode) {
		super(stateListeners, inputKeys, functionNode);
	}

	@Override
	protected void atomicPlay() throws StopException {
		JsonArray jsonArray = readCollectionInput(ConstantsCalculation.inputSumCollection);
		int waitTime = readIntInput(ConstantsCalculation.inputWaitTime);
		
		int result = 0;
		for (JsonElement jsonElement : jsonArray) {
			result += jsonElement.getAsInt();
		}
		
		JsonObject jsonResult = new JsonObject();
		jsonResult.add(ConstantsCalculation.outputSumCollection, new JsonPrimitive(result));
		this.jsonResult = jsonResult;
		waitMilliseconds(waitTime);
	}

	@Override
	protected void myPause() {
		// Nothing here
	}
}
