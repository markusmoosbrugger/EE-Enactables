package at.uibk.dps.ee.enactables;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import net.sf.opendse.model.Task;

public class EnactableAtomicTest {

	protected static final String key1 = "input1";
	protected static final String key2 = "input2";
	protected static final Task function = new Task("function");

	protected static class EnactableMock extends EnactableAtomic {
		protected EnactableMock(Set<EnactableStateListener> stateListeners, Task functionNode) {
			super(stateListeners, functionNode);
		}

		@Override
		protected void myPlay() throws StopException {
		}

		@Override
		protected void myPause() {
		}
	}

	protected static EnactableAtomic getTested() {
		Set<String> inputKeys = new HashSet<>();
		inputKeys.add(key1);
		inputKeys.add(key2);
		return new EnactableMock(new HashSet<>(), function);
	}

	@Test
	public void testGetFunction() {
		EnactableAtomic tested = getTested();
		assertEquals(function, tested.getFunctionNode());
	}
}
