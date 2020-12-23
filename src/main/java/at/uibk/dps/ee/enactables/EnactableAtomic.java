package at.uibk.dps.ee.enactables;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.core.enactable.Enactable;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;

/**
 * The {@link EnactableAtomic}
 * 
 * @author Fedor Smirnov
 *
 */
public abstract class EnactableAtomic extends Enactable {

	protected final Map<String, String> inputMap;
	protected JsonObject jsonInput;
	protected String jsonResult;

	protected EnactableAtomic(Set<EnactableStateListener> stateListeners, Map<String, String> inputMap) {
		super(stateListeners);
		this.inputMap = inputMap;
	}

	@Override
	protected void myInit() {
		// create the json object
		jsonInput = new JsonObject();
		for (Entry<String, String> entry : inputMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			jsonInput.add(key, JsonParser.parseString(value));
		}
	}

	/**
	 * Sets the provided input for the given key. If all inputs are set, activates
	 * it's own init.
	 * 
	 * @param key   the input key
	 * @param value the input value
	 */
	public void setInput(String key, String value) {
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
		for (String key : inputMap.keySet()) {
			if (inputMap.get(key) == null) {
				return false;
			}
		}
		return true;
	}

	public String getJsonResult() {
		return jsonResult;
	}

	@Override
	protected void myReset() {
		// sets the values of all entries to null
		for (String key : inputMap.keySet()) {
			inputMap.put(key, null);
		}
		jsonInput = null;
	}
}
