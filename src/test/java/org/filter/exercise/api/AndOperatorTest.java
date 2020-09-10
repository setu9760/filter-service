package org.filter.exercise.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class AndOperatorTest {
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

    assertTrue(equalsToFilter.and(greaterThanFilter).matches(user));
    assertTrue(greaterThanFilter.and(equalsToFilter).matches(user));
  }

  @Test
  public void shouldNotMatchWhenEitherFilterDoesnt() {
    user.put("test1", "value1");
    user.put("test2", "8");

    Filter<Map<String, String>> equalsToFilter = CoreFilters.isPropertyEqualTo("test1", "value1");
    Filter<Map<String, String>> greaterThanFilter = CoreFilters.isPropertyGreaterThan("test2", "10");

    assertFalse(equalsToFilter.and(greaterThanFilter).matches(user));

    user.put("test1", "value2");
    user.put("test2", "12");

    assertFalse(equalsToFilter.and(greaterThanFilter).matches(user));
  }

  @Test
  public void shouldNotMatchWhenBothFiltersDont() {
    user.put("test1", "value1");
    user.put("test2", "8");

    Filter<Map<String, String>> equalsToFilter = CoreFilters.isPropertyEqualTo("test1", "value1");
    Filter<Map<String, String>> greaterThanFilter = CoreFilters.isPropertyGreaterThan("test2", "10");

    assertFalse(equalsToFilter.and(greaterThanFilter).matches(user));
  }

  @Test
  public void shouldMatchMultipleAndFilters() {
    user.put("test1", "value1");
    user.put("test2", "15");

    Filter<Map<String, String>> equalsToFilter = CoreFilters.isPropertyEqualTo("test1", "value1");
    Filter<Map<String, String>> greaterThanFilter = CoreFilters.isPropertyGreaterThan("test2", "10");
    Filter<Map<String, String>> lessThanFilter = CoreFilters.isPropertyLessThan("test2", "20");

    assertTrue(equalsToFilter.and(greaterThanFilter).and(lessThanFilter).matches(user));
  }

  @Test
  public void testGenericFilters() {
    Filter<String> filter1 = s -> s.length() > 1;
    Filter<String> filter2 = s -> s.length() < 10;

    assertTrue(filter1.and(filter2).matches("matching"));

    assertFalse(filter1.and(filter2).matches("not matching"));
  }
}
