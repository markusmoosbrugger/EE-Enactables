package at.uibk.dps.ee.enactables.serverless;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import net.sf.opendse.model.Resource;

/**
 * The {@link FunctionFactoryServerless} creates the {@link EnactmentFunction}s
 * modeling the enactment of a serverless function.
 * 
 * @author Fedor Smirnov
 */
public class FunctionFactoryServerless {

  /**
   * Creates the {@link ServerlessFunction} which is modeled by the provided
   * resource node.
   * 
   * @param resource the provided resource node
   * @return the {@link ServerlessFunction} which is modeled by the provided
   *         resource node
   */
  public EnactmentFunction createServerlessFunction(Resource resource) {
    String url = PropertyServiceResourceServerless.getUri(resource);
    return new ServerlessFunction(url);
  }
}
