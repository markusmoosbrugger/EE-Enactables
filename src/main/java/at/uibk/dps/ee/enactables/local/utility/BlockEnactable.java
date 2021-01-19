package at.uibk.dps.ee.enactables.local.utility;

import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalAbstract;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import net.sf.opendse.model.Task;

/**
 * The {@link BlockEnactable} is a utilization enactable which transforms
 * collections following a block description.
 * 
 * @author Fedor Smirnov
 *
 */
public class BlockEnactable extends LocalAbstract {

	/**
	 * Same constructor as parent
	 * 
	 * @param stateListeners
	 * @param inputKeys
	 * @param functionNode
	 */
	protected BlockEnactable(Set<EnactableStateListener> stateListeners, Set<String> inputKeys, Task functionNode) {
		super(stateListeners, inputKeys, functionNode);
	}

	@Override
	protected void atomicPlay() throws StopException {
		// get the collection
		String collectionKey = UtilsCollections.getCollectionKey(jsonInput)
				.orElseThrow(() -> new IllegalArgumentException("Key for collection not found."));
		String subCollectionString = PropertyServiceFunctionUtilityCollections.getSubCollectionsString(functionNode);
		JsonArray jsonArray = jsonInput.get(collectionKey).getAsJsonArray();
		SubcollectionBlock blockOperation = new SubcollectionBlock(subCollectionString, jsonInput);
		JsonElement resultElement = blockOperation.getSubCollection(jsonArray);
		jsonResult = new JsonObject();
		jsonResult.add(collectionKey, resultElement);
	}

	@Override
	protected void myPause() {
		// no special behavior here
	}
}
