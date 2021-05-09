package at.uibk.dps.ee.enactables.local.container;

import java.util.AbstractMap.SimpleEntry;
import java.util.Set;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.docker.manager.ContainerManager;
import at.uibk.dps.ee.enactables.EnactmentMode;

/**
 * The {@link ContainerFunction} is used to execute function within local Docker
 * containers.
 * 
 * @author Fedor Smirnov
 */
public class ContainerFunction implements EnactmentFunction {

  protected final String typeId;
  protected final String enactmentMode;
  protected final String implId;
  protected final Set<SimpleEntry<String, String>> additionalAttributes;

  protected final ContainerManager containerManager;
  protected final String imageName;

  @Inject
  public ContainerFunction(String typeId, String implId,
      Set<SimpleEntry<String, String>> additionalAttrubutes, ContainerManager containerManager, String imageName) {
    this.typeId = typeId;
    this.implId = implId;
    this.additionalAttributes = additionalAttrubutes;
    this.enactmentMode = EnactmentMode.Local.name();
    this.containerManager = containerManager;
    this.imageName = imageName;
  }

  @Override
  public JsonObject processInput(JsonObject input) throws StopException {
    return containerManager.runImage(imageName, input);
  }

  @Override
  public String getTypeId() {
    return typeId;
  }

  @Override
  public String getEnactmentMode() {
    return enactmentMode;
  }

  @Override
  public String getImplementationId() {
    return implId;
  }

  @Override
  public Set<SimpleEntry<String, String>> getAdditionalAttributes() {
    return additionalAttributes;
  }
}
