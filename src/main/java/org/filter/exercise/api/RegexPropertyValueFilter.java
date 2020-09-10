package org.filter.exercise.api;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.jetbrains.annotations.NotNull;

public class RegexPropertyValueFilter extends ContainsPropertyFilter {

  private final String propertyKey;
  private final Pattern valueRegex;

  RegexPropertyValueFilter(@NotNull String propertyKey, @NotNull String valueRegex) {
    super(propertyKey);
    Objects.requireNonNull(valueRegex);
    this.propertyKey = propertyKey;
    this.valueRegex = parseRegex(valueRegex);
  }

  @Override
  public boolean matches(@NotNull Map<String, String> user) {
    Objects.requireNonNull(user);
    return super.matches(user) && valueRegex.matcher(user.get(propertyKey)).matches();
  }

  private Pattern parseRegex(String valueRegex) {
    try {
      return Pattern.compile(valueRegex);
    } catch (PatternSyntaxException e) {
      throw new IllegalArgumentException(
          "String '" + valueRegex + "' fails to compile into regex", e);
    }
  }

  @Override
  public String toString() {
    return propertyName + " matches " + valueRegex.pattern();
  }
}
