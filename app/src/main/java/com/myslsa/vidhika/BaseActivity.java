package com.myslsa.vidhika;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class BaseActivity extends AppCompatActivity
{
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater=getMenuInflater();

//    MenuItem item = menu.getItem(0);
//
//    if (item.getItemId() == R.id.imageView3) {
//        ImageView imageView = new ImageView(this);
//        imageView.setMaxHeight(18);
//        imageView.setMaxWidth(18);
//        imageView.setImageResource(R.drawable.home);
//        item.setActionView(imageView);
//    }

    String user_type = SharedPreferencesUtil.getString("user_type", getApplicationContext());
            Log.e("","value of user_type = "+user_type) ;


            if(user_type!= null){
                if(user_type.equals("admin")){
                    Log.e("","1 Current User type is  = "+user_type) ;

                    getMenuInflater().inflate(R.menu.eng_menu_admin, menu);
                }

                else if(user_type.equals("user")){
                    Log.e("","2 Current User type is  = "+user_type) ;

                    getMenuInflater().inflate(R.menu.eng_menu, menu);
                }
            }
            else {
                Log.e("","3 Non User logged Menu ") ;

                getMenuInflater().inflate(R.menu.eng_menu_without_login, menu);
            }


//    Map<String,?> keys = SharedPreferencesUtil.getAll();
//    for(Map.Entry<String,?> entry : keys.entrySet()){
//        Log.d("map values",entry.getKey() + ": " +
//                entry.getValue().toString());
//    }

    return true;
}

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.e("","Code is running through this part" + id);
        switch(id){

//*            case R.id.register_with_file:
//                Intent tes8 = new Intent(this, GrievanceFileActivity.class);
//                startActivity(tes8);
//                return true;
//            case R.id.about_us:
//                Intent tes2 = new Intent(this, SlsaActivity.class);
//                startActivity(tes2);
//                return true;
//            case R.id.legal_aid:
//                Intent tes3 = new Intent(this, LegalAidActivity.class);
//                startActivity(tes3);
//                return true;
//            case R.id.pla:
//                Intent tes4 = new Intent(this, PLAActivity.class);
//                startActivity(tes4);
//                return true;
//            case R.id.plv:
//                Intent tes5 = new Intent(this, PLVActivity.class);
//                startActivity(tes5);
//                return true;
//            case R.id.location:
//                Intent tes9 = new Intent(this, LocationConfirmActivity.class);
//                startActivity(tes9);
//*                return true;

            case R.id.register:
                Intent tes6 = new Intent(this, GrievanceActivity.class);
                startActivity(tes6);
                return true;

            case R.id.my_grievances:
                Intent tes7 = new Intent(this, ListActivity.class);
                startActivity(tes7);
                return true;
            case R.id.login:
                Intent tes10 = new Intent(this, LoginActivity.class);
                startActivity(tes10);
                return true;
            case R.id.alloted_dlsa:
                Intent tes11 = new Intent(this, DLSAAllotedListActivity.class);
                startActivity(tes11);
                return true;
            case R.id.alloted_plv:
                Intent tes12 = new Intent(this, PLVAllotedListActivity.class);
                startActivity(tes12);
                return true;
            case R.id.logout:
                Intent tes13 = new Intent(this, LogoutActivity.class);
                startActivity(tes13);
                return true;
            case R.id.location_remark:
                Intent tes14 = new Intent(this, LocationConfirmActivity.class);
                startActivity(tes14);
                return true;
            case R.id.my_grievances_completed:
                Intent tes15 = new Intent(this, FinishedGrievanceListActivity.class);
                startActivity(tes15);
                return true;
            case R.id.start_tracking:
                Intent tes16 = new Intent(this, TrackingActivity.class);
                startActivity(tes16);
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


    public void onBackPressed() {
// write your code
    }

}
