package at.uibk.dps.ee.enactables;

import java.util.Map;
import java.util.Set;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import net.sf.opendse.model.Task;

/**
 * Interface for the classes used for the construction of enactables.
 * 
 * @author Fedor Smirnov
 *
 */
public interface EnactableBuilder {

	/**
	 * Different types of enactables.
	 * 
	 * @author Fedor Smirnov
	 */
	public enum EnactableType {
		/**
		 * Serverless functions, e.g., AWS Lambda
		 */
		Serverless,
		/**
		 * Functions executed on the same machine where the EE is running.
		 */
		Local
	}

	/**
	 * Returns the type of the enactable built by the given builder.
	 * 
	 * @return the type of the enactable built by the given builder
	 */
	public EnactableType getType();

	/**
	 * Builds an enactable for the provided function node.
	 * 
	 * @param functionNode the provided function node 
	 * @param inputMap the map where each key corresponds to a data in
	 * @param listeners the set of enactable listeners
	 * @return an enactable for the provided function node
	 */
	public EnactableAtomic buildEnactable(Task functionNode, Map<String, String> inputMap,
			Set<EnactableStateListener> listeners);
}
