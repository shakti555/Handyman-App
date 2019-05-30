package com.handyman.handyman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class CdashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String cid;
    SharedPreferences sdps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdash_board);
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

        sdps=getSharedPreferences("cust",MODE_PRIVATE);
        cid=sdps.getString("cid","");
    }

    public  void HandyManPainter(View view)
    {
        Intent intent=new Intent(CdashBoardActivity.this,MandyManListActivity.class);
        intent.putExtra("services","Painter");
        startActivity(intent);
    }

    public  void HandyManCarpenter(View view)
    {
        Intent intent=new Intent(CdashBoardActivity.this,MandyManListActivity.class);
        intent.putExtra("services","Carpenter");
        startActivity(intent);
    }

    public  void HandyManPlumber(View view)
    {
        Intent intent=new Intent(CdashBoardActivity.this,MandyManListActivity.class);
        intent.putExtra("services","Plumber");
        startActivity(intent);
    }

    public  void HandyManMechanic(View view)
    {
        Intent intent=new Intent(CdashBoardActivity.this,MandyManListActivity.class);
        intent.putExtra("services","Mechanic");
        startActivity(intent);
    }

    public  void HandyManPElectrician(View view)
    {
        Intent intent=new Intent(CdashBoardActivity.this,MandyManListActivity.class);
        intent.putExtra("services","Electrician");
        startActivity(intent);
    }

    public  void HandyManElectronicRepair(View view)
    {
        Intent intent=new Intent(CdashBoardActivity.this,MandyManListActivity.class);
        intent.putExtra("services","Electronic Repair");
        startActivity(intent);
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
        getMenuInflater().inflate(R.menu.cdash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.caction_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_booking) {

            Intent bo=new Intent(CdashBoardActivity.this,MyBookingActivity.class);
            bo.putExtra("cid",cid);
            startActivity(bo);

        } else if (id == R.id.my_profile) {

        } else if (id == R.id.change_password) {

        } else if (id == R.id.log_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
