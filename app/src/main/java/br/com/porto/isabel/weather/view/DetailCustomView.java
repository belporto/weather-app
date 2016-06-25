package br.com.porto.isabel.weather.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.porto.isabel.weather.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailCustomView extends LinearLayout {

    @BindView(R.id.details_icon)
    ImageView iconImageView;

    @BindView(R.id.details_title)
    TextView titleTextView;

    @BindView(R.id.details_value)
    TextView valueTextView;


    public DetailCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Detail, 0, 0);
        String title = a.getString(R.styleable.Detail_detailTitle);
        int imageResource = a.getResourceId(R.styleable.Detail_detailIcon, 0);

        initView(title, imageResource);
        a.recycle();
    }

    private void initView(String title, @DrawableRes int imageResource) {
        View view = inflate(getContext(), R.layout.details_component, null);
        ButterKnife.bind(this, view);
        iconImageView.setImageResource(imageResource);
        titleTextView.setText(title);
        addView(view);
    }

    public void setValue(String value){
        valueTextView.setText(value);
    }
}
