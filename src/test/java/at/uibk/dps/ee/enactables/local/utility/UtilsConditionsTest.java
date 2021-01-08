package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.model.objects.Condition.Operator;

public class UtilsConditionsTest {

	@Test
	public void testAnd() {
		JsonElement first = JsonParser.parseString("true");
		JsonElement second = JsonParser.parseString("false");
		JsonElement third = JsonParser.parseString("true");

		assertFalse(UtilsConditions.evaluate(first, second, Operator.AND));
		assertTrue(UtilsConditions.evaluate(first, third, Operator.AND));
	}

	@Test
	public void testOr() {
		JsonElement first = JsonParser.parseString("true");
		JsonElement second = JsonParser.parseString("false");
		JsonElement third = JsonParser.parseString("false");

		assertTrue(UtilsConditions.evaluate(first, second, Operator.OR));
		assertFalse(UtilsConditions.evaluate(second, third, Operator.OR));
	}

	@Test
	public void testEndWith() {
		JsonElement first = JsonParser.parseString("abbccaa");
		JsonElement second = JsonParser.parseString("aa");
		JsonElement third = JsonParser.parseString("ab");

		assertTrue(UtilsConditions.evaluate(first, second, Operator.ENDS_WITH));
		assertFalse(UtilsConditions.evaluate(first, third, Operator.ENDS_WITH));
	}

	@Test
	public void testStartsWith() {
		JsonElement first = JsonParser.parseString("abbccaa");
		JsonElement second = JsonParser.parseString("bc");
		JsonElement third = JsonParser.parseString("ab");

		assertFalse(UtilsConditions.evaluate(first, second, Operator.STARTS_WITH));
		assertTrue(UtilsConditions.evaluate(first, third, Operator.STARTS_WITH));
	}

	@Test
	public void testContains() {
		JsonElement first = JsonParser.parseString("abbccaa");
		JsonElement second = JsonParser.parseString("bc");
		JsonElement third = JsonParser.parseString("ad");

		assertTrue(UtilsConditions.evaluate(first, second, Operator.CONTAINS));
		assertFalse(UtilsConditions.evaluate(first, third, Operator.CONTAINS));
	}

	@Test
	public void testEqual() {
		JsonElement first = JsonParser.parseString("0.001");
		JsonElement second = JsonParser.parseString("0.001000000001");
		JsonElement third = JsonParser.parseString("0.002");

		assertTrue(UtilsConditions.evaluate(first, second, Operator.EQUAL));
		assertFalse(UtilsConditions.evaluate(first, third, Operator.EQUAL));
	}

	@Test
	public void testUnEqual() {
		JsonElement first = JsonParser.parseString("0.001");
		JsonElement second = JsonParser.parseString("0.001000000001");
		JsonElement third = JsonParser.parseString("0.002");

		assertFalse(UtilsConditions.evaluate(first, second, Operator.UNEQUAL));
		assertTrue(UtilsConditions.evaluate(first, third, Operator.UNEQUAL));
	}

	@Test
	public void testLessEqual() {
		JsonElement first = JsonParser.parseString("0.001");
		JsonElement second = JsonParser.parseString("0.001000000001");
		JsonElement third = JsonParser.parseString("0.002");

		assertTrue(UtilsConditions.evaluate(first, second, Operator.LESS_EQUAL));
		assertTrue(UtilsConditions.evaluate(first, third, Operator.LESS_EQUAL));
		assertFalse(UtilsConditions.evaluate(third, first, Operator.LESS_EQUAL));
	}

	@Test
	public void testGreaterEqual() {
		JsonElement first = JsonParser.parseString("0.001");
		JsonElement second = JsonParser.parseString("0.001000000001");
		JsonElement third = JsonParser.parseString("0.002");

		assertTrue(UtilsConditions.evaluate(first, second, Operator.GREATER_EQUAL));
		assertFalse(UtilsConditions.evaluate(first, third, Operator.GREATER_EQUAL));
		assertTrue(UtilsConditions.evaluate(third, first, Operator.GREATER_EQUAL));
	}

	@Test
	public void testGreater() {
		JsonElement first = JsonParser.parseString("0.001");
		JsonElement second = JsonParser.parseString("0.001000000001");
		JsonElement third = JsonParser.parseString("0.002");

		assertFalse(UtilsConditions.evaluate(first, second, Operator.GREATER));
		assertFalse(UtilsConditions.evaluate(first, third, Operator.GREATER));
		assertTrue(UtilsConditions.evaluate(third, first, Operator.GREATER));
	}

	@Test
	public void testLess() {
		JsonElement first = JsonParser.parseString("0.001");
		JsonElement second = JsonParser.parseString("0.001000000001");
		JsonElement third = JsonParser.parseString("0.002");

		assertFalse(UtilsConditions.evaluate(first, second, Operator.LESS));
		assertTrue(UtilsConditions.evaluate(first, third, Operator.LESS));
		assertFalse(UtilsConditions.evaluate(third, first, Operator.LESS));
	}
}
