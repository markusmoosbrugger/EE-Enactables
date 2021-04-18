package at.uibk.dps.ee.enactables;

/**
 * Enum defining the different types of functions.
 * 
 * @author Fedor Smirnov
 */
public enum EnactmentMode {
  /**
   * User functions executed on the host machine.
   */
  Local,
  /**
   * User functions executed as serverless functions on remote machines
   */
  Serverless,
  /**
   * Utility functions executed on the host machine.
   */
  Utility,
  /**
   * Functions used to implement the non-trivial flow of data during the enactment
   */
  DataFlow
}
