package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MyBookingActivity extends AppCompatActivity {

    String bcid;
    ListView listView;

    ArrayList<HashMap<String,String>> array=new ArrayList<HashMap<String, String>>();
    SimpleAdapter simad;
    ConnectionHelper ch;
    Connection con;
    Statement stm;
    ResultSet rs;

    String []from={"hname","hphone","cdesc","bdate","bstatus"};
    int []to={R.id.cbhname,R.id.cbhphone,R.id.cbhdsec,R.id.cbhdate,R.id.cbhstatus};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);

        listView=(ListView)findViewById(R.id.cbookview);

        simad=new SimpleAdapter(this,array,R.layout.cbooking,from,to);
        listView.setAdapter(simad);
        ch=new ConnectionHelper();
        Intent boo=getIntent();
        bcid=boo.getStringExtra("cid");
        //Toast.makeText(this, ""+bcid, Toast.LENGTH_SHORT).show();

        con=ch.getConnection();

        if (con==null)
        {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                stm=con.createStatement();

                String qu="select * from ak_handyman_bookings where cid='"+bcid+"' ";
                rs=stm.executeQuery(qu);

                while (rs.next())
                {
                    HashMap<String,String> hmp=new HashMap<String, String>();
                    hmp.put("hname",rs.getString("hname"));
                    hmp.put("hphone",getHphone(rs.getString("hid")));
                    hmp.put("cdesc",rs.getString("description"));
                    hmp.put("bdate",rs.getString("cur_date"));
                    hmp.put("bstatus",rs.getString("status"));

                    array.add(hmp);
                }
                simad.notifyDataSetChanged();

                listView.invalidateViews();
                listView.refreshDrawableState();

            } catch (SQLException e) {
                Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getHphone(String hid)
    {
        String hphone="";

        con=ch.getConnection();
        if(con==null)
        {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }

        else
        {
            try {
                stm=con.createStatement();
                String query="select mobile from ak_handyman where id='"+hid+"'";
                ResultSet res=stm.executeQuery(query);
                if(res.next())
                {
                    hphone=hphone+res.getString("mobile");
                }
            } catch (SQLException e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        return hphone;

    }
}
