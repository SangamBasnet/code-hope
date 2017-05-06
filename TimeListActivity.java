package com.example.sangambasnet.busfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;

/**
 * Created by techn on 9/21/2016.
 */
public class TimeListActivity extends Activity {
    private String myroute="";
    private String fromstop="", tostop="";
    private String[] routesplit0;
    private String routeNo, routeSource, routeDestination,fromStopNo,toStopNo;
    private List<String> getRoute = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelist);
        tostop=getIntent().getExtras().getString("tstopid");
        myroute=getIntent().getExtras().getString("ftroute");
        fromstop=getIntent().getExtras().getString("fstopid");

        getRoute.add(myroute);
        getRoute.add(fromstop);
        getRoute.add(tostop);

        getDataTwo(getRoute);

    }
    private void getDataTwo(List<String> rsplit) {

        routeNo=rsplit.get(0);
        fromStopNo=rsplit.get(1);
        toStopNo=rsplit.get(2);

        String url = "http://vygen.com.au/busfinder.php?action=timelist&routeid="+routeNo+"&from="+fromStopNo+"&to="+toStopNo;

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

        List<String> stopsArray = new ArrayList<String>();

        String depart_t="";
        String arrive_t="";


        try {

            JSONArray jsonArray= new JSONArray(response);
            for(int $j=0;$j<jsonArray.length();$j++) {
                JSONObject jsonObject = jsonArray.getJSONObject($j);

                depart_t = jsonObject.getString("depart_time");
                arrive_t = jsonObject.getString("arrive_time");

                stopsArray.add(depart_t+"                                    "+arrive_t);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, stopsArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String valuee="";
                valuee=adapter.getItem(i).toString();
                Intent intent = new Intent(TimeListActivity.this,StopTimeListActivity.class);
                intent.putExtra("fromstoptime",valuee);
                tostop=getIntent().getExtras().getString("tstopid");
                myroute=getIntent().getExtras().getString("ftroute");
                fromstop=getIntent().getExtras().getString("fstopid");
                intent.putExtra("from",fromstop);
                intent.putExtra("to",tostop);
                intent.putExtra("ftroute",myroute);

                startActivity(intent);
            }
        });

    }
}
