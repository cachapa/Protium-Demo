package com.codingbuffalo.protiumdemo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.codingbuffalo.protiumdemo.R;

import java.util.Locale;

public class CounterView extends TextView {
    private static final String TEMPLATE = "%d of %d";
    
    private int count;
    private int totalCount;
    
    public CounterView(Context context) {
        super(context);
        init(null);
    }
    
    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    
    public CounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CounterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }
    
    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CounterView);
        for (int i = 0; i < typedArray.getIndexCount(); ++i) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CounterView_count:
                    count = typedArray.getInt(attr, 0);
                    break;
                
                case R.styleable.CounterView_totalCount:
                    totalCount = typedArray.getInt(attr, 0);
                    break;
            }
        }
        typedArray.recycle();
        
        updateText();
    }
    
    public void setCount(int count) {
        this.count = count;
        updateText();
    }
    
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        updateText();
    }
    
    private void updateText() {
        setText(String.format(Locale.US, TEMPLATE, count, totalCount));
    }
}
