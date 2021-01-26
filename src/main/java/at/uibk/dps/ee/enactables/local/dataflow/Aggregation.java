package at.uibk.dps.ee.enactables.local.dataflow;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalAbstract;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import net.sf.opendse.model.Task;

/**
 * Performs the operation of aggregating multiple elements into a single
 * collection.
 * 
 * @author Fedor Smirnov
 *
 */
public class Aggregation extends LocalAbstract {

	/**
	 * Same constructor as the parent.
	 * 
	 * @param stateListeners
	 * @param inputKeys
	 * @param functionNode
	 */
	protected Aggregation(Set<EnactableStateListener> stateListeners, Set<String> inputKeys, Task functionNode) {
		super(stateListeners, inputKeys, functionNode);
	}

	/**
	 * Adjusts the input set to fit to the post-reproduction graph
	 * 
	 * @param elementNumber the number of elements that will be aggregated.
	 */
	public void adjustInputSet(int elementNumber) {
		inputKeys.clear();
		for (int idx = 0; idx < elementNumber; idx++) {
			inputKeys.add(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyAggregation, idx));
		}
	}

	@Override
	protected void atomicPlay() throws StopException {
		JsonArray array = new JsonArray();
		// fill the array, so that we can set indices
		for (int i = 0; i < jsonInput.size(); i++) {
			array.add(0);
		}
		for (Entry<String, JsonElement> entry : jsonInput.entrySet()) {
			String key = entry.getKey();
			JsonElement element = entry.getValue();
			String collectionKey = ConstantsEEModel.getCollectionName(key);
			if (!collectionKey.equals(ConstantsEEModel.JsonKeyAggregation)) {
				throw new IllegalArgumentException("Wrong input for aggregation.");
			}
			int idx = ConstantsEEModel.getElementIndex(key);
			array.set(idx, element);
		}
		JsonObject result = new JsonObject();
		result.add(ConstantsEEModel.JsonKeyAggregation, array);
		this.jsonResult = result;
	}

	@Override
	protected void myPause() {
		// Nothing special here
	}
}
