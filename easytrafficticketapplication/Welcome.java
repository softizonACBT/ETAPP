package com.example.amanda.easytrafficticketapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

public class Welcome extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        VideoView vs;
        vs = (VideoView)findViewById(R.id.vv2);
        String uiP = "android.resource://com.example.amanda.easytrafficticketapplication/"+ R.raw.welcc;
        vs.setVideoURI(Uri.parse(uiP));

        vs.start();

    }

    public void naviLog(View v){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }

    public void naviReg(View v){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);

    }

}
