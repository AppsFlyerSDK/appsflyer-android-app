package appsflyer.com.appsflyerandroidsampleapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

public class Deeplink extends AppCompatActivity {



    /* Test this deep link with the link : https://appsflyertestapponelink.onelink.me/iIpC */

    /* Best to check the link using adb from the emulator, or if you are testing on a test device send the link through email */

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deeplink);


        /* Add this call to the tracker on each deep linked activity */

        AppsFlyerLib.getInstance().sendDeepLinkData(this);


        AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {

            /* Returns the attribution data. Note - the same conversion data is returned every time per install */
            @Override
            public void onInstallConversionDataLoaded(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d(AppsFlyerLib.LOG_TAG, "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onInstallConversionFailure(String errorMessage) {
                Log.d(AppsFlyerLib.LOG_TAG, "error onInstallConversionFailure : " + errorMessage);
            }


            /* Called only when a Deep Link is opened */
            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                String attributionDataText = "Attribution Data: \n";
                for (String attrName : conversionData.keySet()) {
                    Log.d(AppsFlyerLib.LOG_TAG, "attribute: " + attrName + " = " +
                            conversionData.get(attrName));
                    attributionDataText += conversionData.get(attrName) + "\n";

                }
                    setAttributionText(attributionDataText);
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(AppsFlyerLib.LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }
        });

    }


    /* Used to display the deep link data returned from onAppOpenAttribution */

    public void setAttributionText(final String i_data){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView attributionTextView = findViewById(R.id.attributionText);
                attributionTextView.setText(i_data);
            }
        } , 2500);
    }

}
