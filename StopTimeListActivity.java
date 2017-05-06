package com.example.sangambasnet.busfinder;

/**
 * Created by techn on 9/22/2016.
 */

import android.app.Activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by techn on 9/21/2016.
 */

public class StopTimeListActivity extends Activity {
    private String myroute="";
    private String fromstop="", tostop="";
    private String[] splitfst;
    private String routeNo,routesplit1,fstopTime,fromStopNo,toStopNo,fromstoptime;
    private List<String> getRoute = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoptimelist);
        fromstop=getIntent().getExtras().getString("from");
        myroute=getIntent().getExtras().getString("ftroute");
        tostop=getIntent().getExtras().getString("to");
        fromstoptime=getIntent().getExtras().getString("fromstoptime");
        splitfst=fromstoptime.split(" ");
        routesplit1=(splitfst[0]+"%20"+splitfst[1]);

        getRoute.add(myroute);
        getRoute.add(fromstop);
        getRoute.add(tostop);
        getRoute.add(routesplit1);

        getDataTwo(getRoute);

    }
    private void getDataTwo(List<String> rsplit) {

        routeNo=rsplit.get(0);
        fromStopNo=rsplit.get(1);
        toStopNo=rsplit.get(2);
        fstopTime=rsplit.get(3);
        Toast.makeText(this,routeNo.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this,fromStopNo.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this,toStopNo.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this,fstopTime.toString(), Toast.LENGTH_SHORT).show();
        String url = "http://vygen.com.au/busfinder.php?action=stoptimelist&routeid="+routeNo+"&from="+fromStopNo+"&to="+toStopNo+"&firststoptime="+fstopTime;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        //List<String> stopsArray = new ArrayList<String>();
        ArrayList<HashMap<String,String>> stopsArray = new ArrayList<HashMap<String,String>>();
        String stop_n="";
        String arrive_t="";


        try {

            JSONArray jsonArray= new JSONArray(response);
            for(int $j=0;$j<jsonArray.length();$j++) {
                HashMap<String,String> map = new HashMap<String,String>();
                JSONObject jsonObject = jsonArray.getJSONObject($j);

                stop_n = jsonObject.getString("stopname");
                arrive_t = jsonObject.getString("stopstime");
                map.put("stopn",stop_n);
                map.put("arrive",arrive_t);
                stopsArray.add(map);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, stopsArray);
        final CustomStopTimeList adapter = new CustomStopTimeList(this,stopsArray);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);


    }
}