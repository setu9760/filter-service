package org.filter.exercise.api;

import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/** */
public class LessThanFilter extends ContainsPropertyFilter {

  private final int target;

  LessThanFilter(@NotNull String propertyKey, @NotNull String target) {
    super(propertyKey);
    Objects.requireNonNull(target);
    this.target = parseInt(target);
  }

  private static int parseInt(String str) {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "NaN value. Tried to convert str '" + str + "' to integer", e);
    }
  }

  @Override
  public boolean matches(@NotNull Map<String, String> user) {
    Objects.requireNonNull(user);
    return super.matches(user) && parseInt(user.get(propertyName)) < target;
  }

  @Override
  public String toString() {
    return propertyName + " < " + target;
  }
}
