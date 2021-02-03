package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow.DataFlowType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections.OperationType;
import net.sf.opendse.model.Task;

public class DataFlowBuilderTest {

	@Test
	public void test() {
		DataFlowBuilder tested = new DataFlowBuilder();

		Set<EnactableStateListener> stateListeners = new HashSet<>();

		Task input = PropertyServiceFunctionDataFlow.createDataFlowFunction("task", DataFlowType.EarliestInput);
		EnactableAtomic result = tested.buildEnactable(input, stateListeners);
		assertTrue(result instanceof EarliestArrival);

		Task inputDist = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("id",
				OperationType.Distribution, "scope");
		Task inputAggr = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("id",
				OperationType.Aggregation, "scope");

		EnactableAtomic resultDist = tested.buildEnactable(inputDist, stateListeners);
		assertTrue(resultDist instanceof Distribution);

		EnactableAtomic resultAggr = tested.buildEnactable(inputAggr, stateListeners);
		assertTrue(resultAggr instanceof Aggregation);
	}

}
