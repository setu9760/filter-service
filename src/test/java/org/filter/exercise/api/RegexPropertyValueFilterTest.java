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

public class RegexPropertyValueFilterTest {

  private final Map<String, String> user = new LinkedHashMap<>();
  @Rule public ExpectedException expected = ExpectedException.none();

  @After
  public void tearDown() throws Exception {
    user.clear();
  }

  @Test
  public void shouldMatchForValidRegex() {
    user.put("test", "value with digits 12 in-between");
    Filter<Map<String, String>> filter = CoreFilters.valueMatchesRegex("test", "(.)*(\\d)(.)*");
    assertTrue(filter.matches(user));
  }

  @Test
  public void shouldMatchForLiteralValueInRegex() {
    user.put("test", "value");
    Filter<Map<String, String>> filter = CoreFilters.valueMatchesRegex("test", "value");
    assertTrue(filter.matches(user));
  }

  @Test
  public void shouldNotMatchForInvalidValue() {
    user.put("test", "value with no digit");
    Filter<Map<String, String>> filter = CoreFilters.valueMatchesRegex("test", "(.)*(\\d)(.)*");
    assertFalse(filter.matches(user));
  }

  @Test
  public void shouldThrowExceptionForInvalidRegex() {
    expected.expect(IllegalArgumentException.class);
    expected.expectMessage("String '*.*' fails to compile into regex");
    CoreFilters.valueMatchesRegex("test", "*.*");
  }

  @Test
  public void shouldThrowExceptionWhenConstructorArgumentNull() {
    expected.expect(NullPointerException.class);
    CoreFilters.valueMatchesRegex("test", null);
  }

  @Test
  public void shouldThrowExceptionWhenNullUser() {
    expected.expect(NullPointerException.class);
    CoreFilters.valueMatchesRegex("test", "value").matches(null);
  }

  @Test
  public void shouldNotMatchIfPropertyNotExists() {
    Filter<Map<String, String>> filter = CoreFilters.valueMatchesRegex("test", "value");
    assertFalse(filter.matches(user));
  }

  @Test
  public void testToString() {
    Filter<Map<String, String>> filter = CoreFilters.valueMatchesRegex("test", "value");
    assertEquals("test matches value", CoreFilters.toPattern(filter));
  }
}
