package com.weatherandroid.cc;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.Suggestion;
import Model.SuggestionAdapter;

/**
 * Created by Administrator on 2018/3/14.
 */

public class outsug_item extends LinearLayout {
    private int PingmuWidth = 0;

    public outsug_item(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.outsugitem,this);
        PingmuWidth = getResources().getDisplayMetrics().widthPixels;

        LinearLayout tx = (LinearLayout) findViewById(R.id.sug_text);
        tx.setMinimumWidth(PingmuWidth/10*7);
    }

    /**
     *
     */

}
