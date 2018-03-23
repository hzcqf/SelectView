package com.test.chenqifan.library;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Fan. on 2017/8/1.
 */

public class IconfontView extends AppCompatTextView {

    public IconfontView(Context context) {
        super(context);
        initFont();
    }

    public IconfontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    public IconfontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();
    }

    private void initFont() {
        final Typeface iconfont = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "iconfont.ttf");
        setTypeface(iconfont);
    }

}

