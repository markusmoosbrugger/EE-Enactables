package at.uibk.dps.ee.enactables.local.utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.constants.ConstantsEEModel.EIdxParameters;

public class UtilsCollections {

	/**
	 * Uses the descriptive string and the json input to generate the subcollections
	 * object.
	 * 
	 * @param subCollString the string decribing the subcollections structure and
	 *                      static parts
	 * @param jsonInput     the json input (to get the dynamic parts)
	 * @return the subcollections object
	 */
	public static SubCollections readSubCollections(String subCollString, JsonObject jsonInput) {
		subCollString = subCollString.replaceAll("\\s+", "");
		SubCollections result = new SubCollections();
		if (subCollString.contains(ConstantsEEModel.EIdxSeparatorExternal)) {
			// multiple comma-separated values
			String[] subStrings = subCollString.split(ConstantsEEModel.EIdxSeparatorExternal);
			for (int idx = 0; idx < subStrings.length; idx++) {
				String subString = subStrings[idx];
				result.add(getSubCollectionForString(subString, jsonInput, idx));
			}
		} else {
			// only one number
			result.add(getSubCollectionForString(subCollString, jsonInput, 0));
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
	protected static SubCollection getSubCollectionForString(String subcollectionString, JsonObject jsonInput,
			int idx) {
		if (subcollectionString.contains(ConstantsEEModel.EIdxSeparatorInternal)) {
			// start end stride
			int numberSeparators = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal).length - 1;
			if (numberSeparators == 1) {
				String startString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[0];
				String endString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[1];
				int start = determineLoopParam(startString, EIdxParameters.Start, idx, jsonInput);
				int end = determineLoopParam(endString, EIdxParameters.End, idx, jsonInput);
				int stride = SubCollectionStartEndStride.defaultValue;
				return new SubCollectionStartEndStride(start, end, stride);
			} else if (numberSeparators == 2) {
				String startString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[0];
				String endString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[1];
				String strideString = subcollectionString.split(ConstantsEEModel.EIdxSeparatorInternal)[2];
				int start = determineLoopParam(startString, EIdxParameters.Start, idx, jsonInput);
				int end = determineLoopParam(endString, EIdxParameters.End, idx, jsonInput);
				int stride = determineLoopParam(strideString, EIdxParameters.Stride, idx, jsonInput);
				return new SubCollectionStartEndStride(start, end, stride);
			} else {
				throw new IllegalArgumentException("Too many internal element index separators.");
			}
		} else {
			// index
			int indexEntry = -1;
			if (subcollectionString.equals(ConstantsEEModel.EIdxDataKeyWord)) {
				// read from object
				indexEntry = readDataFromInput(jsonInput, EIdxParameters.Index, idx);
			} else {
				indexEntry = readElemendIdxInt(subcollectionString);
			}
			return new SubCollectionElement(indexEntry);
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
	protected static int determineLoopParam(String loopParamString, EIdxParameters param, int idx, JsonObject input) {
		if (loopParamString.equals(ConstantsEEModel.EIdxDataKeyWord)) {
			return readDataFromInput(input, param, idx);
		} else if (loopParamString.isEmpty()) {
			return SubCollectionStartEndStride.defaultValue;
		} else {
			return readElemendIdxInt(loopParamString);
		}
	}

	/**
	 * Reads the necessary data from the json input
	 * 
	 * @param input  the json input
	 * @param params the enum describing the data role
	 * @param idx    the index of the substring
	 * @return the int value read from the input
	 */
	protected static int readDataFromInput(JsonObject input, ConstantsEEModel.EIdxParameters params, int idx) {
		String jsonKey = params.name() + ConstantsEEModel.EIdxEdgeIdxSeparator + idx;
		JsonElement element = input.get(jsonKey);
		return element.getAsInt();
	}

	/**
	 * Interprets the given string as int.
	 * 
	 * @param intString the given string
	 * @return the given string as int
	 */
	protected static int readElemendIdxInt(String intString) {
		try {
			return Integer.parseInt(intString);
		} catch (NumberFormatException exc) {
			throw new IllegalArgumentException("Incorrect num string for element idx: " + intString);
		}
	}

}
