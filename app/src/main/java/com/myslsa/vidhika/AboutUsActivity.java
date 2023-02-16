package com.myslsa.vidhika;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AboutUsActivity extends AppCompatActivity {

    public static AboutUsActivity ma;
    protected Cursor cursor;
    ArrayList<Model> thelist;
    ListView listview;
    List<Model> listItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setTitle("Data");
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(AboutUsActivity.this));
        progressDialog = new ProgressDialog(this);
        listItems = new ArrayList<>();
        ma = this;
        refresh_list();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.eng_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {

            Intent tes = new Intent(AboutUsActivity.this, MainActivity.class);
            startActivity(tes);
        }
        return super.onOptionsItemSelected(item);
    }


    public void refresh_list(){
        listItems.clear();
        adapter = new MyAdapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SELECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{

                    progressDialog.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    Toast.makeText(AboutUsActivity.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                       Model item = new Model(
                                o.getString("id"),
                                o.getString("uid"),
                                o.getString("name"),
                                o.getString("phone"),
                                o.getString("address"),
                               o.getString("details"),
                               o.getString("status")
                        );
                        listItems.add(item);

                        adapter = new MyAdapter(listItems,getApplicationContext());
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
                Toast.makeText(AboutUsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("name", "kl");

                SharedPreferences shared = getSharedPreferences("MpSlsa", MODE_PRIVATE);
                String mobile = shared.getString("Mobile",null);
                String phone = SharedPreferencesUtil.getString("phone", getApplicationContext());
                params.put("name", phone);

//               params.put("name", "9755677527");
//               params.put("name", mobile);
//                Log.d(null, mobile);
                return params;
            }
        };
        RequestHandler.getInstance(AboutUsActivity.this).addToRequestQueue(stringRequest);

    }




}
