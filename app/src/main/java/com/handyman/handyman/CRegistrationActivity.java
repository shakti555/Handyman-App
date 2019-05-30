package com.handyman.handyman;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CRegistrationActivity extends AppCompatActivity {

    String c_vr_mobile;
    TextView textView;
    EditText []c_editTex=new  EditText[4];
    int []ids={R.id.cfullname,R.id.cnpassword,R.id.caddress,R.id.ccity};
    String []val=new  String[c_editTex.length];
    ConnectionHelper conh;
    Connection con;
    Statement sta;

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cregistration);

        for (i=0;i<c_editTex.length;i++)
        {
            c_editTex[i]=(EditText)findViewById(ids[i]);
        }

        Intent intent=getIntent();
        c_vr_mobile=intent.getStringExtra("mobile");
        textView=(TextView)findViewById(R.id.crmobilr);
        textView.setText(textView.getText().toString()+c_vr_mobile);

        conh=new ConnectionHelper();
    }

    public void cRegisterh(View view){

        for (i=0;i<c_editTex.length;i++)
        {
            if(c_editTex[i].getText().toString().isEmpty())
            {
                c_editTex[i].setError("Empty");
                c_editTex[i].requestFocus();
                break;
            }
        }

        if (i==c_editTex.length)
        {
            for (i=0;i<c_editTex.length;i++)
            {
                val[i]=c_editTex[i].getText().toString();
            }

            con=conh.getConnection();

            if (con==null)
            {
                Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try {
                    sta=con.createStatement();
                    String query="insert into ak_handyman_cr values('"+val[0]+"','"+c_vr_mobile+"','"+val[1]+"','"+val[2]+"','"+val[3]+"')";
                    sta.execute(query);
                    Toast.makeText(this, "Registration Successful.", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(CRegistrationActivity.this,CustomerLogActivity.class);
                    startActivity(intent);
                    finish();

                }
                catch (SQLException e) {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
