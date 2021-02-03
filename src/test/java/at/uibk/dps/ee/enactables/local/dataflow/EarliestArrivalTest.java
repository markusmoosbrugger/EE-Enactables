package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import net.sf.opendse.model.Task;

public class EarliestArrivalTest {

	protected static class EarliestArrivalMock extends EarliestArrival {

		protected EarliestArrivalMock(Set<EnactableStateListener> stateListeners, Task functionNode, JsonObject input) {
			super(stateListeners, functionNode);
			jsonInput = input;
		}

	}

	@Test
	public void test() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();

		String content = "myInput";

		JsonObject input = new JsonObject();
		input.add(ConstantsEEModel.EarliestArrivalJsonKey, JsonParser.parseString(content));

		EarliestArrival tested = new EarliestArrivalMock(stateListeners, funcNode, input);
		try {
			tested.myPlay();
		} catch (StopException e) {
			fail();
		}
		String result = tested.getResult().get(ConstantsEEModel.EarliestArrivalJsonKey).getAsString();
		assertEquals(content, result);
	}

}
