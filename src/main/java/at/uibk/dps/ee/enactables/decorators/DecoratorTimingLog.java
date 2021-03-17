package at.uibk.dps.ee.enactables.decorators;

import java.time.Duration;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.enactable.EnactmentFunctionDecorator;

/**
 * The {@link DecoratorTimingLog} is used to log the time necessary for the
 * execution of the function it decorates.
 * 
 * @author Fedor Smirnov
 */
public class DecoratorTimingLog extends EnactmentFunctionDecorator {

  protected final Logger timingLogger = LoggerFactory.getLogger(DecoratorTimingLog.class);
  protected Instant start;


  public DecoratorTimingLog(EnactmentFunction decoratedFunction) {
    super(decoratedFunction);
  }

  @Override
  protected JsonObject preprocess(JsonObject input) {
    start = Instant.now();
    return input;
  }

  @Override
  protected JsonObject postprocess(JsonObject result) {
    timingLogger.info("TYPE {} ID {} EXEC TIME {} milliseconds.", decoratedFunction.getType(),
        decoratedFunction.getId(), Duration.between(start, Instant.now()).toMillis());
    return result;
  }
}
