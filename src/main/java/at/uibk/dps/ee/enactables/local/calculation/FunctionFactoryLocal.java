package at.uibk.dps.ee.enactables.local.calculation;

import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.enactable.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.enactables.FunctionTypes;
import at.uibk.dps.ee.enactables.local.ConstantsLocal.LocalCalculations;

/**
 * The {@link FunctionFactoryLocal} provides the enactment functions modeling
 * local operations.
 * 
 * @author Fedor Smirnov
 *
 */
public class FunctionFactoryLocal extends FunctionFactory {

  @Inject
  public FunctionFactoryLocal(Set<FunctionDecoratorFactory> decoratorFactories) {
    super(decoratorFactories);
  }

  /**
   * Returns the local function for the given enum, decorated with the injected
   * decorators.
   * 
   * @param localFunction the local function enum
   * @return the local function for the enum, decorated with the injected
   *         decorators.
   */
  public EnactmentFunction getLocalFunction(final LocalCalculations localFunction) {
    EnactmentFunction original = getOriginalFunction(localFunction);
    return decorate(original);
  }

  /**
   * Returns the local function for the given enum.
   * 
   * @param localFunction the local function enum
   * @return the local function for the enum
   */
  protected LocalFunctionAbstract getOriginalFunction(final LocalCalculations localFunction) {
    switch (localFunction) {
      case Addition:
        return new Addition(LocalCalculations.Addition.name(), FunctionTypes.Local.name());
      case Subtraction:
        return new Subtraction(LocalCalculations.Subtraction.name(), FunctionTypes.Local.name());
      case SumCollection:
        return new SumCollection(LocalCalculations.SumCollection.name(),
            FunctionTypes.Local.name());
      case SplitArray:
        return new SplitArray(LocalCalculations.SplitArray.name(), FunctionTypes.Local.name());
      default:
        throw new IllegalArgumentException("Unknown local function " + localFunction.name());
    }
  }
}
