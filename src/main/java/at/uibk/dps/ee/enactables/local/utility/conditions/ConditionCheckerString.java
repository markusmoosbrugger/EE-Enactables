package at.uibk.dps.ee.enactables.local.utility.conditions;

import com.google.gson.JsonElement;

public class ConditionCheckerString extends ConditionCheckerAbstract<String> {

  @Override
  protected boolean equal(String firstArgument, String secondArgument) {
    return firstArgument.equals(secondArgument);
  }

  @Override
  protected boolean less(String firstArgument, String secondArgument) {
    throw new IllegalArgumentException("Less operation is not applicable to strings.");
  }

  @Override
  protected boolean greater(String firstArgument, String secondArgument) {
    throw new IllegalArgumentException("Greater operation is not applicable to strings.");
  }

  @Override
  protected boolean lessEqual(String firstArgument, String secondArgument) {
    throw new IllegalArgumentException("LessEqual operation is not applicable to strings.");
  }

  @Override
  protected boolean greaterEqual(String firstArgument, String secondArgument) {
    throw new IllegalArgumentException("GreaterEqual operation is not applicable to strings.");
  }

  @Override
  protected boolean contains(String firstArgument, String secondArgument) {
    return firstArgument.contains(secondArgument);
  }

  @Override
  protected boolean startsWith(String firstArgument, String secondArgument) {
    return firstArgument.startsWith(secondArgument);
  }

  @Override
  protected boolean endsWith(String firstArgument, String secondArgument) {
    return firstArgument.endsWith(secondArgument);
  }

  @Override
  protected String extractArgument(JsonElement element) {
    return element.getAsString();
  }
}
