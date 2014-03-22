package com.watamidoing.tasks;

import java.net.HttpURLConnection;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

import com.watamidoing.twitter.TwitterAuthorization;
import com.watamidoing.utils.ConnectionResult;
import com.watamidoing.utils.HttpConnectionHelper;
import com.watamidoing.utils.UtilsWhatAmIdoing;
import com.watamidoing.view.WhatAmIdoing;
import com.waid.R;

import android.os.AsyncTask;
import android.util.Log;

public class SendTwitterInviteTask extends AsyncTask<Void, Void, Boolean> {

	
	private String url;
	private WhatAmIdoing context;
	private ConnectionResult  results;

	public SendTwitterInviteTask() {
		
	}
	public SendTwitterInviteTask(String url, WhatAmIdoing context) {
		this.url = url;
		this.context = context;
	}
	@Override
	protected Boolean doInBackground(Void... arg0) {
		HttpConnectionHelper httpConnectionHelper = new HttpConnectionHelper();
		results = httpConnectionHelper.connect(url);
		if (results == null) {
			return false;
		} else if(results.getStatusCode() != HttpURLConnection.HTTP_OK) {
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void onPostExecute(final Boolean success) {
  
        
		
		if (success) {
			
			 TwitterAuthorization ta = new TwitterAuthorization(context);
			 
			 Twitter twitter = ta.buildTwitterFactory().getInstance();
			 AccessToken at = ta.getAccessToken();
			 twitter.setOAuthAccessToken(at);
			
			try {
				String url = context.getString(R.string.invite_location_url)+"="+results.getResult();
				twitter4j.Status status = twitter.updateStatus("#WAID (What Am I Doing) I am sharing a live stream here:"+url);
				UtilsWhatAmIdoing.displaySuccessInvitesTwitterDialog(context);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    Log.d("sendinviteemailtask.onpostexecute","succes:");
		} else {
			UtilsWhatAmIdoing.displayNetworkProblemsForInvitesDialog(context);
		    Log.d("sendinviteemailtask.onpostexecute","failure:");
			
		}
	}

}