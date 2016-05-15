package com.example.amanda.easytrafficticketapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class IssueTickFin extends AppCompatActivity {

    String amtPay;
    String idz;
    String lic;

    public static final String idx = "id";

    public static final String KEY_USERNAME = "licenseNumber";
    private static final String idURL = "http://softizon.tk/Final/id.php";
    private static final String paidURL = "http://softizon.tk/Final/paid.php";

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "ATV5tPDDSexl9RyGIrQ7kzhEtxXJnkVNJYZ9cdJqwQeZ1seOgxOtCe6apnQoXyJAt1hDAS3x6wi5N43o";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_tick_fin);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        getzID();


    }


    public void onlinePay(View v){
        amtPay = getIntent().getStringExtra("fee");

        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(IssueTickFin.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);


    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        amtPay = getIntent().getStringExtra("fee");

        double dd = Double.parseDouble(amtPay);
        double ddd = dd/145;
        String total2 = String.valueOf(ddd);

        return new PayPalPayment(new BigDecimal(total2.toString()), "USD", "sample item",
                paymentIntent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.e("Show", confirm.toJSONObject().toString(4));
                        Log.e("Show", confirm.getPayment().toJSONObject().toString(4));
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        //JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         */
                        //Toast.makeText(getApplicationContext(), paymentDetails.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Payment was Sucessfull" +
                                " from PayPal", Toast.LENGTH_LONG).show();

                        updateTick();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "an extremely unlikely failure" +
                                " occurred:", Toast.LENGTH_LONG).show();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "The user canceled.", Toast.LENGTH_LONG).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(getApplicationContext(), "An invalid Payment or PayPalConfiguration" +
                        " was submitted. Please see the docs.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateTick() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, paidURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(IssueTickFin.this, "Sucessfully paid", Toast.LENGTH_LONG).show();
                        String tl = getIntent().getStringExtra("tel");
                        String sms = "You traffic ticket was sucessfully paid on the spot. Reference Number " + idz.toString();
                        sendsms(tl,sms);
                        refresh();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IssueTickFin.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //(String lic, String fee, String vehN, String driT, String crime, String dueD, String issueD)

                Map<String, String> params = new HashMap<String, String>();
                params.put(idx, idz);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void getzID() {
        final String licNO = getIntent().getStringExtra("licNo");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, idURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(IssueTickFin.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        idz = res;

                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IssueTickFin.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME, licNO);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void refresh() {
        Intent intent = new Intent(this, IssueStart.class);
        startActivity(intent);
    }

    private void sendsms(String tel, String sms) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(tel, null, sms, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
