package at.uibk.dps.ee.enactables.local.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * The {@link CollOperIndex} reads the entry from a single index of the original
 * collection.
 * 
 * @author Fedor Smirnov
 *
 */
public class CollOperIndex implements CollOper {

  protected final int index;

  /**
   * Constructor provided with the index of the element which is to be extracted.
   * 
   * @param index the index
   */
  public CollOperIndex(final int index) {
    this.index = index;
  }

  @Override
  public String toString() {
    return String.valueOf(index);
  }

  @Override
  public JsonElement transformCollection(final JsonArray originalCollection) {
    return originalCollection.get(index);
  }
}
