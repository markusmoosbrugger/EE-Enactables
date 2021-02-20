package at.uibk.dps.ee.enactables.serverless;

import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.exception.StopException;
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
  protected final OkHttpClient client = new OkHttpClient();

  /**
   * Default constructor.
   * 
   * @param url the url to access the serverless function
   */
  public ServerlessFunction(final String url) {
    this.url = url;
  }

  @Override
  public JsonObject processInput(final JsonObject input) throws StopException {
    return enactServerlessFunction(url, input);
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
        RequestBody.create(input.toString(), ConstantsServerless.MediaTypeJson);
    final Request request = new Request.Builder().url(url).post(body).build();
    try (final Response response = client.newCall(request).execute()) {
      final String resultString = response.body().string();
      return JsonParser.parseString(resultString).getAsJsonObject();
    } catch (IOException exc) {
      throw new IllegalStateException(
          "IOException when enacting the serverless function with the url:\n" + url, exc);
    }
  }
}
