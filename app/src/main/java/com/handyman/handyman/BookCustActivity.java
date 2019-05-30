package com.handyman.handyman;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.katepratik.msg91api.MSG91;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//8858926986 1234qwer
//han 7800245124
//123456

public class BookCustActivity extends AppCompatActivity {

    String obhoid,obhcname,obhcadd,obhcphone,obhcdesc,obhcdate;

    TextView obid,obname,obadd,obphone,obdesc,obdate;

    ConnectionHelper ch;
    Connection con;
    ResultSet rs;
    Statement stm;

    MSG91 msg91;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_cust);

        obid=(TextView)findViewById(R.id.tv_hboid);
        Toast.makeText(this, ""+obid, Toast.LENGTH_SHORT).show();
        obname=(TextView)findViewById(R.id.tv_hbcname);

        obadd=(TextView)findViewById(R.id.tv_hbcadd);
        obphone=(TextView)findViewById(R.id.tv_hbcphone);
        obdesc=(TextView)findViewById(R.id.tv_hbcdesc);
        obdate=(TextView)findViewById(R.id.tv_hbcdate);

        Intent obintent=getIntent();
        obhoid=obintent.getStringExtra("oid");

        ch= new ConnectionHelper();

        con=ch.getConnection();


        if(con==null)
        {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                stm=con.createStatement();

                String qu="select * from ak_handyman_bookings where orderid='"+obhoid+"' ";
                rs=stm.executeQuery(qu);

                if(rs.next())
                {
                    obhcname=rs.getString("cname");
                    obhcadd=rs.getString("current_location");
                    obhcphone=rs.getString("cno");
                    obhcdesc=rs.getString("description");
                    obhcdate=rs.getString("cur_date");
                }

                obid.setText(obhoid);
                obname.setText(obhcname);
                obadd.setText(obhcadd);
                obphone.setText(obhcphone);
                obdesc.setText(obhcdesc);
                obdate.setText(obhcdate);


            } catch (SQLException e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public  void  reject(View view){
        con=ch.getConnection();

        try {
            stm=con.createStatement();

            String qur="update ak_handyman_bookings set status='Rejected' where orderid='"+obid+"'";

            stm.execute(qur);

            msg91=new MSG91("196023AwNplgJ6qS5a722783");
            msg91.composeMessage("HNDMNG"," Dear "+obhcname+" Your order id "+obhoid+" for "+obhcdesc+" has been rejected");
            msg91.to(obhcphone);
            msg91.send();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void  confirm(View view)
    {
        try {
            stm=con.createStatement();
            Toast.makeText(this, ""+obid, Toast.LENGTH_SHORT).show();
            String qur="update ak_handyman_bookings set status='Confirmed' where orderid='"+obid+"'";
            stm.execute(qur);
            AlertDialog.Builder adb=new AlertDialog.Builder(BookCustActivity.this);
            adb.setTitle("Call To Confirm");
            adb.setMessage("Call on "+obhcphone);
            adb.setPositiveButton("Confirm booking", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent qws=new Intent(Intent.ACTION_DIAL);
                    qws.setData(Uri.parse("tel:"+obhcphone));
                    startActivity(qws);
                }
            });
            adb.show();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
