package at.uibk.dps.ee.enactables;

import java.util.Map;
import java.util.Map.Entry;
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
	protected JsonObject jsonInput;
	protected JsonObject jsonResult;
	protected final Task functionNode;
	protected boolean init = false;

	/**
	 * Protected constructor, used the factory to create enactables.
	 * 
	 * @param stateListeners the state listeners
	 * @param inputMap       map containing the expected input names as keys with
	 *                       null values
	 * @param functionNode   the node from the enactment graph associated with this
	 *                       enactable
	 */
	protected EnactableAtomic(final Set<EnactableStateListener> stateListeners, final Map<String, JsonElement> inputMap,
			final Task functionNode) {
		super(stateListeners);
		this.inputMap = inputMap;
		this.functionNode = functionNode;
		PropertyServiceFunction.setEnactableState(functionNode, state);
	}

	@Override
	protected void myInit() {
		// create the json object
		jsonInput = new JsonObject();
		for (final Entry<String, JsonElement> entry : inputMap.entrySet()) {
			final String key = entry.getKey();
			if (entry.getValue() == null) {
				throw new IllegalStateException("Init called while the input " + key + " is still not set.");
			}
			final JsonElement value = entry.getValue();
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
		if (!inputMap.containsKey(key)) {
			throw new IllegalArgumentException("The provided input key is not in the input map.");
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
		for (final String key : inputMap.keySet()) {
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
		// sets the values of all entries to null
		for (final String key : inputMap.keySet()) {
			inputMap.put(key, null);
		}
		init = false;
	}

	@Override
	public void setState(final State state) {
		PropertyServiceFunction.setEnactableState(functionNode, state);
		super.setState(state);
	}
}
