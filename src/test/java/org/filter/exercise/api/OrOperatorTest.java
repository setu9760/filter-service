package org.filter.exercise.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class OrOperatorTest {
  private final Map<String, String> user = new LinkedHashMap<>();

  @After
  public void teardown() throws Exception {
    user.clear();
  }

  @Test
  public void shouldMatchWhenBothFiltersMatch() {
    user.put("test1", "value1");
    user.put("test2", "15");

    Filter<Map<String, String>> equalsToFilter = CoreFilters.isPropertyEqualTo("test1", "value1");
    Filter<Map<String, String>> greaterThanFilter = CoreFilters.isPropertyGreaterThan("test2", "10");

    assertTrue(equalsToFilter.or(greaterThanFilter).matches(user));
    assertTrue(greaterThanFilter.or(equalsToFilter).matches(user));
  }

  @Test
  public void shouldNotMatchWhenEitherFilterMatch() {
    user.put("test1", "value1");
    user.put("test2", "8");

    Filter<Map<String, String>> equalsToFilter = CoreFilters.isPropertyEqualTo("test1", "value1");
    Filter<Map<String, String>> greaterThanFilter = CoreFilters.isPropertyGreaterThan("test2", "10");

    assertTrue(equalsToFilter.or(greaterThanFilter).matches(user));

    user.put("test1", "value2");
    user.put("test2", "12");

    assertTrue(equalsToFilter.or(greaterThanFilter).matches(user));
  }

  @Test
  public void shouldNotMatchWhenBothFiltersDont() {
    user.put("test1", "value1");
    user.put("test2", "8");

    Filter<Map<String, String>> equalsToFilter = CoreFilters.isPropertyEqualTo("test1", "value2");
    Filter<Map<String, String>> greaterThanFilter = CoreFilters.isPropertyGreaterThan("test2", "10");

    assertFalse(equalsToFilter.or(greaterThanFilter).matches(user));
  }

  @Test
  public void shouldMatchMultipleOrFilters() {
    user.put("test1", "value1");
    user.put("test2", "15");

    Filter<Map<String, String>> equalsToFilter = CoreFilters.isPropertyEqualTo("test1", "value1");
    Filter<Map<String, String>> greaterThanFilter = CoreFilters.isPropertyGreaterThan("test2", "20");
    Filter<Map<String, String>> lessThanFilter = CoreFilters.isPropertyLessThan("test2", "10");

    assertTrue(equalsToFilter.or(greaterThanFilter).or(lessThanFilter).matches(user));
  }

  @Test
  public void testGenericFilters() {
    Filter<String> filter1 = s -> s.length() > 1;
    Filter<String> filter2 = s -> s.length() < 10;

    assertTrue(filter1.or(filter2).matches("matching"));

    assertTrue(filter1.or(filter2).matches("not matching"));
  }
}
