package at.uibk.dps.ee.enactables.local.dataflow;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;

/**
 * The {@link Multiplexer} forwards one of two inputs based on a decision
 * variable.
 * 
 * @author Fedor Smirnov
 *
 */
public class Multiplexer extends LocalFunctionAbstract {

  @Override
  public JsonObject processInput(JsonObject input) throws StopException {
    // get the decision variable
    checkInputEntry(input, ConstantsEEModel.JsonKeyIfDecision);
    boolean decVar = input.get(ConstantsEEModel.JsonKeyIfDecision).getAsBoolean();
    // get the corresponding entry from the input
    String resultKey = decVar ? ConstantsEEModel.JsonKeyThen : ConstantsEEModel.JsonKeyElse;
    checkInputEntry(input, resultKey);
    JsonElement resultElement = input.get(resultKey);
    JsonObject result = new JsonObject();
    result.add(ConstantsEEModel.JsonKeyIfResult, resultElement);
    return result;
  }
}
