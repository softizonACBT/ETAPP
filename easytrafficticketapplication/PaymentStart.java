package com.example.amanda.easytrafficticketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.microblink.activity.BlinkOCRActivity;
import com.microblink.ocr.ScanConfiguration;
import com.microblink.recognizers.blinkocr.parser.generic.RawParserSettings;

public class PaymentStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_start);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


    }
    public void makePay(View c) {

        Intent intent = new Intent(this, BlinkOCRActivity.class);
        intent.putExtra(BlinkOCRActivity.EXTRAS_LICENSE_KEY, "W6UVOZ6O-EHSHRIJD-BHUOMIFV-3ES46CYJ-4RXXWEMO-GT3DM6FB-MOHXRILD-Q4PX4STC");

// setup array of scan configurations. Each scan configuration
// contains 4 elements: resource ID for title displayed
// in BlinkOCRActivity activity, resource ID for text
// displayed in activity, name of the scan element (used
// for obtaining results) and parser setting defining
// how the data will be extracted.
// For more information about parser setting, check the
// chapter "Scanning segments with BlinkOCR recognizer"



        String amount_title;
        ScanConfiguration[] confArray = new ScanConfiguration[] {
                //new ScanConfiguration(R.string.amount_title, R.string.amount_msg, "Amount", new AmountParserSettings()),
                // new ScanConfiguration(R.string.email_title, R.string.email_msg, "EMail", new EMailParserSettings()),
                new ScanConfiguration(R.string.raw_title, R.string.raw_msg, "Raw", new RawParserSettings())
        };
        intent.putExtra(BlinkOCRActivity.EXTRAS_SCAN_CONFIGURATION, confArray);

// Starting Activity
        startActivityForResult(intent, 10000);
    }

    public void pnn(View c) {
        Intent intent = new Intent(this, Payment2.class);
        String ab = "B2289353";
        intent.putExtra("a", ab);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10000) {
            if (resultCode == BlinkOCRActivity.RESULT_OK && data != null) {
                // perform processing of the data here

                // for example, obtain parcelable recognition result
                Bundle extras = data.getExtras();
                Bundle results = extras.getBundle(BlinkOCRActivity.EXTRAS_SCAN_RESULTS);

                // results bundle contains result strings in keys defined
                // by scan configuration name
                // for example, if set up as in step 1, then you can obtain
                // e-mail address with following line
                String email = results.getString("Raw");

                Toast.makeText(PaymentStart.this, email.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(PaymentStart.this, email.toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, Payment2.class);
                String ab = "B2719700";
                String licNo = email.substring(0,8);
                intent.putExtra("a", licNo);
                intent.putExtra("licNo", ab);
                startActivity(intent);

            }
        }
    }
}
