package at.uibk.dps.ee.enactables.serverless;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

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
   * @param mapping the provided mapping edge
   * @return the {@link ServerlessFunction} which is modeled by the provided
   *         resource node
   */
  public EnactmentFunction createServerlessFunction(final Mapping<Task, Resource> mapping) {
    return new ServerlessFunction(mapping);
  }
}
