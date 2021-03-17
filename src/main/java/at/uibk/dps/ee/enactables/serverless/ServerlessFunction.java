package at.uibk.dps.ee.enactables.serverless;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.FunctionTypes;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import net.sf.opendse.model.Resource;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The {@link ServerlessFunction} models the enactment of an atomic serverless
 * function.
 * 
 * @author Fedor Smirnov
 */
public class ServerlessFunction implements EnactmentFunction {

  protected final String url;
  protected final OkHttpClient client;

  /**
   * Default constructor.
   * 
   * @param url the url to access the serverless function
   */
  public ServerlessFunction(final Resource serverlessFunction) {
    this.url = PropertyServiceResourceServerless.getUri(serverlessFunction);
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.connectTimeout(
        PropertyServiceResourceServerless.getTimeoutInSeconds(serverlessFunction),
        TimeUnit.SECONDS);
    builder.readTimeout(ConstantsServerless.readWriteTimeoutSeconds, TimeUnit.SECONDS);
    builder.writeTimeout(ConstantsServerless.readWriteTimeoutSeconds, TimeUnit.SECONDS);
    client = builder.build();
  }

  @Override
  public JsonObject processInput(final JsonObject input) throws StopException {
    return enactServerlessFunction(url, input);
  }

  @Override
  public String getId() {
    return url;
  }

  @Override
  public String getType() {
    return FunctionTypes.Serverless.name();
  }

  /**
   * Enacts the serverless function located at the provided url with the provided
   * jsonobject as input. Returns the {@link JsonObject} produced by the resource.
   * 
   * @param url the url of the function
   * @return the {@link JsonObject} containing the result of the enactment
   */
  protected JsonObject enactServerlessFunction(final String url, final JsonObject input) {
    final RequestBody body =
        RequestBody.create(new Gson().toJson(input), ConstantsServerless.MediaTypeJson);
    final Request request = new Request.Builder().url(url).post(body).build();
    try (Response response = client.newCall(request).execute()) {
      final String resultString = response.body().string();
      return JsonParser.parseString(resultString).getAsJsonObject();
    } catch (IOException exc) {
      throw new IllegalStateException(
          "IOException when enacting the serverless function with the url:\n" + url, exc);
    }
  }
}
