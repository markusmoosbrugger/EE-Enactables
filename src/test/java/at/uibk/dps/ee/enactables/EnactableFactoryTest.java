package at.uibk.dps.ee.enactables;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonElement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import net.sf.opendse.model.Task;

public class EnactableFactoryTest {

	@Test
	public void testGenerateBuilders() {
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableFactory tested = new EnactableFactory(stateListeners);
		assertEquals(2, tested.enactableBuilders.size());
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
		Map<String, JsonElement> inputMap = new HashMap<>();
		tested.createEnactable(task, inputMap);
	}

	@Test
	public void testCreateEnactable() {
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableFactory tested = new EnactableFactory(stateListeners);
		EnactableBuilder builder = mock(EnactableBuilder.class);
		Task task = new Task("bla");
		PropertyServiceFunction.setType(FunctionType.Serverless, task);
		Map<String, JsonElement> inputMap = new HashMap<>();
		EnactableAtomic expected = mock(EnactableAtomic.class);
		tested.enactableBuilders.add(builder);
		when(builder.buildEnactable(task, inputMap, stateListeners)).thenReturn(expected);
		when(builder.getType()).thenReturn(FunctionType.Serverless);
		EnactableAtomic result = tested.createEnactable(task, inputMap);
		assertEquals(expected, result);
	}

}
