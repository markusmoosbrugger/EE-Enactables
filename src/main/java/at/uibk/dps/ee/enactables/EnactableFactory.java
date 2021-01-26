package at.uibk.dps.ee.enactables;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import at.uibk.dps.ee.core.enactable.Enactable;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.local.calculation.CalculationBuilder;
import at.uibk.dps.ee.enactables.local.dataflow.DataFlowBuilder;
import at.uibk.dps.ee.enactables.local.utility.UtilityBuilder;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import net.sf.opendse.model.Task;

/**
 * The {@link EnactableFactory} is used for the creation of elemental
 * {@link Enactable}s.
 * 
 * @author Fedor Smirnov
 *
 */
@Singleton
public class EnactableFactory {

	protected final Set<EnactableStateListener> stateListeners;
	protected final Set<EnactableBuilder> enactableBuilders;

	/**
	 * The factory is provided with a list of listeners which are to be added to
	 * every created enactable.
	 * 
	 * @param stateListeners a list of listeners which are to be added to every
	 *                       created enactable
	 */
	@Inject
	public EnactableFactory(final Set<EnactableStateListener> stateListeners) {
		this.stateListeners = new HashSet<>();
		this.stateListeners.addAll(stateListeners);
		this.enactableBuilders = generateBuilders();
	}

	/**
	 * Generates the enactment builders.
	 * 
	 * @return the set of the {@link EnactableBuilder}s
	 */
	protected final Set<EnactableBuilder> generateBuilders() {
		final Set<EnactableBuilder> result = new HashSet<>();
		result.add(new CalculationBuilder());
		result.add(new DataFlowBuilder());
		result.add(new UtilityBuilder());
		return result;
	}

	/**
	 * Adds the given listener to the list of listeners provided to every
	 * constructed {@link Enactable}.
	 * 
	 * @param listener the listener to add
	 */
	public void addEnactableStateListener(final EnactableStateListener listener) {
		this.stateListeners.add(listener);
	}

	/**
	 * Creates an enactable which can be used to perform the enactment modeled by
	 * the provided function node.
	 * 
	 * @param functionNode the provided function node
	 * @param inputMap     the map containing the keys (but not yet the content) of
	 *                     the inputs of the function
	 * @return an enactable which can be used to perform the enactment modeled by
	 *         the provided function node
	 */
	public EnactableAtomic createEnactable(final Task functionNode, final Set<String> inputKeys) {
		// look for the right builder
		final FunctionType funcType = PropertyServiceFunction.getType(functionNode);
		for (final EnactableBuilder builder : enactableBuilders) {
			if (builder.getType().equals(funcType)) {
				return builder.buildEnactable(functionNode, inputKeys, stateListeners);
			}
		}
		throw new IllegalStateException("No builder provided for enactables of type " + funcType.name());
	}

	/**
	 * Creates the enactable of the given offspring task and adjusts its state so
	 * that it corresponds to the state of the parent enactable.
	 * 
	 * @param offspring       the offspring task
	 * @param parentEnactable the enactable of the parent
	 * @return the child enactable, adjusted to have the same state as the parent
	 *         enactable
	 */
	public void reproduceEnactable(Task offspring, EnactableAtomic parentEnactable) {
		Set<String> inputKeysOffspring = new HashSet<>(parentEnactable.inputKeys);
		Map<String, JsonElement> inputMapOffspring = new HashMap<>(parentEnactable.inputMap);
		EnactableAtomic offspringEnactable = createEnactable(offspring, inputKeysOffspring);
		offspringEnactable.inputMap.clear();
		offspringEnactable.inputMap.putAll(inputMapOffspring);
		PropertyServiceFunction.setEnactable(offspring, offspringEnactable);
	}
}
