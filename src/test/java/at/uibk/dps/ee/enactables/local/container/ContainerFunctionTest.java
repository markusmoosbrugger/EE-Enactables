package at.uibk.dps.ee.enactables.local.container;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.docker.manager.ContainerManager;
import at.uibk.dps.ee.enactables.EnactmentMode;
import static org.mockito.Mockito.mock;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.verify;

public class ContainerFunctionTest {

  @Test
  public void test() {
    String typeId = "funcType";
    String implId = "localComp";
    Set<SimpleEntry<String, String>> additional = new HashSet<>();
    ContainerManager mockManager = mock(ContainerManager.class);
    String imageName = "image";
    ContainerFunction tested =
        new ContainerFunction(typeId, implId, additional, mockManager, imageName);
    // getters
    assertEquals(typeId, tested.getTypeId());
    assertEquals(implId, tested.getImplementationId());
    assertEquals(EnactmentMode.Local.name(), tested.getEnactmentMode());
    assertEquals(additional, tested.getAdditionalAttributes());
    // running the function
    JsonObject input = new JsonObject();
    try {
      tested.processInput(input);
    } catch (StopException e) {
      fail();
    }
    verify(mockManager).runImage(imageName, input);
  }
}
