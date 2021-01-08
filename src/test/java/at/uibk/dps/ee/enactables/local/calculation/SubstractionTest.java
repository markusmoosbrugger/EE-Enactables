package at.uibk.dps.ee.enactables.local.calculation;

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

public class SubstractionTest {

	protected static class SubstractionMock extends Substraction {

		protected SubstractionMock(Set<EnactableStateListener> stateListeners, Map<String, JsonElement> inputMap,
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
		input.add(ConstantsCalculation.inputMinuend, JsonParser.parseString("5"));
		input.add(ConstantsCalculation.inputSubtrahend, JsonParser.parseString("6"));
		input.add(ConstantsCalculation.inputWaitTime, JsonParser.parseString("0"));

		Substraction tested = new SubstractionMock(stateListeners, inputMap, funcNode, input);
		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}
		int result = tested.getJsonResult().get(ConstantsCalculation.outputSubstractionResult).getAsInt();
		assertEquals(-1, result);
	}
}
