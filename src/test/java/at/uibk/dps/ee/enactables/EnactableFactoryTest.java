package at.uibk.dps.ee.enactables;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import static org.mockito.Mockito.mock;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.UsageType;
import net.sf.opendse.model.Task;

public class EnactableFactoryTest {

	@Test
	public void testAddListener() {
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableStateListener listenerMock = mock(EnactableStateListener.class);
		EnactableFactory tested = new EnactableFactory(stateListeners);
		assertFalse(tested.stateListeners.contains(listenerMock));
		tested.addEnactableStateListener(listenerMock);
		assertTrue(tested.stateListeners.contains(listenerMock));
	}

	@Test
	public void testCreateEnactable() {
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableFactory tested = new EnactableFactory(stateListeners);
		Task functionTask = new Task("functionTask");
		EnactableAtomic result = tested.createEnactable(functionTask);
		assertEquals(functionTask, result.functionNode);
	}

	@Test
	public void testReproduceEnactable() {
		Task taskParent = new Task("blabla");
		Task task = new Task(taskParent);
		PropertyServiceFunction.setUsageType(UsageType.User, task);
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		EnactableFactory tested = new EnactableFactory(stateListeners);

		EnactableAtomic parent = new EnactableAtomic(stateListeners, taskParent);
		String key = "key";
		JsonElement value = new JsonPrimitive(true);
		parent.setInputValue(key, value);

		tested.reproduceEnactable(task, parent);
		EnactableAtomic result = (EnactableAtomic) PropertyServiceFunction.getEnactable(task);
		assertEquals(task, result.getFunctionNode());
		assertNotNull(result.getInput());
		assertEquals(value, result.getInput().get(key));
	}
}
