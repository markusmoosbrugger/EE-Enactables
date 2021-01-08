package at.uibk.dps.ee.enactables.local.utility;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.enactables.EnactableBuilder;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility.UtilityType;
import net.sf.opendse.model.Task;

/**
 * Builder for utility enactables.
 * 
 * @author Fedor Smirnov
 */
public class UtilityBuilder implements EnactableBuilder {

	@Override
	public FunctionType getType() {
		return FunctionType.Utility;
	}

	@Override
	public EnactableAtomic buildEnactable(Task functionNode, Map<String, JsonElement> inputMap,
			Set<EnactableStateListener> listeners) {
		if (PropertyServiceFunctionUtility.getUtilityType(functionNode).equals(UtilityType.Condition)) {
			return new ConditionEvaluation(listeners, inputMap, functionNode);
		} else {
			throw new IllegalArgumentException(
					"The node " + functionNode.getId() + " requires a utility enactable which is not supported.");
		}
	}
}
