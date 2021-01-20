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
import at.uibk.dps.ee.enactables.local.utility.BlockEnactableTest.BlockEnactableMock;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import net.sf.opendse.model.Task;

public class ReplicateEnactableTest {

	protected static class ReplicateEnactableMock extends CollOperEnactable {
		protected ReplicateEnactableMock(Set<EnactableStateListener> stateListeners, Set<String> inputKeys,
				Task functionNode, JsonObject input) {
			super(stateListeners, inputKeys, functionNode);
			this.jsonInput = input;
		}
	}
	
	@Test
	public void testReplicate3() {
		JsonObject input = new JsonObject();
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		// create the input collection
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(1));
		array.add(new JsonPrimitive(2));
		array.add(new JsonPrimitive(3));
		String someKey = "someKey";
		input.add(someKey, array);

		// create the task and annotate it with the subcollection
		String overlapSrc = "producer/output";
		String subCollString = overlapSrc;
		String dataId = "dataId";
		Task task = PropertyServiceFunctionUtilityCollections.createCollectionOperation(dataId, subCollString,
				CollectionOperation.Replicate);

		// enter the info on the stride into the input object
		String replKey = overlapSrc;
		JsonElement replNum = new JsonPrimitive(3);
		input.add(replKey, replNum);
		
		CollOperEnactable tested = new BlockEnactableMock(stateListeners, inputKeys, task, input);
		
		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}
		
		JsonObject jsonResult = tested.getJsonResult();
		JsonElement result = jsonResult.get(someKey);
		assertTrue(result.isJsonArray());
		JsonArray resultArray = result.getAsJsonArray();
		assertEquals(9, resultArray.size());
		assertEquals(1, resultArray.get(0).getAsInt());
		assertEquals(1, resultArray.get(1).getAsInt());
		assertEquals(1, resultArray.get(2).getAsInt());
		assertEquals(2, resultArray.get(3).getAsInt());
		assertEquals(2, resultArray.get(4).getAsInt());
		assertEquals(2, resultArray.get(5).getAsInt());
		assertEquals(3, resultArray.get(6).getAsInt());
		assertEquals(3, resultArray.get(7).getAsInt());
		assertEquals(3, resultArray.get(8).getAsInt());
	}
}
