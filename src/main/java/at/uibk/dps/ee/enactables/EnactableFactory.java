package at.uibk.dps.ee.enactables;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import at.uibk.dps.ee.core.enactable.Enactable;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import net.sf.opendse.model.Task;

/**
 * The {@link EnactableFactory} is used for the creation of elemental
 * {@link Enactable}s.
 * 
 * @author Fedor Smirnov
 *
 */
@Singleton
public class EnactableFactory {

  protected final Set<EnactableStateListener> stateListeners;

  /**
   * The factory is provided with a list of listeners which are to be added to
   * every created enactable.
   * 
   * @param stateListeners a list of listeners which are to be added to every
   *        created enactable
   */
  @Inject
  public EnactableFactory(final Set<EnactableStateListener> stateListeners) {
    this.stateListeners = new HashSet<>();
    this.stateListeners.addAll(stateListeners);
  }

  /**
   * Adds the given listener to the list of listeners provided to every
   * constructed {@link Enactable}.
   * 
   * @param listener the listener to add
   */
  public void addEnactableStateListener(final EnactableStateListener listener) {
    this.stateListeners.add(listener);
  }

  /**
   * Creates an enactable which can be used to perform the enactment modeled by
   * the provided function node.
   * 
   * @param functionNode the provided function node
   * @return an enactable which can be used to perform the enactment modeled by
   *         the provided function node
   */
  public EnactableAtomic createEnactable(final Task functionNode) {
    return new EnactableAtomic(stateListeners, functionNode);
  }

  /**
   * Creates the enactable of the given offspring task and adjusts its state so
   * that it corresponds to the state of the parent enactable (if e.g., some
   * inputs were already set before the reproduction).
   * 
   * @param offspring the offspring task
   * @param parentEnactable the enactable of the parent
   * @return the child enactable, adjusted to have the same state as the parent
   *         enactable
   */
  public void reproduceEnactable(final Task offspring, final EnactableAtomic parentEnactable) {
    final EnactableAtomic offspringEnactable = createEnactable(offspring);
    final JsonObject parentInput =
        Optional.ofNullable(parentEnactable.getInput()).orElseGet(() -> new JsonObject());
    parentInput.keySet()
        .forEach(key -> offspringEnactable.setInputValue(key, parentInput.get(key)));
    PropertyServiceFunction.setEnactable(offspring, offspringEnactable);
  }
}
