package at.uibk.dps.ee.enactables.local.utility.conditions;

import com.google.gson.JsonElement;

/**
 * The condition checker for JsonObject inputs.
 * 
 * @author Fedor Smirnov
 */
public class ConditionCheckerObject extends ConditionCheckerAbstract<JsonElement> {

  @Override
  protected boolean equal(final JsonElement firstArgument, final JsonElement secondArgument) {
    return firstArgument.equals(secondArgument);
  }

  @Override
  protected boolean less(final JsonElement firstArgument, final JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation less is not supported for objects.");
  }

  @Override
  protected boolean greater(final JsonElement firstArgument, final JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation greater is not supported for objects.");
  }

  @Override
  protected boolean lessEqual(final JsonElement firstArgument, final JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation lessEqual is not supported for objects.");
  }

  @Override
  protected boolean greaterEqual(final JsonElement firstArgument,
      final JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation greaterEqual is not supported for objects.");
  }

  @Override
  protected boolean contains(final JsonElement firstArgument, final JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation contains is not supported for objects.");
  }

  @Override
  protected boolean startsWith(final JsonElement firstArgument, final JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation startsWith is not supported for objects.");
  }

  @Override
  protected boolean endsWith(final JsonElement firstArgument, final JsonElement secondArgument) {
    throw new IllegalArgumentException("The operation endsWith is not supported for objects.");
  }

  @Override
  protected JsonElement extractArgument(final JsonElement element) {
    return element;
  }
}
