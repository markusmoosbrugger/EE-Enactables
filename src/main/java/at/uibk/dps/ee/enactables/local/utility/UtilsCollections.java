package at.uibk.dps.ee.enactables.local.utility;

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
	 * Uses the descriptive string and the json input to generate the subcollections
	 * object.
	 * 
	 * @param inputString the string decribing the subcollections structure and
	 *                    static parts
	 * @param jsonInput   the json input (to get the dynamic parts)
	 * @return the subcollections object
	 */
	public static SubCollections readSubCollections(final String inputString, final JsonObject jsonInput) {
		// remove the white spaces
		final String stringNoWs = inputString.trim();
		final SubCollections result = new SubCollections();
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
	protected static SubCollection getSubCollectionForString(final String subcollectionString,
			final JsonObject jsonInput) {
		if (subcollectionString.contains(ConstantsEEModel.EIdxSeparatorInternal)) {
			// start end stride
			final int numberSeparators = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal).length - 1;
			if (numberSeparators == 1) {
				final String startString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[0];
				final String endString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[1];
				final int start = determineLoopParam(startString, jsonInput);
				final int end = determineLoopParam(endString, jsonInput);
				final int stride = SubCollectionStartEndStride.defaultValue;
				return new SubCollectionStartEndStride(start, end, stride);
			} else if (numberSeparators == 2) {
				final String startString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[0];
				final String endString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[1];
				final String strideString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[2];
				final int start = determineLoopParam(startString, jsonInput);
				final int end = determineLoopParam(endString, jsonInput);
				final int stride = determineLoopParam(strideString, jsonInput);
				return new SubCollectionStartEndStride(start, end, stride);
			} else {
				throw new IllegalArgumentException("Too many internal element index separators.");
			}
		} else {
			// index
			if (jsonInput.has(subcollectionString)) {
				// read from object
				return new SubCollectionElement(jsonInput.get(subcollectionString).getAsInt());
			} else {
				return new SubCollectionElement(readElemendIdxInt(subcollectionString));
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
	protected static int determineLoopParam(final String loopParamString, final JsonObject input) {
		String trimmed = loopParamString.trim();
		if (input.has(trimmed)) {
			return input.get(trimmed).getAsInt();
		} else if (trimmed.isEmpty()) {
			return SubCollectionStartEndStride.defaultValue;
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
