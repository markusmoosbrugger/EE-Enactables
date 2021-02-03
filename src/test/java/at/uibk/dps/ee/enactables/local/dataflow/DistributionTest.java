package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections.OperationType;
import net.sf.opendse.model.Task;

public class DistributionTest {

	protected static class DistributionMock extends Distribution {

		protected DistributionMock(Set<EnactableStateListener> stateListeners, Set<String> inputKeys, Task functionNode,
				JsonObject input) {
			super(stateListeners, inputKeys, functionNode);
			jsonInput = input;
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testInorrectIterator() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		String key = ConstantsEEModel.JsonKeyConstantIterator;

		JsonObject jsonInput = new JsonObject();

		jsonInput.add(key, new JsonPrimitive("bla"));

		Distribution tested = new DistributionMock(stateListeners, inputKeys, funcNode, jsonInput);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInorrectIterator2() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		String key = "key";

		JsonObject jsonInput = new JsonObject();

		jsonInput.add(key, new JsonPrimitive("bla"));

		Distribution tested = new DistributionMock(stateListeners, inputKeys, funcNode, jsonInput);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
		}
	}

	@Test
	public void testCorrectIntIterator() {
		Task funcNode = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t",
				OperationType.Distribution, "scope");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		String key = ConstantsEEModel.JsonKeyConstantIterator;

		JsonObject jsonInput = new JsonObject();

		jsonInput.add(key, new JsonPrimitive(5));

		Distribution tested = new DistributionMock(stateListeners, inputKeys, funcNode, jsonInput);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}

		assertEquals(5, PropertyServiceFunctionDataFlowCollections.getIterationNumber(tested.getFunctionNode()));

		JsonObject output = tested.getResult();
		assertTrue(output.get(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 0))
				.getAsBoolean());
		assertTrue(output.get(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 1))
				.getAsBoolean());
		assertTrue(output.get(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 2))
				.getAsBoolean());
		assertTrue(output.get(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 3))
				.getAsBoolean());
		assertTrue(output.get(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 4))
				.getAsBoolean());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntIteratorAndCollection() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		String key = ConstantsEEModel.JsonKeyConstantIterator;
		String key1 = "coll1";

		JsonObject jsonInput = new JsonObject();

		jsonInput.add(key, new JsonPrimitive(5));

		JsonArray array1 = new JsonArray();
		array1.add(1);
		array1.add(2);
		array1.add(3);

		jsonInput.add(key1, array1);

		Distribution tested = new DistributionMock(stateListeners, inputKeys, funcNode, jsonInput);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
		}
	}

	@Test
	public void testCorrectCollections() {
		Task funcNode = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t",
				OperationType.Distribution, "scope");

		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		String key1 = "coll1";
		String key2 = "coll2";

		JsonObject jsonInput = new JsonObject();

		JsonArray array1 = new JsonArray();
		array1.add(1);
		array1.add(2);
		array1.add(3);

		JsonArray array2 = new JsonArray();
		array2.add("one");
		array2.add("two");
		array2.add("three");

		jsonInput.add(key1, array1);
		jsonInput.add(key2, array2);

		Distribution tested = new DistributionMock(stateListeners, inputKeys, funcNode, jsonInput);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}

		assertEquals(3, PropertyServiceFunctionDataFlowCollections.getIterationNumber(tested.getFunctionNode()));

		JsonObject output = tested.getResult();
		assertEquals(1, output.get(ConstantsEEModel.getCollectionElementKey(key1, 0)).getAsInt());
		assertEquals(2, output.get(ConstantsEEModel.getCollectionElementKey(key1, 1)).getAsInt());
		assertEquals(3, output.get(ConstantsEEModel.getCollectionElementKey(key1, 2)).getAsInt());
		assertEquals("one", output.get(ConstantsEEModel.getCollectionElementKey(key2, 0)).getAsString());
		assertEquals("two", output.get(ConstantsEEModel.getCollectionElementKey(key2, 1)).getAsString());
		assertEquals("three", output.get(ConstantsEEModel.getCollectionElementKey(key2, 2)).getAsString());
	}

	@Test
	public void testCorrectOneCollection() {
		Task funcNode = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t",
				OperationType.Distribution, "scope");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		String key1 = "coll1";

		JsonObject jsonInput = new JsonObject();

		JsonArray array1 = new JsonArray();
		array1.add(1);
		array1.add(2);
		array1.add(3);

		jsonInput.add(key1, array1);

		Distribution tested = new DistributionMock(stateListeners, inputKeys, funcNode, jsonInput);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}
		assertEquals(3, PropertyServiceFunctionDataFlowCollections.getIterationNumber(tested.getFunctionNode()));
		JsonObject output = tested.getResult();
		assertEquals(1, output.get(ConstantsEEModel.getCollectionElementKey(key1, 0)).getAsInt());
		assertEquals(2, output.get(ConstantsEEModel.getCollectionElementKey(key1, 1)).getAsInt());
		assertEquals(3, output.get(ConstantsEEModel.getCollectionElementKey(key1, 2)).getAsInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testUnequalCollections() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		String key1 = "coll1";
		String key2 = "coll2";

		JsonObject jsonInput = new JsonObject();

		JsonArray array1 = new JsonArray();
		array1.add(1);
		array1.add(2);
		array1.add(3);

		JsonArray array2 = new JsonArray();
		array2.add("one");
		array2.add("two");
		array2.add("three");
		array2.add("four");

		jsonInput.add(key1, array1);
		jsonInput.add(key2, array2);

		Distribution tested = new DistributionMock(stateListeners, inputKeys, funcNode, jsonInput);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
		}
	}
}
