package at.uibk.dps.ee.enactables.local;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import net.sf.opendse.model.Task;

public class LocalAdditionTest {

	protected static class AdditionMock extends LocalAddition {

		protected AdditionMock(Set<EnactableStateListener> stateListeners, Map<String, JsonElement> inputMap,
				Task functionNode, JsonObject input) {
			super(stateListeners, inputMap, functionNode);
			jsonInput = input;
		}

	}

	@Test
	public void test() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Map<String, JsonElement> inputMap = new HashMap<>();

		JsonObject input = new JsonObject();
		input.add(ConstantsLocalEnactables.inputSumFirst, JsonParser.parseString("5"));
		input.add(ConstantsLocalEnactables.inputSumSecond, JsonParser.parseString("6"));
		input.add(ConstantsLocalEnactables.inputSumWaitTime, JsonParser.parseString("500"));

		LocalAddition tested = new AdditionMock(stateListeners, inputMap, funcNode, input);
		long start = System.currentTimeMillis();
		long duration = 0;
		try {
			tested.atomicPlay();
			duration = System.currentTimeMillis() - start;
		} catch (StopException e) {
			fail();
		}
		int result = tested.getJsonResult().get(ConstantsLocalEnactables.outputSumResult).getAsInt();
		assertEquals(11, result);
		assertTrue(duration >= 500);
	}
}
