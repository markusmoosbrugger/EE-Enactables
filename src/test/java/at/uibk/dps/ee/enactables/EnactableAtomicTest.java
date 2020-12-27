package at.uibk.dps.ee.enactables;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.Enactable.State;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import net.sf.opendse.model.Task;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EnactableAtomicTest {

	protected static final String key1 = "input1";
	protected static final String key2 = "input2";
	protected static final Task function = new Task("function");

	protected static class EnactableMock extends EnactableAtomic {
		protected EnactableMock(Set<EnactableStateListener> stateListeners, Map<String, JsonElement> inputMap,
				Task functionNode) {
			super(stateListeners, inputMap, functionNode);
		}

		@Override
		protected void myPlay() throws StopException {
		}

		@Override
		protected void myPause() {
		}
	}

	protected static EnactableAtomic getTested() {
		Map<String, JsonElement> inputMap = new HashMap<>();
		inputMap.put(key1, null);
		inputMap.put(key2, null);
		return new EnactableMock(new HashSet<>(), inputMap, function);
	}

	@Test(expected = IllegalStateException.class)
	public void testMyInitIncorrect() {
		EnactableAtomic tested = getTested();
		JsonElement value1 = mock(JsonElement.class);
		tested.inputMap.put(key1, value1);
		tested.myInit();
	}
	
	@Test
	public void testMyInitCorrect() {
		EnactableAtomic tested = getTested();
		JsonElement value1 = mock(JsonElement.class);
		JsonElement value2 = mock(JsonElement.class);
		tested.inputMap.put(key1, value1);
		tested.inputMap.put(key2, value2);
		tested.myInit();
		JsonObject input = tested.jsonInput;
		assertEquals(value1, input.get(key1));
		assertEquals(value2, input.get(key2));
	}

	@Test
	public void testGetJsonResult() {
		EnactableAtomic tested = getTested();
		JsonObject result = new JsonObject();
		tested.jsonResult = result;
		assertEquals(result, tested.getJsonResult());
	}

	@Test
	public void testSetState() {
		EnactableAtomic tested = getTested();
		PropertyServiceFunction.setEnactableState(function, State.READY);
		tested.setState(State.STOPPED);
		assertEquals(State.STOPPED, PropertyServiceFunction.getEnactableState(function));
	}

	@Test
	public void testMyReset() {
		EnactableAtomic tested = getTested();
		JsonElement value1 = mock(JsonElement.class);
		JsonElement value2 = mock(JsonElement.class);
		tested.inputMap.put(key1, value1);
		tested.inputMap.put(key2, value2);
		tested.myReset();
		assertNull(tested.inputMap.get(key1));
		assertNull(tested.inputMap.get(key2));
	}

	@Test
	public void testGetFunction() {
		EnactableAtomic tested = getTested();
		assertEquals(function, tested.getFunctionNode());
	}

	@Test
	public void testAreInputsSet() {
		EnactableAtomic tested = getTested();
		JsonElement value1 = mock(JsonElement.class);
		JsonElement value2 = mock(JsonElement.class);
		assertFalse(tested.areAllInputsSet());
		tested.inputMap.put(key1, value1);
		assertFalse(tested.areAllInputsSet());
		tested.inputMap.put(key2, value2);
		assertTrue(tested.areAllInputsSet());
	}
}
