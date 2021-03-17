package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;

public class MultiplexerTest {

  @Test
  public void test() {
    Multiplexer tested = new Multiplexer("id", "type");
    JsonObject inputTrue = new JsonObject();
    inputTrue.add(ConstantsEEModel.JsonKeyIfDecision, new JsonPrimitive(true));
    inputTrue.add(ConstantsEEModel.JsonKeyThen, new JsonPrimitive(42));
    try {
      JsonObject result = tested.processInput(inputTrue);
      assertEquals(42, result.get(ConstantsEEModel.JsonKeyIfResult).getAsInt());
    } catch (StopException e) {
      fail();
    }
    JsonObject inputFalse = new JsonObject();
    inputFalse.add(ConstantsEEModel.JsonKeyIfDecision, new JsonPrimitive(false));
    inputFalse.add(ConstantsEEModel.JsonKeyElse, new JsonPrimitive(false));
    try {
      JsonObject result = tested.processInput(inputFalse);
      assertFalse(result.get(ConstantsEEModel.JsonKeyIfResult).getAsBoolean());
    } catch (StopException e) {
      fail();
    }
  }
}
