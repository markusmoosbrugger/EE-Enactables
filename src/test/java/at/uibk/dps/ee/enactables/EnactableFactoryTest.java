package at.uibk.dps.ee.enactables;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.UsageType;
import net.sf.opendse.model.Task;

public class EnactableFactoryTest {

	protected class EnactableMock extends EnactableAtomic {
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
		PropertyServiceFunction.setUsageType(UsageType.Serverless, task);
		tested.createEnactable(task);
	}

	@Test
	public void testCreateEnactable() {
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableFactory tested = new EnactableFactory(stateListeners);
		EnactableBuilder builder = mock(EnactableBuilder.class);
		Task task = new Task("bla");
		PropertyServiceFunction.setUsageType(UsageType.Serverless, task);
		EnactableAtomic expected = mock(EnactableAtomic.class);
		tested.enactableBuilders.add(builder);
		when(builder.buildEnactable(task, stateListeners)).thenReturn(expected);
		when(builder.getType()).thenReturn(UsageType.Serverless);
		EnactableAtomic result = tested.createEnactable(task);
		assertEquals(expected, result);
	}

	@Test
	public void testReproduceEnactable() {
		Task taskParent = new Task("blabla");
		Task task = new Task("bla");
		PropertyServiceFunction.setUsageType(UsageType.Serverless, task);
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableFactory tested = new EnactableFactory(stateListeners);
		EnactableBuilder builder = mock(EnactableBuilder.class);
		tested.enactableBuilders.add(builder);

		EnactableAtomic parentMock = new EnactableMock(stateListeners, taskParent);
		EnactableAtomic childMock = new EnactableMock(stateListeners, task);

		when(builder.buildEnactable(task, stateListeners)).thenReturn(childMock);
		when(builder.getType()).thenReturn(UsageType.Serverless);

		tested.reproduceEnactable(task, parentMock);
		assertEquals(childMock, PropertyServiceFunction.getEnactable(task));
	}

}
