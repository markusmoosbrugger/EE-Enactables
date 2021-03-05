package at.uibk.dps.ee.enactables.local.utility.conditions;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConditionCheckerBooleanTest {

  @Test
  public void testEqual() {
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    assertTrue(tested.equal(true, true));
    assertTrue(tested.equal(false, false));
    assertFalse(tested.equal(true, false));
    assertFalse(tested.equal(false, true));
  }

  @Test
  public void testExtractArgument() {
    JsonElement trueEl = new JsonPrimitive(true);
    JsonElement falseEl = new JsonPrimitive(false);
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    assertEquals(true, tested.extractArgument(trueEl));
    assertEquals(false, tested.extractArgument(falseEl));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLess() {
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    tested.less(true, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessEqual() {
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    tested.lessEqual(true, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreater() {
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    tested.greater(true, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreaterEqual() {
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    tested.greaterEqual(true, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testContains() {
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    tested.contains(true, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartsWith() {
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    tested.startsWith(true, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEndsWith() {
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    tested.endsWith(true, true);
  }
}
