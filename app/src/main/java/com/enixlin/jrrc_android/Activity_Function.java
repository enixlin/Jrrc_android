package com.enixlin.jrrc_android;


import android.app.Activity;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.enixlin.jrrc_android.net.WifiAdmin;

import java.util.List;

public class Activity_Function extends Activity {
    private Button btn_Policy = null;
    private Button btn_Chart = null;
    private Button btn_Mail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        //按下政策文件按键
        btn_Policy = (Button) findViewById(R.id.btn_policy);
        btn_Policy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(Activity_Function.this, Activity_Policy.class);
                startActivity(intent);

            }
        });


        //按下
        btn_Chart = (Button) findViewById(R.id.btn_chart);
        btn_Chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Function.this, Activity_Chart.class);
                startActivity(intent);
            }
        });

        //按下邮件助手
        btn_Mail = (Button) findViewById(R.id.btn_mail);
        btn_Mail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent=new Intent(Activity_Function.this,Activity_Mail.class);
                startActivity(intent);
            }
        });


    }

    public  boolean fds(){

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.function, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
