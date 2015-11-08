package com.enixlin.jrrc_android;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.enixlin.jrrc_android.net.WifiAdmin;
import com.enixlin.jrrc_android_ui.ViewPagerAdapter;

import java.util.ArrayList;


public class Activity_Mail extends Activity {
    private static final int WIFI_STATE_DISABLED = 1;
    private Button btn_search;
    private Button btn_switchwift;
    private TextView tv_ssid;
    private ViewPager vp;
    private ArrayList<View> list;
    private ViewPagerAdapter vpa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        initViewPager();

        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //
        btn_switchwift = (Button) findViewById(R.id.btn_switchwifi);
        btn_switchwift.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              // 打开ＷＩＦ网络
              WifiAdmin wifiAdmin = new WifiAdmin(Activity_Mail.this);
              wifiAdmin.openWifi();
              wifiAdmin.addNetwork(wifiAdmin.CreateWifiInfo("jrrc", "meeting@rhe8O23", 3));
              vp= (ViewPager) findViewById(R.id.vp);
          }
      });


    }

    public void initViewPager(){
        vp= (ViewPager) findViewById(R.id.vp);
        list = new ArrayList<View>();
        LayoutInflater li=LayoutInflater.from(this);
        View v1=li.inflate(R.layout.mail_list, null);
        View v2=li.inflate(R.layout.mail_content,null);
        list.add(v1);
        list.add(v2);
        vpa = new ViewPagerAdapter(list, this);
        vp.setAdapter(vpa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__mail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
