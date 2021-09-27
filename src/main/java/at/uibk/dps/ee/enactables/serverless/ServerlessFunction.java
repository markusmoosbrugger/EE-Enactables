package at.uibk.dps.ee.enactables.serverless;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.EnactmentMode;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * The {@link ServerlessFunction} models the enactment of an atomic serverless function.
 *
 * @author Fedor Smirnov
 */
public class ServerlessFunction implements EnactmentFunction {

  protected final String typeId;
  protected final String implementationId;
  protected final Set<SimpleEntry<String, String>> additionalAttributes;

  protected final String url;
  protected final WebClient client;
  protected JsonObject resultJson;

  protected final Logger logger = LoggerFactory.getLogger(ServerlessFunction.class);

  /**
   * Default constructor.
   *
   * @param serverlessMapping the mapping from task to resource
   * @param client            the http client used for the serverless requests
   */
  public ServerlessFunction(final Mapping<Task, Resource> serverlessMapping,
      final WebClient client) {
    final Task task = serverlessMapping.getSource();
    final Resource res = serverlessMapping.getTarget();
    this.typeId = PropertyServiceFunctionUser.getTypeId(task);
    this.implementationId = PropertyServiceMapping.getImplementationId(serverlessMapping);
    this.additionalAttributes = new HashSet<>();
    this.url = PropertyServiceResourceServerless.getUri(res);
    additionalAttributes.add(
        new SimpleEntry<String, String>(ConstantsServerless.logAttrSlUrl, url));
    this.client = client;
  }

  @Override
  public JsonObject processInput(final JsonObject input) throws StopException {
    return enactServerlessFunction(url, input);
  }



  /**
   * Enacts the serverless function located at the provided url with the provided jsonobject as
   * input. Returns the {@link JsonObject} produced by the resource.
   *
   * @param url the url of the function
   * @return the {@link JsonObject} containing the result of the enactment
   */
  protected JsonObject enactServerlessFunction(final String url, final JsonObject input) {
    CountDownLatch latch = new CountDownLatch(1);
    final Future<HttpResponse<Buffer>> futureResponse =
        client.postAbs(url).sendJson(new io.vertx.core.json.JsonObject(input.toString()));
    logger.info("Serverless function {} triggerred.", url);
    futureResponse.onComplete(asyncRes -> {
      if (asyncRes.succeeded()) {
        logger.info("Serverless function {} finished", url);
        resultJson = JsonParser.parseString(asyncRes.result().body().toString()).getAsJsonObject();
      } else {
        asyncRes.cause();
      }
      latch.countDown();
    });

    // waiting for the call back to complete:
    try {
      latch.await();
      if (resultJson != null) {
        return resultJson; // will have the result here when get back
      } else {
        throw new IllegalStateException("Should never get here");
      }
    } catch (InterruptedException e) {
      throw new IllegalStateException("Interrupted while waiting for completion of ...", e);
    }

  }

  @Override
  public String getTypeId() {
    return typeId;
  }

  @Override
  public String getEnactmentMode() {
    return EnactmentMode.Serverless.name();
  }

  @Override
  public String getImplementationId() {
    return implementationId;
  }

  @Override
  public Set<SimpleEntry<String, String>> getAdditionalAttributes() {
    return additionalAttributes;
  }
}
