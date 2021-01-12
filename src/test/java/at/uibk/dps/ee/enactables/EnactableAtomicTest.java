package at.uibk.dps.ee.enactables;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.Enactable.State;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import net.sf.opendse.model.Task;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;

public class EnactableAtomicTest {

	protected static final String key1 = "input1";
	protected static final String key2 = "input2";
	protected static final Task function = new Task("function");

	protected static class EnactableMock extends EnactableAtomic {
		protected EnactableMock(Set<EnactableStateListener> stateListeners, Set<String> inputKeys,
				Task functionNode) {
			super(stateListeners, inputKeys, functionNode);
		}

		@Override
		protected void atomicPlay() throws StopException {
		}

		@Override
		protected void myPause() {
		}
	}

	protected static EnactableAtomic getTested() {
		Set<String> inputKeys = new HashSet<>();
		inputKeys.add(key1);
		inputKeys.add(key2);
		return new EnactableMock(new HashSet<>(), inputKeys, function);
	}

	@Test
	public void testSetInputCorrect() {
		EnactableAtomic tested = getTested();
		EnactableAtomic testedSpy = spy(tested);
		JsonElement value1 = mock(JsonElement.class);
		JsonElement value2 = mock(JsonElement.class);
		testedSpy.setInput(key1, value1);
		assertEquals(value1, testedSpy.inputMap.get(key1));
		verify(testedSpy, never()).init();
		testedSpy.setInput(key2, value2);
		assertEquals(value2, testedSpy.inputMap.get(key2));
		verify(testedSpy).init();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetInputIncorrect() {
		EnactableAtomic tested = getTested();
		JsonElement value1 = mock(JsonElement.class);
		tested.setInput("bla", value1);
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
		assertFalse(tested.init);
		JsonElement value1 = mock(JsonElement.class);
		JsonElement value2 = mock(JsonElement.class);
		tested.inputMap.put(key1, value1);
		tested.inputMap.put(key2, value2);
		tested.myInit();
		JsonObject input = tested.jsonInput;
		assertEquals(value1, input.get(key1));
		assertEquals(value2, input.get(key2));
		assertTrue(tested.init);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testPlayNoInit() {
		EnactableAtomic tested = getTested();
		tested.setState(State.READY);
		try {
			tested.play();
		} catch (StopException e) {
			fail();
		}
	}
	
	@Test
	public void testPlay() {
		EnactableAtomic tested = getTested();
		tested.setState(State.READY);
		tested.init = true;
		try {
			tested.play();
		} catch (StopException e) {
			fail();
		}
	}

	@Test
	public void testGetJsonResult() {
		EnactableAtomic tested = getTested();
		JsonObject result = new JsonObject();
		tested.jsonResult = result;
		assertEquals(result, tested.getJsonResult());
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
