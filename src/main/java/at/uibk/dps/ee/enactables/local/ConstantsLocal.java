package at.uibk.dps.ee.enactables.local;

/**
 * Static constants for the local enactables.
 * 
 * @author Fedor Smirnov
 *
 */
public class ConstantsLocal {

  public enum LocalCalculations {
    Addition, Subtraction, SumCollection
  }

  // Expected input and output names

  // addition
  public static final String inputAdditionFirst = "firstSummand";
  public static final String inputAdditionSecond = "secondSummand";
  public static final String outputAdditionResult = "sum";

  // substraction
  public static final String inputSubtractionMinuend = "minuend";
  public static final String inputSubtractionSubtrahend = "subtrahend";
  public static final String outputSubstractionResult = "difference";

  // sum aggregation
  public static final String inputSumCollection = "collectionToSum";
  public static final String outputSumCollection = "collectionSum";

  // used by multiple functions
  public static final String inputWaitTime = "waitTimeIn";
}
