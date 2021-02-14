package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.objects.Condition;
import at.uibk.dps.ee.model.objects.Condition.Operator;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition.Summary;
import net.sf.opendse.model.Task;

public class ConditionEvaluationTest {

  @Test
  public void test() {
    JsonElement first = JsonParser.parseString("0.001");
    JsonElement second = JsonParser.parseString("0.001000000001");
    JsonObject input = new JsonObject();
    input.add("first", first);
    input.add("second", second);

    Condition condition1 = new Condition("first", "second", Operator.EQUAL, true);
    Condition condition2 = new Condition("first", "second", Operator.EQUAL, false);
    Condition condition3 = new Condition("first", "second", Operator.LESS_EQUAL, true);
    Condition condition4 = new Condition("first", "second", Operator.GREATER_EQUAL, false);
    Set<Condition> conditions = new HashSet<>();
    conditions.add(condition1);
    conditions.add(condition2);
    conditions.add(condition3);
    conditions.add(condition4);

    Task funcNode = PropertyServiceFunctionUtilityCondition.createConditionEvaluation("task",
        conditions, Summary.AND);
    ConditionEvaluation tested = new ConditionEvaluation(funcNode);
    String expectedKey = funcNode.getId() + ConstantsEEModel.DecisionVariableSuffix;

    try {
      JsonObject jsonResult = tested.processInput(input);
      boolean result = jsonResult.get(expectedKey).getAsBoolean();
      assertFalse(result);
    } catch (StopException e) {
      fail();
    }

    funcNode = PropertyServiceFunctionUtilityCondition.createConditionEvaluation("task", conditions,
        Summary.OR);
    tested = new ConditionEvaluation(funcNode);
    try {
      JsonObject jsonResult = tested.processInput(input);
      boolean result = jsonResult.get(expectedKey).getAsBoolean();
      assertTrue(result);
    } catch (StopException e) {
      fail();
    }
  }
}
