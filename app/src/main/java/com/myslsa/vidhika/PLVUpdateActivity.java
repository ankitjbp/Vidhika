package com.myslsa.vidhika;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import java.util.Map;

import static android.R.layout.simple_spinner_item;


public class PLVUpdateActivity extends AppCompatActivity {
    EditText uid, name, address, phone,instructions;
    String Uid, Name, Address, Phone, Id,ASIGNEE,Instructions;
    Button button;
    Boolean valid = true;
    ProgressDialog progressDialog;

    SharedPreferences sharedpreferences;

    private Spinner spinner;
    private  ArrayList<String> dlao;
    public JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_plv);


        progressDialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.button);

        Id = getIntent().getStringExtra("id");
//        uid.setText(getIntent().getStringExtra("uid"));
//        name.setText(getIntent().getStringExtra("name"));
//        address.setText(getIntent().getStringExtra("address"));
//        phone.setText(getIntent().getStringExtra("phone"));

        dlao = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.dlao);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Setting the values to textviews for a selected item
                //ASIGNEE=spinner.getSelectedItem().toString();
                ASIGNEE = spinner.getSelectedItem().toString();
                //   SharedPreferencesUtil.saveString("district", ASIGNEE.toString(), getApplicationContext());
                //   String district = SharedPreferencesUtil.getString("dlao", getApplicationContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SharedPreferencesUtil.saveString("dlao", ASIGNEE.toString(), getApplicationContext());
                String district = SharedPreferencesUtil.getString("dlao", getApplicationContext());
            }
        });
        getDataASIGNEEs();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructions = findViewById(R.id.instructions);
                Instructions=instructions.getText().toString();

                if(TextUtils.isEmpty(Instructions)){
                    uid.setError("Remark Cannot be Empty");
                    valid = false;
                }else {
                    valid = true;

                }

                if(valid){
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_PLV, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(PLVUpdateActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                if(jsonObject.getString("message").equals("Edit Data Successful")){
                                    ListActivity.ma.refresh_list();
                                    finish();
                                }

                                Intent intent=new Intent(PLVUpdateActivity.this,PLVAllotedListActivity.class);
                                startActivity(intent);

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(PLVUpdateActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            SharedPreferences shared = getSharedPreferences("MpSlsa", MODE_PRIVATE);
                            String mobile = shared.getString("Mobile",null);
                            String phone = SharedPreferencesUtil.getString("phone", getApplicationContext());
                            //  params.put("phone", phone);

                            params.put("id", Id);
                            params.put("asignee", ASIGNEE);
                            params.put("instructions", Instructions);
//                            params.put("phone", Phone);
//                            params.put("address",Address);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(PLVUpdateActivity.this).addToRequestQueue(stringRequest);

                }
            }
        });
    }

    private void getDataASIGNEEs(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,Constants.URL_GET_ALL_USERS_FOR_PLV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j=null;
                        try{
                            j=new JSONObject(response);
                            result = j.getJSONArray("data");
                            getASIGNEEs(result);
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences shared = getSharedPreferences("MpSlsa", MODE_PRIVATE);
                String mobile = shared.getString("Mobile",null);
                String phone = SharedPreferencesUtil.getString("phone", getApplicationContext());
                Log.e("value of phone is =",phone);
                params.put("phone", phone);
                return params;
            }
        };


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void getASIGNEEs(JSONArray j){
        for(int i=0;i<j.length();i++){
            try{

                JSONObject json =j.getJSONObject(i);
                dlao.add(json.getString("name"));
                // dlao.add(json.getString("id"));

            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }

        Log.e("","dlao.size()"+dlao.size());

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PLVUpdateActivity.this,simple_spinner_item, dlao);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

//        removeSimpleProgressDialog();
    }
}