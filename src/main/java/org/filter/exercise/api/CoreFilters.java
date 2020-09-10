package org.filter.exercise.api;

import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class providing easy access to core filters via static methods.
 *
 */
public final class CoreFilters {

  public static Filter<Map<String, String>> isPropertyGreaterThan(
      @NotNull String propertyName, @NotNull String value) {
    return new GreaterThanFilter(propertyName, value);
  }

  public static Filter<Map<String, String>> isPropertyLessThan(
      @NotNull String propertyName, @NotNull String value) {
    return new LessThanFilter(propertyName, value);
  }

  public static Filter<Map<String, String>> isPropertyEqualTo(
      @NotNull String propertyName, @NotNull String value) {
    Objects.requireNonNull(value);
    return new EqualsToFilter(propertyName, value);
  }

  public static Filter<Map<String, String>> valueMatchesRegex(
      @NotNull String propertyName, @NotNull String regex) {
    Objects.requireNonNull(regex);
    return new RegexPropertyValueFilter(propertyName, regex);
  }

  public static Filter<Map<String, String>> containsProperty(@NotNull String propertyName) {
    Objects.requireNonNull(propertyName);
    return new ContainsPropertyFilter(propertyName);
  }

  /**
   * Utility method to generate filter of type {@code Map<String, String>}
   * based on string expression and operators.
   * <br /><br />
   * Currently supported operators are > , < , == , contains and matches.
   * <br /><br />
   * <b>Example patterns</b>
   * <li> propertyName > 5
   * <li> propertyName < 5
   * <li> propertyName == 5
   * <li> propertyName matches value
   * <li> resource contains propertyName
   *
   * @param pattern string pattern to parse
   * @return Filter of type {@code Map<String, String>}
   */
  public static Filter<Map<String, String>> fromPattern(@NotNull String pattern) {
    // TODO should be able to use some third party string parsing library to extract complex operator combination instead of hardcoded switch cases.
    Objects.requireNonNull(pattern);
    StringTokenizer tokenizer = new StringTokenizer(pattern);
    if (tokenizer.countTokens() != 3) {
      throw new IllegalArgumentException(
          "Tried to generate filter from unsupported pattern '" + pattern + "'");
    }
    String v1 = tokenizer.nextToken();
    String op = tokenizer.nextToken();
    String v2 = tokenizer.nextToken();
    Filter<Map<String, String>> filter = null;
    switch (op) {
      case ">":
        filter = isPropertyGreaterThan(v1, v2);
        break;
      case "<":
        filter = isPropertyLessThan(v1, v2);
        break;
      case "==":
        filter = isPropertyEqualTo(v1, v2);
        break;
      case "contains":
        filter = containsProperty(v2);
        break;
      case "matches":
        filter = valueMatchesRegex(v1, v2);
        break;
      default:
        throw new IllegalArgumentException("Unsupported operator '" + op + "'");
    }
    return filter;
  }

  public static String toPattern(Filter<Map<String, String>> filter) {
    return filter.toString();
  }
}
