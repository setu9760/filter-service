package org.filter.exercise.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GreaterThanFilterTest {

  private final Map<String, String> user = new LinkedHashMap<>();
  @Rule public ExpectedException expected = ExpectedException.none();

  @After
  public void tearDown() {
    user.clear();
  }

  @Test
  public void shouldMatchForHigherValue() {
    user.put("test", "12");
    Filter<Map<String, String>> filter = CoreFilters.isPropertyGreaterThan("test", "10");
    assertTrue(filter.matches(user));
  }

  @Test
  public void shouldNotMatchForLowerValue() {
    user.put("test", "8");
    Filter<Map<String, String>> filter = CoreFilters.isPropertyGreaterThan("test", "10");
    assertFalse(filter.matches(user));
  }

  @Test
  public void shouldNotMatchForEqualValue() {
    user.put("test", "10");
    Filter<Map<String, String>> filter = CoreFilters.isPropertyGreaterThan("test", "10");
    assertFalse(filter.matches(user));
  }

  @Test
  public void shouldThrowExceptionWhenNullUser() {
    expected.expect(NullPointerException.class);
    CoreFilters.isPropertyGreaterThan("test", "10").matches(null);
  }

  @Test
  public void shouldThrowExceptionWhenNotNumber() {
    expected.expect(IllegalArgumentException.class);
    expected.expectMessage("NaN value. Tried to convert str 'value' to integer");
    user.put("test", "value");
    Filter<Map<String, String>> filter = CoreFilters.isPropertyGreaterThan("test", "10");
    assertFalse(filter.matches(user));
  }

  @Test
  public void shouldNotMatchIfPropertyNotExists() {
    Filter<Map<String, String>> filter = CoreFilters.isPropertyGreaterThan("test", "10");
    assertFalse(filter.matches(user));
  }

  @Test
  public void testToString() {
    Filter<Map<String, String>> filter = CoreFilters.isPropertyGreaterThan("test", "10");
    assertEquals("test > 10", CoreFilters.toPattern(filter));
  }
}
