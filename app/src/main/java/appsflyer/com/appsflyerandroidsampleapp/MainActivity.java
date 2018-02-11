package appsflyer.com.appsflyerandroidsampleapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    /*** Ignore -  Used for UI  ***/
    public static String dataToShow = "";
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                eventValue.put(AFInAppEventParameterName.CURRENCY, "USD");
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
                installData.setText(dataToShow);
            }
        } , 2500);

    }


}
