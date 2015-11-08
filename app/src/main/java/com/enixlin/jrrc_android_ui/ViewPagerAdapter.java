/**
 * 
 */
package com.enixlin.jrrc_android_ui;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author linzhenhuan
 *
 */
public class ViewPagerAdapter extends PagerAdapter {

	private List<View> list = null;
	private Context context = null;

	public ViewPagerAdapter(List<View> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeViewInLayout(list.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(list.get(position));
		return list.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
	 * java.lang.Object)
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
