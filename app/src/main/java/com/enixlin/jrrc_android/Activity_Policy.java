package com.enixlin.jrrc_android;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.enixlin.jrrc_android.MainActivity.Handler_getusername;
import com.enixlin.jrrc_android.net.Http_request;
import com.enixlin.jrrc_android_ui.ListviewAdapter_policy_titlelist;
import com.enixlin.jrrc_android_ui.Policy_titlelist_celldata;
import com.enixlin.jrrc_android_ui.ViewPagerAdapter;

import android.app.Activity;
import android.content.Context;
import android.net.VpnService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Policy extends Activity {
	private static ViewPager vp = null;
	private ViewPagerAdapter vpa = null;
	private ArrayList<View> list = null;
	private ListviewAdapter_policy_titlelist lva = null;
	private static ListView lv = null;

	private Button btn_search = null;
	private Spinner spi_search_type = null;
	private EditText et_condition = null;
	private static TextView tv_policy_content = null;
	private static TextView tv_title_tips = null;

	// 处理网络查询线程返回的数据
	static class Handler_search extends Handler {
		WeakReference<Activity> mActivity;

		public Handler_search(Activity activity) {
			mActivity = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				lv = (ListView) mActivity.get().findViewById(R.id.listview_title);
				tv_title_tips=(TextView) mActivity.get().findViewById(R.id.policy_title_tips);
				try {
					JSONArray jsonArray = new JSONArray(msg.obj.toString());
					ArrayList<Policy_titlelist_celldata> FileList = new ArrayList<Policy_titlelist_celldata>();
					for (int n = 0; n < jsonArray.length(); n++) {
						JSONObject jsonObject = new JSONObject(jsonArray.getString(n));
						Policy_titlelist_celldata record = new Policy_titlelist_celldata();
						record.setId(jsonObject.getString("d_id"));
						record.setTitle(jsonObject.getString("d_title"));
						record.setIssuedate(jsonObject.getString("d_issue_date"));
						FileList.add(record);
					}
					final ListviewAdapter_policy_titlelist lva = new ListviewAdapter_policy_titlelist(mActivity.get(),
							FileList);
					tv_title_tips.setText("本次共查询到文件：" + FileList.size() + "份");
					lv.setAdapter(lva);
					lv.setFastScrollAlwaysVisible(true);

					lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							// TODO Auto-generated method stub
							tv_policy_content = (TextView) mActivity.get().findViewById(R.id.policy_content);
							tv_policy_content.setText(" 正在载入  ....");
							vp.setCurrentItem(vp.getCurrentItem() + 1, false);

							// 发送网络请求，取得文章的内容
							String d_id = lva.getItem(position).getId();
							String url = "http://linzhenhuan.net/Jrrc_web/Home/Policy/findContentByTitle_android";
							ArrayList<NameValuePair> file = new ArrayList<NameValuePair>();
							file.add(new BasicNameValuePair("id", d_id));
							Handler_getContent handler_getContent = new Handler_getContent(mActivity.get());
							Http_request http_request = new Http_request(handler_getContent, file, url, "POST");
							Thread thread = new Thread(http_request);
							thread.start();

						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class Handler_getContent extends Handler {
		WeakReference<Activity> mActivity;

		public Handler_getContent(Activity activity) {
			this.mActivity = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				String content = msg.obj.toString();
				content = content.replace("<style>", "");
				content = content.replace("</style>", "");
				tv_policy_content.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
				tv_policy_content.setText(Html.fromHtml(content));
			}
		}
	}

	public void initViewPager() {
//		lv = (ListView) findViewById(R.id.listview_title);
		vp = (ViewPager) findViewById(R.id.viewpager);
		tv_title_tips = (TextView) findViewById(R.id.policy_title_tips);
		list = new ArrayList<View>();
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View view1 = layoutInflater.inflate(R.layout.policy_title_list, null);
		View view2 = layoutInflater.inflate(R.layout.policy_content, null);
		list.add(view1);
		list.add(view2);
		vpa = new ViewPagerAdapter(list, this);
		vp.setAdapter(vpa);
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	public void initListView() {
		// lv = (ListView) findViewById(R.id.listview_title);
		// ArrayList<Policy_titlelist_celldata> cellDatas = null;
		// lva = new
		// ListviewAdapter_policy_titlelist(Activity_Policy.this,cellDatas);
		tv_title_tips = (TextView) findViewById(R.id.policy_title_tips);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				tv_policy_content = (TextView) Activity_Policy.this.findViewById(R.id.policy_content);
				tv_policy_content.setText("==========  正在载入  ....");
				vp.setCurrentItem(vp.getCurrentItem() + 1, false);

				// 发送网络请求，取得文章的内容
				String d_id = lva.getItem(position).getId();
				String url = "http://linzhenhuan.net/Jrrc_web/Home/Policy/findContentByTitle_android";
				ArrayList<NameValuePair> file = new ArrayList<NameValuePair>();
				file.add(new BasicNameValuePair("id", d_id));
				Handler_getContent handler_getContent = new Handler_getContent(Activity_Policy.this);
				Http_request http_request = new Http_request(handler_getContent, file, url, "POST");
				Thread thread = new Thread(http_request);
				thread.start();

			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_policy);
		// 初始化viewpager
		this.initViewPager();

		// 定议查找按键，并绑定监听器
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// 收起软键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
				}
				// 每一次查找文件前都重新定义刷新viewpager,listview
				initViewPager();
				// initListView();

				// 定义查询条件并开启新线程发新查询请求
				EditText et_condition = (EditText) findViewById(R.id.condition);
				Spinner spi_type = (Spinner) findViewById(R.id.spinner_type);
				String strCondition = et_condition.getText().toString();
				String strType = spi_type.getSelectedItem().toString();
				// 请求的网址
				String url = "http://linzhenhuan.net/Jrrc_web/Home/Policy/findByTitle_android";
				// 请求参数
				ArrayList<NameValuePair> al_condition = new ArrayList<NameValuePair>();
				al_condition.add(new BasicNameValuePair("type", strType));
				al_condition.add(new BasicNameValuePair("condition", strCondition));
				Handler_search handler_search = new Handler_search(Activity_Policy.this);
				Http_request http_request = new Http_request(handler_search, al_condition, url, "POST");
				Thread thread = new Thread(http_request);
				thread.start();
			}
		});
		// this.initListView();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__policy, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		return id==R.id.action_settings || super.onOptionsItemSelected(item);
	}
}
