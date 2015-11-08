package com.enixlin.jrrc_android.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by linzhenhuan on 15/9/13.
 */
public class Http_request implements Runnable {
    private Handler handler;
    private ArrayList<NameValuePair> nameValuePairList = null;
    private String url = null;
    private String Method = null;
    
   


    public Http_request(Handler handler, ArrayList<NameValuePair> nameValuePairList, String url, String Method) {
        this.handler = handler;
        this.Method = Method;
        this.nameValuePairList = nameValuePairList;
        this.url = url;

    }

    public Http_request(Handler handler, String url, String Method) {
        this.handler = handler;
        this.Method = Method;
        this.url = url;
    }
    

    @Override
    public void run() {
        Looper.prepare();
        
        

        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;

        if (this.Method.equals("GET")) {
            HttpGet httpGet = new HttpGet(this.url);
            try {
                httpResponse = httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (this.Method.equals("POST")) {
            HttpPost httpPost = new HttpPost(url);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(this.nameValuePairList, HTTP.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                httpResponse = httpClient.execute(httpPost);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//       HttpEntity httpEntity = httpResponse.getEntity();
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity().getContent()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuffer strBuffer = new StringBuffer();
            String line = "";
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    strBuffer.append(line);
                }
                //Toast.makeText(MainActivity.this,strBuffer,Toast.LENGTH_LONG).show();
                Message message = Message.obtain();
                message.obj = strBuffer;
                message.what = 1;
                this.handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Looper.loop();
        }

    }
}
