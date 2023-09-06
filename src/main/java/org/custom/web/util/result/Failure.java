package org.custom.web.util.result;

import java.util.function.Consumer;
import java.util.function.Function;

public final class Failure<S, F extends Throwable> extends Result<S, F> {

  private final F value;

  public Failure(F value) {
    this.value = value;
  }

  @Override
  public boolean isFailure() {
    return true;
  }

  @Override
  public boolean isSuccess() {
    return false;
  }

  @Override
  public S getOrNull() {
    return null;
  }

  @Override
  public F exceptionOrNull() {
    return this.value;
  }

  @Override
  public S getOrThrow() {
    throw new RuntimeException(this.value);
  }

  @Override
  public <T> Result<T, F> getOrElse(Function<? super F, T> mapper) {
    return of(() -> mapper.apply(this.value));
  }

  @Override
  public <T extends S> T getOrDefault(T defaultValue) {
    return defaultValue;
  }

  @Override
  public <F extends Throwable> void onFailure(Consumer<F> consumer) {
    consumer.accept((F) this.value);
  }

  @Override
  public <T extends Throwable> Result<S, T> mapException(Function<? super F, ? extends T> mapper) {
    return failure(mapper.apply(this.value));
  }

  @Override
  public <T> Result<T, F> map(Function<? super S, ? extends T> mapper) {
    return (Result<T, F>) this;
  }

  @Override
  public <T> Result<T, F> flatMap(
      Function<? super S, ? extends Result<? extends T, F>> mapper) {
    return (Result<T, F>) this;
  }

  @Override
  public <T extends Throwable> Result<S, T> flatMapFailure(
      Function<? super F, ? extends Result<S, ? extends T>> mapper) {
    return (Result<S, T>) mapper.apply(this.value);
  }
}
