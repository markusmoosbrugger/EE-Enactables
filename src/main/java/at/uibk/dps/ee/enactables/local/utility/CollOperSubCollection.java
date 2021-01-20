package at.uibk.dps.ee.enactables.local.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import at.uibk.dps.ee.model.constants.ConstantsEEModel;

/**
 * The {@link CollOperSubCollection} reads a sub collection from the
 * original collection.
 * 
 * @author Fedor Smirnov
 *
 */
public class CollOperSubCollection implements CollOper {

	protected static final int defaultValue = -1;

	protected final int start;
	protected final int end;
	protected final int stride;

	/**
	 * Sets the start, the end and the stride. Called with -1 to assign the default
	 * value.
	 * 
	 * @param start  start (defaults to 0)
	 * @param end    end (defaults to the size of the collection)
	 * @param stride (defaults to 1)
	 */
	public CollOperSubCollection(final int start, final int end, final int stride) {
		this.start = start;
		this.end = end;
		this.stride = stride;
	}

	@Override
	public JsonElement getSubCollection(final JsonArray originalCollection) {
		final JsonArray result = new JsonArray();
		final int actualStart = start == defaultValue ? 0 : start;
		final int actualEnd = end == defaultValue ? originalCollection.size() - 1 : end;
		final int actualStride = stride == defaultValue ? 1 : stride;

		for (int i = actualStart; i <= actualEnd; i += actualStride) {
			final JsonElement entry = originalCollection.get(i);
			result.add(entry);
		}
		return result;
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		final String startString = start == defaultValue ? "" : String.valueOf(start);
		final String endString = end == defaultValue ? "" : String.valueOf(end);
		final String strideString = stride == defaultValue ? "" : String.valueOf(stride);
		buffer.append(startString).append(ConstantsEEModel.EIdxSeparatorInternal).append(endString)
				.append(ConstantsEEModel.EIdxSeparatorInternal).append(strideString);
		return buffer.toString();
	}
}
