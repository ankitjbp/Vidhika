package com.myslsa.vidhika;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

Button button_grv,button_list,login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String user_type = SharedPreferencesUtil.getString("user_type", getApplicationContext());
        Log.e("", "value of user_type = " + user_type);


        if (user_type != null) {
            Log.e("", "1  User is logged ");

            Intent logged_user = new Intent(this, ConfirmSubmitLoginActivity.class);
            startActivity(logged_user);
        } else {
            Log.e("", "2 No User is logged ");

        setContentView(R.layout.activity_main);
        button_grv = (Button) findViewById(R.id.home_grv_btn);


        button_grv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWelcomeHindiActivity();
            }
        });

        button_list = (Button) findViewById(R.id.home_list_btn);
        button_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWelcomeEnglishActivity();
            }
        });

        login_btn = (Button) findViewById(R.id.login);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
    }
    }

    public void openWelcomeEnglishActivity(){
        Intent intent= new Intent(this, GrievanceActivity.class);
        startActivity(intent);
    }

    public void openWelcomeHindiActivity(){
        Intent intent= new Intent(this, WelcomeHindiActivity.class);
        startActivity(intent);
    }
    public void openLoginActivity(){
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



}
