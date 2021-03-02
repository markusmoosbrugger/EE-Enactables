package at.uibk.dps.ee.enactables.local.utility.conditions;

import com.google.gson.JsonElement;
import at.uibk.dps.ee.enactables.local.utility.ConstantsUtility;

public class ConditionCheckerNumber extends ConditionCheckerAbstract<Number> {

  @Override
  protected boolean equal(Number firstArgument, Number secondArgument) {
    double first = firstArgument.doubleValue();
    double second = secondArgument.doubleValue();
    double differenceAbs = Math.abs(first - second);
    return differenceAbs < ConstantsUtility.epsilon;
  }

  @Override
  protected boolean less(Number firstArgument, Number secondArgument) {
    double first = firstArgument.doubleValue();
    double second = secondArgument.doubleValue();
    return !equal(firstArgument, secondArgument) && (first < second);
  }

  @Override
  protected boolean greater(Number firstArgument, Number secondArgument) {
    double first = firstArgument.doubleValue();
    double second = secondArgument.doubleValue();
    return !equal(firstArgument, secondArgument) && (first > second);
  }

  @Override
  protected boolean lessEqual(Number firstArgument, Number secondArgument) {
    double first = firstArgument.doubleValue();
    double second = secondArgument.doubleValue();
    return equal(firstArgument, secondArgument) || (first < second);
  }

  @Override
  protected boolean greaterEqual(Number firstArgument, Number secondArgument) {
    double first = firstArgument.doubleValue();
    double second = secondArgument.doubleValue();
    return equal(firstArgument, secondArgument) || (first > second);
  }

  @Override
  protected boolean contains(Number firstArgument, Number secondArgument) {
    throw new IllegalArgumentException("The operation contains is not applicable to numbers.");
  }

  @Override
  protected boolean startsWith(Number firstArgument, Number secondArgument) {
    throw new IllegalArgumentException("The operation startsWith is not applicable to numbers.");
  }

  @Override
  protected boolean endsWith(Number firstArgument, Number secondArgument) {
    throw new IllegalArgumentException("The operation endsWith is not applicable to numbers.");
  }

  @Override
  protected Number extractArgument(JsonElement element) {
    return element.getAsNumber();
  }
}
