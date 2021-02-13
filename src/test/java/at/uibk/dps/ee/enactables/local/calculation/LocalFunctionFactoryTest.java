package at.uibk.dps.ee.enactables.local.calculation;

import static org.junit.Assert.*;
import org.junit.Test;
import at.uibk.dps.ee.enactables.local.ConstantsLocal.LocalCalculations;

public class LocalFunctionFactoryTest {

  @Test
  public void test() {
    LocalFunctionFactory tested = new LocalFunctionFactory();
    assertTrue(tested.getLocalFunction(LocalCalculations.Addition) instanceof Addition);
    assertTrue(tested.getLocalFunction(LocalCalculations.Subtraction) instanceof Subtraction);
    assertTrue(tested.getLocalFunction(LocalCalculations.SumCollection) instanceof SumCollection);
  }
}
