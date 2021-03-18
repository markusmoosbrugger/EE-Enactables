package at.uibk.dps.ee.enactables.local.utility;

import java.util.List;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.enactables.local.utility.conditions.ConditionEvaluator;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.objects.Condition;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition;
import net.sf.opendse.model.Task;

/**
 * The {@link ConditionEvaluation} is an enactable used to process a set of
 * conditions and calculate a boolean result.
 * 
 * @author Fedor Smirnov
 *
 */
public class ConditionEvaluation extends LocalFunctionAbstract {

  protected final List<Condition> conditions;

  /**
   * Same constructor as the parent.
   * 
   * @param functionNode the node defining the condition evaluation.
   * @param idString the function id
   * @param type the function type
   */
  public ConditionEvaluation(final Task functionNode, final String idString, final String type) {
    super(idString, type);
    this.conditions = PropertyServiceFunctionUtilityCondition.getConditions(functionNode);
  }

  @Override
  public JsonObject processInput(final JsonObject input) throws StopException {
    final ConditionEvaluator evaluator = new ConditionEvaluator();
    final boolean result = evaluator.evaluate(conditions, input);
    final JsonObject jsonResult = new JsonObject();
    jsonResult.addProperty(ConstantsEEModel.JsonKeyIfDecision, result);
    return jsonResult;
  }
}
