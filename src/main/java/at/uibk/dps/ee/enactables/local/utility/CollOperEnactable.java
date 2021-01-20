package at.uibk.dps.ee.enactables.local.utility;

import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalAbstract;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import net.sf.opendse.model.Task;

/**
 * The {@link CollOperEnactable} is a utility enactable responsible
 * for the transformation of collections.
 * 
 * @author Fedor Smirnov
 *
 */
public class CollOperEnactable extends LocalAbstract {

	protected final String subCollectionString;
	protected final CollectionOperation collectionOperation;

	protected CollOperEnactable(Set<EnactableStateListener> stateListeners, Set<String> inputKeys,
			Task functionNode) {
		super(stateListeners, inputKeys, functionNode);
		this.subCollectionString = PropertyServiceFunctionUtilityCollections.getSubCollectionsString(functionNode);
		this.collectionOperation = PropertyServiceFunctionUtilityCollections.getCollectionOperation(functionNode);
	}

	@Override
	protected void atomicPlay() throws StopException {
		// get the collection
		String collectionKey = UtilsCollections.getCollectionKey(jsonInput)
				.orElseThrow(() -> new IllegalArgumentException("Key for collection not found."));
		JsonArray jsonArray = jsonInput.get(collectionKey).getAsJsonArray();
		CollOper subCollectionOperation = getSubCollectionOperation();
		JsonElement resultElement = subCollectionOperation.getSubCollection(jsonArray);
		jsonResult = new JsonObject();
		jsonResult.add(collectionKey, resultElement);
	}

	@Override
	protected void myPause() {
		// no special behavior here
	}

	/**
	 * Returns the {@link CollOper} applicable for the configures collection
	 * operation
	 * 
	 * @return the {@link CollOper} applicable for the configures collection
	 *         operation
	 */
	protected CollOper getSubCollectionOperation() {
		switch (collectionOperation) {
		case ElementIndex:
			return UtilsCollections.readSubCollections(subCollectionString, jsonInput);

		case Block:
			return new CollOperBlock(subCollectionString, jsonInput);

		default:
			throw new IllegalStateException("No subcollections known for operation " + collectionOperation.name());
		}
	}
}
