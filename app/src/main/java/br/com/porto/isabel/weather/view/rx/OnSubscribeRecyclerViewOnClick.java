package br.com.porto.isabel.weather.view.rx;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;
import rx.functions.Action1;

public class OnSubscribeRecyclerViewOnClick<T> implements Observable.OnSubscribe<RecyclerClickEvent<T>> {

    private final Action1<RecycleViewItemClickListener<T>> bindingFunction;

    /**
     * @param bindingFunction function that is called when the listener
     *                        needs to be registered to the adapter
     */
    public OnSubscribeRecyclerViewOnClick(Action1<RecycleViewItemClickListener<T>> bindingFunction) {
        this.bindingFunction = bindingFunction;
    }

    @Override
    public void call(Subscriber<? super RecyclerClickEvent<T>> subscriber) {
        final RecycleViewItemClickListener<T> clickListener = (viewHolder, view, t) -> {
            if (subscriber != null && !subscriber.isUnsubscribed()) {
                subscriber.onNext(new RecyclerClickEvent<>(view, t, viewHolder.getAdapterPosition()));
            }
        };

        bindingFunction.call(clickListener);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                bindingFunction.call(null);
            }
        });
    }
}
