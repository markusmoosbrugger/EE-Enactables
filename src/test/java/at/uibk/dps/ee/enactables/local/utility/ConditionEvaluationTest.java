package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.objects.Condition;
import at.uibk.dps.ee.model.objects.Condition.Operator;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility.UtilityType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition.Summary;
import net.sf.opendse.model.Task;

public class ConditionEvaluationTest {

	protected static class ConditionEvalMock extends ConditionEvaluation {
		protected ConditionEvalMock(Set<EnactableStateListener> stateListeners, Set<String> inputKeys,
				Task functionNode, JsonObject input) {
			super(stateListeners, inputKeys, functionNode);
			jsonInput = input;
		}
	}

	@Test
	public void test() {

		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();
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

		Task funcNode = new Task("t");
		PropertyServiceFunction.setType(FunctionType.Utility, funcNode);
		PropertyServiceFunctionUtility.setUtilityType(funcNode, UtilityType.Condition);
		PropertyServiceFunctionUtilityCondition.setConditions(funcNode, conditions);
		PropertyServiceFunctionUtilityCondition.setSummary(funcNode, Summary.AND);

		ConditionEvaluation tested = new ConditionEvalMock(stateListeners, inputKeys, funcNode, input);
		String expectedKey = funcNode.getId() + ConstantsEEModel.DecisionVariableSuffix;

		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}
		boolean result = tested.getResult().get(expectedKey).getAsBoolean();
		assertFalse(result);

		funcNode = new Task("t");
		PropertyServiceFunction.setType(FunctionType.Utility, funcNode);
		PropertyServiceFunctionUtility.setUtilityType(funcNode, UtilityType.Condition);
		PropertyServiceFunctionUtilityCondition.setConditions(funcNode, conditions);
		PropertyServiceFunctionUtilityCondition.setSummary(funcNode, Summary.OR);

		tested = new ConditionEvalMock(stateListeners, inputKeys, funcNode, input);

		try {
			tested.atomicPlay();
		} catch (StopException e) {
			fail();
		}
		result = tested.getResult().get(expectedKey).getAsBoolean();
		assertTrue(result);
	}

}
