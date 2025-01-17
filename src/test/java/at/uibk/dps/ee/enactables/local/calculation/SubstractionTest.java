package at.uibk.dps.ee.enactables.local.calculation;

import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import org.junit.Test;

import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;

public class SubstractionTest {

  @Test
  public void test() {
    Subtraction tested = new Subtraction("id", "type");
    JsonObject input = new JsonObject();
    input.addProperty(ConstantsLocal.inputSubtractionMinuend, 6);
    input.addProperty(ConstantsLocal.inputSubtractionSubtrahend, 7);
    input.addProperty(ConstantsLocal.inputWaitTime, 150);

    Instant before = Instant.now();
    JsonObject result;
    try {
      result = tested.processInput(input);
      Instant after = Instant.now();
      assertEquals(-1, result.get(ConstantsLocal.outputSubstractionResult).getAsLong());
      assertTrue(Duration.between(before, after).toMillis() >= 150);
    } catch (StopException e) {
      fail();
    }
  }
}
