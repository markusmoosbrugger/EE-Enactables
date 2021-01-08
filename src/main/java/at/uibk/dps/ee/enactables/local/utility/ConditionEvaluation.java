package at.uibk.dps.ee.enactables.local.utility;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalAbstract;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.objects.Condition;
import at.uibk.dps.ee.model.objects.Condition.Operator;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition.Summary;
import net.sf.opendse.model.Task;

/**
 * The {@link ConditionEvaluation} is an enactable used to process a set of
 * conditions and calculate a boolean result.
 * 
 * @author Fedor Smirnov
 *
 */
public class ConditionEvaluation extends LocalAbstract {

	protected final Set<Condition> conditions;
	protected final Summary summary;
	protected final String resultJsonKey;

	/**
	 * Same constructor as the parent.
	 * 
	 * @param stateListeners
	 * @param inputMap
	 * @param functionNode
	 */
	protected ConditionEvaluation(final Set<EnactableStateListener> stateListeners,
			final Map<String, JsonElement> inputMap, final Task functionNode) {
		super(stateListeners, inputMap, functionNode);
		this.conditions = PropertyServiceFunctionUtilityCondition.getConditions(functionNode);
		this.summary = PropertyServiceFunctionUtilityCondition.getSummary(functionNode);
		this.resultJsonKey = functionNode.getId() + ConstantsEEModel.DecisionVariableSuffix;
	}

	@Override
	protected void atomicPlay() throws StopException {
		boolean result = summary.equals(Summary.AND); // true is neutral for the AND
		// iterate the conditions and process each of them
		for (Condition condition : conditions) {
			boolean conditionResult = evaluateCondition(condition);
			if (summary.equals(Summary.AND)) {
				// summary is AND
				result &= conditionResult;
			} else {
				// summary is OR
				result |= conditionResult;
			}
		}
		JsonObject jsonResult = new JsonObject();
		jsonResult.addProperty(resultJsonKey, result);
		this.jsonResult = jsonResult;
	}

	/**
	 * Evaluates the provided condition and returs its result.
	 * 
	 * @param condition the provided condition
	 * @return the result of the condition evaluation
	 */
	protected boolean evaluateCondition(final Condition condition) throws StopException {
		final Operator operator = condition.getOperator();
		// get the inputs
		final JsonElement inputOne = readEntry(condition.getFirstInputId());
		final JsonElement inputTwo = readEntry(condition.getSecondInputId());
		final boolean result = UtilsConditions.evaluate(inputOne, inputTwo, operator);
		return condition.isNegation() ? !result : result;
	}

	@Override
	protected void myPause() {
		// Nothing here
	}
}
