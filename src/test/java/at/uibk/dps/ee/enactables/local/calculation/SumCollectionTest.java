package at.uibk.dps.ee.enactables.local.calculation;

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
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import net.sf.opendse.model.Task;

public class SumCollectionTest {

	protected static class SumCollectionMock extends SumCollection {
		public SumCollectionMock(Set<EnactableStateListener> stateListeners, Task functionNode, JsonObject jsonInput) {
			super(stateListeners, functionNode);
			this.jsonInput = jsonInput;
		}
	}

	@Test
	public void test() {
		JsonArray collection = new JsonArray();
		JsonElement first = new JsonPrimitive(1);
		JsonElement second = new JsonPrimitive(3);
		JsonElement third = new JsonPrimitive(5);
		collection.add(first);
		collection.add(second);
		collection.add(third);

		JsonObject input = new JsonObject();
		input.add(ConstantsLocal.inputSumCollection, collection);
		JsonElement waitTime = new JsonPrimitive(0);
		input.add(ConstantsLocal.inputWaitTime, waitTime);

		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();

		SumCollection tested = new SumCollectionMock(stateListeners, funcNode, input);

		try {
			tested.myPlay();
			assertTrue(tested.getResult().has(ConstantsLocal.outputSumCollection));
			int result = tested.getResult().get(ConstantsLocal.outputSumCollection).getAsInt();
			assertEquals(result, 9);
		} catch (StopException e) {
			fail();
		}
	}
}
