/**
 * 
 */
package com.enixlin.jrrc_android_ui;

import java.util.ArrayList;

import org.apache.http.NameValuePair;


/**
 * @author linzhenhuan
 *
 */
public class Policy_titlelist_celldata {
	private String title;
	private String id;
	private String issuedate;
	
	public String getIssuedate() {
		return issuedate;
	}

	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}


	public Policy_titlelist_celldata() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
