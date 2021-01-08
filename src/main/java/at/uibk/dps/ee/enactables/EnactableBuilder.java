package at.uibk.dps.ee.enactables;

import java.util.Set;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import net.sf.opendse.model.Task;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;

/**
 * Interface for the classes used for the construction of enactables.
 * 
 * @author Fedor Smirnov
 *
 */
public interface EnactableBuilder {

	/**
	 * Returns the type of the enactable built by the given builder.
	 * 
	 * @return the type of the enactable built by the given builder
	 */
	FunctionType getType();

	/**
	 * Builds an enactable for the provided function node.
	 * 
	 * @param functionNode the provided function node
	 * @param inputKey     the keys (each key corresponds to a data in)
	 * @param listeners    the set of enactable listeners
	 * @return an enactable for the provided function node
	 */
	EnactableAtomic buildEnactable(Task functionNode, Set<String> inputKeys, Set<EnactableStateListener> listeners);
}
