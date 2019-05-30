package com.handyman.handyman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerLogActivity extends AppCompatActivity {

    EditText cust_mobile,cust_pass;
    String ct_mobile,ct_pass;

    ConnectionHelper ch;
    Connection con;
    ResultSet rs;
    Statement stm;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_log);
        cust_mobile=(EditText)findViewById(R.id.mo_cu);
        cust_pass=(EditText)findViewById(R.id.pa_cu);
        ch=new ConnectionHelper();
    }

    public void  createCustomer(View view){
        Intent intent=new Intent(CustomerLogActivity.this,CMobileActivity.class);
        startActivity(intent);
    }

    public void customerlogin(View view) throws SQLException {

        if(cust_mobile.getText().toString().isEmpty())
        {
            cust_mobile.setError("Please Enter Mobile No.");
            cust_mobile.requestFocus();
        }
        else if(cust_pass.getText().toString().isEmpty())
        {
            cust_pass.setError("Please Enter Password");
            cust_pass.requestFocus();
        }
        else
        {
            ct_mobile=cust_mobile.getText().toString().trim();
            ct_pass=cust_pass.getText().toString().trim();

            con=ch.getConnection();

            if(con==null)
            {
                Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
            }
            else {
                try {


                    stm = con.createStatement();
                    String query="select * from ak_handyman_cr where cmobile='"+ct_mobile+"' and cpswd='"+ct_pass+"' ";
                    rs=stm.executeQuery(query);

                   // Toast.makeText(this, ""+rs, Toast.LENGTH_SHORT).show();

                    if(rs.next())
                    {
                        sp=getSharedPreferences("cust",MODE_PRIVATE);
                        SharedPreferences.Editor ed=sp.edit();
                        ed.putString("cid",rs.getString("cid"));
                        ed.putString("name",rs.getString("cname"));
                        ed.putString("city",rs.getString("ccity"));
                        ed.putString("mobile",rs.getString("cmobile"));
                        ed.commit();
                        Intent intent=new Intent(CustomerLogActivity.this,CdashBoardActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(this, "Invalid Mobile or Password", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (SQLException e){
                    Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

}
