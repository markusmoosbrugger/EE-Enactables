package at.uibk.dps.ee.enactables.local.calculation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;

/**
 * Processes a number collection by adding up all the numbers.
 * 
 * @author Fedor Smirnov
 */
public class SumCollection extends LocalFunctionAbstract {

  @Override
  public JsonObject processInput(final JsonObject input) throws StopException {
    final JsonArray jsonArray = readCollectionInput(input, ConstantsLocal.inputSumCollection);
    final int waitTime = readIntInput(input, ConstantsLocal.inputWaitTime);
    int result = 0;
    for (final JsonElement jsonElement : jsonArray) {
      result += jsonElement.getAsInt();
    }
    final JsonObject jsonResult = new JsonObject();
    jsonResult.add(ConstantsLocal.outputSumCollection, new JsonPrimitive(result));
    waitMilliseconds(waitTime);
    return jsonResult;
  }
}
