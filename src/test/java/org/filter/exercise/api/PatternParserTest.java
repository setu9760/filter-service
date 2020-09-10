package org.filter.exercise.api;

import static org.junit.Assert.*;

import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PatternParserTest {


  @Rule
  public ExpectedException expected = ExpectedException.none();

  @Test
  public void shouldReturnGreaterThanFilter(){
    String pattern = "test > 5";
    Filter<Map<String,String>> filter = CoreFilters.fromPattern(pattern);
    assertTrue(filter instanceof GreaterThanFilter);
    assertEquals(pattern, filter.toString());
  }

  @Test
  public void shouldReturnLessThanFilter(){
    String pattern = "test < 5";
    Filter<Map<String,String>> filter = CoreFilters.fromPattern(pattern);
    assertTrue(filter instanceof LessThanFilter);
    assertEquals(pattern, filter.toString());
  }

  @Test
  public void shouldReturnEqualsToFilter(){
    String pattern = "test == 5";
    Filter<Map<String,String>> filter = CoreFilters.fromPattern(pattern);
    assertTrue(filter instanceof EqualsToFilter);
    assertEquals(pattern, filter.toString());
  }

  @Test
  public void shouldReturnContainsPropertyFilter(){
    String pattern = "resource contains test";
    Filter<? extends Map<String, String>> filter = CoreFilters.fromPattern(pattern);
    assertTrue(filter instanceof ContainsPropertyFilter);
    assertEquals(pattern, filter.toString());
  }

  @Test
  public void shouldReturnRegexPropertyValueFilter(){
    String pattern = "test matches 5";
    Filter<Map<String,String>> filter = CoreFilters.fromPattern(pattern);
    assertTrue(filter instanceof RegexPropertyValueFilter);
    assertEquals(pattern, filter.toString());
  }

  @Test
  public void shouldThrowExceptionForUnknownOperator() {
    expected.expect(IllegalArgumentException.class);
    expected.expectMessage("Unsupported operator '!='");
    CoreFilters.fromPattern("test != 5");
  }

  @Test
  public void shouldThrowExceptionForInvalidPattern() {
    expected.expect(IllegalArgumentException.class);
    expected.expectMessage("Tried to generate filter from unsupported pattern 'test > 5 && test < 10'");
    CoreFilters.fromPattern("test > 5 && test < 10");
  }

  @Test
  public void shouldThrowsExceptionForNullPattern(){
    expected.expect(NullPointerException.class);
    CoreFilters.fromPattern(null);
  }
}
