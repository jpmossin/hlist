[![Circle CI](https://circleci.com/gh/jpmossin/hlist.svg?style=svg)](https://circleci.com/gh/jpmossin/hlist)

A java.util.List with some useful Higher-order functions.

```java
int example = hlist(asList(1, 2, 3))
        .map(e -> e * 2)
        .filter(e -> e > 2)
        .reduce((sum, e) -> sum + e, 0);
```