package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonElement;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow.DataFlowType;
import net.sf.opendse.model.Task;

public class DataFlowBuilderTest {

	@Test
	public void test() {
		DataFlowBuilder tested = new DataFlowBuilder();

		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Map<String, JsonElement> inputMap = new HashMap<>();

		Task input = PropertyServiceFunctionDataFlow.createDataFlowFunction("task", DataFlowType.EarliestInput);
		EnactableAtomic result = tested.buildEnactable(input, inputMap, stateListeners);
		assertTrue(result instanceof EarliestArrival);
	}

}
