package at.uibk.dps.ee.enactables.local.calculation;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import net.sf.opendse.model.Task;

public class AdditionTest {

	protected static class AdditionMock extends Addition {

		protected AdditionMock(Set<EnactableStateListener> stateListeners, Task functionNode, JsonObject input) {
			super(stateListeners, functionNode);
			jsonInput = input;
		}

	}

	@Test
	public void test() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();

		JsonObject input = new JsonObject();
		input.add(ConstantsCalculation.inputSumFirst, JsonParser.parseString("5"));
		input.add(ConstantsCalculation.inputSumSecond, JsonParser.parseString("6"));
		input.add(ConstantsCalculation.inputWaitTime, JsonParser.parseString("500"));

		Addition tested = new AdditionMock(stateListeners, funcNode, input);
		long start = System.currentTimeMillis();
		long duration = 0;
		try {
			tested.myPlay();
			duration = System.currentTimeMillis() - start;
		} catch (StopException e) {
			fail();
		}
		int result = tested.getResult().get(ConstantsCalculation.outputAdditionResult).getAsInt();
		assertEquals(11, result);
		assertTrue(duration >= 500);
	}
}
