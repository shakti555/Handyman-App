package com.handyman.handyman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class HandymanDashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String hid,oid;
    ListView hlistview;
    ArrayList<HashMap<String,String>> hbclist=new ArrayList<HashMap<String, String>>();
    SimpleAdapter siad;
    ConnectionHelper ch;
    Connection con;
    Statement stm;
    ResultSet rs;
    String []from={"cname","ctadd","cphone","cdesc","hdbid"};
    int []to={R.id.hdbcname,R.id.hdbcadd,R.id.hdcphone,R.id.hdcdesc,R.id.hdbookingid};

    SharedPreferences sph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        hlistview=(ListView)findViewById(R.id.handylistview);

        siad=new SimpleAdapter(this,hbclist,R.layout.hbooking,from,to);
        hlistview.setAdapter(siad);

        sph=getSharedPreferences("handy",MODE_PRIVATE);
        hid=sph.getString("hid","");

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

                String qu="select * from ak_handyman_bookings where hid='"+hid+"' order by cur_date desc ";
                rs=stm.executeQuery(qu);

                while (rs.next())
                {
                    HashMap<String,String> hmp=new HashMap<String, String>();
                    hmp.put("cname",rs.getString("cname"));
                    hmp.put("ctadd",rs.getString("current_location"));
                    hmp.put("cphone",rs.getString("cno"));
                    hmp.put("cdesc",rs.getString("description"));
                    hmp.put("hdbid",rs.getString("orderid"));

                    hbclist.add(hmp);
                }
                siad.notifyDataSetChanged();
                hlistview.invalidateViews();
                hlistview.refreshDrawableState();

                hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        oid=((TextView)view.findViewById(R.id.hdbookingid)).getText().toString();
                        Intent ord=new Intent(HandymanDashBoardActivity.this,BookCustActivity.class);
                        ord.putExtra("oid",oid);
                        startActivity(ord);
                    }
                });


            } catch (SQLException e) {
                Toast.makeText(this, ""+e, Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.handyman_dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.haction_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hadyprofile) {

        } else if (id == R.id.nav_handycpd) {

        } else if (id == R.id.nav_handylogout) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
