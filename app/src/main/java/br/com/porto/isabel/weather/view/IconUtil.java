package br.com.porto.isabel.weather.view;


import android.content.res.Resources;
import android.support.annotation.DrawableRes;

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
        return drawableResourceId;
    }
}
