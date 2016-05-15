package com.example.amanda.easytrafficticketapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class regis extends AppCompatActivity {

    private EditText un;
    private EditText pw;
    private EditText pw2;

    private static final String LOGIN_URL = "http://softizon.tk/Final/register.php";
    private static final String regID = "regID";
    private static final String password = "password";
    private static final String uType = "uType";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);


    }

    public void registerClick(View v){

        String usn = un.getText().toString();
        String pass = pw2.getText().toString();
        String pass2 = pw.getText().toString();

        if(usn.toString().trim().length() <= 0 ||pass.toString().trim().length() <= 0 ||pass2.toString().trim().length() <= 0){
            Toast.makeText(regis.this, "Fill all fields to continue", Toast.LENGTH_LONG).show();
        }else{
            if(pass.equals(pass2)){
                register();
            }else{
                Toast.makeText(regis.this, "Passwords are not matching", Toast.LENGTH_LONG).show();
            }
        }


    }

    private void register() {
        final String reg = un.getText().toString();
        final String passW = pw2.getText().toString();
        Spinner ss = (Spinner) findViewById(R.id.spinner);


        final String ut = ss.getSelectedItem().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(regis.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(regis.this, "This registration number is already registered for this application", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //(String lic, String fee, String vehN, String driT, String crime, String dueD, String issueD)

                Map<String, String> params = new HashMap<String, String>();
                params.put(regID, reg);
                params.put(password, passW);
                params.put(uType, ut);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
