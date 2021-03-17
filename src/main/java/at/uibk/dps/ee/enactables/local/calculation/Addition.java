package at.uibk.dps.ee.enactables.local.calculation;

import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;

/**
 * Simple Addition of 2 inputs and an option to wait for a given number of
 * milliseconds.
 * 
 * @author Fedor Smirnov
 *
 */
public class Addition extends LocalFunctionAbstract {

  public Addition(String id, String type) {
    super(id, type);
  }

  @Override
  public JsonObject processInput(final JsonObject input) throws StopException {
    final int firstSummand = readIntInput(input, ConstantsLocal.inputAdditionFirst);
    final int secondSummand = readIntInput(input, ConstantsLocal.inputAdditionSecond);
    final int waitTime = readIntInput(input, ConstantsLocal.inputWaitTime);
    final int sum = firstSummand + secondSummand;
    final JsonObject result = new JsonObject();
    result.addProperty(ConstantsLocal.outputAdditionResult, sum);
    waitMilliseconds(waitTime);
    return result;
  }
}

