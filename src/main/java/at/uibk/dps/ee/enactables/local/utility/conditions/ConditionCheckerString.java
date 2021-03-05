package at.uibk.dps.ee.enactables.local.utility.conditions;

import com.google.gson.JsonElement;

/**
 * The condition checker for string inputs.
 * 
 * @author Fedor Smirnov
 *
 */
public class ConditionCheckerString extends ConditionCheckerAbstract<String> {

  @Override
  protected boolean equal(final String firstArgument, final String secondArgument) {
    return firstArgument.equals(secondArgument);
  }

  @Override
  protected boolean less(final String firstArgument, final String secondArgument) {
    throw new IllegalArgumentException("Less operation is not applicable to strings.");
  }

  @Override
  protected boolean greater(final String firstArgument, final String secondArgument) {
    throw new IllegalArgumentException("Greater operation is not applicable to strings.");
  }

  @Override
  protected boolean lessEqual(final String firstArgument, final String secondArgument) {
    throw new IllegalArgumentException("LessEqual operation is not applicable to strings.");
  }

  @Override
  protected boolean greaterEqual(final String firstArgument, final String secondArgument) {
    throw new IllegalArgumentException("GreaterEqual operation is not applicable to strings.");
  }

  @Override
  protected boolean contains(final String firstArgument, final String secondArgument) {
    return firstArgument.contains(secondArgument);
  }

  @Override
  protected boolean startsWith(final String firstArgument, final String secondArgument) {
    return firstArgument.startsWith(secondArgument);
  }

  @Override
  protected boolean endsWith(final String firstArgument, final String secondArgument) {
    return firstArgument.endsWith(secondArgument);
  }

  @Override
  protected String extractArgument(final JsonElement element) {
    return element.getAsString();
  }
}
