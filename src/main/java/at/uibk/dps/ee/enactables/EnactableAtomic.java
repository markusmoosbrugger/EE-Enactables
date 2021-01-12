package at.uibk.dps.ee.enactables;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.Enactable;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import net.sf.opendse.model.Task;

/**
 * The {@link EnactableAtomic}
 * 
 * @author Fedor Smirnov
 *
 */
public abstract class EnactableAtomic extends Enactable {

	protected final Map<String, JsonElement> inputMap;
	protected final Set<String> inputKeys;
	protected JsonObject jsonInput;
	protected JsonObject jsonResult;
	protected final Task functionNode;
	protected boolean init;

	/**
	 * Protected constructor, used the factory to create enactables.
	 * 
	 * @param stateListeners the state listeners
	 * @param inputMap       map containing the expected input names as keys with
	 *                       null values
	 * @param functionNode   the node from the enactment graph associated with this
	 *                       enactable
	 */
	protected EnactableAtomic(final Set<EnactableStateListener> stateListeners, final Set<String> inputKeys,
			final Task functionNode) {
		super(stateListeners);
		this.inputMap = new HashMap<>();
		this.inputKeys = inputKeys;
		this.functionNode = functionNode;
		PropertyServiceFunction.setEnactable(functionNode, this);
	}

	@Override
	protected void myInit() {
		// create the json object
		jsonInput = new JsonObject();
		for (final String key : inputKeys) {
			if (!inputMap.containsKey(key)) {
				throw new IllegalStateException("Init called while the input " + key + " is still not set.");
			}
			final JsonElement value = inputMap.get(key);
			jsonInput.add(key, value);
		}
		init = true;
	}

	@Override
	protected void myPlay() throws StopException {
		if (!init) {
			throw new IllegalStateException("Play triggered before atomic initialized.");
		}
		atomicPlay();
	}

	/**
	 * The method for the actual enactment
	 * 
	 * @throws StopException
	 */
	protected abstract void atomicPlay() throws StopException;

	public Task getFunctionNode() {
		return functionNode;
	}

	/**
	 * Sets the provided input for the given key. If all inputs are set, activates
	 * it's own init.
	 * 
	 * @param key   the input key
	 * @param value the input value
	 */
	public void setInput(final String key, final JsonElement value) {
		if (!inputKeys.contains(key)) {
			throw new IllegalArgumentException("Unknown input key: " + key);
		}
		inputMap.put(key, value);
		if (areAllInputsSet()) {
			init();
		}
	}

	/**
	 * Checks whether all inputs are set so that the processing can be started.
	 * 
	 * @return true iff all inputs are set.
	 */
	protected boolean areAllInputsSet() {
		for (final String key : inputKeys) {
			if (inputMap.get(key) == null) {
				return false;
			}
		}
		return true;
	}

	public JsonObject getJsonResult() {
		return jsonResult;
	}

	@Override
	protected void myReset() {
		// empties the input map
		inputMap.clear();
		init = false;
	}
}
