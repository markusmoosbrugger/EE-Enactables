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


	protected EnactableServerless(Set<EnactableStateListener> stateListeners, Map<String, JsonElement> inputMap,
			String resourceLink, Task functionNode) {
		super(stateListeners, inputMap, functionNode);
	}

	@Override
	protected void myPlay() throws StopException {
		throw new IllegalStateException("Not yet implemented.");
	}

	@Override
	protected void myPause() {
		// Nothing special for the pause.
	}
}
