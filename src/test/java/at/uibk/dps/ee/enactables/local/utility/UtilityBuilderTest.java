package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.model.objects.Condition;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility.UtilityType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition.Summary;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.UsageType;
import net.sf.opendse.model.Task;

public class UtilityBuilderTest {

	@Test
	public void test() {
		UtilityBuilder tested = new UtilityBuilder();
		assertEquals(UsageType.Utility, tested.getType());

		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<Condition> conditions = new HashSet<>();

		Task input = new Task("task");
		PropertyServiceFunction.setUsageType(UsageType.Utility, input);
		PropertyServiceFunctionUtility.setUtilityType(input, UtilityType.Condition);
		PropertyServiceFunctionUtilityCondition.setConditions(input, conditions);
		PropertyServiceFunctionUtilityCondition.setSummary(input, Summary.AND);
		EnactableAtomic result = tested.buildEnactable(input, stateListeners);

		assertTrue(result instanceof ConditionEvaluation);

		Task otherTask = PropertyServiceFunctionUtilityCollections.createCollectionOperation("data", "subcoll", CollectionOperation.ElementIndex);
		EnactableAtomic secondResult = tested.buildEnactable(otherTask, stateListeners);
		assertTrue(secondResult instanceof CollOperEnactable);
	}
}
