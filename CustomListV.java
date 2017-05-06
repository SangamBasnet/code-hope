package com.example.sangambasnet.busfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by techn on 9/30/2016.
 */

public class CustomListV extends BaseAdapter implements Filterable {

    private Activity activity;
    private ArrayList<HashMap<String,String>> data;
    private ArrayList<HashMap<String, String>> filteredData;
    private static LayoutInflater inflater=null;

    HashMap<String,String> busroute = new HashMap<String,String>();
    public CustomListV(Activity a, ArrayList<HashMap<String,String>> d) {
        activity = a;
        data=d;
        filteredData = d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.routeid);
        TextView artist = (TextView)vi.findViewById(R.id.source);
        TextView duration = (TextView)vi.findViewById(R.id.destination);
        TextView busno = (TextView)vi.findViewById(R.id.busno);

        busroute = filteredData.get(position);

        title.setText(busroute.get("rid"));
        artist.setText(busroute.get("sourcer"));
        duration.setText(busroute.get("rdestination"));
        busno.setText(busroute.get("busn"));

        return vi;
    }
    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {


                FilterResults results = new FilterResults();

                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = data;
                    results.count = data.size();
                }
                else
                {

                    ArrayList<HashMap<String,String>> filterResultsData = new ArrayList<HashMap<String,String>>();


                        //In this loop, original data is filtered to compare each item to charSequence.

                        if(charSequence.length() > 0)
                        {
                            for(HashMap<String,String> dataa : data)
                            {
                                if(dataa.get("rid").contains(charSequence.toString()) || dataa.get("sourcer").toLowerCase().contains(charSequence.toString())
                                        || dataa.get("rdestination").toLowerCase().contains(charSequence.toString()) || dataa.get("busn").contains(charSequence.toString()))
                                {

                                    filterResultsData.add(dataa);
                                }else if(dataa.get("rid").contains(charSequence.toString()) || dataa.get("sourcer").toUpperCase().contains(charSequence.toString())
                                    || dataa.get("rdestination").toUpperCase().contains(charSequence.toString()) || dataa.get("busn").contains(charSequence.toString()))
                            {

                                filterResultsData.add(dataa);
                            }
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredData = (ArrayList<HashMap<String,String>>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}




