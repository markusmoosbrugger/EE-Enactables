package at.uibk.dps.ee.enactables.local.syntax;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonElement;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionSyntax;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionSyntax.SyntaxType;
import net.sf.opendse.model.Task;

public class SyntaxBuilderTest {

	@Test
	public void test() {
		SyntaxBuilder tested = new SyntaxBuilder();

		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Map<String, JsonElement> inputMap = new HashMap<>();

		Task input = PropertyServiceFunctionSyntax.createSyntaxFunction("task", SyntaxType.EarliestInput);
		EnactableAtomic result = tested.buildEnactable(input, inputMap, stateListeners);
		assertTrue(result instanceof EarliestArrival);
	}

}
