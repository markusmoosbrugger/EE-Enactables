package at.uibk.dps.ee.enactables.local;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import net.sf.opendse.model.Task;

public class LocalAbstractTest {

	protected static class LocalMock extends LocalAbstract {

		protected LocalMock(Set<EnactableStateListener> stateListeners, Set<String> inputKeys, Task functionNode,
				JsonObject jsonObject) {
			super(stateListeners, inputKeys, functionNode);
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
		Set<String> inputKeys = new HashSet<>();

		JsonObject input = new JsonObject();
		input.add("prop", JsonParser.parseString("5"));
		LocalAbstract tested = new LocalMock(stateListeners, inputKeys, funcNode, input);

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
