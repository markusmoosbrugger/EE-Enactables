package at.uibk.dps.ee.enactables.local.utility;

import com.google.gson.JsonElement;

import at.uibk.dps.ee.model.objects.Condition.Operator;

/**
 * Static method container for the methods used to evaluate the branching
 * conditions.
 * 
 * @author Fedor Smirnov
 */
public final class UtilsConditions {

	/**
	 * No constructor.
	 */
	private UtilsConditions() {
	}

	/**
	 * Returns the boolean result which is obtained when combining the provided
	 * inputs with the provided operator.
	 * 
	 * @param first    the first input
	 * @param second   the second input
	 * @param operator the provided operator
	 * @return the boolean result which is obtained when combining the provided
	 *         inputs with the provided operator
	 */
	public static boolean evaluate(final JsonElement first, final JsonElement second, final Operator operator) {
		switch (operator) {
		case EQUAL:
			return evaluateEqual(first, second);

		case LESS:
			return evaluateLess(first, second);

		case GREATER:
			return evaluateGreater(first, second);

		case LESS_EQUAL:
			return evaluateLessEqual(first, second);

		case GREATER_EQUAL:
			return evaluateGreaterEqual(first, second);

		case UNEQUAL:
			return !evaluateEqual(first, second);

		case CONTAINS:
			return evaluateContains(first, second);

		case STARTS_WITH:
			return evaluateStartsWith(first, second);

		case ENDS_WITH:
			return evaluateEndsWith(first, second);

		case AND:
			return evaluateAnd(first, second);

		case OR:
			return evaluateOr(first, second);

		default:
			throw new IllegalArgumentException("Unknown condition operator");
		}
	}

	/**
	 * Evaluates the OR combination of 2 provided booleans.
	 * 
	 * @param first  the first boolean
	 * @param second the second boolean
	 * @return the OR combination of 2 provided booleans
	 */
	protected static boolean evaluateOr(final JsonElement first, final JsonElement second) {
		final boolean firstBoolean = first.getAsBoolean();
		final boolean secondBoolean = second.getAsBoolean();
		return firstBoolean || secondBoolean;
	}

	/**
	 * Evaluates the AND combination of 2 provided booleans.
	 * 
	 * @param first  the first boolean
	 * @param second the second boolean
	 * @return the AND combination of 2 provided booleans
	 */
	protected static boolean evaluateAnd(final JsonElement first, final JsonElement second) {
		final boolean firstBoolean = first.getAsBoolean();
		final boolean secondBoolean = second.getAsBoolean();
		return firstBoolean && secondBoolean;
	}

	/**
	 * Evaluates whether the first string ends with the second one.
	 * 
	 * @param first  the first string
	 * @param second the second string
	 * @return evaluation result
	 */
	protected static boolean evaluateEndsWith(final JsonElement first, final JsonElement second) {
		final String firstString = first.getAsString();
		final String secondString = second.getAsString();
		return firstString.endsWith(secondString);
	}

	/**
	 * Evaluates whether the first string starts with the second one.
	 * 
	 * @param first  the first string
	 * @param second the second string
	 * @return evaluation result
	 */
	protected static boolean evaluateStartsWith(final JsonElement first, final JsonElement second) {
		final String firstString = first.getAsString();
		final String secondString = second.getAsString();
		return firstString.startsWith(secondString);
	}

	/**
	 * Evaluates whether the first string contains the second one.
	 * 
	 * @param first  the first string
	 * @param second the second string
	 * @return evaluation result
	 */
	protected static boolean evaluateContains(final JsonElement first, final JsonElement second) {
		final String firstString = first.getAsString();
		final String secondString = second.getAsString();
		return firstString.contains(secondString);
	}

	/**
	 * Evaluates number greater
	 * 
	 * @param first  first number
	 * @param second second number
	 * @return equals greater
	 */
	protected static boolean evaluateGreater(final JsonElement first, final JsonElement second) {
		if (evaluateEqual(first, second)) {
			return false;
		}
		final double firstDouble = first.getAsDouble();
		final double secondDouble = second.getAsDouble();
		return firstDouble > secondDouble;
	}

	/**
	 * Evaluates number greater equal
	 * 
	 * @param first  first number
	 * @param second second number
	 * @return equals greater equal
	 */
	protected static boolean evaluateGreaterEqual(final JsonElement first, final JsonElement second) {
		if (evaluateEqual(first, second)) {
			return true;
		}
		final double firstDouble = first.getAsDouble();
		final double secondDouble = second.getAsDouble();
		return firstDouble > secondDouble;
	}

	/**
	 * Evaluates number less
	 * 
	 * @param first  first number
	 * @param second second number
	 * @return equals less
	 */
	protected static boolean evaluateLess(final JsonElement first, final JsonElement second) {
		if (evaluateEqual(first, second)) {
			return false;
		}
		final double firstDouble = first.getAsDouble();
		final double secondDouble = second.getAsDouble();
		return firstDouble < secondDouble;
	}

	/**
	 * Evaluates number less equal
	 * 
	 * @param first  first number
	 * @param second second number
	 * @return equals less equal
	 */
	protected static boolean evaluateLessEqual(final JsonElement first, final JsonElement second) {
		if (evaluateEqual(first, second)) {
			return true;
		}
		final double firstDouble = first.getAsDouble();
		final double secondDouble = second.getAsDouble();
		return firstDouble < secondDouble;
	}

	/**
	 * Evaluates number quality
	 * 
	 * @param first  first number
	 * @param second second number
	 * @return equals result
	 */
	protected static boolean evaluateEqual(final JsonElement first, final JsonElement second) {
		final double firstDouble = first.getAsDouble();
		final double secondDouble = second.getAsDouble();
		return Math.abs(Math.abs(firstDouble) - Math.abs(secondDouble)) < ConstantsUtility.epsilon;
	}

}
