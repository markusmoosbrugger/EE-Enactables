package at.uibk.dps.ee.enactables.local.utility;

import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

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
	
	protected ElementIndexEnactable(Set<EnactableStateListener> stateListeners, Set<String> inputKeys,
			Task functionNode) {
		super(stateListeners, inputKeys, functionNode);
		if (inputKeys.size() != 1) {
			throw new IllegalArgumentException("The element index enactable expects exactly 1 input.");
		}
		this.eIdxString = PropertyServiceFunctionUtilityElementIndex.getSubCollectionsString(functionNode);
	}

	@Override
	protected void atomicPlay() throws StopException {
		// get the collection object and the key
		String collectionKey = null;
		JsonArray jsonArray = null;
		for (String key : jsonInput.keySet()) {
			JsonElement element = jsonInput.get(key);
			if (element.isJsonArray()) {
				collectionKey = key;
				jsonArray = element.getAsJsonArray();
				break;
			}
		}
		
		// get the subcollections
		SubCollections subcollections = UtilsCollections.readSubCollections(eIdxString, jsonInput);
		
		// apply the operator and return the result
		JsonElement resultElement = subcollections.processJsonArray(jsonArray);
		jsonResult.add(collectionKey, resultElement);
	}

	@Override
	protected void myPause() {
	}
}
