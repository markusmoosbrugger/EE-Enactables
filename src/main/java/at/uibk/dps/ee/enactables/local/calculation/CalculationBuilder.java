package at.uibk.dps.ee.enactables.local.calculation;

import java.util.Set;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.enactables.EnactableBuilder;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import net.sf.opendse.model.Task;

/**
 * Class to build local enactables.
 * 
 * @author Fedor Smirnov
 *
 */
public class CalculationBuilder implements EnactableBuilder {

	@Override
	public FunctionType getType() {
		return FunctionType.Local;
	}

	@Override
	public EnactableAtomic buildEnactable(final Task functionNode, final Set<String> inputKeys,
			final Set<EnactableStateListener> listeners) {
		final String name = functionNode.getId();
		if (name.startsWith(ConstantsCalculation.prefixAddition)) {
			return new Addition(listeners, inputKeys, functionNode);
		} else if (name.startsWith(ConstantsCalculation.prefixSubstraction)) {
			return new Substraction(listeners, inputKeys, functionNode);
		} else {
			throw new IllegalArgumentException("No local enactables known for task " + name);
		}
	}
}
