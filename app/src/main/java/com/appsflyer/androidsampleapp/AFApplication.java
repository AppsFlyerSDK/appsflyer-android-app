package com.appsflyer.androidsampleapp;

import android.app.Application;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerLibCore;

import java.util.Map;


/*********************************************************************
 In order for us to provide optimal support,
 we would kindly ask you to submit any issues to support@appsflyer.com
 *********************************************************************/



public class AFApplication extends Application {


    private static final String AF_DEV_KEY = "AF_DEV_KEY";


    @Override
    public void onCreate(){
        super.onCreate();

        /**  Set Up Conversion Listener to get attribution data **/

        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {

            /* Returns the attribution data. Note - the same conversion data is returned every time per install */
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d(AppsFlyerLibCore.LOG_TAG, "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
                setInstallData(conversionData);
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d(AppsFlyerLibCore.LOG_TAG, "error getting conversion data: " + errorMessage);
            }

            /* Called only when a Deep Link is opened */
            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d(AppsFlyerLibCore.LOG_TAG, "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(AppsFlyerLibCore.LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }
        };


        /* This API enables AppsFlyer to detect installations, sessions, and updates. */

        AppsFlyerLib.getInstance().init(AF_DEV_KEY , conversionListener , getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(this);


        /* Set to true to see the debug logs. Comment out or set to false to stop the function */

        AppsFlyerLib.getInstance().setDebugLog(true);

    }



    /* IGNORE - USED TO DISPLAY INSTALL DATA */
    public static String InstallConversionData =  "";
    public static int sessionCount = 0;
    public static void setInstallData(Map<String, Object> conversionData){
        if(sessionCount == 0){
            final String install_type = "Install Type: " + conversionData.get("af_status") + "\n";
            final String media_source = "Media Source: " + conversionData.get("media_source") + "\n";
            final String install_time = "Install Time(GMT): " + conversionData.get("install_time") + "\n";
            final String click_time = "Click Time(GMT): " + conversionData.get("click_time") + "\n";
            final String is_first_launch = "Is First Launch: " + conversionData.get("is_first_launch") + "\n";
            InstallConversionData += install_type + media_source + install_time + click_time + is_first_launch;
            sessionCount++;
        }

    }


}
