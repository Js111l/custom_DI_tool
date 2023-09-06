package org.custom.web.util.result;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Result<S, F extends Throwable> {


  private Success<S, F> success;
  private Failure<S, F> failure;

  public static <S, F extends Throwable> Success<S, F> success(S value) {
    return new Success<>(value);
  }

  public static <S, F extends Throwable> Failure<S, F> failure(F exception) {
    return new Failure<>(exception);
  }

  public static <T, F extends Throwable> Result<T, F> of(
      Supplier<T> computation) {
    try {
      var result = computation.get();
      return Result.success(result);
    } catch (Exception exception) {
      return Result.failure((F) exception);
    }
  }

  public abstract boolean isFailure();

  public abstract boolean isSuccess();

  public abstract S getOrNull();

  public abstract F exceptionOrNull();

  public abstract S getOrThrow();

  public abstract <T> Result<T, F> getOrElse(Function<? super F, T> onFailure);

  public abstract <T extends S> T getOrDefault(T defaultValue);

  public abstract <F extends Throwable> void onFailure(Consumer<F> consumer);

  public abstract <T extends Throwable> Result<S, T> mapException(
      Function<? super F, ? extends T> mapper);


  public abstract <T> Result<T, F> map(Function<? super S, ? extends T> mapper);

  public abstract <T> Result<T, F> flatMap(
      Function<? super S, ? extends Result<? extends T, F>> mapper);

  public abstract <T extends Throwable> Result<S, T> flatMapFailure(
      Function<? super F, ? extends Result<S, ? extends T>> mapper);
}
