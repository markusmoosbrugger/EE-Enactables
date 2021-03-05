package at.uibk.dps.ee.enactables.local.utility.conditions;

import static org.junit.Assert.*;
import org.junit.Test;
import at.uibk.dps.ee.model.properties.PropertyServiceData.DataType;

public class ConditionEvaluatorTest {

  @Test
  public void test() {
    ConditionEvaluator tested = new ConditionEvaluator();
    assertTrue(tested.getConditionChecker(DataType.String) instanceof ConditionCheckerString);
    assertTrue(tested.getConditionChecker(DataType.Number) instanceof ConditionCheckerNumber);
    assertTrue(tested.getConditionChecker(DataType.Boolean) instanceof ConditionCheckerBoolean);
    assertTrue(tested.getConditionChecker(DataType.Object) instanceof ConditionCheckerObject);
    assertTrue(tested.getConditionChecker(DataType.String) instanceof ConditionCheckerString);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testExc() {
    ConditionEvaluator tested = new ConditionEvaluator();
    tested.getConditionChecker(DataType.Array);
  }
}
