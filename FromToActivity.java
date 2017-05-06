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
import java.util.HashMap;
import java.util.List;


/**
 * Created by Sangam Basnet on 8/26/2016.
 */
public class FromToActivity extends Activity {
    private String myroute="";
    private String mystop="", tostop="";
    private String[] routesplit0;
    private String routeNo,fromStopNo,toStopNo;
    private List<String> routesplit = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fromto);
        tostop=getIntent().getExtras().getString("route");
        myroute=getIntent().getExtras().getString("routeNo1");
        mystop=getIntent().getExtras().getString("stopNo1");

        routesplit0 = tostop.split(" ");
        routesplit.add(routesplit0[0]);
        routesplit.add(routesplit0[1]);
        routesplit.add(myroute);
        routesplit.add(mystop);
        Toast.makeText(this,tostop.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this,myroute.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this,mystop.toString(), Toast.LENGTH_SHORT).show();
        getDataTwo(routesplit);

    }
    private void getDataTwo(List<String> rsplit) {

        routeNo=rsplit.get(2);
        fromStopNo=rsplit.get(3);
        toStopNo=rsplit.get(0);
        Toast.makeText(this,routeNo.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,fromStopNo.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,toStopNo.toString(),Toast.LENGTH_SHORT).show();
        String url = "http://vygen.com.au/busfinder.php?action=ftStop&routeid="+routeNo+"&from="+fromStopNo+"&to="+toStopNo;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
        ArrayList<HashMap<String,String>> stopsArray = new ArrayList<HashMap<String,String>>();

        String stopfrom="";
        String stopto="";
        String routeID="";

        try {

            JSONArray jsonArray= new JSONArray(response);
            for(int $j=0;$j<jsonArray.length();$j++) {
                HashMap<String,String> map = new HashMap<String,String>();
                JSONObject jsonObject = jsonArray.getJSONObject($j);

                stopfrom = jsonObject.getString("fromstop");
                routeID = jsonObject.getString("routeidd");
                stopto = jsonObject.getString("tostop");
                map.put("stopf",stopfrom);
                map.put("routeid",routeID);
                map.put("stopt",stopto);

                stopsArray.add(map);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final CustomListFromTo adapter = new CustomListFromTo(this,stopsArray);;

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                 String valuee="", stopfromid="",stoptoid="",ftrid="";
                 String[] fromToSplit;

                Intent intent = new Intent(FromToActivity.this,TimeListActivity.class);

                tostop=getIntent().getExtras().getString("route");
                fromToSplit=tostop.split(" ");
                stoptoid=fromToSplit[0];
                ftrid=getIntent().getExtras().getString("routeNo1");
                stopfromid=getIntent().getExtras().getString("stopNo1");
                intent.putExtra("fstopid",stopfromid);
                intent.putExtra("tstopid",stoptoid);
                intent.putExtra("ftroute",ftrid);
                startActivity(intent);
            }
        });

    }
}

