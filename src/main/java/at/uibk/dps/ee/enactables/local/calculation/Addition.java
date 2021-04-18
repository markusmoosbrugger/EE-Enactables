package at.uibk.dps.ee.enactables.local.calculation;

import java.util.HashSet;
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

  /**
   * Default constructor
   * 
   * @param idString the function id
   * @param type the function type
   */
  public Addition(final String idString, final String type) {
    super(idString, type, new HashSet<>());
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

