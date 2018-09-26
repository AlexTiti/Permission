package com.example.apilibrary;

import android.app.Activity;
import android.view.View;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/09/17
 */
public class ActivityViewFinder implements ViewFinder {
    @Override
    public View findView(Object o, int resId) {
        return ((Activity) o).findViewById(resId);
    }
}
