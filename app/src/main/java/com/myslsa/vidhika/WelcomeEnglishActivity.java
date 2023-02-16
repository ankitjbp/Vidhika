package com.myslsa.vidhika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeEnglishActivity extends BaseActivity {

Button button_grv,button_list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome_english);
        button_grv = (Button) findViewById(R.id.home_grv_btn);
        button_grv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGrievanceActivity();
            }
        });
        button_list = (Button) findViewById(R.id.home_list_btn);
        button_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openListActivity();
            }
        });

}

    public void openGrievanceActivity(){
        Intent intent= new Intent(this, GrievanceActivity.class);
        startActivity(intent);
    }

    public void openListActivity(){
        Intent intent= new Intent(this, ListActivity.class);
        startActivity(intent);
    }

}
