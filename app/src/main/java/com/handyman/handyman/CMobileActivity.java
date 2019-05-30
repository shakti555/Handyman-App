package com.handyman.handyman;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.katepratik.msg91api.MSG91;

public class CMobileActivity extends AppCompatActivity {

    EditText mobno;
    String mobile,OTP;
    CaptchaGenerator cg;
    MSG91 msg91;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmobile);
        mobno=(EditText)findViewById(R.id.cmobile);
        cg=new CaptchaGenerator();
    }
    public  void verifyMobile(View view){

        if (mobno.getText().toString().isEmpty())
        {
            mobno.setError("Please Enter Mobile no.");
            mobno.requestFocus();
        }
        else {

            msg91=new MSG91("196023AwNplgJ6qS5a722783");
            OTP=cg.getOTP();
            mobile=mobno.getText().toString().trim();
            msg91.composeMessage("HNDMNG","Your OTP for HAndyMan Customer registration is "+OTP);
            msg91.to(mobile);
            msg91.send();
            Toast.makeText(this, "OTP Send", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(CMobileActivity.this,VerifyOTPActivity.class);
            intent.putExtra("mobile",mobile);
            intent.putExtra("otp",OTP);
            startActivity(intent);
           finish();
        }


    }
}
