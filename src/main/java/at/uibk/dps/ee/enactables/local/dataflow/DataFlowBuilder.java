package at.uibk.dps.ee.enactables.local.dataflow;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.enactables.EnactableBuilder;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow.DataFlowType;
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
	public EnactableAtomic buildEnactable(final Task functionNode, final Map<String, JsonElement> inputMap,
			final Set<EnactableStateListener> listeners) {
		if (PropertyServiceFunctionDataFlow.getDataFlowType(functionNode).equals(DataFlowType.EarliestInput)) {
			return new EarliestArrival(listeners, inputMap, functionNode);
		} else {
			throw new IllegalArgumentException(
					"The node " + functionNode.getId() + " requires a syntax enactable which is not supported.");
		}
	}
}
