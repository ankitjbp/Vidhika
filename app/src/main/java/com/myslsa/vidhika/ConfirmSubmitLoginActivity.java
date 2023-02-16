package com.myslsa.vidhika;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmSubmitLoginActivity extends BaseActivity {
    Button alloted_btn,accepted;
    String user_type;
    @Override
    protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_confirm_login);
        alloted_btn = (Button) findViewById(R.id.alloted);

        accepted = (Button) findViewById(R.id.accepted);
        String user_type = SharedPreferencesUtil.getString("user_type", getApplicationContext());
        if(user_type.equals("user")){
        accepted.setVisibility(View.GONE);
        }

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


            accepted.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String user_type = SharedPreferencesUtil.getString("user_type", getApplicationContext());
                        openDLSAAcceptedListActivity();
                    }
            });



    }

    public void openDLSAAllotedListActivity(){
        Intent intent= new Intent(this, DLSAAllotedListActivity.class);
        startActivity(intent);
    }
    public void openDLSAAcceptedListActivity(){
        Intent intent= new Intent(this, DLSAAcceptedListActivity.class);
        startActivity(intent);
    }


    public void openPLVAllotedListActivity(){
        Intent intent= new Intent(this, PLVAllotedListActivity.class);
        startActivity(intent);
    }


}
