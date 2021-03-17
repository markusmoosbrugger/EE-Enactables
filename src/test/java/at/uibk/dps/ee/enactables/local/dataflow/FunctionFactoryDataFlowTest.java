package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.Assert.*;
import java.util.HashSet;
import org.junit.Test;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow.DataFlowType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections.OperationType;
import net.sf.opendse.model.Task;

public class FunctionFactoryDataFlowTest {

  @Test
  public void test() {
    FunctionFactoryDataFlow tested = new FunctionFactoryDataFlow(new HashSet<>());
    Task earliestInTask =
        PropertyServiceFunctionDataFlow.createDataFlowFunction("t", DataFlowType.EarliestInput);
    Task aggrTask = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t1",
        OperationType.Aggregation, "scope");
    Task distTask = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t1",
        OperationType.Distribution, "scope");
    Task muxerTask =
        PropertyServiceFunctionDataFlow.createDataFlowFunction("t1", DataFlowType.Multiplexer);
    assertTrue(tested.getDataFlowFunction(earliestInTask) instanceof EarliestArrival);
    assertTrue(tested.getDataFlowFunction(distTask) instanceof Distribution);
    assertTrue(tested.getDataFlowFunction(aggrTask) instanceof Aggregation);
    assertTrue(tested.getDataFlowFunction(muxerTask) instanceof Multiplexer);
  }
}
