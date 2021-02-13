package at.uibk.dps.ee.enactables.local.calculation;

import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import org.junit.Test;

import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;

public class AdditionTest {

  @Test
  public void test() {

    Addition tested = new Addition();
    JsonObject input = new JsonObject();
    input.addProperty(ConstantsLocal.inputAdditionFirst, 6);
    input.addProperty(ConstantsLocal.inputAdditionSecond, 7);
    input.addProperty(ConstantsLocal.inputWaitTime, 150);

    Instant before = Instant.now();
    JsonObject result;
    try {
      result = tested.processInput(input);
      Instant after = Instant.now();
      assertEquals(13, result.get(ConstantsLocal.outputAdditionResult).getAsLong());
      assertTrue(Duration.between(before, after).toMillis() >= 150);
    } catch (StopException e) {
      fail();
    }
  }
}
