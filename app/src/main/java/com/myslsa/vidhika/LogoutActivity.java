package com.myslsa.vidhika;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
        SharedPreferencesUtil sharedPreferencesUtil= new SharedPreferencesUtil();
        SharedPreferencesUtil.clear(LogoutActivity.this);
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
