package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class VerifyOTPActivity extends AppCompatActivity {

    EditText et_opt;
    String vr_mobile,vr_OTP,otp_v;
    CaptchaGenerator cg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        et_opt=(EditText)findViewById(R.id.cmOTP);
        Intent intent=getIntent();
        vr_OTP=intent.getStringExtra("otp");
        vr_mobile=intent.getStringExtra("mobile");

        //Toast.makeText(this, ""+vr_OTP, Toast.LENGTH_LONG).show();

    }

    public  void verifyOTP(View view){
        if(et_opt.getText().toString().isEmpty())
        {
            et_opt.setError("Please Enter OTP");
            et_opt.requestFocus();
        }

        else {
            otp_v=et_opt.getText().toString();
            if (otp_v.equals(vr_OTP)) {
                Toast.makeText(this, "Mobile Number Verifyed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VerifyOTPActivity.this, CRegistrationActivity.class);
                intent.putExtra("mobile",vr_mobile);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this, "Enter Correct OTP", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
