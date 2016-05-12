package god.codename.brightside;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Loknath Shankar on 5/6/2016.
 */
public class SharedPreferenceRW {
    /**Shared Pred Keys**/
    public static String SLIDERMAX="SLIDERMAX";
    public static String APPKEYS = "APPKEYS" ;
    public static String TOKEN = "TOKEN" ;        //Token helps me to to identify whether data in sabercat has changed or not
    public static String MAXNEWSITEMCOUNT = "MAXNEWSITEMCOUNT" ;
    public static String SLIDERCURRENT="SLIDERCURRENT";
    public static String FONTSIZE="FONTSIZE";
    /**Universal Sharedprederence writer**/
    public void SharedPreferenceWriter(String Key,String Data,Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(APPKEYS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Key,Data);
        editor.apply();
    }

    public String SharedPrefrenceReader(String Key,Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(APPKEYS, Context.MODE_PRIVATE);
        return (sharedpreferences.getString(Key,"NULL"));
    }

    public int SharedPrefrenceReaderInteger(String Key,int Default,Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(APPKEYS, Context.MODE_PRIVATE);
        return (sharedpreferences.getInt(Key,Default));
    }
    public void SharedPreferenceWriterInteger(String Key,int Data,Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(APPKEYS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(Key,Data);
        editor.apply();
    }

    public float SharedPrefrenceReaderFloat(String Key,float Default,Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(APPKEYS, Context.MODE_PRIVATE);
        return (sharedpreferences.getFloat(Key,Default));
    }
    public void SharedPreferenceWriterFloat(String Key,float Data,Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(APPKEYS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat(Key,Data);
        editor.apply();
    }
}
