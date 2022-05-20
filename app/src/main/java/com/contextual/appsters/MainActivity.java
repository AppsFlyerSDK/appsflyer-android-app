package com.contextual.appsters;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppsFlyerLib.getInstance().setDebugLog(true);
        AppsFlyerLib.getInstance().startTracking(this.getApplication(), "usz4H5kJ4fcMDQdqrjx7Ae");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetInstallDataText();

        Button trackEventButton = findViewById(R.id.trackEventButton);
        trackEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Track Events in real time */
                Map<String, Object> eventValue = new HashMap<String, Object>();
                eventValue.put(AFInAppEventParameterName.REVENUE, 200);
                eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, "category_a");
                eventValue.put(AFInAppEventParameterName.CONTENT_ID, "1234567");
                eventValue.put(AFInAppEventParameterName.CURRENCY, "INR");
                AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.PURCHASE, eventValue);
            }
        });
    }



    /*** Ignore - used to display install data ***/
    public void SetInstallDataText(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView installData = findViewById(R.id.installDataText);
                installData.setText(AFApplication.InstallConversionData);
            }
        } , 5000);

    }

}
