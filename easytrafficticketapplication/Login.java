package com.example.amanda.easytrafficticketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
    private EditText un;
    private EditText pw;
    String username;
    String password;

    public static final String KEY_USERNAME = "regID";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "uType";
    private static final String LOGIN_URL = "http://softizon.tk/Final/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        un = (EditText) findViewById(R.id.uName);
        pw = (EditText) findViewById(R.id.pwd);


    }

    public void loginB(View v){

        String usn = un.getText().toString();
        String pass = pw.getText().toString();

        if(usn.toString().trim().length() <= 0 ||pass.toString().trim().length() <= 0 ){
            Toast.makeText(Login.this, "Enter Username and Password", Toast.LENGTH_LONG).show();
        }else{
            userLogin();
            Toast.makeText(Login.this,usn.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(Login.this,pass.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void userLogin() {
        username = un.getText().toString().trim();
        password = pw.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Login.this, response.toString(), Toast.LENGTH_LONG).show();

                        String res = response.trim().toString();
                        if (res.equals("Traffic") || res.equals("Payment")) {
                            if (res.equals("Traffic")) {
                                Toast.makeText(Login.this, "Went to traffic", Toast.LENGTH_LONG).show();
                                trafNavi();
                            } else if (res.equals("Payment")) {
                                Toast.makeText(Login.this, "Went to payment", Toast.LENGTH_LONG).show();
                                payNavi();
                            }
                        } else {
                            Toast.makeText(Login.this, "Username or password is wrong please try again", Toast.LENGTH_LONG).show();
                            Toast.makeText(Login.this, res.toString(), Toast.LENGTH_LONG).show();
                        }

                        //typeee.setText(response.trim().toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Username or password is wrong please try again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME, username);
                map.put(KEY_PASSWORD, password);
                map.put(KEY_EMAIL, "hgyu");
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void payNavi() {
        Intent intent = new Intent(this, PaymentStart.class);
        startActivity(intent);
    }

    private void trafNavi() {
        Intent intent = new Intent(this, IssueStart.class);
        startActivity(intent);
    }
}
