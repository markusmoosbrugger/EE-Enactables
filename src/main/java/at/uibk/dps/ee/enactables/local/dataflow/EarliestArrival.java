package at.uibk.dps.ee.enactables.local.dataflow;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;

/**
 * The {@link EarliestArrival} enactable simply forwards its input (which is set
 * by the earliest of several potential inputs) to the output.
 * 
 * @author Fedor Smirnov
 */
public class EarliestArrival extends LocalFunctionAbstract {

  @Override
  public JsonObject processInput(JsonObject input) throws StopException {
    final String key = ConstantsEEModel.EarliestArrivalJsonKey;
    // Get the input object
    checkInputEntry(input, key);
    final JsonElement element = input.get(key);
    // Put it into the output
    final JsonObject result = new JsonObject();
    result.add(key, element);
    return result;
  }
}
