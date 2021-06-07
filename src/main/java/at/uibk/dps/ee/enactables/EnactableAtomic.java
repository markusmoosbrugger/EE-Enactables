package at.uibk.dps.ee.enactables;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.uibk.dps.ee.core.enactable.Enactable;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import net.sf.opendse.model.Task;

/**
 * The {@link EnactableAtomic}
 * 
 * @author Fedor Smirnov
 *
 */
public class EnactableAtomic extends Enactable {

  protected final Task functionNode;
  Logger logger = LoggerFactory.getLogger(EnactableAtomic.class);

  /**
   * Protected constructor, used the factory to create enactables.
   * 
   * @param stateListeners the state listeners
   * @param inputMap map containing the expected input names as keys with null
   *        values
   * @param functionNode the node from the enactment graph associated with this
   *        enactable
   */
  protected EnactableAtomic(final Set<EnactableStateListener> stateListeners,
      final Task functionNode) {
    super(stateListeners);
    this.functionNode = functionNode;
    PropertyServiceFunction.setEnactable(functionNode, this);
  }

  @Override
  public void play() throws StopException {
    try {
      logger.debug("Playing task {}", functionNode.getId());
      super.play();
      logger.debug("Finished task {}", functionNode.getId());
    } catch (StopException stopExc) {
      stopExc.appendExceptionInformation("Problem task: " + functionNode.getId());
      throw stopExc;
    }
  }

  @Override
  protected void myInit() {
    // do nothing
  }

  /**
   * Returns the function node of the enactable.
   * 
   * @return the function node of the enactable
   */
  public Task getFunctionNode() {
    return functionNode;
  }

  @Override
  protected void myReset() {
    // do nothing
  }
}
