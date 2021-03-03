package at.uibk.dps.ee.enactables.local.utility.conditions;

import com.google.gson.JsonElement;
import at.uibk.dps.ee.model.objects.Condition.Operator;

/**
 * Parent class for the classes evaluating the individual conditions.
 * 
 * @author Fedor Smirnov
 *
 * @param <T> the data type processed by the condition checker
 */
public abstract class ConditionCheckerAbstract<T> implements ConditionChecker {

  @Override
  public boolean checkCondition(JsonElement firstElement, JsonElement secondElement,
      Operator operator, boolean negation) {
    T firstArgument = extractArgument(firstElement);
    T secondArgument = extractArgument(secondElement);
    boolean result = getPreNegationResult(firstArgument, secondArgument, operator);
    return negation ? !result : result;
  }

  /**
   * Calculates the result by applying the given operator to the provided
   * arguments.
   * 
   * @param firstArgument the first argument
   * @param secondArgument the second argument
   * @param operator the provided operator
   * @return the result obtained by applying the given operator to the provided
   *         arguments
   */
  protected boolean getPreNegationResult(T firstArgument, T secondArgument, Operator operator) {
    switch (operator) {
      case EQUAL:
        return equal(firstArgument, secondArgument);

      case LESS:
        return less(firstArgument, secondArgument);

      case GREATER:
        return greater(firstArgument, secondArgument);

      case LESS_EQUAL:
        return lessEqual(firstArgument, secondArgument);

      case GREATER_EQUAL:
        return greaterEqual(firstArgument, secondArgument);

      case CONTAINS:
        return contains(firstArgument, secondArgument);

      case STARTS_WITH:
        return startsWith(firstArgument, secondArgument);

      case ENDS_WITH:
        return endsWith(firstArgument, secondArgument);

      default:
        throw new IllegalArgumentException("Unknown condition operator: " + operator.name());
    }
  }

  /**
   * The Equals operation
   * 
   * @param firstArgument the first argument
   * @param secondArgument the second argument
   * @return the operation result
   */
  protected abstract boolean equal(T firstArgument, T secondArgument);

  /**
   * The Less operation
   * 
   * @param firstArgument the first argument
   * @param secondArgument the second argument
   * @return the operation result
   */
  protected abstract boolean less(T firstArgument, T secondArgument);

  /**
   * The Greater operation
   * 
   * @param firstArgument the first argument
   * @param secondArgument the second argument
   * @return the operation result
   */
  protected abstract boolean greater(T firstArgument, T secondArgument);

  /**
   * The lessEqual operation
   * 
   * @param firstArgument the first argument
   * @param secondArgument the second argument
   * @return the operation result
   */
  protected abstract boolean lessEqual(T firstArgument, T secondArgument);

  /**
   * The greaterEqual operation
   * 
   * @param firstArgument the first argument
   * @param secondArgument the second argument
   * @return the operation result
   */
  protected abstract boolean greaterEqual(T firstArgument, T secondArgument);

  /**
   * The Contains operation
   * 
   * @param firstArgument the first argument
   * @param secondArgument the second argument
   * @return the operation result
   */
  protected abstract boolean contains(T firstArgument, T secondArgument);

  /**
   * The startsWith operation
   * 
   * @param firstArgument the first argument
   * @param secondArgument the second argument
   * @return the operation result
   */
  protected abstract boolean startsWith(T firstArgument, T secondArgument);

  /**
   * The endsWith operation
   * 
   * @param firstArgument the first argument
   * @param secondArgument the second argument
   * @return the operation result
   */
  protected abstract boolean endsWith(T firstArgument, T secondArgument);

  /**
   * Reads the given json element and extracts an object of the type processed by
   * the condition checker.
   * 
   * @param element the given json element
   * @return an object of the type processed by the condition checker
   */
  protected abstract T extractArgument(JsonElement element);
}
