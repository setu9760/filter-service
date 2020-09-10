package org.filter.exercise.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ContainsPropertyFilterTest {

  private final Map<String, String> user = new LinkedHashMap<>();
  @Rule public ExpectedException expected = ExpectedException.none();

  @After
  public void teardown() throws Exception {
    user.clear();
  }

  @Test
  public void shouldMatchWhenPropertyExists() {
    user.put("test", "value");
    Filter<Map<String, String>> filter = CoreFilters.containsProperty("test");
    assertTrue(filter.matches(user));
  }

  @Test
  public void shouldNotMatchWhenPropertyDoesNotExist() {
    user.put("random", "value");
    Filter<Map<String, String>> filter = CoreFilters.containsProperty("test");
    assertFalse(filter.matches(user));
  }

  @Test
  public void shouldNotMatchWhenValueEmpty() {
    user.put("test", "");
    Filter<Map<String, String>> filter = CoreFilters.containsProperty("test");
    assertFalse(filter.matches(user));
  }

  @Test
  public void shouldNotMatchWhenValueNull() {
    user.put("test", null);
    Filter<Map<String, String>> filter = CoreFilters.containsProperty("test");
    assertFalse(filter.matches(user));
  }

  @Test
  public void shouldThrowExceptionWhenConstructorArgumentNull() {
    expected.expect(NullPointerException.class);
    CoreFilters.containsProperty(null);
  }

  @Test
  public void shouldThrowExceptionWhenNullUser() {
    expected.expect(NullPointerException.class);
    CoreFilters.containsProperty("test").matches(null);
  }

  @Test
  public void testToString() {
    Filter<Map<String, String>> filter = CoreFilters.containsProperty("test");
    assertEquals("resource contains test", CoreFilters.toPattern(filter));
  }
}
