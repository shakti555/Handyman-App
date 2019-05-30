package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HRegistrationActivity extends AppCompatActivity {

    EditText []editTexts=new EditText[8];
    int []ids={R.id.hfname,R.id.hlname,R.id.hmobile,R.id.haddress,R.id.hcity,R.id.hpincode,R.id.hemailid,R.id.hpassword};
    String []values=new String[editTexts.length];
    int i;
    Spinner spinner;
    String []work={"Plumber","Carpenter","Painter","Electrician","Mechanic","Electronic Repair"};
    ArrayAdapter ad;

    ConnectionHelper conh;
    Connection con;
    Statement sta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hregistration);

        spinner=(Spinner)findViewById(R.id.spin);
        ad=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,work);
        spinner.setAdapter(ad);


        for(i=0;i<editTexts.length;i++)
        {
            editTexts[i]=(EditText) findViewById(ids[i]);
        }

        conh=new ConnectionHelper();
    }

    public void handymanlog(View view){
        Intent intent=new Intent(HRegistrationActivity.this,HandyManLogActivity.class);
        startActivity(intent);
        finish();
    }

    public void hRegistration(View view)
    {
        for(i=0;i<editTexts.length;i++)
        {
            if(editTexts[i].getText().toString().isEmpty())
            {
                editTexts[i].setError("Don't Leave Empty");
                editTexts[i].requestFocus();
                break;
            }
        }

        if (i==editTexts.length)
        {
            for (i=0;i<editTexts.length;i++)
            {
                values[i]=editTexts[i].getText().toString();
            }
            //Toast.makeText(this, ""+values[0]+values[1]+values[2]+values[3]+values[4]+values[5]+values[6]+values[7], Toast.LENGTH_LONG).show();

            con=conh.getConnection();

            if (con==null)
            {
                Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try {
                     sta=con.createStatement();
                     String query="insert into ak_handyman values('"+values[0]+"','"+values[1]+"','"+values[2]+"','"+values[3]+"','"+spinner.getSelectedItem().toString()+"','"+values[4]+"','"+values[5]+"','"+values[6]+"','"+values[7]+"')  ";
                     sta.execute(query);
                    Toast.makeText(this, "Registration Successful.", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(HRegistrationActivity.this,HandyManLogActivity.class);
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
