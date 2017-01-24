/**
Please also refer to
AppsFlyer's SDK Integration Documentations -
https://support.appsflyer.com/hc/en-us/articles/207032126-AppsFlyer-SDK-Integration-Android
*/

package android.appsflyer.sampleapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.appsflyer.*;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        graphicsHandler(1);

        /**
        #AppsFlyer: Optional methods- Use according to your specific needs. Please refer to the SDK integration documentation for more information

        #AppsFlyer: Please pay special attention to the collection of device IDs

        AppsFlyerLib.getInstance().setImeiData("GET_DEVICE_IMEI");
        AppsFlyerLib.getInstance().setAndroidIdData("GET_DEVICE_ANDROID_ID");
        AppsFlyerLib.getInstance().setCustomerUserId("myId");
        AppsFlyerLib.getInstance().setCurrencyCode("GBP");
        */

        /**
         #AppsFlyer: collecting your GCM project ID by setGCMProjectID allows you to track uninstall data in your dashboard

         AppsFlyerLib.getInstance().setGCMProjectNumber("SENDER_ID");

         Please refer to this documentation for more information:
         https://support.appsflyer.com/hc/en-us/articles/208004986
         */

        /**
         #AppsFlyer: the startTracking method must be called after any optional 'Set' methods
         You can get your AppsFlyer Dev Key from the "SDK Integration" section in your dashboard
         */

        AppsFlyerLib.getInstance().startTracking(getApplication(), "YOUR_DEV_KEY");

        /**
        #AppsFlyer: registerConversionListener implements the collection of attribution (conversion) data
        Please refer to this documentation to view all the available attribution parameters:
        https://support.appsflyer.com/hc/en-us/articles/207032096-Accessing-AppsFlyer-Attribution-Conversion-Data-from-the-SDK-Deferred-Deeplinking
         */
       AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {
            @Override
            public void onInstallConversionDataLoaded(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d(AppsFlyerLib.LOG_TAG, "attribute: " + attrName + " = " +
                            conversionData.get(attrName));
                }
                //SCREEN VALUES//
                final String install_type = "Install Type: " + conversionData.get("af_status");
                final String media_source = "Media Source: " + conversionData.get("media_source");
                final String install_time = "Install Time(GMT): " + conversionData.get("install_time");
                final String click_time = "Click Time(GMT): " + conversionData.get("click_time");
                runOnUiThread(new Runnable() {
                    public void run() {
                         ((TextView) findViewById(R.id.logView)).setText(install_type + "\n" + media_source + "\n" + click_time + "\n" + install_time);
                    }


                });

            }

            @Override
            public void onInstallConversionFailure(String errorMessage) {
                Log.d(AppsFlyerLib.LOG_TAG, "error getting conversion data: " + errorMessage);
                ((TextView) findViewById(R.id.logView)).setText(errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(AppsFlyerLib.LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }
        });


    }

    /**#AppsFlyer: This button initiates a sample (purchase) tracking event which you can then see in your dashboard.
    For additional in app event types see https://support.appsflyer.com/hc/en-us/articles/207032186
     */
    public void buttonOnClick(View v) {
        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.REVENUE, 200);
        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, "category_a");
        eventValue.put(AFInAppEventParameterName.CONTENT_ID, "1234567");
        eventValue.put(AFInAppEventParameterName.CURRENCY, "USD");
        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.PURCHASE, eventValue);

        graphicsHandler(2);
    }

    //Please ignore this function (handles sample app graphics)
    private void graphicsHandler(int c) {
        final Handler handler = new Handler();
        final ImageView img = (ImageView) findViewById(R.id.big_logo);
        final ImageView bg = (ImageView) findViewById(R.id.bg);
        final ImageView check=(ImageView)findViewById(R.id.check);
        final ImageView small_logo=(ImageView)findViewById(R.id.imageView3);
        final TextView sdk_v = (TextView) findViewById(R.id.sdk_v);
        final TextView text = (TextView) findViewById(R.id.textView);
        final TextView header = (TextView) findViewById(R.id.textView2);
        final TextView confirmation = (TextView) findViewById(R.id.textView3);
        final TextView log = (TextView) findViewById(R.id.logView);
        final Button button = (Button) findViewById(R.id.button);

        sdk_v.setText(AppsFlyerLib.getInstance().getSdkVersion());

        if (c == 1) {
            text.setVisibility(View.INVISIBLE);
            log.setVisibility(View.INVISIBLE);
            button.setVisibility(View.INVISIBLE);
            check.setVisibility(View.INVISIBLE);
            confirmation.setVisibility(View.INVISIBLE);
            bg.setVisibility(View.INVISIBLE);
            header.setVisibility(View.INVISIBLE);
            small_logo.setVisibility(View.INVISIBLE);
            sdk_v.setVisibility(View.INVISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setInterpolator(new AccelerateInterpolator());
                    fadeOut.setDuration(1500);
                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationEnd(Animation animation) {
                            img.setVisibility(View.GONE);
                            text.setVisibility(View.VISIBLE);
                            log.setVisibility(View.VISIBLE);
                            button.setVisibility(View.VISIBLE);
                            bg.setVisibility(View.VISIBLE);
                            header.setVisibility(View.VISIBLE);
                            sdk_v.setVisibility(View.VISIBLE);
                            small_logo.setVisibility(View.VISIBLE);
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }

                        public void onAnimationStart(Animation animation) {
                        }
                    });
                    img.startAnimation(fadeOut);

                }
            }, 2500);


        }
        if (c == 2) {
            button.setVisibility(View.INVISIBLE);
            check.setVisibility(View.VISIBLE);
            confirmation.setVisibility(View.VISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setInterpolator(new AccelerateInterpolator());
                    fadeOut.setDuration(1000);
                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationEnd(Animation animation) {
                            check.setVisibility(View.INVISIBLE);
                            confirmation.setVisibility(View.INVISIBLE);
                            button.setVisibility(View.VISIBLE);
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }

                        public void onAnimationStart(Animation animation) {

                        }
                    });
                    check.startAnimation(fadeOut);
                    confirmation.startAnimation(fadeOut);
                }
            }, 3000);
        }

        }


}




