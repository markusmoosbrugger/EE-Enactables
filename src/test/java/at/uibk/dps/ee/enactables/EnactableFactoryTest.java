package at.uibk.dps.ee.enactables;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonPrimitive;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import net.sf.opendse.model.Task;

public class EnactableFactoryTest {

	protected class EnactableMock extends EnactableAtomic {
		protected EnactableMock(Set<EnactableStateListener> stateListeners, Set<String> inputKeys, Task functionNode) {
			super(stateListeners, inputKeys, functionNode);
		}

		@Override
		protected void atomicPlay() throws StopException {
		}

		@Override
		protected void myPause() {
		}
	}

	@Test
	public void testGenerateBuilders() {
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableFactory tested = new EnactableFactory(stateListeners);
		assertEquals(3, tested.enactableBuilders.size());
	}

	@Test
	public void testAddListener() {
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableStateListener listenerMock = mock(EnactableStateListener.class);
		EnactableFactory tested = new EnactableFactory(stateListeners);
		assertFalse(tested.stateListeners.contains(listenerMock));
		tested.addEnactableStateListener(listenerMock);
		assertTrue(tested.stateListeners.contains(listenerMock));
	}

	@Test(expected = IllegalStateException.class)
	public void testNoBuilderException() {
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableFactory tested = new EnactableFactory(stateListeners);
		Task task = new Task("task");
		PropertyServiceFunction.setType(FunctionType.Serverless, task);
		Set<String> inputKeys = new HashSet<>();
		tested.createEnactable(task, inputKeys);
	}

	@Test
	public void testCreateEnactable() {
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableFactory tested = new EnactableFactory(stateListeners);
		EnactableBuilder builder = mock(EnactableBuilder.class);
		Task task = new Task("bla");
		PropertyServiceFunction.setType(FunctionType.Serverless, task);
		Set<String> inputKeys = new HashSet<>();
		EnactableAtomic expected = mock(EnactableAtomic.class);
		tested.enactableBuilders.add(builder);
		when(builder.buildEnactable(task, inputKeys, stateListeners)).thenReturn(expected);
		when(builder.getType()).thenReturn(FunctionType.Serverless);
		EnactableAtomic result = tested.createEnactable(task, inputKeys);
		assertEquals(expected, result);
	}

	@Test
	public void testReproduceEnactable() {
		Task taskParent = new Task("blabla");
		Task task = new Task("bla");
		PropertyServiceFunction.setType(FunctionType.Serverless, task);
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableFactory tested = new EnactableFactory(stateListeners);
		EnactableBuilder builder = mock(EnactableBuilder.class);
		tested.enactableBuilders.add(builder);

		Set<String> inputKeys = new HashSet<String>(Arrays.asList("one", "two"));
		EnactableAtomic parentMock = new EnactableMock(stateListeners, inputKeys, taskParent);
		EnactableAtomic childMock = new EnactableMock(stateListeners, inputKeys, task);

		parentMock.inputMap.put("one", new JsonPrimitive(1));

		when(builder.buildEnactable(task, inputKeys, stateListeners)).thenReturn(childMock);
		when(builder.getType()).thenReturn(FunctionType.Serverless);

		tested.reproduceEnactable(task, parentMock);
		assertTrue(childMock.inputMap.containsKey("one"));
		assertEquals(1, childMock.inputMap.get("one").getAsInt());
	}

}
