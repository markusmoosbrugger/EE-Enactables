package at.uibk.dps.ee.enactables.decorators;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.exception.StopException;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

public class DecoratorTimingLogTest {

  protected static class MockFunction implements EnactmentFunction {

    protected final String id;
    protected final String type;
    protected final int timeMillisecs;

    public MockFunction(String id, String type, int timeMillisecs) {
      this.id = id;
      this.type = type;
      this.timeMillisecs = timeMillisecs;
    }

    @Override
    public JsonObject processInput(JsonObject input) throws StopException {
      try {
        Thread.sleep(timeMillisecs);
      } catch (InterruptedException e) {
        fail();
      }
      return input;
    }

    @Override
    public String getId() {
      return id;
    }

    @Override
    public String getType() {
      return type;
    }
  }

  @Test
  public void test() {

    String id = "id";
    String type = "type";
    int waitTime = 50;

    MockFunction original = new MockFunction(id, type, waitTime);
    DecoratorTimingLog tested = new DecoratorTimingLog(original);
    Logger logger = (Logger) tested.timingLogger;
    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    logger.addAppender(listAppender);

    String expectedStart = "TYPE " + type + " ID " + id + " EXEC TIME ";
    try {
      JsonObject input = new JsonObject();
      JsonObject result = tested.processInput(new JsonObject());
      assertEquals(input, result);
    } catch (StopException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    List<ILoggingEvent> logList = listAppender.list;
    assertEquals(1, logList.size());
    assertEquals(Level.INFO, logList.get(0).getLevel());
    assertTrue(logList.get(0).getFormattedMessage().startsWith(expectedStart));
  }
}
