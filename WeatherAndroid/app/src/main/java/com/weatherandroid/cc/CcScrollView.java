package com.weatherandroid.cc;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
import android.view.View;
import android.graphics.Rect ;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by Administrator on 2018/3/19.
 */

public class CcScrollView extends PullToRefreshScrollView {

    public CcScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
       //LayoutInflater.from(context).inflate(R.layout.activity_main,this);

    }

}
