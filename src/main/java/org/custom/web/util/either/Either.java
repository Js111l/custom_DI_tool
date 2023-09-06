package org.custom.web.util.either;

import java.util.function.Function;

public abstract class Either<L, R> {

  public Either() {

  }

  public static <L, R> Either<L, R> left(L left) {
    return new Left<>(left);
  }

  public static <L, R> Either<L, R> right(R right) {
    return new Right<>(right);
  }


  public abstract boolean isRight();

  public abstract boolean isLeft();

  public abstract R getOrElse(Function<L, R> function);

  public abstract <T> Either<L, T> map(Function<? super R, ? extends T> function);

  public abstract R getOrNull();

  public abstract L leftOrNull();

  public abstract <T> Either<T, R> mapLeft(Function<? super L, ? extends T> mapper);
}
