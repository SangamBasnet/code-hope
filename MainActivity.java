package com.example.sangambasnet.busfinder;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().clearFocus();
        setContentView(R.layout.activity_main);
        getData();


    }
    private void getData() {

        String url = "http://vygen.com.au/busfinder.php";

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

    public void showJSON(String response){

        ArrayList<HashMap<String,String>> mobileArray = new ArrayList<HashMap<String,String>>();

        String routeid="";
        String busno="";
        String rsource="";
        String rdestination = "";
        try {

            JSONArray jsonArray= new JSONArray(response);
           for(int $j=0;$j<jsonArray.length();$j++) {
               HashMap<String,String> map = new HashMap<String,String>();
               JSONObject jsonObject = jsonArray.getJSONObject($j);

                routeid=jsonObject.getString(Config.ID_ROUTE);
                busno = jsonObject.getString(Config.BUS_NO);
                rsource = jsonObject.getString(Config.SOURCE);
                rdestination = jsonObject.getString(Config.DESTINATION);
                map.put("rid",routeid);
                map.put("sourcer",rsource);
                map.put("rdestination",rdestination);
                map.put("busn",busno);

               mobileArray.add(map);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

       final CustomListV adapter = new CustomListV(this,mobileArray);
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
        * On Click on listview item Listener
        * */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String valuee="";
                valuee=adapter.getItem(i).toString();
                Intent intent = new Intent(MainActivity.this,FromActivity.class);
                intent.putExtra("route",valuee);

                startActivity(intent);
            }
        });

    }
}