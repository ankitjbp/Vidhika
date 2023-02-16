package com.myslsa.vidhika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DLSAAllotedListActivity extends AppCompatActivity {


    public static DLSAAllotedListActivity ma;
    protected Cursor cursor;
    ArrayList<ModelDlsa> thelist;
    ListView listview;
    List<ModelDlsa> listItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alloted_dlsa);
        getSupportActionBar().setTitle("Pending Grievances");
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(DLSAAllotedListActivity.this));
        progressDialog = new ProgressDialog(this);
        listItems = new ArrayList<>();
        ma = this;
        refresh_list();
    }

    public void refresh_list(){
        listItems.clear();
        adapter = new DLSAAdapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SELECT_ALLOTED_DLSA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{

                    progressDialog.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    Toast.makeText(DLSAAllotedListActivity.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                        ModelDlsa item = new ModelDlsa(
                                o.getString("id"),
                                o.getString("name"),
                                o.getString("number"),
                                o.getString("address"),
                                o.getString("details")
                        );
                        listItems.add(item);

                        adapter = new DLSAAdapter(listItems,getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(DLSAAllotedListActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("name", "kl");

                SharedPreferences shared = getSharedPreferences("MpSlsa", MODE_PRIVATE);
                String mobile = shared.getString("Mobile",null);
                String phone = SharedPreferencesUtil.getString("phone", getApplicationContext());
                params.put("phone", phone);

//               params.put("name", "9755677527");
//               params.put("name", mobile);
//                Log.d(null, mobile);
                return params;
            }
        };
        RequestHandler.getInstance(DLSAAllotedListActivity.this).addToRequestQueue(stringRequest);

    }




}
