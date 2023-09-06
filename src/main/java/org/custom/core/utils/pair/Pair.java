package org.custom.core.utils.pair;

import java.util.List;

public record Pair<L, R>(L left, R right) {

  public static <L, R> Pair<L, R> of(L left, R right) {
    return new Pair<>(left, right);
  }

  public List<Object> toList() {
    return List.of(this.left(), this.right());
  }
}
