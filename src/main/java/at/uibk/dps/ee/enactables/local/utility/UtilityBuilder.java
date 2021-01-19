package at.uibk.dps.ee.enactables.local.utility;

import java.util.Set;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.enactables.EnactableBuilder;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility.UtilityType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
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
	public EnactableAtomic buildEnactable(final Task functionNode, final Set<String> inputKeys,
			final Set<EnactableStateListener> listeners) {
		final UtilityType type = PropertyServiceFunctionUtility.getUtilityType(functionNode);
		if (type.equals(UtilityType.Condition)) {
			return new ConditionEvaluation(listeners, inputKeys, functionNode);
		} else if (type.equals(UtilityType.CollectionOperation)) {
			CollectionOperation operation = PropertyServiceFunctionUtilityCollections
					.getCollectionOperation(functionNode);
			switch (operation) {
			case ElementIndex:
				return new ElementIndexEnactable(listeners, inputKeys, functionNode);
			case Block:
				return new BlockEnactable(listeners, inputKeys, functionNode);
			default:
				throw new IllegalArgumentException("No builder for collection operation " + operation.name());
			}

		} else {
			throw new IllegalArgumentException(
					"The node " + functionNode.getId() + " requires a utility enactable which is not supported.");
		}
	}
}
