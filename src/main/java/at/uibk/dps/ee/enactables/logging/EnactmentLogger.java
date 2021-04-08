package at.uibk.dps.ee.enactables.logging;

/**
 * Interface for the classes used for logging of enactments.
 *
 * @author Markus Moosbrugger
 */

public interface EnactmentLogger {

  /**
   * Logs an enactment entry.
   *
   * @param entry the entry containing information about the enactment
   */
  void logEnactment(EnactmentLogEntry entry);
}
