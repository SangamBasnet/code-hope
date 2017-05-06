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
    public class CustomListFromTo extends BaseAdapter{

        private Activity activity;
        private ArrayList<HashMap<String, String>> data;
        private ArrayList<HashMap<String, String>> filteredData;
        private static LayoutInflater inflater = null;

        HashMap<String, String> fromtoroute = new HashMap<String, String>();

        public CustomListFromTo(Activity a, ArrayList<HashMap<String, String>> d) {
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
                vi = inflater.inflate(R.layout.custom_list_row, null);

            TextView title = (TextView) vi.findViewById(R.id.title);
            TextView artist = (TextView) vi.findViewById(R.id.artist);
            TextView duration = (TextView) vi.findViewById(R.id.duration);


            fromtoroute = filteredData.get(position);

            title.setText(fromtoroute.get("stopf"));
            artist.setText(fromtoroute.get("routeid"));
            duration.setText(fromtoroute.get("stopt"));

            return vi;
        }
    }