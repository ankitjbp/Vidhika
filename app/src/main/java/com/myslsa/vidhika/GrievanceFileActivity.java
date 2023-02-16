package com.myslsa.vidhika;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.location.Location;
import android.location.LocationManager;



public class GrievanceFileActivity extends BaseActivity {

    private FusedLocationProviderClient fusedLocationClient;

    private static final String TAG = LocationConfirmActivity.class.getSimpleName();

    TextView txtLocationResult;
    TextView txtUpdatedOn;
    Button btnStartUpdates;
    Button btnStopUpdates;
    // location last updated time
    private String mLastUpdateTime;
    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    Button button_call_map;


    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;

    private Bitmap bitmap;
    private String filePath;
    ImageView imageView;
    TextView textView;

    EditText uid, name, address, phone,details;
    String Uid, Name, Address, Phone,Mobile,Details;
    Button button,select_file_button;
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
        init();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance_file);
        radioGroup=findViewById(R.id.help_type);

        districts=new ArrayList<String>();
        spinner= (Spinner) findViewById(R.id.districts) ;

//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)this);

        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        getDataDistricts();

        uid = (EditText) findViewById(R.id.uid);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);
        details= (EditText) findViewById(R.id.details);
        progressDialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.button);
        select_file_button = (Button) findViewById(R.id.select_file_button);



        //initializing views
        imageView =  findViewById(R.id.image_view_upload);
        textView =  findViewById(R.id.file_textView);

//        select_file_button.setOnClickListener( View{});

        select_file_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uid = uid.getText().toString();
                Name = name.getText().toString();
                Address = address.getText().toString();
                Phone = phone.getText().toString();
                Details = details.getText().toString();


                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    getLocation();
                }

                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(GrievanceFileActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(GrievanceFileActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(GrievanceFileActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    showFileChooser();

                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uid = uid.getText().toString();
                Name = name.getText().toString();
                Address = address.getText().toString();
                Phone = phone.getText().toString();
                Details = details.getText().toString();


                //call textToSpeech for exercise one
                int selectedRadioButtonId=radioGroup.getCheckedRadioButtonId();
                selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedRbText = selectedRadioButton.getText().toString();

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
                                Toast.makeText(GrievanceFileActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                                    Intent intent=new Intent(GrievanceFileActivity.this, ConfirmSubmitActivity.class);
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
                            Toast.makeText(GrievanceFileActivity.this, "Not Able To Submit Grievance",Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String , String> getParams() throws AuthFailureError {
                            Map<String , String> params = new HashMap<>();


                            params.put("name", Name);
                            params.put("uid", Uid);
                            params.put("phone", Phone);
                            params.put("address",Address);
                            params.put("details",Details);

                            return params;
                        }


                    };
                    RequestHandler.getInstance(GrievanceFileActivity.this).addToRequestQueue(stringRequest);
                }
            }
        });
    }


    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                GrievanceFileActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                GrievanceFileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                    2000,
                    10, locationListener);

            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {

                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

//                double lat = locationGPS.getLatitude();
//                double longi = locationGPS.getLongitude();

                Dexter.withActivity(this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                mRequestingLocationUpdates = true;
                                // startLocationUpdates();
                            }
                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()) {
                                    // open device settings when the permission is
                                    // denied permanently
                                    openSettings();
                                }
                            }
                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                           PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

                double lat = mCurrentLocation.getLatitude();
                double longi =mCurrentLocation.getLatitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                SharedPreferencesUtil.saveString("lat_val", String.valueOf(lat), getApplicationContext());
                SharedPreferencesUtil.saveString("long_val", String.valueOf(longi), getApplicationContext());
//                showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private final LocationListener locationListener= new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double    longitude=location.getLongitude();
            double    latitude=location.getLatitude();
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            if (filePath != null) {
                try {

                    textView.setText("File Selected");
                    Log.d("filePath", String.valueOf(filePath));
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);

                    uploadBitmap(bitmap);

                    imageView.setImageBitmap(bitmap);

                    imageView.getLayoutParams().width = 250;
                    imageView.getLayoutParams().height = 250;
                    imageView.setAdjustViewBounds(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(
                        GrievanceFileActivity.this,"no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.URL_UPLOAD_FILE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            SharedPreferencesUtil.saveString("phone", Phone.toString(), getApplicationContext());
                            String phone = SharedPreferencesUtil.getString("phone", getApplicationContext());
//                                    ListActivity.ma.refresh_list();
                            Intent intent=new Intent(GrievanceFileActivity.this, ConfirmSubmitActivity.class);
                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                    }
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
            @Override
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                String latitude = SharedPreferencesUtil.getString("lat_val", getApplicationContext());
                String longitude = SharedPreferencesUtil.getString("long_val", getApplicationContext());
                params.put("lat_val",latitude);
                params.put("long_val",longitude);

                params.put("name", Name);
                params.put("uid", Uid);
                params.put("phone", Phone);
                params.put("address",Address);
                params.put("details",Details);
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
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


    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
          //      mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
             //   updateLocationUI();
            }
        };
        mRequestingLocationUpdates = false;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

}