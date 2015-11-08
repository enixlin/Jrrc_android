/**
 * 
 */
package com.enixlin.jrrc_android_ui;

import java.util.ArrayList;
import java.util.List;

import com.enixlin.jrrc_android.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author linzhenhuan
 *
 */
public class ListviewAdapter_policy_titlelist extends BaseAdapter {

	private ArrayList<Policy_titlelist_celldata> cellDatas = null;
	private ArrayList<View> views = null;
	private Context context = null;

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
	if (observer != null) {
	       super.unregisterDataSetObserver(observer);
	   }
	}
	public ListviewAdapter_policy_titlelist(Context context,ArrayList<Policy_titlelist_celldata> celldata) {
		this.context = context;
		this.cellDatas=celldata;
	}

	;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cellDatas.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Policy_titlelist_celldata getItem(int position) {

		return cellDatas.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout ll;
		if(convertView!=null){
			ll=(LinearLayout) convertView;
		}else{
			ll=(LinearLayout) LayoutInflater.from(context).inflate(R.layout.policy_title_list_cell, null);
		}
		

		
		Policy_titlelist_celldata data=getItem(position);
		
		TextView tv_policy_issuedate=(TextView) ll.findViewById(R.id.tv_policy_issuedate);
		TextView tv_policy_title=(TextView) ll.findViewById(R.id.tv_policy_title);
		tv_policy_title.setText(data.getTitle());
		tv_policy_issuedate.setText(data.getIssuedate());

		return ll;
	}

}
