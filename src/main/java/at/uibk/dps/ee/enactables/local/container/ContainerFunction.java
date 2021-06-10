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

  /**
   * Injection constructor.
   * 
   * @param typeId the type id of the functions
   * @param implId the id of the implementation (with the id of the resource
   *        running the container)
   * @param additionalAttrubutes additional attributes
   * @param containerManager the class managing the containers
   * @param imageName the Docker image of the function
   */
  @Inject
  public ContainerFunction(final String typeId, final String implId,
      final Set<SimpleEntry<String, String>> additionalAttrubutes, final ContainerManager containerManager,
      final String imageName) {
    this.typeId = typeId;
    this.implId = implId;
    this.additionalAttributes = additionalAttrubutes;
    this.enactmentMode = EnactmentMode.Local.name();
    this.containerManager = containerManager;
    this.imageName = imageName;
  }

  @Override
  public JsonObject processInput(final JsonObject input) throws StopException {
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
