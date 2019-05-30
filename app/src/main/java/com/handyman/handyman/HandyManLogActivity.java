package com.handyman.handyman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HandyManLogActivity extends AppCompatActivity {

    EditText h_mobile,h_pass;
    String hm_mobile,hm_pass;

    ConnectionHelper ch;
    Connection con;
    Statement stn;
    SharedPreferences shp;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handy_man_log);

        h_mobile=(EditText)findViewById(R.id.hm_m);
        h_pass=(EditText)findViewById(R.id.hm_p);

        ch=new ConnectionHelper();
    }

    public void  createHandyMan(View view){
        Intent intent=new Intent(HandyManLogActivity.this,HRegistrationActivity.class);
        startActivity(intent);
    }

    public  void  HandymanLogin(View view)
    {
        if(h_mobile.getText().toString().isEmpty())
        {
            h_mobile.setError("Empty");
            h_mobile.requestFocus();
        }
        else  if(h_pass.getText().toString().isEmpty())
        {
            h_pass.setError("Empty");
            h_pass.requestFocus();
        }
        else
        {
            hm_mobile=h_mobile.getText().toString().trim();
            hm_pass=h_pass.getText().toString().trim();

            con=ch.getConnection();

            if(con==null)
            {
                Toast.makeText(this, "NO Connection", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try {


                    stn=con.createStatement();
                    Toast.makeText(this, ""+hm_pass+hm_mobile, Toast.LENGTH_SHORT).show();
                    String query="select * from ak_handyman where mobile='"+hm_mobile+"' and password='"+hm_pass+"'";
                    rs=stn.executeQuery(query);
                    if(rs.next())
                    {
                        shp=getSharedPreferences("handy",MODE_PRIVATE);
                        SharedPreferences.Editor ed=shp.edit();
                        ed.putString("hid",rs.getString("id"));
                        ed.putString("name",rs.getString("fname"));
                        ed.putString("city",rs.getString("city"));
                        ed.putString("mobile",rs.getString("mobile"));
                        ed.commit();
                        //Toast.makeText(this, "HandyMan Login", Toast.LENGTH_LONG).show();
                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(HandyManLogActivity.this,HandymanDashBoardActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }


                } catch (SQLException e) {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
