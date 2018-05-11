package com.weatherandroid.cc;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

/**
 * Created by Administrator on 2018/3/14.
 */

public class air_cloud_wenduView extends LinearLayout {
    private int PingmuWidth = 0;
    public air_cloud_wenduView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.air_cloud_wendu,this);
        PingmuWidth = getResources().getDisplayMetrics().widthPixels;

        //2个导航条
        PingmuWidth = PingmuWidth-20;

        TextView cloudtv = (TextView) findViewById(R.id.cloud);
        cloudtv.setWidth(PingmuWidth/3);
        TextView cloudpowertv = (TextView) findViewById(R.id.cloudpower);
        cloudtv.setWidth(PingmuWidth/3);

        TextView shidutv = (TextView) findViewById(R.id.shidu);
        shidutv.setWidth(PingmuWidth/3);
        TextView shidupowertv = (TextView) findViewById(R.id.shidupower);
        shidupowertv.setWidth(PingmuWidth/3);


        TextView tgtv = (TextView) findViewById(R.id.tgtx);
        tgtv.setWidth(PingmuWidth/3);
        TextView tgpowertv = (TextView) findViewById(R.id.tgwdtx);
        tgpowertv.setWidth(PingmuWidth/3);
    }

}
