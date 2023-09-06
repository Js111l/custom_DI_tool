package org.custom.web.util.either;

import java.util.function.Function;

public final class Left<L, R> extends Either<L, R> {

  private final L value;

  public Left(L value) {
    this.value = value;
  }

  @Override
  public boolean isRight() {
    return false;
  }

  @Override
  public boolean isLeft() {
    return true;
  }

  @Override
  public R getOrElse(Function<L, R> function) {
    return function.apply(this.value);
  }

  @Override
  public <T> Either<L, T> map(Function<? super R, ? extends T> function) {
    return new Left<>(this.value);
  }

  @Override
  public R getOrNull() {
    return null;
  }

  @Override
  public L leftOrNull() {
    return this.value;
  }

  @Override
  public <T> Either<T, R> mapLeft(Function<? super L, ? extends T> mapper) {
    return new Left<>(mapper.apply(this.value));
  }
}
