package org.filter.exercise.api;

import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class ContainsPropertyFilter implements Filter<Map<String, String>> {

  protected final String propertyName;

  ContainsPropertyFilter(@NotNull String propertyName) {
    Objects.requireNonNull(propertyName);
    this.propertyName = propertyName;
  }

  @Override
  public boolean matches(@NotNull Map<String, String> user) {
    Objects.requireNonNull(user);
    return user.containsKey(propertyName)
        && user.get(propertyName) != null
        && !user.get(propertyName).trim().isEmpty();
  }

  @Override
  public String toString() {
    return "resource contains " + propertyName;
  }
}
