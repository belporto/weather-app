package br.com.porto.isabel.weather.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.porto.isabel.weather.view.rx.RecycleViewItemClickListener;

/**
 * Created by isabelporto on 18/10/2016.
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {


    protected RecycleViewItemClickListener<T> mClickListener;

    public BaseHolder(View itemView, RecycleViewItemClickListener<T> clickListener) {
        super(itemView);
        mClickListener = clickListener;
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            mClickListener.onItemClicked(this, v, getData(getAdapterPosition()));
        }
    }

    protected abstract T getData(int adapterPosition);
}