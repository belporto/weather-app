package br.com.porto.isabel.weather.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.View;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.model.City;


public class CityTouchCallback extends ItemTouchHelper.SimpleCallback {

    private CityListAdapter mAdapter;
    private CityTouchCallbackContract mContract;
    private int mColor;
    private Bitmap mIcon;
    private float margin;

    public CityTouchCallback(CityListAdapter adapter, CityTouchCallbackContract contract, Context context, Resources mResources, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        mAdapter = adapter;
        mContract = contract;
        mIcon = BitmapFactory.decodeResource(mResources, R.drawable.ic_delete);
        mColor = ContextCompat.getColor(context, R.color.colorPrimary);
        margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, mResources.getDisplayMetrics());
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        int position = viewHolder.getAdapterPosition();
        City city = mAdapter.getItem(position);
        mContract.onSwipe(city);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            boolean Left = dX > 0;
            if (Left) {
                drawView(c, dX,
                        (float) itemView.getLeft(),
                        (float) itemView.getLeft() + margin,
                        itemView);
            } else {
                drawView(c, (float) itemView.getRight(),
                        (float) itemView.getRight() + dX,
                        (float) itemView.getRight() - mIcon.getWidth() - margin,
                        itemView);
            }
        }
    }

    private void drawView(Canvas c, float right, float left, float bitmapLeft, View itemView) {
        Paint p = new Paint();
        p.setColor(mColor);
        c.drawRect(left, (float) itemView.getTop(), right, (float) itemView.getBottom(), p);
        float bitmapTop = (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - mIcon.getHeight()) / 2;
        c.drawBitmap(mIcon, bitmapLeft, bitmapTop, p);

    }
}
