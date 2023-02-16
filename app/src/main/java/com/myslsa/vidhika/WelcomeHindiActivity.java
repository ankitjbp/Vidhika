package com.myslsa.vidhika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class WelcomeHindiActivity extends BaseHindiActivity {

Button button_grv,button_list,home_grv_btn_english;

    @Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome_hindi);
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
        home_grv_btn_english = (Button) findViewById(R.id.home_grv_btn_english);
        home_grv_btn_english.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openWelcomeEnglishActivity();
            }
        });
}

    public void openGrievanceActivity(){
        Intent intent= new Intent(this, GrievanceActivityHindi.class);
        startActivity(intent);
    }

    public void openListActivity(){
        Intent intent= new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void openWelcomeEnglishActivity(){
        Intent intent= new Intent(this, GrievanceActivity.class);
        startActivity(intent);
    }


}
