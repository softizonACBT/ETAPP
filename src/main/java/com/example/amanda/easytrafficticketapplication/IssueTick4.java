package com.example.amanda.easytrafficticketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class IssueTick4 extends AppCompatActivity {
TextView lname;
    TextView llic;
    TextView ladress;
    TextView ltel;
    TextView ldt;
    TextView lcrime;
    TextView lfee;
    TextView ldued;
    TextView lissd;
    String name;
    String address;
    String tel;
    String driType;
    String licenseNumber;
    String crime;
    String fee;
    String actFee;
    String dueD;
    String issueD;

    private static final String LOGIN_URL = "http://softizon.tk/Final/insertTicket.php";
    private static final String upURL = "http://softizon.tk/Final/updatePoints.php";


    private static final String licenseNumber2 = "licenseNumber";
    private static final String crimef = "crime";
    private static final String feef = "fee";
    private static final String actFeee = "actFee";
    private static final String drivingTypes = "drivingTypes";
    private static final String issueDv = "issueD";
    private static final String deadLine = "deadLine";
    private static final String telephone = "tele";
    private static final String vehicleNo = "vehicleNo";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_tick4);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        lname = (TextView) findViewById(R.id.txtName);
        llic = (TextView) findViewById(R.id.txtlic);
        ladress = (TextView) findViewById(R.id.txtadress);
        ltel = (TextView) findViewById(R.id.txtTel);
        ldt = (TextView) findViewById(R.id.txtDt);
        lcrime = (TextView) findViewById(R.id.txtCrime);
        lfee = (TextView) findViewById(R.id.txtFee);
        ldued = (TextView) findViewById(R.id.txtDueD);
        lissd = (TextView) findViewById(R.id.txtIDate);

        displayInfo();

    }

    private void displayInfo() {

        licenseNumber = getIntent().getStringExtra("licNo");
        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("adress");
        tel = getIntent().getStringExtra("tele");
        driType = getIntent().getStringExtra("driType");
        crime = getIntent().getStringExtra("crime");
        fee = getIntent().getStringExtra("fee");
        actFee = getIntent().getStringExtra("actfee");
        dueD = getIntent().getStringExtra("dueD");
        issueD = getIntent().getStringExtra("issueD");





        llic.setText("License Number: "+licenseNumber);
        lname.setText("Name: "+name);
       ladress.setText("Address: "+address);
 ltel.setText("Telephone Number: " +tel);
       lcrime.setText("Crime: "+crime);
       lfee.setText("Fee to be paid: "+fee);
       ldued.setText("Issued Date: "+dueD);
       lissd.setText("Due Date: "+issueD);
        ldt.setText("Driving Types: " + driType);



    }

    public void issueFinalize(View v){

      final String  lic = getIntent().getStringExtra("licNo");


        final String  tels = getIntent().getStringExtra("tele");
        final String  driT = getIntent().getStringExtra("driType");
        final String crimez = getIntent().getStringExtra("crime");
        final String  feez = getIntent().getStringExtra("fee");
        final String  actFeez = getIntent().getStringExtra("actfee");
        final String  dueDz = getIntent().getStringExtra("dueD");
        final String issueDz = getIntent().getStringExtra("issueD");
        final String vehN = "Not applicable";

        addToDB(lic, feez, vehN, driT, crimez, dueDz, issueDz, actFeez, tels);
        updatePoints(lic);
        View bb = findViewById(R.id.imageButton10);
        bb.setVisibility(View.VISIBLE);

    }

    private void updatePoints(final String lic) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(issueTick2.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IssueTick4.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //(String lic, String fee, String vehN, String driT, String crime, String dueD, String issueD)

                Map<String, String> params = new HashMap<String, String>();
                params.put(licenseNumber2, lic);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void addToDB(final String lic, final String fee, final String vehN, final String driT, final String crime, final String dueD, final String issueD, final String acf, final String telp) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(IssueTick4.this, response, Toast.LENGTH_LONG).show();
                        String sms = "You have a traffic ticket worth of " + fee + " for " + crime + " payable before " + dueD;
                        sendsms(telp,sms);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IssueTick4.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //(String lic, String fee, String vehN, String driT, String crime, String dueD, String issueD)

                Map<String, String> params = new HashMap<String, String>();
                params.put(licenseNumber2, lic);
                params.put(crimef, crime);
                params.put(feef, fee);
                params.put(actFeee, acf);
               params.put(vehicleNo, vehN);
               params.put(drivingTypes, driT);
               params.put(issueDv, issueD);
               params.put(deadLine, dueD);
               params.put(telephone, telp);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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

    public void finzz(View v){

        actFee = getIntent().getStringExtra("actfee");
        String tl = getIntent().getStringExtra("tele");
        String klic = getIntent().getStringExtra("licNo");

        Intent intent = new Intent(this, IssueTickFin.class);

        intent.putExtra("licNo", klic);
        intent.putExtra("fee", actFee);
        intent.putExtra("tel", tl);

        startActivity(intent);
    }

}
