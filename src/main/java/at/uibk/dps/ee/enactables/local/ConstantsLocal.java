package at.uibk.dps.ee.enactables.local;

/**
 * Static constants for the local enactables.
 * 
 * @author Fedor Smirnov
 *
 */
public class ConstantsLocal {

	public enum LocalCalculations{
		Addition, Subtraction, SumCollection
	}
	
	// Name prefixes
	public static final String prefixAddition = "addition_";
	public static final String prefixSubstraction = "substraction_";
	public static final String prefixSumCollection = "collectionSum_";
	
	
	// Expected input and output names
	
	// addition
	public static final String inputSumFirst = "firstSummand";
	public static final String inputSumSecond = "secondSummand";
	public static final String outputAdditionResult = "sum";
	
	// substraction
	public static final String inputMinuend = "minuend";
	public static final String inputSubtrahend = "subtrahend";
	public static final String outputSubstractionResult = "difference";
	
	// sum aggregation
	public static final String inputSumCollection = "collectionToSum";
	public static final String outputSumCollection = "collectionSum";
	
	// used by multiple classes 
	public static final String inputWaitTime = "waitTimeIn";
	
}
