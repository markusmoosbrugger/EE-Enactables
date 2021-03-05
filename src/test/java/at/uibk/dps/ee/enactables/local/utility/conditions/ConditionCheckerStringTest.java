package at.uibk.dps.ee.enactables.local.utility.conditions;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConditionCheckerStringTest {

  @Test
  public void testExtractArgument() {
    String first = "abc";
    String second = "hello";
    JsonElement firstEl = new JsonPrimitive(first);
    JsonElement secondEl = new JsonPrimitive(second);
    ConditionCheckerString tested = new ConditionCheckerString();
    assertEquals(first, tested.extractArgument(firstEl));
    assertEquals(second, tested.extractArgument(secondEl));
  }

  @Test
  public void testCorrect() {
    ConditionCheckerString tested = new ConditionCheckerString();
    assertTrue(tested.equal("abc", "abc"));
    assertFalse(tested.equal("abc", "abd"));
    assertTrue(tested.contains("abcd", "bc"));
    assertFalse(tested.contains("abcd", "bd"));
    assertTrue(tested.startsWith("abcd", "ab"));
    assertFalse(tested.startsWith("abcd", "bb"));
    assertTrue(tested.endsWith("abcd", "cd"));
    assertFalse(tested.endsWith("abcd", "ab"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLess() {
    ConditionCheckerString tested = new ConditionCheckerString();
    tested.less("abc", "ABC");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessEqual() {
    ConditionCheckerString tested = new ConditionCheckerString();
    tested.lessEqual("abc", "ABC");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreater() {
    ConditionCheckerString tested = new ConditionCheckerString();
    tested.greater("abc", "ABC");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreaterEqual() {
    ConditionCheckerString tested = new ConditionCheckerString();
    tested.greaterEqual("abc", "ABC");
  }
}
