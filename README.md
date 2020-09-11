## Filter Service

An API that provides a function interface `Filter` to match a resource with given criteria.
The functional method of this interface is `matches`.

With future extensibility and ease of use by external systems, the interface `Filter` is of
generic type `T`. This allows the implementations to generate custom filters for any type of
resource with type safety.

This interface also provides some default method implementations such as `and`, `or`
to allow for chaining multiple filters to validate resource numerous criteria. Another
default method is `not` which simply returns a logical negation of the filter. 

### Usage
#### Custom Filter with complex logic

```java
public class ComplexFilter implements Filter<Resource> {

  // Provide any external dependencies via constructor 
  // if required to evaluate the match

  @Override
  public boolean matches(@NotNull Resource resource) {
    Objects.requireNonNull(resource);
    boolean matches = false;
     // Add logic to check if resource matches
    return matches;
  }
}
```

#### Custom Filter with inline lambda

```java
Filter<Resource> filter = r -> r.isActive(); 
```

#### Using logical operator style default methods

##### _AND_
```java
Filter<String> filter1 = s -> s.length() > 1;
Filter<String> filter2 = s -> s.length() < 10;

assert filter1.and(filter2).matches("matching");
``` 
##### _OR_
```java
Filter<String> filter1 = s -> s.length() > 1;
Filter<String> filter2 = s -> s.length() < 10;

assert filter1.or(filter2).matches("matching");
```
##### _NOT_
```java
Filter<String> filter1 = s -> s.length() > 15;

assert !filter1.not().matches("matching");
```

## CoreFilters

The `CoreFilters` class provides implementation of some core filtering functionalities by
implementing the `Filter` interface. The `Filter`s provided by `CoreFilters` are for type
`Map<String,String>` of resource.    

#### ContainsPropertyFilter

Allows filtering given resource of type `Map<String,String>`, matching if contains given 
propertyName as a key
```java
user.put("test", "value");
Filter<Map<String, String>> filter = CoreFilters.containsProperty("test");
assert filter.matches(user);
```

#### EqualsToFilter

Allows filtering given resource of type `Map<String,String>`, matching if the resource
contains the property, and the value is equal to the given value
```java
user.put("test", "value");
Filter<Map<String, String>> filter = CoreFilters.isPropertyEqualTo("test", "value");
assert filter.matches(user);
```

#### GreaterThanFilter

Allows filtering given resource of type `Map<String,String>`, matching if the resource
contains the property, and the value is greater than the given value. Applicable to integer
types only.
```java
user.put("test", "12");
Filter<Map<String, String>> filter = CoreFilters.isPropertyGreaterThan("test", "10");
assert filter.matches(user);
```

#### LessThanFilter

Allows filtering given resource of type `Map<String,String>`, matching if the resource
contains the property, and the value is less than the given value. Applicable to integer
types only.
```java
user.put("test", "8");
Filter<Map<String, String>> filter = CoreFilters.isPropertyLessThan("test", "10");
assert filter.matches(user);
```

#### RegexPropertyValueFilter

Allows filtering given resource of type `Map<String,String>`, matching if the resource
contains the property, and the value matches to the given regex pattern.
```java
user.put("test", "value with digits 12 in-between");
Filter<Map<String, String>> filter = CoreFilters.valueMatchesRegex("test", "(.)*(\\d)(.)*");
assert filter.matches(user);
```