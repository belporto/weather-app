package br.com.porto.isabel.weather.view.rx;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

final class SpinnerSelectOnSubscribe<T> implements Observable.OnSubscribe<T> {

  final Spinner mSpinner;

  SpinnerSelectOnSubscribe(Spinner spinner) {
    mSpinner = spinner;
  }

  @Override
  public void call(Subscriber<? super T> subscriber) {
    final AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        T item = (T) parent.getItemAtPosition(position);
        subscriber.onNext(item);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    };

    mSpinner.setOnItemSelectedListener(listener);

    subscriber.add(new MainThreadSubscription() {
      @Override
      protected void onUnsubscribe() {
        mSpinner.setOnItemSelectedListener(null);
      }
    });
  }
}
