package at.uibk.dps.ee.enactables.local.container;

import java.util.HashSet;
import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.core.ContainerManager;
import at.uibk.dps.ee.core.enactable.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceMappingLocal;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * The {@link FunctionFactoryLocal} provides the functions modeling function
 * execution within local containers.
 * 
 * @author Fedor Smirnov
 */
public class FunctionFactoryLocal extends FunctionFactory {

  protected final ContainerManager containerManager;

  /**
   * Injection constructor.
   * 
   * @param decoratorFactories the factories for the decorators which are used to
   *        wrap the created functions
   */
  @Inject
  public FunctionFactoryLocal(Set<FunctionDecoratorFactory> decoratorFactories,
      ContainerManager containerManager) {
    super(decoratorFactories);
    this.containerManager = containerManager;
  }

  /**
   * Generates the container function for the provided mapping.
   * 
   * @param containerMapping the provided mapping
   * @return the container function for the provided mapping
   */
  public ContainerFunction getContainerFunction(Mapping<Task, Resource> containerMapping) {
    Task task = containerMapping.getSource();
    String imageName = PropertyServiceMappingLocal.getImageName(containerMapping);
    String typeId = PropertyServiceFunctionUser.getTypeId(task);
    String implId = PropertyServiceMapping.getImplementationId(containerMapping);
    return new ContainerFunction(typeId, implId, new HashSet<>(), containerManager, imageName);
  }
}
