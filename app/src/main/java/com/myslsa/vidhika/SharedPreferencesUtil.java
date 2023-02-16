package com.myslsa.vidhika;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtil {

    private static final String MY_PREFS_NAME = "prefs";
    public static final String IMAGE_DATA = "image_data";


    public static void saveBoolean(String key, boolean value, Activity activity){
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key, Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static void saveString(String key, String value, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key, Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(key, null);
    }

    public static void clear(Activity activity) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.clear().commit();
    }

    public static void saveProfilePic(String avatar, Activity activity) {
        SharedPreferences shre = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit=shre.edit();
        edit.putString(IMAGE_DATA,avatar);
        edit.commit();
    }
    public static Bitmap getProfilePic(Activity activity) {
        SharedPreferences shre = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String previouslyEncodedImage = shre.getString(IMAGE_DATA, "");
        if( !previouslyEncodedImage.equalsIgnoreCase("") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return bitmap;
        }
        return null;
    }

    public static void saveInt(String key, int value, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(String key, Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }
}
