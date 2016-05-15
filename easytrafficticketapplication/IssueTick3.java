package com.example.amanda.easytrafficticketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IssueTick3 extends AppCompatActivity {

    private static final String LOGIN_URL = "http://softizon.tk/Final/fineData.php";
    private static final String LOGIN_URL2 = "http://softizon.tk/Final/fineData2.php";
    private TextView tt;
    public static final String CrimeName = "pos";
    String name = "";
    String address = "";
    String tel = "";
    String driType = "";
    String licenseNumber = "";
    String crime;
    String fee;
    String actFee;
    String dueD;
    String issueD;
    Spinner spn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_tick3);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        tt = (TextView) findViewById(R.id.txtPrice);
        Spinner spnLocale = (Spinner) findViewById(R.id.spinner2);

        spnLocale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Your code here
                String ff = String.valueOf(position);
                findfee(ff);
                findfee2(ff);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        spn = (Spinner) findViewById(R.id.spinner2);

    }

    private void finddate() {
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String tt = String.valueOf(day);
        String ttt = String.valueOf(month);
        String tttt = String.valueOf(year);
        dueD = tttt + "-" + ttt + "-" + tt;
        issueD = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        crime = spn.getSelectedItem().toString();

        licenseNumber = getIntent().getStringExtra("licNo");
        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("adress");
        tel = getIntent().getStringExtra("tele");
        driType = getIntent().getStringExtra("driType");
    }

    private void findfee(final String ff) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(IssueTicket.this, response.toString(), Toast.LENGTH_LONG).show();
                        String res = response.trim().toString();
                        String ress = response.trim().toString();
                        tt.setText(res.toString());
                        fee = res;

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
                map.put(CrimeName, ff.toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void findfee2(final String ff) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         //Toast.makeText(IssueTick3.this, response.toString(), Toast.LENGTH_LONG).show();
                        String res = response.trim().toString();
                        String ress = response.trim().toString();
                        actFee = res;
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
                map.put(CrimeName, ff.toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void next45(View v){

        finddate();

        Intent intent = new Intent(this, IssueTick4.class);
        intent.putExtra("name", name);
        intent.putExtra("licNo", licenseNumber);
        intent.putExtra("adress", address);
        intent.putExtra("tele", tel);
        intent.putExtra("driType", driType);
        intent.putExtra("issueD", issueD);
        intent.putExtra("dueD", dueD);
        intent.putExtra("crime", crime);
        intent.putExtra("fee", fee);
        intent.putExtra("actfee", actFee);
        startActivity(intent);


    }

}
