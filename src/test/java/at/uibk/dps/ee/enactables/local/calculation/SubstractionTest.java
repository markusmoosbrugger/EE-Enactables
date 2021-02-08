package at.uibk.dps.ee.enactables.local.calculation;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import net.sf.opendse.model.Task;

public class SubstractionTest {

	protected static class SubstractionMock extends Subtraction {

		protected SubstractionMock(Set<EnactableStateListener> stateListeners, Task functionNode, JsonObject input) {
			super(stateListeners, functionNode);
			jsonInput = input;
		}

	}

	@Test
	public void test() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();

		JsonObject input = new JsonObject();
		input.add(ConstantsLocal.inputMinuend, JsonParser.parseString("5"));
		input.add(ConstantsLocal.inputSubtrahend, JsonParser.parseString("6"));
		input.add(ConstantsLocal.inputWaitTime, JsonParser.parseString("0"));

		Subtraction tested = new SubstractionMock(stateListeners, funcNode, input);
		try {
			tested.myPlay();
		} catch (StopException e) {
			fail();
		}
		int result = tested.getResult().get(ConstantsLocal.outputSubstractionResult).getAsInt();
		assertEquals(-1, result);
	}
}
