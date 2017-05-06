package com.example.sangambasnet.busfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
public class HomeActivity extends Activity {
    private String myroute="";
    private String[] routesplit;
    private String routeSource, routeDestination,mroute="";
    private String routeNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //HashMap<String,String> myroute= new HashMap<String,String>;
        myroute=getIntent().getExtras().getString("route");
        //mroute=myroute;
        //Toast.makeText(this,myroute.toString(),Toast.LENGTH_SHORT).show();
        mroute=myroute.replaceAll("[{}]","");
        // myroute.get()
        routesplit = mroute.split(",");
        Toast.makeText(this,routesplit[0].toString(),Toast.LENGTH_SHORT).show();
        getDataTwo(routesplit);

    }
    private void getDataTwo(String[] rsplit) {
        String routid="";
        String[] ridd;
        //String id = editTextId.getText().toString().trim();
        // if (id.equals("")) {
        //   Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
        //   return;
        //}
        //loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        routid=rsplit[1];
        ridd=routid.split("=");
        routeNo=ridd[1];
        Toast.makeText(this,routeNo.toString(),Toast.LENGTH_SHORT).show();

        //routeSource=rsplit[1];
        //routeDestination=rsplit[2];
        String url = "http://vygen.com.au/busfinder.php?action=routestopss&rid="+routeNo;

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
        // int $i=0;ooo
        //String suburbname="";
        String stopslist="";
        String stopNumber="";
        String RouteNumber="";

        try {
            //JSONArray jsonObject = new JSONArray(response);
            JSONArray jsonArray= new JSONArray(response);
            for(int $j=0;$j<jsonArray.length();$j++) {
                JSONObject jsonObject = jsonArray.getJSONObject($j);
                //JSONArray result = jsonObject.getJSONArray("jsonObject");
                //JSONObject collegeData = result.getJSONObject(0);
                 stopslist = jsonObject.getString(Config.STOPS_LIST);
                 stopNumber = jsonObject.getString("stopid");
                 RouteNumber = jsonObject.getString("routeid");
                //stopslist = jsonObject.getString(Config.STOPS_LIST);

                //address = collegeData.getString(Config.KEY_ADDRESS);
                //vc = collegeData.getString(Config.KEY_VC)
                stopsArray.add(stopslist+" "+RouteNumber+" "+stopNumber);
                //stopsArray.add(stopslist);
                // $i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //textViewResult.setText(busno);
        //textViewResult.setText(rsource);
        //textViewResult.setText(rdestination);


        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, stopsArray);
        EditText inputSearch;
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        /*
        * Search list
        * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs);
            }

            //            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            // @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(MainActivity.this,adapter.getItem(i).toString(),Toast.LENGTH_SHORT).show();
                String fromStop="";
                fromStop=adapter.getItem(i).toString();
                Intent intent = new Intent(HomeActivity.this,ToActivity.class);
                intent.putExtra("from",fromStop);
                startActivity(intent);
            }
        });

    }
}
