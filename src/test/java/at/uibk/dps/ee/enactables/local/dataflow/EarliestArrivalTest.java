package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;

public class EarliestArrivalTest {

  @Test
  public void test() {
    String content = "myInput";
    JsonObject input = new JsonObject();
    input.add(ConstantsEEModel.EarliestArrivalJsonKey, JsonParser.parseString(content));
    EarliestArrival tested = new EarliestArrival("id", "type");
    try {
      JsonObject result = tested.processInput(input);
      assertEquals(content, result.get(ConstantsEEModel.EarliestArrivalJsonKey).getAsString());
    } catch (StopException e) {
      fail();
    }
  }
}
