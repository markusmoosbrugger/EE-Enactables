package at.uibk.dps.ee.enactables.local.utility;

import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.model.constants.ConstantsEEModel;

/**
 * Static method container for the methods handling the collections.
 * 
 * @author Fedor Smirnov
 */
public final class UtilsCollections {

	/**
	 * No constructor.
	 */
	private UtilsCollections() {
	}

	/**
	 * Searches for the key to the collection in the given json object. Only use
	 * this if there is at most one collection in the json input.
	 * 
	 * @param jsonInput the given json object
	 * @return (optional) the key to the collection object
	 */
	public static Optional<String> getCollectionKey(JsonObject jsonInput) {
		String result = null;
		for (final String key : jsonInput.keySet()) {
			final JsonElement element = jsonInput.get(key);
			if (element.isJsonArray()) {
				result = key;
				break;
			}
		}
		return Optional.ofNullable(result);
	}

	/**
	 * Uses the descriptive string and the json input to generate the subcollections
	 * object.
	 * 
	 * @param inputString the string decribing the subcollections structure and
	 *                    static parts
	 * @param jsonInput   the json input (to get the dynamic parts)
	 * @return the subcollections object
	 */
	public static CollOperEIdx readSubCollections(final String inputString, final JsonObject jsonInput) {
		// remove the white spaces
		final String stringNoWs = inputString.trim();
		final CollOperEIdx result = new CollOperEIdx();
		if (stringNoWs.contains(ConstantsEEModel.EIdxSeparatorExternal)) {
			// multiple comma-separated values
			final String[] subStrings = stringNoWs.split(ConstantsEEModel.EIdxSeparatorExternal);
			for (int idx = 0; idx < subStrings.length; idx++) {
				final String subString = subStrings[idx];
				result.add(getSubCollectionForString(subString, jsonInput));
			}
		} else {
			// only one number
			result.add(getSubCollectionForString(stringNoWs, jsonInput));
		}
		return result;
	}

	/**
	 * Returns the subcollection for the given string.
	 * 
	 * @param subcollectionString the given string
	 * @param jsonInput           (to read the dynamic data)
	 * @param substring           idx (to know which json element ot access)
	 * @return the subcollection for the given string
	 */
	protected static CollOper getSubCollectionForString(final String subcollectionString,
			final JsonObject jsonInput) {
		if (subcollectionString.contains(ConstantsEEModel.EIdxSeparatorInternal)) {
			// start end stride
			final int numberSeparators = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal).length - 1;
			if (numberSeparators == 1) {
				final String startString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[0];
				final String endString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[1];
				final int start = determineSubcollectionParam(startString, jsonInput);
				final int end = determineSubcollectionParam(endString, jsonInput);
				final int stride = CollOperSubCollection.defaultValue;
				return new CollOperSubCollection(start, end, stride);
			} else if (numberSeparators == 2) {
				final String startString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[0];
				final String endString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[1];
				final String strideString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[2];
				final int start = determineSubcollectionParam(startString, jsonInput);
				final int end = determineSubcollectionParam(endString, jsonInput);
				final int stride = determineSubcollectionParam(strideString, jsonInput);
				return new CollOperSubCollection(start, end, stride);
			} else {
				throw new IllegalArgumentException("Too many internal element index separators.");
			}
		} else {
			// index
			if (jsonInput.has(subcollectionString)) {
				// read from object
				return new CollOperIndex(jsonInput.get(subcollectionString).getAsInt());
			} else {
				return new CollOperIndex(readElemendIdxInt(subcollectionString));
			}
		}
	}

	/**
	 * Determines the int for the param of the SES subcollection.
	 * 
	 * @param loopParamString the string describing the param
	 * @param param           the param type
	 * @param idx             the substring index
	 * @param input           the json input
	 * @return
	 */
	protected static int determineSubcollectionParam(final String loopParamString, final JsonObject input) {
		String trimmed = loopParamString.trim();
		if (input.has(trimmed)) {
			return input.get(trimmed).getAsInt();
		} else if (trimmed.isEmpty()) {
			return CollOperSubCollection.defaultValue;
		} else {
			return readElemendIdxInt(trimmed);
		}
	}

	/**
	 * Interprets the given string as int.
	 * 
	 * @param intString the given string
	 * @return the given string as int
	 */
	protected static int readElemendIdxInt(final String intString) {
		try {
			return Integer.parseInt(intString);
		} catch (NumberFormatException exc) {
			throw new IllegalArgumentException("Incorrect num string for element idx: " + intString, exc);
		}
	}
}
