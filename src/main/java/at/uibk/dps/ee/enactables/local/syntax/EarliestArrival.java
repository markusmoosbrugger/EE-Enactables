package at.uibk.dps.ee.enactables.local.syntax;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalAbstract;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import net.sf.opendse.model.Task;

/**
 * The {@link EarliestArrival} enactable simply forwards its input (which is set
 * by the earliest of several potential inputs) to the output.
 * 
 * @author Fedor Smirnov
 */
public class EarliestArrival extends LocalAbstract {

	/**
	 * Same constructor as the parent.
	 * 
	 * @param stateListeners
	 * @param inputMap
	 * @param functionNode
	 */
	protected EarliestArrival(Set<EnactableStateListener> stateListeners, Map<String, JsonElement> inputMap,
			Task functionNode) {
		super(stateListeners, inputMap, functionNode);
	}

	@Override
	protected void atomicPlay() throws StopException {
		String key = ConstantsEEModel.EarliestArrivalJsonKey;
		// Get the input object
		checkInputEntry(key);
		JsonElement input = jsonInput.get(key);
		// Put it into the output
		JsonObject result = new JsonObject();
		result.add(key, input);
		this.jsonResult = result;
	}

	@Override
	protected void myPause() {
		// Nothing to do here
	}
}
