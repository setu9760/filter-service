package org.filter.exercise.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class NotOperatorTest {

  private final Map<String, String> user = new LinkedHashMap<>();

  @After
  public void teardown() throws Exception {
    user.clear();
  }

  @Test
  public void shouldNotMatchWhenNotMatchesFilter(){
    user.put("test", "value");

    Filter<Map<String,String>> filter = CoreFilters.valueMatchesRegex("test", "value").not();

    assertFalse(filter.matches(user));
  }

  @Test
  public void shouldMatchForNegativeNotMatchesFilter(){
    user.put("test", "random");

    Filter<Map<String,String>> filter = CoreFilters.valueMatchesRegex("test", "value").not();

    assertTrue(filter.matches(user));
  }
}
