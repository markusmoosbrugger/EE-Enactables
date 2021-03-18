package at.uibk.dps.ee.enactables.local.calculation;

import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;

/**
 * Simple substraction of 2 inputs with an option to wait for a given number of
 * milliseconds.
 * 
 * @author Fedor Smirnov
 *
 */
public class Subtraction extends LocalFunctionAbstract {

  /**
   * Default constructor
   * 
   * @param idString the function id
   * @param type the function type
   */
  public Subtraction(final String idString, final String type) {
    super(idString, type);
  }

  @Override
  public JsonObject processInput(final JsonObject input) throws StopException {
    final int minuend = readIntInput(input, ConstantsLocal.inputSubtractionMinuend);
    final int subtrahend = readIntInput(input, ConstantsLocal.inputSubtractionSubtrahend);
    final int waitTime = readIntInput(input, ConstantsLocal.inputWaitTime);
    final int difference = minuend - subtrahend;
    final JsonObject result = new JsonObject();
    result.addProperty(ConstantsLocal.outputSubstractionResult, difference);
    waitMilliseconds(waitTime);
    return result;
  }
}
