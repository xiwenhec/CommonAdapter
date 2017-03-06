package com.sivin.adapter.section.calculation;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * Created by Sivin on 2017/2/17.
 */

public class DimensionCalculator {

    public void initMargins(Rect margins, View view) {

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            initMarginRect(margins, marginLayoutParams);

        } else {
            margins.set(0, 0, 0, 0);
        }
    }

    private void initMarginRect(Rect marginRect, ViewGroup.MarginLayoutParams marginLayoutParams) {

        marginRect.set(
                marginLayoutParams.leftMargin,
                marginLayoutParams.topMargin,
                marginLayoutParams.rightMargin,
                marginLayoutParams.bottomMargin
        );
    }



}
