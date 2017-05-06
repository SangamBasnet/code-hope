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
import java.util.List;

/**
 * Created by Sangam Basnet on 8/26/2016.
 */
public class FromActivity extends Activity {
    private String myroute="";
    private String[] routesplit;
    private String mroute="";
    private String routeNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from);
        getWindow().getDecorView().clearFocus();
        myroute=getIntent().getExtras().getString("route");
        mroute=myroute.replaceAll("[{}]","");
        routesplit = mroute.split(",");

        getDataTwo(routesplit);

    }
    private void getDataTwo(String[] rsplit) {
        String routid="";
        String[] ridd;

        routid=rsplit[2];
        ridd=routid.split("=");
        routeNo=ridd[1];
        Toast.makeText(this,routeNo.toString(), Toast.LENGTH_SHORT).show();
        String url = "http://vygen.com.au/busfinder.php?action=routestopss&rid="+routeNo;

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

        List<String> stopsArray = new ArrayList<String>();

        String stopslist="";
        String stopNumber="";
        String RouteNumber="",seqNumber="";

        try {

            JSONArray jsonArray= new JSONArray(response);
            for(int $j=0;$j<jsonArray.length();$j++) {
                JSONObject jsonObject = jsonArray.getJSONObject($j);

                stopslist = jsonObject.getString(Config.STOPS_LIST);
                stopNumber = jsonObject.getString("stopid");
                RouteNumber = jsonObject.getString("routeid");
                seqNumber = jsonObject.getString("stopseq");

                stopsArray.add(RouteNumber+" "+stopNumber+" "+seqNumber+"        "+stopslist);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



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

        /*
        * On Click Listener of listview item
        * */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String fromStop="";
                fromStop=adapter.getItem(i).toString();
                Intent intent = new Intent(FromActivity.this,ToActivity.class);
                intent.putExtra("from",fromStop);
                startActivity(intent);
            }
        });

    }
}
