package org.filter.exercise.api;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * Functional Interface providing boolean value filter functionality with one argument.
 * <br /><br />
 * The functional method of this interface is {@link Filter#matches(Object)}
 * <br /><br />
 *
 * <b>Inline custom lambda style filters</b>
 * <pre>
 * {@code
 * Filter<Resource> hasAdminRoleFilter = resource -> resource.getRoles().contains("ADMIN");
 * Filter<Resource> isActiveFilter = resource -> resource.isActive();
 *
 * assert hasAdminRoleFilter.and(isActiveFilter).matches(resource)
 * }
 * </pre>
 * @param <T> Type of the resource to apply the filter
 */
@FunctionalInterface
public interface Filter<T> {

  /**
   * Run this filter for given resource
   * @param resource the resource object
   * @return true if the input argument matches the predicate, otherwise false
   */
  boolean matches(@NotNull T resource);

  /**
   *
   * @param another
   * @return
   */
  default Filter<T> and(@NotNull Filter<T> another) {
    Objects.requireNonNull(another);
    return resource -> this.matches(resource) && another.matches(resource);
  }

  default Filter<T> or(@NotNull Filter<T> another) {
    Objects.requireNonNull(another);
    return resource -> this.matches(resource) || another.matches(resource);
  }

  default Filter<T> not() {
    return resource -> !this.matches(resource);
  }
}
