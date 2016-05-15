package com.example.amanda.easytrafficticketapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Payment2 extends AppCompatActivity {

    TextView ln;
    TextView crme;
    TextView feeee;
    TextView dd;
    ImageView nb;

    String lnn;
    String amtPay;
    String idz;
    String ddn;
    String fz;
    String ccn = "";
    String telz = "";
    public static final String idx = "id";
    private static final String cfURL = "http://softizon.tk/Final/getfineBool.php";
    private static final String telURL = "http://softizon.tk/Final/gettel.php";
    private static final String feeURL = "http://softizon.tk/Final/fee.php";
    private static final String fineURL = "http://softizon.tk/Final/fine.php";
    private static final String idURL = "http://softizon.tk/Final/id.php";
    private static final String paidURL = "http://softizon.tk/Final/paid.php";
    private static final String ddURL = "http://softizon.tk/Final/dd.php";
    public static final String KEY_USERNAME = "licenseNumber";
    private static final String LOGIN_URL2 = "http://softizon.tk/Final/tickamt.php";

    public static final String CrimeName = "pos";

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
        setContentView(R.layout.activity_payment2);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        ln = (TextView) findViewById(R.id.txtl);
        crme = (TextView) findViewById(R.id.txtc);
        feeee = (TextView) findViewById(R.id.txtam);
        dd = (TextView) findViewById(R.id.txtdd);
        nb = (ImageView)findViewById(R.id.imageView9);
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        checkLick2();


        getzID();

    }

    private void checkLick2() {

        final String licNO = getIntent().getStringExtra("a");
       // licenseNumber = getIntent().getStringExtra("a");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, cfURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(CheckLic.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        if (res.equals("Yes")) {
                            ln.setText(licNO.toString());
                            crme.setText("No outstandng tickets");
                            dd.setText("No outstandng tickets");
                            feeee.setText("No outstandng tickets");

                            nb.setBackgroundResource(R.drawable.notick);


                        } else if (res.equals("No")) {
                            ln.setText(licNO.toString());
                            nb.setBackgroundResource(R.drawable.ticketv);
                            View b = findViewById(R.id.imageButton12);
                            b.setVisibility(View.VISIBLE);
                            View bb = findViewById(R.id.imageButton13);
                            bb.setVisibility(View.VISIBLE);
                            getzcrime();
                            getzamount();
                            gettztel();
                            getzdd();
                            getzID();
                            getzamt();
                            


                            // Toast.makeText(payFine.this, "Username or password is wrong please try again", Toast.LENGTH_LONG).show();
                        } else {

                            ln.setText(licNO.toString());
                            crme.setText("No outstandng tickets");
                            dd.setText("No outstandng tickets");
                            feeee.setText("No outstandng tickets");
                            nb.setBackgroundResource(R.drawable.notick);
                        }

                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(payFine.this, "Username or password is wrong please try again", Toast.LENGTH_LONG).show();
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

    private void getzamt() {


        final String licNO = getIntent().getStringExtra("a");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Payment2.this, response.toString(), Toast.LENGTH_LONG).show();
                        String res = response.trim().toString();
                        String ress = response.trim().toString();
                        amtPay = res;
                        //tt.setText(res.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MainActivity.this, "Username or password is wrong please try again", Toast.LENGTH_LONG).show();
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

    private void getzID() {
        final String licNO = getIntent().getStringExtra("a");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, idURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(Payment2.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        idz = res;

                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment2.this, error.toString(), Toast.LENGTH_LONG).show();
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

    private void getzcrime() {

        final String licNO = getIntent().getStringExtra("a");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, fineURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(Payment2.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        ccn = res;
                        crme.setText(ccn.toString());

                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment2.this, error.toString(), Toast.LENGTH_LONG).show();
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

    private void getzamount() {

        final String licNO = getIntent().getStringExtra("a");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, feeURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(Payment2.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        fz = res;
                        feeee.setText(fz.toString());

                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment2.this, error.toString(), Toast.LENGTH_LONG).show();
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

    private void gettztel() {

        final String licNO = getIntent().getStringExtra("a");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, telURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Payment2.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        telz = res;
                        Toast.makeText(Payment2.this, telz.toString(), Toast.LENGTH_LONG).show();
                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment2.this, error.toString(), Toast.LENGTH_LONG).show();
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

    private void getzdd() {

        final String licNO = getIntent().getStringExtra("a");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ddURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(Payment2.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        ddn = res;
                        dd.setText(ddn.toString());

                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment2.this, error.toString(), Toast.LENGTH_LONG).show();
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

    public void payPhysical(View v){
        payfinebymoney();
    }

    private void payfinebymoney() {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, paidURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Payment2.this, "Sucessfully paid", Toast.LENGTH_LONG).show();
                        String sms = "Your traffic ticket worth of " +fz+ " for " + ccn.toString() + " was paid sucessfully. Reference Number " +idz.toString();
                        sendsms(telz, sms);
                        refresh();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment2.this, error.toString(), Toast.LENGTH_LONG).show();
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

    public void onPPay(View v){

        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(Payment2.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);


    }

    private PayPalPayment getThingToBuy(String paymentIntent) {


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
                        payfinebymoney();
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


    private void refresh() {
        Intent intent = new Intent(this, PaymentStart.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
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
