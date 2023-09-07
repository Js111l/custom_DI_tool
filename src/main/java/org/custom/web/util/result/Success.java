package org.custom.web.util.result;

import java.util.function.Consumer;
import java.util.function.Function;

public final class Success<S, F extends Throwable> extends Result<S, F> {

  private final S value;

  public Success(S value) {
    this.value = value;
  }


  @Override
  public boolean isFailure() {
    return false;
  }

  @Override
  public boolean isSuccess() {
    return true;
  }

  @Override
  public S getOrNull() {
    return this.value;
  }

  @Override
  public F exceptionOrNull() {
    return null;
  }

  @Override
  public S getOrThrow() {
    return this.value;
  }

  @Override
  public <T> Result<T, F> getOrElse(Function<? super F, T> mapper) {
    return (Result<T, F>) this;
  }

  @Override
  public <T extends S> T getOrDefault(T defaultValue) {
    return (T) this.value;
  }

  @Override
  public <F extends Throwable> void onFailure(Consumer<F> consumer) {
    consumer.accept(null);
  }

  @Override
  public <T extends Throwable> Result<S, T> mapException(Function<? super F, ? extends T> mapper) {
    return (Result<S, T>) this;
  }

  @Override
  public <T> Result<T, F> map(Function<? super S, ? extends T> mapper) {
    return success(mapper.apply(this.value));
  }

  @Override
  public <T> Result<T, F> flatMap(
      Function<? super S, ? extends Result<? extends T, F>> mapper) {
    return (Result<T, F>) mapper.apply(this.value);
  }

  @Override
  public <T extends Throwable> Result<S, T> flatMapFailure(
      Function<? super F, ? extends Result<S, ? extends T>> mapper) {
    return (Result<S, T>) this;
  }
}
