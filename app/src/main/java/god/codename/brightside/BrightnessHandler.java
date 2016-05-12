package god.codename.brightside;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Loknath Shankar on 5/10/2016.
 */
public class BrightnessHandler {
    public boolean BrightnessHandler(final Context context,final Activity activity){
        final SharedPreferenceRW SPRW=new SharedPreferenceRW();
        LayoutInflater linf = LayoutInflater.from(context);
        final View inflator = linf.inflate(R.layout.brightness_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(inflator).show();
        builder.setCancelable(true);
        SeekBar sk=(SeekBar)inflator.findViewById(R.id.seekBar);
        SPRW.SharedPreferenceWriterFloat(SharedPreferenceRW.SLIDERMAX,sk.getMax(),context);
        final TextView tv=(TextView)inflator.findViewById(R.id.textView);
        {
            float arg1=SPRW.SharedPrefrenceReaderFloat(SharedPreferenceRW.SLIDERCURRENT,255,context);
            float BackLightValue = (float)arg1/100;
            WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes(); // Get Params
            layoutParams.screenBrightness = BackLightValue; // Set Value
            activity.getWindow().setAttributes(layoutParams); // Set params
            sk.setProgress((int)arg1);
        }
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                float BackLightValue = (float)arg1/100;
                //int curBrightnessValue=0;
                //try {
                    //curBrightnessValue = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
                //}catch (android.provider.Settings.SettingNotFoundException e){/****/}
                WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes(); // Get Params
                layoutParams.screenBrightness = BackLightValue; // Set Value
                activity.getWindow().setAttributes(layoutParams); // Set params
                SPRW.SharedPreferenceWriterFloat(SharedPreferenceRW.SLIDERCURRENT,arg1,context);
            }
        });
        return true;
    }

    public void RestoreBrightNess(Context context,Activity activity){
        /**RESTORE BRIGHTNESS**/
            float arg1=new SharedPreferenceRW().SharedPrefrenceReaderFloat(SharedPreferenceRW.SLIDERCURRENT,250,context);
            float BackLightValue = arg1/100;
            WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes(); // Get Params
            layoutParams.screenBrightness = BackLightValue; // Set Value
            activity.getWindow().setAttributes(layoutParams); // Set params
        /**END**/
    }
}
