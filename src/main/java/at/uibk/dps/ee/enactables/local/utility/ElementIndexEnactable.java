package at.uibk.dps.ee.enactables.local.utility;

import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalAbstract;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityElementIndex;
import net.sf.opendse.model.Task;

/**
 * The {@link ElementIndexEnactable} is a utilization enactable which tranforms
 * collections following an element index description.
 * 
 * @author Fedor Smirnov
 */
public class ElementIndexEnactable extends LocalAbstract {

	protected final String eIdxString;

	/**
	 * Same constructor as for the parent class.
	 * 
	 * @param stateListeners
	 * @param inputKeys
	 * @param functionNode
	 */
	protected ElementIndexEnactable(final Set<EnactableStateListener> stateListeners, final Set<String> inputKeys,
			final Task functionNode) {
		super(stateListeners, inputKeys, functionNode);
		this.eIdxString = PropertyServiceFunctionUtilityElementIndex.getSubCollectionsString(functionNode);
	}

	@Override
	protected void atomicPlay() throws StopException {
		// get the collection object and the key
		String collectionKey = null;
		JsonArray jsonArray = null;
		for (final String key : jsonInput.keySet()) {
			final JsonElement element = jsonInput.get(key);
			if (element.isJsonArray()) {
				collectionKey = key;
				jsonArray = element.getAsJsonArray();
				break;
			}
		}

		if (collectionKey == null || jsonArray == null) {
			throw new IllegalArgumentException("Key for collection not found.");
		}

		// get the subcollections
		final SubCollections subcollections = UtilsCollections.readSubCollections(eIdxString, jsonInput);

		// apply the operator and return the result
		final JsonElement resultElement = subcollections.processJsonArray(jsonArray);
		jsonResult = new JsonObject();
		jsonResult.add(collectionKey, resultElement);
	}

	@Override
	protected void myPause() {
		// no special pause behavior
	}
}
