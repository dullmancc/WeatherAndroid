package Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherandroid.cc.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */

public class SuggestionAdapter extends ArrayAdapter<Suggestion> {
    private int resourceId;

    public SuggestionAdapter( Context context, int resource,List<Suggestion> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Suggestion suggestion = getItem(position);
        View view;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else {
            view = convertView;
        }
        ImageView sgimage = (ImageView) view.findViewById(R.id.sug_img);
        sgimage.setImageResource(suggestion.getImgid());
        TextView txv1 = (TextView) view.findViewById(R.id.sug_txt1);
        txv1.setText(suggestion.getSuggtextSample());
        TextView txv2 = (TextView) view.findViewById(R.id.sug_txt2);
        txv2.setText(suggestion.getSuggtext());
        return view;
    }
}
