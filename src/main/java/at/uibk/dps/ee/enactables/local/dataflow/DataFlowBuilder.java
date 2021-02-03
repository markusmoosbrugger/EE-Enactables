package at.uibk.dps.ee.enactables.local.dataflow;

import java.util.Set;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.enactables.EnactableBuilder;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow.DataFlowType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections.OperationType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections;
import net.sf.opendse.model.Task;

/**
 * Builder for syntax enactables.
 * 
 * @author Fedor Smirnov
 */
public class DataFlowBuilder implements EnactableBuilder {

	@Override
	public FunctionType getType() {
		return FunctionType.DataFlow;
	}

	@Override
	public EnactableAtomic buildEnactable(final Task functionNode, final Set<EnactableStateListener> listeners) {
		if (PropertyServiceFunctionDataFlow.getDataFlowType(functionNode).equals(DataFlowType.EarliestInput)) {
			return new EarliestArrival(listeners, functionNode);
		} else if (PropertyServiceFunctionDataFlow.getDataFlowType(functionNode).equals(DataFlowType.Collections)) {

			if (PropertyServiceFunctionDataFlowCollections.getOperationType(functionNode)
					.equals(OperationType.Distribution)) {
				return new Distribution(listeners, functionNode);
			} else if (PropertyServiceFunctionDataFlowCollections.getOperationType(functionNode)
					.equals(OperationType.Aggregation)) {
				return new Aggregation(listeners, functionNode);
			} else {
				throw new IllegalArgumentException("The node " + functionNode.getId()
						+ " requires a data flow collection enactable which is not supported.");
			}

		} else {
			throw new IllegalArgumentException(
					"The node " + functionNode.getId() + " requires a data flow enactable which is not supported.");
		}
	}
}
