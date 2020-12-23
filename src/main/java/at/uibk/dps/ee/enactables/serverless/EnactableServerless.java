package at.uibk.dps.ee.enactables.serverless;

import java.util.Map;
import java.util.Set;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.EnactableAtomic;

/**
 * The {@link EnactableServerless} models the enactment of an atomic serverless
 * function.
 * 
 * @author Fedor Smirnov
 */
public class EnactableServerless extends EnactableAtomic {

	protected final String resourceLink;

	protected EnactableServerless(Set<EnactableStateListener> stateListeners, Map<String, String> inputMap,
			String resourceLink) {
		super(stateListeners, inputMap);
		this.resourceLink = resourceLink;
	}

	@Override
	protected void myPlay() throws StopException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void myPause() {
		// Nothing special for the pause.
	}
}
