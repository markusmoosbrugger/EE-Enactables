package at.uibk.dps.ee.enactables.local.utility.conditions;

import java.util.Iterator;
import java.util.List;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.model.objects.Condition;
import at.uibk.dps.ee.model.objects.Condition.CombinedWith;
import at.uibk.dps.ee.model.properties.PropertyServiceData.DataType;

/**
 * The {@link ConditionEvaluator} evaluates a set of conditions and calculates a
 * boolean result.
 * 
 * @author Fedor Smirnov
 */
public class ConditionEvaluator {

  /**
   * Evaluates the conditions and returns the result.
   * 
   * @return the result of the condition evaluation.
   */
  public boolean evaluate(List<Condition> conditions, JsonObject jsonInput) {
    Iterator<Condition> iterator = conditions.iterator();
    Condition first = iterator.next();
    boolean result = processCondition(first, jsonInput);
    boolean andCombination = first.getCombinedWith().equals(CombinedWith.And);
    while (iterator.hasNext()) {
      Condition next = iterator.next();
      boolean nextRes = processCondition(next, jsonInput);
      result = andCombination ? (result &= nextRes) : (result |= nextRes);
      andCombination = next.getCombinedWith().equals(CombinedWith.And);
    }
    return result;
  }

  /**
   * Calculates the result of the given condition.
   * 
   * @param condition the given condition.
   * @param jsonInput the json object containing the input
   * @return the result of the given condition
   */
  protected boolean processCondition(Condition condition, JsonObject jsonInput) {
    ConditionChecker checker = getConditionChecker(condition.getType());
    JsonElement firstElement = jsonInput.get(condition.getFirstInputId());
    JsonElement secondElement = jsonInput.get(condition.getSecondInputId());
    return checker.checkCondition(firstElement, secondElement, condition.getOperator(),
        condition.isNegation());
  }

  /**
   * Returns the appropriate condition checker for the requested type.
   * 
   * @param type
   * @return
   */
  protected ConditionChecker getConditionChecker(DataType type) {
    switch (type) {
      case String:
        return new ConditionCheckerString();
      case Number:
        return new ConditionCheckerNumber();
      case Boolean:
        return new ConditionCheckerBoolean();
      case Object:
        return new ConditionCheckerObject();
      default:
        throw new IllegalArgumentException(
            "Conditions defined for the type " + type.name() + " are not supported.");
    }
  }
}
