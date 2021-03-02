package at.uibk.dps.ee.enactables.local.utility.conditions;

import com.google.gson.JsonElement;

public class ConditionCheckerBoolean extends ConditionCheckerAbstract<Boolean> {

  @Override
  protected boolean equal(Boolean firstArgument, Boolean secondArgument) {
    return firstArgument.booleanValue() == secondArgument.booleanValue();
  }

  @Override
  protected boolean less(Boolean firstArgument, Boolean secondArgument) {
    throw new IllegalArgumentException("The operation less is not applicable for booleans.");
  }

  @Override
  protected boolean greater(Boolean firstArgument, Boolean secondArgument) {
    throw new IllegalArgumentException("The operation greater is not applicable for booleans.");
  }

  @Override
  protected boolean lessEqual(Boolean firstArgument, Boolean secondArgument) {
    throw new IllegalArgumentException("The operation lessEqual is not applicable for booleans.");
  }

  @Override
  protected boolean greaterEqual(Boolean firstArgument, Boolean secondArgument) {
    throw new IllegalArgumentException(
        "The operation greaterEqual is not applicable for booleans.");
  }

  @Override
  protected boolean contains(Boolean firstArgument, Boolean secondArgument) {
    throw new IllegalArgumentException("The operation contains is not applicable for booleans.");
  }

  @Override
  protected boolean startsWith(Boolean firstArgument, Boolean secondArgument) {
    throw new IllegalArgumentException("The operation startsWith is not applicable for booleans.");
  }

  @Override
  protected boolean endsWith(Boolean firstArgument, Boolean secondArgument) {
    throw new IllegalArgumentException("The operation endsWith is not applicable for booleans.");
  }

  @Override
  protected Boolean extractArgument(JsonElement element) {
    return Boolean.valueOf(element.getAsBoolean());
  }
}
