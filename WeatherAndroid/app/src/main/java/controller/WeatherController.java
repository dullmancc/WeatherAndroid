package controller;

import android.util.Log;
import android.widget.TextView;

import com.weatherandroid.cc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.HashMap;

import Tools.SignTools;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/27.
 */

public class WeatherController {
    public static String url = new String("https://free-api.heweather.com/s6/weather/");
    private OkHttpClient client = new OkHttpClient();
    JSONArray  ThreeDay = null;
    /**
     * 请求未来3天数据
     */
    public String GetThreeDays(String city) throws IOException {
        String apiname = "forecast";
        String urlapi = url+apiname;
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("location",city);
        params.put("username", SignTools.userId);
        long ts = System.currentTimeMillis();
        params.put("t",ts+"");
        String sign = SignTools.getSignature(params,SignTools.Key);
        params.put("sign",sign);

      //  urlapi = urlapi+"?location="+city+"&username="+params.get("username")+"&t="+params.get("t")+"&sign="+params.get("sign");
        urlapi = urlapi+"?location="+city+"&key="+SignTools.Key;
        return urlapi;

    }

    public String GetNow(String city) {
        String apiname = "now";
        String urlapi = url + apiname;
        urlapi = urlapi + "?location=" + city + "&key=" + SignTools.Key;

        return urlapi;
    }

    public String GetLifeStyle(String city){
        String apiname = "lifestyle";
        String urlapi = url + apiname;
        urlapi = urlapi + "?location=" + city + "&key=" + SignTools.Key;

        return urlapi;
    }

}
