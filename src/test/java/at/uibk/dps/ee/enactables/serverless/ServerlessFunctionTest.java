package at.uibk.dps.ee.enactables.serverless;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.core.exception.StopException;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ServerlessFunctionTest {

  @Test
  public void test() {
    String inputKey = "inputKey";
    String outputKey = "outputKey";
    String inputString = "why are we here?";
    // set up the mock server
    JsonObject output = new JsonObject();
    output.add(outputKey, new JsonPrimitive(42));
    String url = "/address/function";
    try (MockWebServer serverMock = new MockWebServer()) {
      serverMock.enqueue(new MockResponse().setBody(output.toString()));
      serverMock.start();
      HttpUrl baseUrl = serverMock.url(url);
      String serverUrl = baseUrl.toString();
      ServerlessFunction tested = new ServerlessFunction(serverUrl);
      JsonObject input = new JsonObject();
      input.add(inputKey, new JsonPrimitive(inputString));
      try {
        JsonObject result = tested.processInput(input);
        assertEquals(output, result);
        RecordedRequest request = serverMock.takeRequest();
        assertEquals("[text=" + input.toString() + "]", request.getBody().toString());
      } catch (StopException e) {
        fail();
      } catch (InterruptedException e) {
        fail();
      }
    } catch (IOException exc) {
      fail();
    }
  }
}
