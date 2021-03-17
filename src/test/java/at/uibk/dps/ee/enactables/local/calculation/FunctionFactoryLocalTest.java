package at.uibk.dps.ee.enactables.local.calculation;

import static org.junit.Assert.*;
import java.util.HashSet;
import org.junit.Test;
import at.uibk.dps.ee.enactables.local.ConstantsLocal.LocalCalculations;

public class FunctionFactoryLocalTest {

  @Test
  public void test() {
    FunctionFactoryLocal tested = new FunctionFactoryLocal(new HashSet<>());
    assertTrue(tested.getLocalFunction(LocalCalculations.Addition) instanceof Addition);
    assertTrue(tested.getLocalFunction(LocalCalculations.Subtraction) instanceof Subtraction);
    assertTrue(tested.getLocalFunction(LocalCalculations.SumCollection) instanceof SumCollection);
  }
}
