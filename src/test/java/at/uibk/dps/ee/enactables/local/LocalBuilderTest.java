package at.uibk.dps.ee.enactables.local;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonElement;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.enactables.local.calculation.Addition;
import at.uibk.dps.ee.enactables.local.calculation.ConstantsCalculation;
import at.uibk.dps.ee.enactables.local.calculation.Substraction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import net.sf.opendse.model.Task;

public class LocalBuilderTest {

	@Test
	public void test() {
		LocalBuilder tested = new LocalBuilder();
		assertEquals(FunctionType.Local, tested.getType());

		String name = ConstantsCalculation.prefixAddition + "bla";
		Task task = new Task(name);
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Map<String, JsonElement> inputMap = new HashMap<>();

		EnactableAtomic result = tested.buildEnactable(task, inputMap, stateListeners);
		assertTrue(result instanceof Addition);
		
		String nameSubst = ConstantsCalculation.prefixSubstraction + "blabla";
		task = new Task(nameSubst);

		result = tested.buildEnactable(task, inputMap, stateListeners);
		assertTrue(result instanceof Substraction);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnknownName() {
		LocalBuilder tested = new LocalBuilder();

		String name = "bla";
		Task task = new Task(name);
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Map<String, JsonElement> inputMap = new HashMap<>();

		tested.buildEnactable(task, inputMap, stateListeners);
	}
}
