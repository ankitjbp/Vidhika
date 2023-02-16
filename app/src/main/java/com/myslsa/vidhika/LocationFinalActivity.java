package com.myslsa.vidhika;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LocationFinalActivity extends BaseActivity {
    Button alloted_btn;
    String user_type;
    @Override
    protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_location_final);
        alloted_btn = (Button) findViewById(R.id.alloted);

        alloted_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String user_type = SharedPreferencesUtil.getString("user_type", getApplicationContext());
                if(user_type.equals("admin")){
                openDLSAAllotedListActivity();
                }
                else if(user_type.equals("user")){
                    openPLVAllotedListActivity();
                }
            }
        });
    }

    public void openDLSAAllotedListActivity(){
        Intent intent= new Intent(this, DLSAAllotedListActivity.class);
        startActivity(intent);
    }

    public void openPLVAllotedListActivity(){
        Intent intent= new Intent(this, PLVAllotedListActivity.class);
        startActivity(intent);
    }


}
