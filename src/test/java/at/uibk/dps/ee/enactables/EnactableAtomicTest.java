package at.uibk.dps.ee.enactables;

import static org.junit.Assert.*;

import java.util.HashSet;
import org.junit.Test;
import org.mockito.Mockito;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.exception.StopException;
import net.sf.opendse.model.Task;

public class EnactableAtomicTest {

  @Test
  public void testPlayException() {
    Task functionNode = new Task("functionNode");
    EnactableAtomic tested = new EnactableAtomic(new HashSet<>(), functionNode);
    EnactmentFunction funcMock = mock(EnactmentFunction.class);
    tested.init();
    tested.schedule(funcMock);
    String innerMessage = "some internal detail";
    String expected = innerMessage + " \nProblem task: " + functionNode.getId();
    tested.setInputValue("bla", new JsonPrimitive(true));
    try {
      Mockito.doThrow(new StopException(innerMessage)).when(funcMock)
          .processInput(any(JsonObject.class));
      tested.play();
      fail();
    } catch (StopException stopExc) {
      assertEquals(expected, stopExc.getMessage());
    }
  }


  @Test
  public void testGetFunctionNode() {
    Task functionNode = new Task("functionNode");
    EnactableAtomic tested = new EnactableAtomic(new HashSet<>(), functionNode);
    assertEquals(functionNode, tested.getFunctionNode());
  }
}
