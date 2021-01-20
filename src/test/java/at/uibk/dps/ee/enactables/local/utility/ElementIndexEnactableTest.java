package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import net.sf.opendse.model.Task;

public class ElementIndexEnactableTest {

	protected static class EIdxEnactableMock extends CollOperEnactable {

		protected EIdxEnactableMock(Set<EnactableStateListener> stateListeners, Set<String> inputKeys,
				Task functionNode, JsonObject input) {
			super(stateListeners, inputKeys, functionNode);
			this.jsonInput = input;
		}

	}

	@Test
	public void test() {

		JsonObject input = new JsonObject();
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		// create the input collection
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(1));
		array.add(new JsonPrimitive(2));
		array.add(new JsonPrimitive(3));
		array.add(new JsonPrimitive(4));
		String someKey = "someKey";
		input.add(someKey, array);

		// create the task and annotate it with the subcollection
		String strideSrc = "producer/output";
		String subCollString = "0, 1::" + strideSrc;
		String dataId = "dataId";
		Task task = PropertyServiceFunctionUtilityCollections.createCollectionOperation(dataId, subCollString,
				CollectionOperation.ElementIndex);

		// enter the info on the stride into the input object
		String strideKey = strideSrc;
		JsonElement strideNum = new JsonPrimitive(2);
		input.add(strideKey, strideNum);

		// create the object
		CollOperEnactable tested = new EIdxEnactableMock(stateListeners, inputKeys, task, input);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}

		JsonObject jsonResult = tested.getJsonResult();
		JsonElement result = jsonResult.get(someKey);
		assertTrue(result.isJsonArray());
		JsonArray resultArray = result.getAsJsonArray();
		assertEquals(3, resultArray.size());
		assertEquals(1, resultArray.get(0).getAsInt());
		assertEquals(2, resultArray.get(1).getAsInt());
		assertEquals(4, resultArray.get(2).getAsInt());
	}

}
