package org.custom.web.util.either;

import java.util.function.Function;

public final class Right<L, R> extends Either<L, R> {

  private final R value;

  public Right(R right) {
    this.value = right;
  }

  @Override
  public boolean isRight() {
    return true;
  }

  @Override
  public boolean isLeft() {
    return false;
  }

  @Override
  public R getOrElse(Function<L, R> function) {
    return this.value;
  }

  @Override
  public <X> Either<L, X> map(Function<? super R, ? extends X> function) {
    return new Right<>(function.apply(this.value));
  }

  @Override
  public R getOrNull() {
    return this.value;
  }

  @Override
  public L leftOrNull() {
    return null;
  }

  @Override
  public <X> Either<X, R> mapLeft(Function<? super L, ? extends X> mapper) {
    return new Right<>(this.value);
  }


}
