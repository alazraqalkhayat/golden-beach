package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import static android.view.View.MEASURED_SIZE_MASK;

public class My_gride_view extends GridView {


    public My_gride_view(Context context) {
        super(context);
    }

    public My_gride_view(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public My_gride_view(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public My_gride_view(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Calculate entire height by providing a very large height hint.
        // View.MEASURED_SIZE_MASK represents the largest height possible.
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
