package com.example.sangambasnet.busfinder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by techn on 10/1/2016.
 */
public class CustomStopTimeList extends BaseAdapter{

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<HashMap<String, String>> filteredData;
    private static LayoutInflater inflater = null;

    HashMap<String, String> fromtoroute = new HashMap<String, String>();

    public CustomStopTimeList(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        filteredData = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return filteredData.size();
    }

    public Object getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.custom_time_list, null);

        TextView title = (TextView) vi.findViewById(R.id.stops);
        TextView artist = (TextView) vi.findViewById(R.id.time);



        fromtoroute = filteredData.get(position);

        title.setText(fromtoroute.get("stopn"));
        artist.setText(fromtoroute.get("arrive"));


        return vi;
    }
}