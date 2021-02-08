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

	protected static class LocalMock extends LocalFunctionAbstract {

		protected LocalMock(Set<EnactableStateListener> stateListeners, Task functionNode, JsonObject jsonObject) {
			super(stateListeners, functionNode);
			jsonInput = jsonObject;
		}

		@Override
		protected void myPlay() throws StopException {
		}

		@Override
		protected void myPause() {
		}
	}

	@Test
	public void test() {
		Task funcNode = new Task("t");
		Set<EnactableStateListener> stateListeners = new HashSet<>();

		JsonObject input = new JsonObject();
		input.add("prop", JsonParser.parseString("5"));
		LocalFunctionAbstract tested = new LocalMock(stateListeners, funcNode, input);

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
