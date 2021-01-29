package at.uibk.dps.ee.enactables.local.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Used for the collection transformation which corresponds to the REPLICATE
 * constraint of the AFCL language.
 * 
 * @author Fedor Smirnov
 *
 */
public class CollOperReplicate implements CollOper {

	protected final int replicationNumber;

	/**
	 * Standard constructor
	 * 
	 * @param collOperString the string configuring the operation
	 * @param jsonInput      json input (containing the dynamic config parameters)
	 */
	public CollOperReplicate(final String collOperString, final JsonObject jsonInput) {
		this.replicationNumber = getReplicationNumber(collOperString, jsonInput);
	}

	@Override
	public JsonElement transformCollection(final JsonArray originalCollection) {
		final JsonArray result = new JsonArray();
		for (final JsonElement entry : originalCollection) {
			for (int i = 0; i < replicationNumber; i++) {
				result.add(entry);
			}
		}
		return result;
	}

	/**
	 * Reads the replication number from the passed string (possibly using the json
	 * input)
	 * 
	 * @param collOperString the string describing the replicate operation
	 * @param jsonInput      the json input of the enactable
	 * @return the replication number
	 */
	protected final int getReplicationNumber(final String collOperString, final JsonObject jsonInput) {
		final int result = UtilsCollections.determineCollOperParam(collOperString, jsonInput);
		if (result < 2) {
			throw new IllegalArgumentException("The replication number must be bigger than 1.");
		}
		return result;
	}
}
