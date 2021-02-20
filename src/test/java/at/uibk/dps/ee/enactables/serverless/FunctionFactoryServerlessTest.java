package at.uibk.dps.ee.enactables.serverless;

import static org.junit.Assert.*;
import org.junit.Test;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import net.sf.opendse.model.Resource;

public class FunctionFactoryServerlessTest {

  @Test
  public void test() {
    FunctionFactoryServerless tested = new FunctionFactoryServerless();
    Resource slRes = PropertyServiceResourceServerless.createServerlessResource("res", "link");
    EnactmentFunction result = tested.createServerlessFunction(slRes);
    assertTrue(result instanceof ServerlessFunction);
    ServerlessFunction slResult = (ServerlessFunction) result;
    assertEquals("link", slResult.url);
  }
}
