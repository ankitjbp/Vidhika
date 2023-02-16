package com.myslsa.vidhika;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailData extends AppCompatActivity {
    TextView uid, name, address, phone,details,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);

        uid = (TextView) findViewById(R.id.uid);
        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);
        phone = (TextView) findViewById(R.id.phone);
        details = (TextView) findViewById(R.id.details);
        status = (TextView) findViewById(R.id.status);

        uid.setText(getIntent().getStringExtra("uid"));
        name.setText(getIntent().getStringExtra("name"));
        address.setText(getIntent().getStringExtra("address"));
        phone.setText(getIntent().getStringExtra("phone"));
        details.setText(getIntent().getStringExtra("details"));
        status.setText(getIntent().getStringExtra("status"));
    }
}
