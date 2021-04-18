package at.uibk.dps.ee.enactables.serverless;

import static org.junit.Assert.*;
import org.junit.Test;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping.EnactmentMode;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

public class FunctionFactoryServerlessTest {

  @Test
  public void test() {
    FunctionFactoryServerless tested = new FunctionFactoryServerless();
    Resource slRes = PropertyServiceResourceServerless.createServerlessResource("res", "link");
    Task task = PropertyServiceFunctionUser.createUserTask("task", "addition");
    Mapping<Task, Resource> mapping =
        PropertyServiceMapping.createMapping(task, slRes, EnactmentMode.Serverless, "link");
    EnactmentFunction result = tested.createServerlessFunction(mapping);
    assertTrue(result instanceof ServerlessFunction);
    ServerlessFunction slResult = (ServerlessFunction) result;
    assertEquals("link", slResult.url);
  }
}
