package at.uibk.dps.ee.enactables.local.utility.conditions;

import com.google.gson.JsonElement;
import at.uibk.dps.ee.model.objects.Condition.Operator;

/**
 * Interface for the classes used to check a condition between two provided
 * {@link JsonElement}s.
 * 
 * @author Fedor Smirnov
 */
public interface ConditionChecker {

  /**
   * Checks the condition and returns the boolean result
   * 
   * @param firstElement the first input (JSON element)
   * @param secondElement the second input (JSON element)
   * @param operator the condition operator
   * @param negation true if the result is negated
   * @return the boolean result
   */
  boolean checkCondition(JsonElement firstElement, JsonElement secondElement, Operator operator,
      boolean negation);
}
