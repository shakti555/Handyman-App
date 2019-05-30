package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void handymanlog(View view){
        Intent intent=new Intent(WelcomeActivity.this,HandyManLogActivity.class);
        startActivity(intent);
    }

    public void customerlog(View view){
        Intent intent=new Intent(WelcomeActivity.this,CdashBoardActivity.class);
        startActivity(intent);


    }
}
