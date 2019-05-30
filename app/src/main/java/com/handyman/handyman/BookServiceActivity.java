package com.handyman.handyman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookServiceActivity extends AppCompatActivity {

    String hnmyid,hnmyname,hnmyphone,humywork,cid,cadd,cprob,cname,cmobi,dt;

    TextView bhn,bhp,bhw;
    EditText custaddress,custproblem;

    ConnectionHelper ch;
    Connection con;
    ResultSet rs;
    Statement stm;
    AlertDialog.Builder adb;

    SimpleDateFormat sdf;
    Date date;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);


        bhn=(TextView)findViewById(R.id.bhname);
        bhp=(TextView)findViewById(R.id.bhphone);
        bhw=(TextView)findViewById(R.id.bhwork);
        custaddress=(EditText)findViewById(R.id.ccur_addr);
        custproblem=(EditText)findViewById(R.id.cprodis);

        sharedPreferences=getSharedPreferences("cust",MODE_PRIVATE);
        cid=sharedPreferences.getString("cid","");
        cname=sharedPreferences.getString("name","");
        cmobi=sharedPreferences.getString("mobile","");

        Intent intent=getIntent();
        hnmyid=intent.getStringExtra("hmid");
        //Toast.makeText(this, ""+hnmyid, Toast.LENGTH_SHORT).show();

        sdf=new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        date=new Date();
        dt=sdf.format(date);

        ch=new ConnectionHelper();

        con=ch.getConnection();

        if(con==null)
        {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                stm=con.createStatement();

                String query="select * from ak_handyman where id='"+hnmyid+"'";
                rs=stm.executeQuery(query);

                if(rs.next())
                {
                    hnmyname=(rs.getString("fname")+" "+rs.getString("lname"));
                    hnmyphone=rs.getString("mobile");
                    humywork=rs.getString("works");
                }
                bhn.setText(hnmyname);
                bhp.setText(hnmyphone);
                bhw.setText(humywork);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }



    }
    public  void  onBackPressed()
    {
        finish();
    }

    public void bookhandyman(View view)
    {
        if (custaddress.getText().toString().isEmpty())
        {
            custaddress.setError("Empty");
            custaddress.requestFocus();
        }
        else if(custproblem.getText().toString().isEmpty())
        {
            custproblem.setError("Empty");
            custproblem.requestFocus();
        }
        else
        {
            cadd=custaddress.getText().toString();
            cprob=custproblem.getText().toString();

            try {
                stm=con.createStatement();
                String qu="insert into ak_handyman_bookings values('"+hnmyid+"','"+cid+"','"+cname+"','"+cmobi+"','"+hnmyname+"','"+cadd+"','"+cprob+"','Pending','"+dt+"')";
                stm.execute(qu);
                Toast.makeText(this, "Service Book", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            adb=new AlertDialog.Builder(BookServiceActivity.this);
            adb.setTitle("Book Your Servce");
            adb.setMessage("Do you want call  "+hnmyphone);
            adb.setCancelable(false);//not cancel
            adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  Intent callintent=new Intent(Intent.ACTION_DIAL);
                  callintent.setData(Uri.parse("tel:"+hnmyphone));//set on dialer
                  startActivity(callintent);
                  finish();
                }
            });
            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alt=adb.create();
            alt.show();


        }
    }
}
