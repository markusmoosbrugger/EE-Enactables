package at.uibk.dps.ee.enactables.decorators;

import org.opt4j.core.start.Constant;
import com.google.inject.Inject;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.enactable.FunctionDecoratorFactory;

/**
 * The {@link DecoratorTimingLogFactory} decorates functions by wrapping them in
 * {@link DecoratorTimingLog}s.
 * 
 * @author Fedor Smirnov
 */
public class DecoratorTimingLogFactory extends FunctionDecoratorFactory {

  public final int priority;

  /**
   * The injection constructor.
   * 
   * @param priority The priority of the decorator (see parent class comments)
   */
  @Inject
  public DecoratorTimingLogFactory(
      @Constant(value = "prio", namespace = DecoratorTimingLogFactory.class) final int priority) {
    this.priority = priority;
  }

  @Override
  public EnactmentFunction decorateFunction(final EnactmentFunction function) {
    return new DecoratorTimingLog(function);
  }

  @Override
  public int getPriority() {
    return priority;
  }
}
