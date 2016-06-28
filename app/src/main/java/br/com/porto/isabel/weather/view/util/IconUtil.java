package br.com.porto.isabel.weather.view.util;


import android.content.res.Resources;
import android.support.annotation.DrawableRes;

import br.com.porto.isabel.weather.R;

public class IconUtil {

    private Resources mResources;
    private String mPackageName;

    public IconUtil(Resources resources, String packageName) {
        mResources = resources;
        mPackageName = packageName;
    }

    public
    @DrawableRes
    int getWeatherImageResource(String code) {
        String drawableName = "ic_" + code;
        @DrawableRes int drawableResourceId = mResources.getIdentifier(drawableName, "drawable", mPackageName);
        if (drawableResourceId == 0) {
            drawableResourceId = R.drawable.ic_01d; //Default icon
        }
        return drawableResourceId;
    }

    public
    @DrawableRes
    int getWeatherImageLargeResource(String code) {
        String drawableName = "ic_" + code + "_large";
        @DrawableRes int drawableResourceId = mResources.getIdentifier(drawableName, "drawable", mPackageName);
        if (drawableResourceId == 0) {
            drawableResourceId = R.drawable.ic_01d_large; //Default icon
        }
        return drawableResourceId;
    }
}
