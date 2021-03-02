package at.uibk.dps.ee.enactables.local.utility.conditions;

import com.google.gson.JsonElement;

public class ConditionCheckerObject extends ConditionCheckerAbstract<JsonElement> {

  @Override
  protected boolean equal(JsonElement firstArgument, JsonElement secondArgument) {
    return firstArgument.equals(secondArgument);
  }

  @Override
  protected boolean less(JsonElement firstArgument, JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation less is not supported for objects.");
  }

  @Override
  protected boolean greater(JsonElement firstArgument, JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation greater is not supported for objects.");
  }

  @Override
  protected boolean lessEqual(JsonElement firstArgument, JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation lessEqual is not supported for objects.");
  }

  @Override
  protected boolean greaterEqual(JsonElement firstArgument, JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation greaterEqual is not supported for objects.");
  }

  @Override
  protected boolean contains(JsonElement firstArgument, JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation contains is not supported for objects.");
  }

  @Override
  protected boolean startsWith(JsonElement firstArgument, JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation startsWith is not supported for objects.");
  }

  @Override
  protected boolean endsWith(JsonElement firstArgument, JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation endsWith is not supported for objects.");
  }

  @Override
  protected JsonElement extractArgument(JsonElement element) {
    return element;
  }
}
