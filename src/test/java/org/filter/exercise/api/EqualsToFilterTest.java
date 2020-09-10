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

public class EqualsToFilterTest {

  private final Map<String, String> user = new LinkedHashMap<>();
  @Rule public ExpectedException expected = ExpectedException.none();

  @After
  public void tearDown() throws Exception {
    user.clear();
  }

  @Test
  public void shouldMatchWhenValueEqual() {
    user.put("test", "value");
    Filter<Map<String, String>> filter = CoreFilters.isPropertyEqualTo("test", "value");
    assertTrue(filter.matches(user));
  }

  @Test
  public void shouldNotMatchForDiffValue() {
    user.put("test", "random");
    Filter<Map<String, String>> filter = CoreFilters.isPropertyEqualTo("test", "value");
    assertFalse(filter.matches(user));
  }

  @Test
  public void shouldThrowExceptionWhenConstructorArgumentNull() {
    expected.expect(NullPointerException.class);
    CoreFilters.isPropertyEqualTo("test", null);
  }

  @Test
  public void shouldThrowExceptionWhenNullUser() {
    expected.expect(NullPointerException.class);
    CoreFilters.isPropertyEqualTo("test", "value").matches(null);
  }

  @Test
  public void shouldNotMatchIfPropertyNotExists() {
    Filter<Map<String, String>> filter = CoreFilters.isPropertyEqualTo("test", "value");
    assertFalse(filter.matches(user));
  }

  @Test
  public void testToString() {
    Filter<Map<String, String>> filter = CoreFilters.isPropertyEqualTo("test", "value");
    assertEquals("test == value", CoreFilters.toPattern(filter));
  }
}
