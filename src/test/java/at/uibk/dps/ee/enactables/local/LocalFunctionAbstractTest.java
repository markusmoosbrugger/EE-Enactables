package at.uibk.dps.ee.enactables.local;

import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import org.junit.Test;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.core.exception.StopException;

public class LocalFunctionAbstractTest {

  protected static class FunctionMock extends LocalFunctionAbstract {

    @Override
    public JsonObject processInput(JsonObject input) throws StopException {
      return input;
    }

  }

  @Test
  public void testCheckInputEntryTest() {
    FunctionMock tested = new FunctionMock();
    try {
      tested.checkInputEntry(new JsonObject(), "key");
      fail();
    } catch (StopException e) {
    }
  }

  @Test
  public void testReadIntEntry() {
    FunctionMock tested = new FunctionMock();
    JsonObject input = new JsonObject();
    input.addProperty("key", 42);
    try {
      assertEquals(42, (long) tested.readIntInput(input, "key"));
    } catch (StopException e) {
      fail();
    }
  }

  @Test
  public void testReadEntry() {
    FunctionMock tested = new FunctionMock();
    JsonObject input = new JsonObject();
    JsonElement value = new JsonPrimitive("string");
    input.add("key", value);
    try {
      assertEquals(value, tested.readEntry(input, "key"));
    } catch (StopException e) {
      fail();
    }
  }

  @Test
  public void testWait() {
    FunctionMock tested = new FunctionMock();
    Instant before = Instant.now();
    tested.waitMilliseconds(250);
    Instant after = Instant.now();
    assertTrue(Duration.between(before, after).toMillis() >= 250);
  }

  @Test
  public void testReadCollectionInputCorrect() {
    FunctionMock tested = new FunctionMock();
    JsonArray array = new JsonArray();
    String key = "key";
    JsonObject input = new JsonObject();
    input.add(key, array);
    try {
      assertEquals(array, tested.readCollectionInput(input, key));
    } catch (StopException e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadCollectionInputIncorrect() {
    FunctionMock tested = new FunctionMock();
    JsonPrimitive primitive = new JsonPrimitive(42);
    String key = "key";
    JsonObject input = new JsonObject();
    input.add(key, primitive);
    try {
      assertEquals(primitive, tested.readCollectionInput(input, key));
    } catch (StopException e) {
      fail();
    }
  }
}
