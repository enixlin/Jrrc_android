package com.enixlin.jrrc_android;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import com.enixlin.jrrc_android.net.Http_request;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static Spinner strUserName = null;
	private EditText strPassWord = null;
	private Button btnLogin = null;
	private Button btnLogout = null;
	private Handler_getusername handler_GetAllUserName;
	private Handler_login handler_login;

	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {

			Log.v("error", e.toString());
		}
		return false;
	}

	// 从网络取得所有的用户名
	private void getUserName() {
		handler_GetAllUserName=new Handler_getusername(MainActivity.this);
		String url = "http://linzhenhuan.net/Jrrc_web/Home/User/getAllUser";
		String Method = "GET";
		// 启动新线程发送网络请求
		Http_request http_request_getname = new Http_request(handler_GetAllUserName, url, Method);
		Thread thread_getUserName = new Thread(http_request_getname);
		thread_getUserName.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		strPassWord = (EditText) findViewById(R.id.password);
		strUserName = (Spinner) findViewById(R.id.spinner_userName);
		btnLogin = (Button) findViewById(R.id.btn_login);
		strPassWord.clearFocus();

		// 检测网络，如果网络不可用则退出，否则就下载用户列表
		if (isConnect(MainActivity.this) == false) {
			new AlertDialog.Builder(MainActivity.this).setTitle("网络错误").setMessage("网络连接失败，请确认网络连接")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							android.os.Process.killProcess(android.os.Process.myPid());
							// progressDialog.dismiss();
							System.exit(0);
						}
					}).show();

		} else {
			getUserName();
		}

		// 登录按键按下
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
	
			@Override
			public void onClick(View v) {
				
				//收起软键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if(imm!=null){
					imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);   
				}

				// 发送ＨＴＴＰ请求
				// 请求参数
				String url = "http://linzhenhuan.net/Jrrc_web/Home/Login/android_login";
				String Method = "POST";
				ArrayList<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
				nameValuePairList.add(new BasicNameValuePair("name", strUserName.getSelectedItem().toString()));
				nameValuePairList.add(new BasicNameValuePair("password", strPassWord.getText().toString()));
				// 启动新线程发送网络请求
				handler_login=new Handler_login(MainActivity.this);
				Http_request http_request = new Http_request(handler_login, nameValuePairList, url, Method);
				Thread thread_login = new Thread(http_request);
				thread_login.start();
			}
		});

		// 按下退出按键
		btnLogout = (Button) findViewById(R.id.btn_logout);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "退出应用！再见", Toast.LENGTH_LONG).show();
				try {
					Thread.sleep(1000, 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);

			}
		});

	}

	/**
	 * 处理从网络返回的取得所有的用户名
	 */
	static class Handler_getusername extends Handler {
		WeakReference<Activity> mActivity;
		Handler_getusername(Activity activity) {
			mActivity = new WeakReference<Activity>(activity);
		}
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				try {

					JSONArray jsonArray = new JSONArray(msg.obj.toString());
					List<String> UserList = new ArrayList<String>();
					UserList.add("请选择用户名");
					for (int n = 0; n < jsonArray.length(); n++) {
						UserList.add(jsonArray.getString(n).toString());
					}
					ArrayAdapter<String> userlistadpter = new ArrayAdapter<String>(mActivity.get(),
							android.R.layout.simple_spinner_dropdown_item, UserList);
					strUserName.setAdapter(userlistadpter);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 处理返回的登录结果
	 */
	static class Handler_login extends Handler {
		WeakReference<Activity> mActivity;
		Handler_login(Activity activity) {
			mActivity = new WeakReference<Activity>(activity);
		}
		@Override
		public void handleMessage(Message msg) {
			// 如果返回true则转到主功能页面
						if (msg.obj.toString().equals("true")) {
							Intent intent = new Intent(mActivity.get(), Activity_Function.class);
							mActivity.get().startActivity(intent);
							return;
						} else {
							Toast.makeText(mActivity.get(), "用户名或密码有误！", Toast.LENGTH_SHORT).show();
						}
			
		}

	}





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
