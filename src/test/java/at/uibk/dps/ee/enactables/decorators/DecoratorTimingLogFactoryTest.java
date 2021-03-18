package at.uibk.dps.ee.enactables.decorators;

import static org.junit.Assert.*;
import org.junit.Test;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import static org.mockito.Mockito.mock;

public class DecoratorTimingLogFactoryTest {

  @Test
  public void test() {
    int prio = 42;
    EnactmentFunction original = mock(EnactmentFunction.class);
    DecoratorTimingLogFactory tested = new DecoratorTimingLogFactory(prio);
    assertEquals(prio, tested.getPriority());
    EnactmentFunction result = tested.decorateFunction(original);
    assertTrue(result instanceof DecoratorTimingLog);
  }
}
