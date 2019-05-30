package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


public class MandyManListActivity extends AppCompatActivity {

    String hm_services;
    ListView hmlist;

    ArrayList<HashMap<String,String>> array=new ArrayList<HashMap<String, String>>();
    SimpleAdapter simad;
    ConnectionHelper ch;
    Connection con;
    Statement stm;
    ResultSet  rs;
    String hmid;

    TextView []textViews=new TextView[4];


    String []from={"name","phone","work","id"};
    int []to={R.id.tvname,R.id.tvphone,R.id.tvwork,R.id.tvidqwe};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandy_man_list);
        Intent intent=getIntent();
        hm_services=intent.getStringExtra("services");
        hmlist=(ListView)findViewById(R.id.halist);
        simad=new SimpleAdapter(this,array,R.layout.customlist,from,to);
        hmlist.setAdapter(simad);
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
                String query="select * from ak_handyman where works='"+hm_services+"' ";
                rs=stm.executeQuery(query);

                while (rs.next())
                {
                    HashMap<String,String> hmp=new HashMap<String, String>();
                    hmp.put("name",rs.getString("fname")+" "+rs.getString("lname"));
                    hmp.put("phone",rs.getString("mobile"));
                    hmp.put("work",rs.getString("works"));
                    hmp.put("id",rs.getString("id"));
                    array.add(hmp);
                }
                simad.notifyDataSetChanged();

                hmlist.invalidateViews();
                hmlist.refreshDrawableState();

                hmlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       hmid=((TextView)view.findViewById(R.id.tvidqwe)).getText().toString();
                        //Toast.makeText(MandyManListActivity.this, ""+hmid, Toast.LENGTH_SHORT).show();

                        Intent iner=new Intent(MandyManListActivity.this,BookServiceActivity.class);
                        iner.putExtra("hmid",hmid);
                        startActivity(iner);

                    }
                });

            } catch (SQLException e) {
                Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
