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

public class LocalAbstractTest {

	protected static class LocalMock extends LocalAbstract {

		protected LocalMock(Set<EnactableStateListener> stateListeners, Map<String, JsonElement> inputMap,
				Task functionNode, JsonObject jsonObject) {
			super(stateListeners, inputMap, functionNode);
			jsonInput = jsonObject;
		}

		@Override
		protected void atomicPlay() throws StopException {
		}

		@Override
		protected void myPause() {
		}
	}

	@Test
	public void test() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Map<String, JsonElement> inputMap = new HashMap<>();

		JsonObject input = new JsonObject();
		input.add("prop", JsonParser.parseString("5"));
		LocalAbstract tested = new LocalMock(stateListeners, inputMap, funcNode, input);

		try {
			assertEquals(5, tested.readIntInput("prop"));
		} catch (StopException e) {
			fail();
		}

		try {
			tested.readIntInput("prop2");
			fail();
		} catch (StopException e) {
		}
	}
}
