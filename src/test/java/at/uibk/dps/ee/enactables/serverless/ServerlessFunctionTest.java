package at.uibk.dps.ee.enactables.serverless;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping.EnactmentMode;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
      Resource serverless =
          PropertyServiceResourceServerless.createServerlessResource("resId", serverUrl);
      Task task = PropertyServiceFunctionUser.createUserTask("task", "addition");
      Mapping<Task, Resource> mapping =
          PropertyServiceMapping.createMapping(task, serverless, EnactmentMode.Serverless,
              serverUrl);

      // build the web client
      final Vertx vertx = Vertx.vertx();
      ServerlessFunction tested = new ServerlessFunction(mapping, WebClient.create(vertx));
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
