package at.uibk.dps.ee.enactables.local.dataflow;

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
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import net.sf.opendse.model.Task;

public class AggregationTest {

	protected static class AggregationMock extends Aggregation {
		protected AggregationMock(Set<EnactableStateListener> stateListeners, Set<String> inputKeys, Task functionNode,
				JsonObject input) {
			super(stateListeners, inputKeys, functionNode);
			jsonInput = input;
		}
		
		protected Set<String> getInputKeys(){
			return inputKeys;
		}
	}
	
	@Test
	public void testAdjustInputSet() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();
		AggregationMock tested = new AggregationMock(stateListeners, inputKeys, funcNode, new JsonObject());
		tested.adjustInputSet(4);
		Set<String> inputSet = tested.getInputKeys();
		assertEquals(4, inputSet.size());
		assertTrue(inputSet.contains(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyAggregation, 0)));
		assertTrue(inputSet.contains(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyAggregation, 1)));
		assertTrue(inputSet.contains(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyAggregation, 2)));
		assertTrue(inputSet.contains(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyAggregation, 3)));
	}

	@Test
	public void testCorrect() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		String key = ConstantsEEModel.JsonKeyAggregation;

		JsonObject input = new JsonObject();
		input.add(ConstantsEEModel.getCollectionElementKey(key, 0), new JsonPrimitive(0));
		input.add(ConstantsEEModel.getCollectionElementKey(key, 1), new JsonPrimitive(1));
		input.add(ConstantsEEModel.getCollectionElementKey(key, 2), new JsonPrimitive(2));
		input.add(ConstantsEEModel.getCollectionElementKey(key, 3), new JsonPrimitive(3));

		Aggregation tested = new AggregationMock(stateListeners, inputKeys, funcNode, input);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}

		JsonObject result = tested.getJsonResult();
		assertTrue(result.has(key));
		JsonElement element = result.get(key);
		assertTrue(element.isJsonArray());
		JsonArray array = element.getAsJsonArray();

		assertEquals(0, array.get(0).getAsInt());
		assertEquals(1, array.get(1).getAsInt());
		assertEquals(2, array.get(2).getAsInt());
		assertEquals(3, array.get(3).getAsInt());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInCorrect() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		String key = ConstantsEEModel.JsonKeyAggregation;

		JsonObject input = new JsonObject();
		input.add(ConstantsEEModel.getCollectionElementKey(key, 0), new JsonPrimitive(0));
		input.add(ConstantsEEModel.getCollectionElementKey(key, 0), new JsonPrimitive(1));
		input.add("randomOtherKey", new JsonPrimitive(2));
		input.add(ConstantsEEModel.getCollectionElementKey(key, 0), new JsonPrimitive(3));

		Aggregation tested = new AggregationMock(stateListeners, inputKeys, funcNode, input);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}
	}
}
