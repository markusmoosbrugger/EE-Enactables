package at.uibk.dps.ee.enactables.local.container;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import at.uibk.dps.ee.core.enactable.FunctionDecoratorFactory;
import at.uibk.dps.ee.docker.manager.ContainerManager;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMappingLocal;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;
import static org.mockito.Mockito.mock;

public class FunctionFactoryLocalTest {

  @Test
  public void test() {
    Set<FunctionDecoratorFactory> decorators = new HashSet<>();
    ContainerManager mockManager = mock(ContainerManager.class);
    FunctionFactoryLocal tested = new FunctionFactoryLocal(decorators, mockManager);

    Task task = PropertyServiceFunctionUser.createUserTask("task", "addition");
    Resource res = new Resource("r");
    Mapping<Task, Resource> map =
        PropertyServiceMappingLocal.createMappingLocal(task, res, "image");

    ContainerFunction result = tested.getContainerFunction(map);

    assertEquals("addition", result.getTypeId());
    assertEquals(at.uibk.dps.ee.enactables.EnactmentMode.Local.name(), result.getEnactmentMode());
    assertTrue(result.getAdditionalAttributes().isEmpty());
  }
}
