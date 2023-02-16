package com.myslsa.vidhika;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseHindiActivity extends AppCompatActivity
{
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater=getMenuInflater();
    getMenuInflater().inflate(R.menu.hindi_menu, menu);
    return true;
}

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.e("","Code is running through this part" + id);
        switch(id){

            case R.id.about_us:
                Intent tes2 = new Intent(this, SlsaActivity.class);
                startActivity(tes2);
                return true;
            case R.id.legal_aid:
                Intent tes3 = new Intent(this, LegalAidActivity.class);
                startActivity(tes3);
                return true;
            case R.id.pla:
                Intent tes4 = new Intent(this, PLAActivity.class);
                startActivity(tes4);
                return true;
            case R.id.plv:
                Intent tes5 = new Intent(this, PLVActivity.class);
                startActivity(tes5);
                return true;

            case R.id.register:
                Intent tes6 = new Intent(this, GrievanceActivityHindi.class);
                startActivity(tes6);
                return true;
            case R.id.my_grievances:
                Intent tes7 = new Intent(this, ListActivity.class);
                startActivity(tes7);
                return true;

            default:
                super.onOptionsItemSelected(item);
        }

//        if (id == R.id.add) {
//            Intent tes = new Intent(this, MainActivity.class);
//            startActivity(tes);
//        }

        return super.onOptionsItemSelected(item);
    }



}
