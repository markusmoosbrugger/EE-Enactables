package at.uibk.dps.ee.enactables.serverless;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import net.sf.opendse.model.Task;

/**
 * The {@link EnactableServerless} models the enactment of an atomic serverless
 * function.
 * 
 * @author Fedor Smirnov
 */
public class EnactableServerless extends EnactableAtomic {

	protected final String resourceLink;

	/**
	 * Same constructor as for {@link EnactableAtomic}; Additionally setting the
	 * resource link.
	 * 
	 * @param stateListeners
	 * @param inputMap
	 * @param resourceLink   the resource link
	 * @param functionNode
	 */
	protected EnactableServerless(final Set<EnactableStateListener> stateListeners,
			final Map<String, JsonElement> inputMap, final String resourceLink, final Task functionNode) {
		super(stateListeners, inputMap, functionNode);
		this.resourceLink = resourceLink;
	}

	@Override
	protected void atomicPlay() throws StopException {
		throw new IllegalStateException("Not yet implemented.");
	}

	@Override
	protected void myPause() {
		// Nothing special for the pause.
	}
}
