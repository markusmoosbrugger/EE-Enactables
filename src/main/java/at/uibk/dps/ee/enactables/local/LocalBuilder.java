package at.uibk.dps.ee.enactables.local;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.enactables.EnactableBuilder;
import at.uibk.dps.ee.enactables.local.calculation.Addition;
import at.uibk.dps.ee.enactables.local.calculation.Substraction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import net.sf.opendse.model.Task;

/**
 * Class to build local enactables.
 * 
 * @author Fedor Smirnov
 *
 */
public class LocalBuilder implements EnactableBuilder {

	@Override
	public FunctionType getType() {
		return FunctionType.Local;
	}

	@Override
	public EnactableAtomic buildEnactable(final Task functionNode, final Map<String, JsonElement> inputMap,
			final Set<EnactableStateListener> listeners) {
		final String name = functionNode.getId();
		if (name.startsWith(ConstantsLocalEnactables.prefixAddition)) {
			return new Addition(listeners, inputMap, functionNode);
		} else if (name.startsWith(ConstantsLocalEnactables.prefixSubstraction)) {
			return new Substraction(listeners, inputMap, functionNode);
		} else {
			throw new IllegalArgumentException("No local enactables known for task " + name);
		}
	}
}
