package at.uibk.dps.ee.enactables.local.utility;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Used for the collection transformation which corresponds to the SPLIT
 * constraint of the AFCL language.
 * 
 * @author Fedor Smirnov
 */
public class CollOperSplit implements CollOper {

  protected final int splitNumber;

  /**
   * Standard constructor.
   * 
   * @param collOperString the string configuring the operation
   * @param jsonInput the json input (potentially containing the dynamic config
   *        parameters)
   */
  public CollOperSplit(final String collOperString, final JsonObject jsonInput) {
    this.splitNumber = getSplitNumber(collOperString, jsonInput);
  }
  
  /**
   * Constructor directly setting the split number.
   * 
   * @param splitNumber the desired number of sub array.
   */
  public CollOperSplit(final int splitNumber) {
    this.splitNumber = splitNumber;
  }

  @Override
  public JsonElement transformCollection(final JsonArray originalCollection) {
    final JsonArray result = new JsonArray();
    final int elementsPerResultEntry =
        (int) Math.round(Math.ceil(1. * originalCollection.size() / splitNumber));

    final List<JsonArray> resultEntries =
        Stream.generate(() -> new JsonArray()).limit(splitNumber).collect(Collectors.toList());
    for (int i = 0; i < splitNumber; i++) {
      final int start = i * elementsPerResultEntry;
      final int end = Math.min(originalCollection.size(), (i + 1) * elementsPerResultEntry);
      final JsonArray entry = resultEntries.get(i);
      for (int ii = start; ii < end; ii++) {
        entry.add(originalCollection.get(ii));
      }
      result.add(entry);
    }
    return result;
  }

  /**
   * Reads the replication number from the passed string (possibly using the json
   * input)
   * 
   * @param collOperString the string describing the replicate operation
   * @param jsonInput the json input of the enactable
   * @return the replication number
   */
  protected final int getSplitNumber(final String collOperString, final JsonObject jsonInput) {
    final int result = UtilsCollections.determineCollOperParam(collOperString, jsonInput);
    if (result < 2) {
      throw new IllegalArgumentException("The split number must be bigger than 1.");
    }
    return result;
  }
}
