package at.uibk.dps.ee.enactables.local.dataflow;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalAbstract;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import net.sf.opendse.model.Task;

/**
 * Enactable performing the distribution of collection data among many
 * consumers.
 * 
 * @author Fedor Smirnov
 */
public class Distribution extends LocalAbstract {

	/**
	 * Same constructor as parent
	 * 
	 * @param stateListeners
	 * @param inputKeys
	 * @param functionNode
	 */
	protected Distribution(Set<EnactableStateListener> stateListeners, Set<String> inputKeys, Task functionNode) {
		super(stateListeners, inputKeys, functionNode);
	}

	@Override
	protected void atomicPlay() throws StopException {

		if (jsonInput.size() == 1) {
			String key = jsonInput.keySet().iterator().next();
			if (key.equals(ConstantsEEModel.JsonKeyConstantIterator)) {
				// int iterator
				JsonElement element = jsonInput.get(key);
				if (element.isJsonPrimitive()) {
					JsonPrimitive primitive = element.getAsJsonPrimitive();
					if (primitive.isNumber()) {
						int iteration = primitive.getAsInt();
						processIntIterator(iteration);
						return;
					}
				}
				throw new IllegalArgumentException("Incorrect int iterator.");
			} else {
				// one collection
				JsonElement element = jsonInput.get(key);
				if (element.isJsonArray()) {
					JsonObject result = new JsonObject();
					processCollection(key, element.getAsJsonArray(), result);
					this.jsonResult = result;
					return;
				} else {
					throw new IllegalArgumentException("Incorrect iterator.");
				}
			}
		} else {
			// multiple collections
			int collSize = -1;
			JsonObject result = new JsonObject();
			for (Entry<String, JsonElement> entry : jsonInput.entrySet()) {
				String key = entry.getKey();
				JsonElement element = entry.getValue();
				if (!element.isJsonArray()) {
					throw new IllegalArgumentException("Multiple iterators, not all are collections.");
				}
				JsonArray array = element.getAsJsonArray();
				if (collSize == -1) {
					collSize = array.size();
				}
				if (collSize != array.size()) {
					throw new IllegalStateException("Collections of different size used as iterators.");
				}
				processCollection(key, array, result);
			}
			this.jsonResult = result;
			return;
		}
	}

	/**
	 * Processes the collection by creating an entry for each element
	 * 
	 * @param key   the key of the collection
	 * @param array the array
	 * @param the   object which will be the Json result
	 */
	protected void processCollection(String key, JsonArray array, JsonObject jsonObject) {
		for (int idx = 0; idx < array.size(); idx++) {
			String elementKey = ConstantsEEModel.getCollectionElementKey(key, idx);
			jsonObject.add(elementKey, array.get(idx));
		}
	}

	/**
	 * Creates a collection rerpesenting iterations with the provided number.
	 * 
	 * @param iterationNum
	 */
	protected void processIntIterator(int iterationNum) {
		JsonObject result = new JsonObject();
		for (int i = 0; i < iterationNum; i++) {
			String key = ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, i);
			result.add(key, new JsonPrimitive(true));
		}
		this.jsonResult = result;
	}

	@Override
	protected void myPause() {
		// Nothing here
	}
}
