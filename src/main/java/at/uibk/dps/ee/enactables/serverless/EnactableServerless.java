package at.uibk.dps.ee.enactables.serverless;

import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.exception.StopException;

/**
 * The {@link EnactableServerless} models the enactment of an atomic serverless
 * function.
 * 
 * @author Fedor Smirnov
 */
public class EnactableServerless implements EnactmentFunction {

  @Override
  public JsonObject processInput(JsonObject input) throws StopException {
    throw new IllegalStateException("Not yet implemented.");
  }
}
