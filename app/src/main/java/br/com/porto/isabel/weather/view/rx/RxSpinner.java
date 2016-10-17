package br.com.porto.isabel.weather.view.rx;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Spinner;

import rx.Observable;
import rx.functions.Action1;

import static com.jakewharton.rxbinding.internal.Preconditions.checkNotNull;

public final class RxSpinner {

  /**
   * Create an observable of select item events
   * <p>
   * <em>Warning:</em> The created observable keeps a strong reference to {@code view}. Unsubscribe
   * to free this reference.
   */
  @CheckResult @NonNull
  public static <T> Observable<T> selectItem(@NonNull Spinner spinner) {
    return Observable.create(new SpinnerSelectOnSubscribe<T>(spinner));
  }

  private RxSpinner() {
    throw new AssertionError("No instances.");
  }
}