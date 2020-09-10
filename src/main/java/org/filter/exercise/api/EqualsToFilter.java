package org.filter.exercise.api;

import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class EqualsToFilter extends ContainsPropertyFilter {

  private final String target;

  EqualsToFilter(@NotNull String propertyName, @NotNull String target) {
    super(propertyName);
    Objects.requireNonNull(target);
    this.target = target;
  }

  @Override
  public boolean matches(@NotNull Map<String, String> user) {
    Objects.requireNonNull(user);
    return super.matches(user) && target.equalsIgnoreCase(user.get(propertyName));
  }

  @Override
  public String toString() {
    return propertyName + " == " + target;
  }
}
