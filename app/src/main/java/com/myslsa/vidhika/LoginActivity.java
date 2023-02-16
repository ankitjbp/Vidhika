package com.myslsa.vidhika;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;

import android.content.SharedPreferences;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import org.json.JSONArray;

public class LoginActivity  extends BaseActivity {

    EditText uid, password, address, phone,details;
    String Uid, Name, Address, Phone,Mobile,Details,Password;
    Button button;
    Boolean valid = true;
    ProgressDialog progressDialog;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    SharedPreferences sharedpreferences;

    private Spinner spinner;
    private  ArrayList<String> districts;
    public JSONArray result;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);
        details= (EditText) findViewById(R.id.details);
        progressDialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Password = password.getText().toString();
                Phone = phone.getText().toString();

                if(valid){
                    progressDialog.setMessage("Loading");
                    progressDialog.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                if(jsonObject.getBoolean("success")){

                                    SharedPreferencesUtil.saveString("phone", Phone.toString(), getApplicationContext());
                                    SharedPreferencesUtil.saveString("user_type", jsonObject.getString("user_type").toString(), getApplicationContext());

                                    String phone = SharedPreferencesUtil.getString("phone", getApplicationContext());
//                                    ListActivity.ma.refresh_list();
                                    Intent intent=new Intent(LoginActivity.this, ConfirmSubmitLoginActivity.class);
                                    startActivity(intent);
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(LoginActivity.this, "Not able to login due to  technical error",Toast.LENGTH_SHORT).show();
                        }
                    }){
                        protected Map<String , String> getParams() throws AuthFailureError {
                            Map<String , String> params = new HashMap<>();
                            params.put("password", Password);
                            params.put("phone", Phone);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
                }
            }
        });
}



}