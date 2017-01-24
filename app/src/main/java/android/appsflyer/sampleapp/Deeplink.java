/**
Please also refer to
AppsFlyer's SDK Integration Documentations -
https://support.appsflyer.com/hc/en-us/articles/207032126-AppsFlyer-SDK-Integration-Android
*/

package android.appsflyer.sampleapp;
import android.content.pm.ActivityInfo;
import android.os.Bundle;;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;


public class Deeplink extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deeplink);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        graphicsHandler();

        /**
        #AppsFlyer: sendDeepLinkData method must be the first one the be called in the onCreate() of your Deep Link activity
        Please refer to your AndroidManifest.xml for AppsFlyer deep links for the sample app
         **/
        AppsFlyerLib.getInstance().sendDeepLinkData(this);


        AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {
            @Override
            public void onInstallConversionDataLoaded(Map<String, String> conversionData) {
            }

            @Override
            public void onInstallConversionFailure(String errorMessage) {
                //
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d(AppsFlyerLib.LOG_TAG, "attribute: " + attrName + " = " +
                            conversionData.get(attrName));
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(AppsFlyerLib.LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }
        });
    }


    //Please ignore this function (handles sample app graphics)
    private void graphicsHandler() {
        final Handler handler = new Handler();
        final ImageView small = (ImageView) findViewById(R.id.small_logo);
        final ImageView big = (ImageView) findViewById(R.id.big_logo);
        final ImageView bg =  (ImageView) findViewById(R.id.bg);
        final TextView text = (TextView) findViewById(R.id.textView);
        final TextView link = (TextView) findViewById(R.id.link);
        final TextView header= (TextView) findViewById(R.id.header);

        header.setVisibility(View.INVISIBLE);
        bg.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);
        small.setVisibility(View.INVISIBLE);
        link.setVisibility(View.INVISIBLE);

        link.setText(this.getIntent().getDataString());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setInterpolator(new AccelerateInterpolator());
                fadeOut.setDuration(1500);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationEnd(Animation animation) {
                        text.setVisibility(View.VISIBLE);
                        small.setVisibility(View.VISIBLE);
                        big.setVisibility(View.INVISIBLE);
                        link.setVisibility(View.VISIBLE);
                        bg.setVisibility(View.VISIBLE);
                        header.setVisibility(View.VISIBLE);
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }
                });
                big.startAnimation(fadeOut);

            }
        }, 2500);
    }
}

