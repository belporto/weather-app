package br.com.porto.isabel.weather.view.rx;

import android.view.View;

import br.com.porto.isabel.weather.view.adapter.BaseHolder;

public interface RecycleViewItemClickListener<Item> {

    void onItemClicked(BaseHolder<Item> viewHolder, View view, Item item);
}
