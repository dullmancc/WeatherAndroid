package com.weatherandroid.cc;

import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.Suggestion;
import Model.SuggestionAdapter;
import Tools.Utils;
import controller.WeatherController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends CheckPermission implements AMapLocationListener, Callback{
    WeatherController weatherController = new WeatherController();
    private OkHttpClient client = new OkHttpClient();
    private OkHttpClient nowclient = new OkHttpClient();
    private OkHttpClient lifeclient = new OkHttpClient();
    JSONArray  ThreeDay = null;
    JSONObject  Now = null;
    JSONArray LifeStyle = null;
    public AMapLocationClient mapLocation = null;

    AMapLocation location;
    public AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
    private List<Suggestion> clothsuggestionList = new ArrayList<>();
    private List<Suggestion> carsuggestionList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e("MainActivity","Enter start 0");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitLocation();

        Log.e("MainActivity","Location start");
        SuggestionAdapter clothadapter = new SuggestionAdapter(MainActivity.this,R.layout.outsugitem,clothsuggestionList);
        SuggestionAdapter caradapter = new SuggestionAdapter(MainActivity.this,R.layout.outsugitem,carsuggestionList);
        ListView clothlistView = (ListView) findViewById(R.id.outsug);
        clothlistView.setAdapter(clothadapter);
        ListView carlistView = (ListView) findViewById(R.id.drivecarsug);
        carlistView.setAdapter(caradapter);

    }

    private void InitLocation(){
        mapLocation = new AMapLocationClient(getApplicationContext());
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setHttpTimeOut(20000);
        if(null!=mapLocation){
            mapLocation.setLocationOption(mLocationOption);
            mapLocation.setLocationListener(this);
            mapLocation.stopLocation();
            mapLocation.startLocation();
        }
    }

    private Handler handler_now = new Handler(){
        @Override
        public  void handleMessage(Message msg){
            super.handleMessage(msg);
            try {
                JSONObject message = new JSONObject(msg.obj.toString());
                TextView wendu = (TextView) findViewById(R.id.Temperturetx);
                wendu.setText(message.getString("tmp"));
                TextView weather = (TextView) findViewById(R.id.weat);
                weather.setText(message.getString("cond_txt"));
                TextView cloud = (TextView) findViewById(R.id.cloud);
                cloud.setText(message.getString("wind_dir"));
                TextView cloudpower = (TextView) findViewById(R.id.cloudpower);
                cloudpower.setText(message.getString("wind_sc"));
                TextView hum = (TextView) findViewById(R.id.shidupower);
                hum.setText(message.getString("hum")+"%");
                TextView tgtx = (TextView) findViewById(R.id.tgwdtx);
                tgtx.setText(message.getString("fl")+"℃");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    };

    private  Handler handler_setLifeStyle = new Handler(){
        @Override
        public  void handleMessage(Message msg){
            super.handleMessage(msg);
            try {
                JSONArray message = new JSONArray(msg.obj.toString());

                Suggestion s1 = new Suggestion("舒适度指数:"+message.getJSONObject(1).getString("brf"),message.getJSONObject(1).getString("txt"),R.drawable.an2);
                Suggestion s2 = new Suggestion("紫外线指数:"+message.getJSONObject(5).getString("brf"),message.getJSONObject(1).getString("txt"),R.drawable.ajg);
                Suggestion s3 = new Suggestion("洗车指数:"+message.getJSONObject(6).getString("brf"),message.getJSONObject(6).getString("txt"),R.drawable.abc);
                clothsuggestionList.add(s1);
                clothsuggestionList.add(s2);
                carsuggestionList.add(s3);
                SuggestionAdapter clothadapter = new SuggestionAdapter(MainActivity.this,R.layout.outsugitem,clothsuggestionList);
                SuggestionAdapter caradapter = new SuggestionAdapter(MainActivity.this,R.layout.outsugitem,carsuggestionList);
                ListView outsug = (ListView) findViewById(R.id.outsug);
                outsug.setAdapter(clothadapter);
                ListView drivecarsug = (ListView) findViewById(R.id.drivecarsug);
                drivecarsug.setAdapter(caradapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    //非主线程更新ui会导致app崩溃
    private Handler handler_setdate =  new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                JSONArray message = new JSONArray(msg.obj.toString());
                TextView today = (TextView) findViewById(R.id.todayweather);
                TextView todaywendu = (TextView) findViewById(R.id.todaywendu);
                ImageView todayimg = (ImageView) findViewById(R.id.todayweatherlogo);
                Resources resources = getApplicationContext().getResources();
                int resID = getResources().getIdentifier("c"+message.getJSONObject(0).getString("cond_code_d"), "drawable","com.weatherandroid.cc");
                today.setText(message.getJSONObject(0).getString("cond_txt_d"));
                todaywendu.setText(message.getJSONObject(0).getString("tmp_max")+"/"+message.getJSONObject(0).getString("tmp_min")+"℃");
                todayimg.setImageResource(resID);

                TextView tomor = (TextView) findViewById(R.id.tomordayweather);
                TextView tomortmp = (TextView) findViewById(R.id.tomorwendu);
                ImageView tomorimg = (ImageView) findViewById(R.id.tomordayweatherlogo);
                tomor.setText(message.getJSONObject(1).getString("cond_txt_d"));
                tomortmp.setText(message.getJSONObject(1).getString("tmp_max")+"/"+message.getJSONObject(1).getString("tmp_min")+"℃");
                int tomorresID = getResources().getIdentifier("c"+message.getJSONObject(1).getString("cond_code_d"), "drawable","com.weatherandroid.cc");
                tomorimg.setImageResource(tomorresID);

                TextView houtian = (TextView) findViewById(R.id.houtianweather);
                TextView houtianwendu = (TextView) findViewById(R.id.houtianwendu);
                ImageView houtianimg = (ImageView) findViewById(R.id.houtianweatherlogo);
                houtian.setText(message.getJSONObject(2).getString("cond_txt_d"));
                houtianwendu.setText(message.getJSONObject(2).getString("tmp_max")+"/"+message.getJSONObject(2).getString("tmp_min")+"℃");
                int houtianresId = getResources().getIdentifier("c"+message.getJSONObject(2).getString("cond_code_d"), "drawable","com.weatherandroid.cc");
                houtianimg.setImageResource(tomorresID);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    };


    public  void InitWeather(String urlapi){

        Request request = new Request.Builder().url(urlapi).build();
        Log.e("MainActivity","Okhttp call :"+urlapi);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Weathercontroll","Okhttp call fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    Log.e("Weathercontroll","Successed: "+responseData);
                    ThreeDay = new JSONObject(responseData).getJSONArray("HeWeather6").getJSONObject(0).getJSONArray("daily_forecast");


                    Message msg = new Message();
                    msg.obj= ThreeDay;
                    handler_setdate.sendMessage(msg);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void InitNow(String now){
        Request request = new Request.Builder().url(now).build();
        Log.e("MainActivity","Okhttp call :"+now);
        nowclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Weathercontroll","Okhttp call fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    Log.e("Weathercontroll","Successed: "+responseData);
                    Now = new JSONObject(responseData).getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now");

                    Message msg = new Message();
                    msg.obj= Now;
                    handler_now.sendMessage(msg);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initTitleScroll(){
        PullToRefreshScrollView pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.Frame);

        pullToRefreshScrollView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ScrollView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ScrollView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
                Log.e("MainActivity","this doing update");
            }
        });

        pullToRefreshScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

            }
        });
    }

    private void initSuggestion(String lifestyle){

        Request request = new Request.Builder().url(lifestyle).build();
        Log.e("MainActivity","Okhttp call :"+lifestyle);
        lifeclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Weathercontroll","Okhttp call fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    Log.e("Weathercontroll","Successed: "+responseData);
                    LifeStyle = new JSONObject(responseData).getJSONArray("HeWeather6").getJSONObject(0).getJSONArray("lifestyle");

                    Message msg = new Message();
                    msg.obj= LifeStyle;
                    handler_setLifeStyle.sendMessage(msg);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation!=null){
            if (aMapLocation.getErrorCode() == 0) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setMessage(aMapLocation.getCity());
                alertDialog.setTitle(R.string.alerttitle);
                String result = aMapLocation.getCity()+aMapLocation.getDistrict();
                Log.e("MainActivity","location successed: "+result);
                TextView tv = (TextView) findViewById(R.id.LocationPtx);
                tv.setText(result);
                try {
                    InitWeather(weatherController.GetThreeDays(aMapLocation.getCity()));
                    InitNow(weatherController.GetNow(aMapLocation.getCity()));
                    initSuggestion(weatherController.GetLifeStyle(aMapLocation.getCity()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
