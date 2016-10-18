package br.com.porto.isabel.weather.view.rx;

import android.view.View;

public class RecyclerClickEvent<T> {

    public final View view;
    public final T data;
    public final int position;

    public RecyclerClickEvent(View view, T data, int position) {
        this.view = view;
        this.data = data;
        this.position = position;
    }
}
