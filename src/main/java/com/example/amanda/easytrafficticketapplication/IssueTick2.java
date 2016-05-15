package com.example.amanda.easytrafficticketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
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

import java.util.HashMap;
import java.util.Map;

public class IssueTick2 extends AppCompatActivity {
    TextView nametxt;
    TextView adresstxt;
    TextView telephonetxt;
    TextView driTtxt;
    ImageView ban;
    ImageButton btnNext;
    public static final String KEY_USERNAME = "licNo";
    public static final String KEY_USERNAMEE = "licenseNumber";

    String name;
    String address;
    String tel;
    String driType;
    String licenseNumber;
    String name2;
    String address2;
    String tel2;
    String driType2;
    String licenseNumbe2r;
    private static final String nameURL = "http://softizon.tk/Final/name.php";
    private static final String adressURL = "http://softizon.tk/Final/adress.php";
    private static final String telURL = "http://softizon.tk/Final/tel.php";
    private static final String driURL = "http://softizon.tk/Final/dri.php";
    private static final String cfURL = "http://softizon.tk/Final/getfineBool.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_tick2);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        nametxt = (TextView) findViewById(R.id.txtname);
        adresstxt = (TextView) findViewById(R.id.txtadress);
        telephonetxt = (TextView) findViewById(R.id.txttel);
        driTtxt = (TextView) findViewById(R.id.txtDT);
        ban = (ImageView) findViewById(R.id.imgBanner);
        btnNext = (ImageButton) findViewById(R.id.btnNextt);

       findname();
       findAddress();
       findTel();
       findDT();
        findname();
        findAddress();
        findTel();
        findDT();
        blackList();

    }

    private void blackList() {

        final String licNO = getIntent().getStringExtra("a");
        licenseNumber = getIntent().getStringExtra("a");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, cfURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(CheckLic.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        if (res.equals("Yes")) {
                            View b = findViewById(R.id.btnNextt);
                            b.setVisibility(View.VISIBLE);
                            ban.setBackgroundResource(R.drawable.noblac);


                        } else if (res.equals("No")) {
                            View b = findViewById(R.id.btnNextt);
                            b.setVisibility(View.INVISIBLE);
                            ban.setBackgroundResource(R.drawable.blac);


                           // Toast.makeText(payFine.this, "Username or password is wrong please try again", Toast.LENGTH_LONG).show();
                        } else {

                            View b = findViewById(R.id.btnNextt);
                            b.setVisibility(View.VISIBLE);
                            ban.setBackgroundResource(R.drawable.noblac);
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
                map.put(KEY_USERNAMEE, licNO);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void findDT() {

        final String licNO = getIntent().getStringExtra("a");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, driURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(IssueTick2.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        driType2 = res;
                        driTtxt.setText("Driving Types are ");
                        driType = driTtxt.getText() + " " + res;
                        driTtxt.setText(driType);


                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IssueTick2.this, error.toString(), Toast.LENGTH_LONG).show();
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

    private void findTel() {
        final String licNO = getIntent().getStringExtra("a");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, telURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(IssueTick2.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        tel2 = res;
                        telephonetxt.setText("Telephone Number: ");
                        tel = telephonetxt.getText() + " " + res;
                        telephonetxt.setText(tel);


                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IssueTick2.this, error.toString(), Toast.LENGTH_LONG).show();
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

    private void findAddress() {

        final String licNO = getIntent().getStringExtra("a");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, adressURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(IssueTick2.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        address2 = res;
                        adresstxt.setText("Address : ");
                        address = adresstxt.getText() + " " + res;
                        adresstxt.setText(address);


                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IssueTick2.this, error.toString(), Toast.LENGTH_LONG).show();
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

    private void findname() {

        final String licNO = getIntent().getStringExtra("a");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, nameURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(IssueTick2.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        name2 = res;
                        nametxt.setText("Name : ");
                        name = nametxt.getText() + " " + res;
                        nametxt.setText(name);

                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IssueTick2.this, error.toString(), Toast.LENGTH_LONG).show();
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

    public void step3(View v){

        Intent intent = new Intent(this, IssueTick3.class);
        intent.putExtra("name", name2);
        intent.putExtra("licNo", licenseNumber);
        intent.putExtra("adress", address2);
        intent.putExtra("tele", tel2);
        intent.putExtra("driType", driType2);
        startActivity(intent);
    }


}
