package at.uibk.dps.ee.enactables.local.calculation;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;

public class SplitArrayTest {

  @Test
  public void test() {

    JsonArray array = new JsonArray();
    array.add(1);
    array.add(2);
    array.add(3);
    array.add(4);
    JsonObject input = new JsonObject();
    input.add(ConstantsLocal.inputSplitArrayArray, array);
    input.add(ConstantsLocal.inputSplitArrayNumber, new JsonPrimitive(2));

    SplitArray tested = new SplitArray();
    try {
      JsonObject result = tested.processInput(input);
      assertTrue(result.get(ConstantsLocal.outputSplitArray).isJsonArray());
      JsonArray resultArray = result.get(ConstantsLocal.outputSplitArray).getAsJsonArray();
      assertEquals(2, resultArray.size());
      JsonElement first = resultArray.get(0);
      JsonElement second = resultArray.get(1);
      assertEquals(1, first.getAsJsonArray().get(0).getAsNumber());
      assertEquals(2, first.getAsJsonArray().get(1).getAsNumber());
      assertEquals(3, second.getAsJsonArray().get(0).getAsNumber());
      assertEquals(4, second.getAsJsonArray().get(1).getAsNumber());
    } catch (StopException e) {
      fail();
    }
  }
}
