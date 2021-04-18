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

  /**
   * Default constructor.
   * 
   * @param decoratedFunction the function whose execution time is to be measured.
   */
  public DecoratorTimingLog(final EnactmentFunction decoratedFunction) {
    super(decoratedFunction);
  }

  @Override
  protected JsonObject preprocess(final JsonObject input) {
    start = Instant.now();
    return input;
  }

  @Override
  protected JsonObject postprocess(final JsonObject result) {
    StringBuffer attrBuffer = new StringBuffer();
    decoratedFunction.getAdditionalAttributes().forEach(
        attr -> attrBuffer.append(attr.getKey()).append(':').append(attr.getValue()).append('\n'));
    timingLogger.info(
        "TYPE ID {} EnactmentMode {} Implementation ID {} Additional Attributes {} EXEC TIME {} milliseconds.",
        decoratedFunction.getTypeId(), decoratedFunction.getEnactmentMode(),
        decoratedFunction.getImplementationId(), attrBuffer.toString(),
        Duration.between(start, Instant.now()).toMillis());
    return result;
  }
}
