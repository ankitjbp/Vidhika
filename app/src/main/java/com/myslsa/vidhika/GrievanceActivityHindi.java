package com.myslsa.vidhika;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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


public class GrievanceActivityHindi extends BaseActivity {

    RadioGroup rg1;
    RadioButton rb1;

    EditText uid, name, address, phone,details;
  String Uid, Name, Address, Phone,Mobile,Details,district_,selection;
  Button button;
  Boolean valid = true;
  ProgressDialog progressDialog;
  RadioGroup radioGroup;
  RadioButton selectedRadioButton;
  SharedPreferences sharedpreferences;
  Integer help_type;

  private Spinner spinner;
  private  ArrayList<String> districts;
  public JSONArray result;


  @Override
  protected void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_grievance_hindi);

      radioGroup=findViewById(R.id.help_type);

      districts=new ArrayList<String>();
      spinner= (Spinner) findViewById(R.id.districts) ;

//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)this);

      getDataDistricts();




      uid = (EditText) findViewById(R.id.uid);
      name = (EditText) findViewById(R.id.name);
      address = (EditText) findViewById(R.id.address);
      phone = (EditText) findViewById(R.id.phone);
      details= (EditText) findViewById(R.id.details);
      progressDialog = new ProgressDialog(this);
      button = (Button) findViewById(R.id.button);

      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Uid = uid.getText().toString();
              Name = name.getText().toString();
              Address = address.getText().toString();
              Phone = phone.getText().toString();
              Details = details.getText().toString();
              district_ = spinner.getSelectedItem().toString();

              rg1 = (RadioGroup) findViewById(R.id.help_type);
              if(rg1.getCheckedRadioButtonId()!=-1){
                   help_type= rg1.getCheckedRadioButtonId();
                  Log.e("","Help Type Submit by user is  = "+help_type) ;

                  int id= rg1.getCheckedRadioButtonId();
                  View radioButton = rg1.findViewById(id);
                  int radioId = radioGroup.indexOfChild(radioButton);
                  RadioButton btn = (RadioButton) rg1.getChildAt(radioId);
                   selection = (String) btn.getText();
              }


//                int selectedRadioButtonId=radioGroup.getCheckedRadioButtonId();
//                selectedRadioButton = findViewById(selectedRadioButtonId);
//                String selectedRbText = selectedRadioButton.getText().toString();
//
//                int selected=rg1.getCheckedRadioButtonId();
//                rb1=(RadioButton)findViewById(selected);
////                Toast.makeText(GrievanceActivity.this,rb1.getText(),Toast.LENGTH_LONG).show();
//                rg1 = (RadioGroup) findViewById(R.id.help_type);
//                help_type= (String) rb1.getText();

//                RadioGroup radioGroup = findViewById(R.id.help_type);
//
//                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//                {
//                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        switch(checkedId){
//                            case R.id.radio1:
//                                help_type=7;
//                                break;
//                            case R.id.radio2:
//                                help_type=8;
//                                break;
//                            case R.id.radio3:
//                                help_type=9;
//                                break;
//                            case R.id.radio4:
//                                help_type=10;
//                                break;
//                        }
//                        Log.e("","value of help type  = "+help_type) ;
//                    }
//                });

              if(TextUtils.isEmpty(Uid)){
                  uid.setError("Email Cannot be Empty");
                  valid = false;
              }else {
                  valid = true;

                  if(TextUtils.isEmpty(Name)){
                      name.setError("Name Cannot be Empty");
                      valid = false;
                  }else {
                      valid = true;

                      if(TextUtils.isEmpty(Address)){
                          address.setError("Address Cannot be Empty");
                          valid = false;
                      }else {
                          valid = true;

                          if(TextUtils.isEmpty(Phone)){
                              phone.setError("Contact Number Cannot be Empty");
                              valid = false;
                          }else {
                              valid = true;
                          }

                          String str_phone = phone.getText().toString();
                          if (str_phone.length()<10) {
                              phone.setError("Contact Number must be 10 digit long");
                              valid=false;

                          }else {
                              valid=true;
                              String str = uid.getText().toString();
                              if (android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
                                  valid= true;
                              }else {
                                  uid.setError("Email is not valid");
                                  valid=false;
                              }

                          }

                      }

                  }
              }

              if(valid){
                  progressDialog.setMessage("Loading");
                  progressDialog.show();

                  StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD, new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          progressDialog.dismiss();
                          try{
                              JSONObject jsonObject = new JSONObject(response);
                              Toast.makeText(GrievanceActivityHindi.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                              if(jsonObject.getBoolean("success")){

//                                    SharedPreferences sharedPreferences=getSharedPreferences("MpSlsa",MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    editor.putString(Mobile, Phone);
//                                    editor.commit();
//                                    ListActivity.ma.refresh_list();
//                                    Intent intent=new Intent(GrievanceActivity.this, ConfirmSubmitActivity.class);
//                                    startActivity(intent);
//                                    finish();
                                  SharedPreferencesUtil.saveString("phone", Phone.toString(), getApplicationContext());
                                  String phone = SharedPreferencesUtil.getString("phone", getApplicationContext());
//                                    ListActivity.ma.refresh_list();
                                  Intent intent=new Intent(GrievanceActivityHindi.this, ConfirmSubmitActivity.class);
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
                          Toast.makeText(GrievanceActivityHindi.this, "Not Able To Submit Grievance",Toast.LENGTH_SHORT).show();
                      }
                  }){
                      protected Map<String , String> getParams() throws AuthFailureError {
                          Map<String , String> params = new HashMap<>();
                          params.put("name", Name);
                          params.put("uid", Uid);
                          params.put("phone", Phone);
                          params.put("address",Address);
                          params.put("details",Details);
                          params.put("district",district_);
                          params.put("help_type",selection);

//                            params.put("help_type",help_type.toString());
                          return params;
                      }
                  };
                  RequestHandler.getInstance(GrievanceActivityHindi.this).addToRequestQueue(stringRequest);

              }
          }
      });

  }


  private void getDataDistricts(){
      StringRequest stringRequest=new StringRequest(Constants.URL_GET_DISTRICTS,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      JSONObject j=null;
                      try{
                          j=new JSONObject(response);
                          result = j.getJSONArray("data");
                          getDistricts(result);
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
              });

      RequestQueue requestQueue= Volley.newRequestQueue(this);
      requestQueue.add(stringRequest);
  }

  private void getDistricts(JSONArray j){
      for(int i=0;i<j.length();i++){
          try{
              JSONObject json =j.getJSONObject(i);
              districts.add(json.getString("name"));
              }
              catch(JSONException e){
              e.printStackTrace();
          }

      }
  }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item

    }

    //When no item is selected this method would execute

    public void onNothingSelected(AdapterView<?> parent) {

    }

}