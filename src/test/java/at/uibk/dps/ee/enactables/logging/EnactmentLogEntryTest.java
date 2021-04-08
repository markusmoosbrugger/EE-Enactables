package at.uibk.dps.ee.enactables.logging;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

public class EnactmentLogEntryTest {

  @Test
  public void test() {
    String id = "id1";
    String type = "type1";
    double executionTime = 110.1;
    double inputComplexity = 0;
    boolean success = false;
    Instant timestamp = Instant.now();

    EnactmentLogEntry entry =
        new EnactmentLogEntry(timestamp, id, type, executionTime, success, inputComplexity);
    assertEquals(id, entry.getFunctionId());
    assertEquals(type, entry.getFunctionType());
    assertEquals(executionTime, entry.getExecutionTime(), 0.001);
    assertNotNull(entry.getTimestamp());
    assertEquals(0.0, entry.getInputComplexity(), 0.001);
    assertEquals(success, entry.isSuccess());

    String id2 = "id2";
    String type2 = "type2";
    double executionTime2 = 90.01;
    Instant timestamp2 = Instant.now();
    boolean success2 = true;
    double inputComplexity2 = 0.8;

    entry.setFunctionId(id2);
    entry.setFunctionType(type2);
    entry.setExecutionTime(executionTime2);
    entry.setTimestamp(timestamp2);
    entry.setSuccess(success2);
    entry.setInputComplexity(inputComplexity2);

    assertEquals(timestamp2, entry.getTimestamp());
    assertEquals(id2, entry.getFunctionId());
    assertEquals(type2, entry.getFunctionType());
    assertEquals(executionTime2, entry.getExecutionTime(), 0.001);
    assertEquals(inputComplexity2, entry.getInputComplexity(), 0.001);
    assertEquals(success2, entry.isSuccess());
  }

  @Test
  public void testEquals() {
    String id = "id1";
    String type = "type1";
    double executionTime = 9.99;
    Instant timestamp = Instant.now();

    EnactmentLogEntry entry1 = new EnactmentLogEntry(timestamp, id, type, executionTime, true, 0.2);

    EnactmentLogEntry entry2 =
        new EnactmentLogEntry(timestamp, id, type, executionTime, false, 0.3);

    assertEquals(entry1.hashCode(), entry2.hashCode());
    assertEquals(entry1, entry2);

    entry2.setTimestamp(Instant.now());
    assertNotEquals(entry1.hashCode(), entry2.hashCode());
    assertNotEquals(entry1, entry2);
  }
}
