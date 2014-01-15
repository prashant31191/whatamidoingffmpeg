package com.watamidoing.utils;


import com.watamidoing.Login;
import com.watamidoing.R;
import com.watamidoing.view.WhatAmIdoing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


public class UtilsWhatAmIdoing {
	


	public static void hideKeyBoard(Activity context) {
		context.getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	public static ScreenDimension getScreenDimensions(Activity activity) {
		
		WindowManager w =activity.getWindowManager();
		Display d = w.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		
	
		d.getMetrics(metrics);
		// since SDK_INT = 1;
		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;
		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
		try {
		    widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
		    heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
		} catch (Exception ignored) {
		}
		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 17)
		try {
		    Point realSize = new Point();
		    Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
		    widthPixels = realSize.x;
		    heightPixels = realSize.y;
		} catch (Exception ignored) {
		}
		
		ScreenDimension screenDimension = new ScreenDimension(heightPixels, widthPixels,activity.getResources().getDisplayMetrics().density);
		return screenDimension;
	}
	
	public static void displayWebsocketProblemsDialog(Activity activity) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		// set title
		alertDialogBuilder.setTitle(activity.getString(R.string.unable_to_share));
 
		alertDialogBuilder
				.setMessage(activity.getString(R.string.server_connection_problems))
				.setCancelable(true)
				.setNeutralButton(activity.getString(R.string.damn),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
	}
	
	public static void displayNetworkProblemsDialog(Activity activity) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		// set title
		alertDialogBuilder.setTitle(activity.getString(R.string.unable_to_connect));
 
		alertDialogBuilder
				.setMessage(activity.getString(R.string.server_connection_problems))
				.setCancelable(true)
				.setNeutralButton(activity.getString(R.string.damn),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
	}
	
	
	public static void displayNetworkProblemsForInvitesDialog(Activity activity) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		// set title
		alertDialogBuilder.setTitle(activity.getString(R.string.unable_to_send_invites));
 
		alertDialogBuilder
				.setMessage(activity.getString(R.string.server_connection_problems))
				.setCancelable(true)
				.setNeutralButton(activity.getString(R.string.damn),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
	}
	
	
	public static void displaySuccessInvitesDialog(Activity activity) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		// set title
		alertDialogBuilder.setTitle(activity.getString(R.string.able_to_send_invites));
 
		alertDialogBuilder
				.setMessage(activity.getString(R.string.success_sent_invites))
				.setCancelable(true)
				.setNeutralButton(activity.getString(R.string.nice),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
	}

	public static void displayWebsocketServiceStoppedDialog(
			Activity activity) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		// set title
		alertDialogBuilder.setTitle(activity.getString(R.string.sharing_service_stopped));
 
		alertDialogBuilder
				.setMessage(activity.getString(R.string.sharing_stopped))
				.setCancelable(true)
				.setNeutralButton(activity.getString(R.string.nice),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
		
	}

	public static void displayWebsocketServiceConnectionClosedDialog(
			Activity activity) {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		// set title
		alertDialogBuilder.setTitle(activity.getString(R.string.sharing_service_connection_closed));
 
		alertDialogBuilder
				.setMessage(activity.getString(R.string.sharing_connection_closed))
				.setCancelable(true)
				.setNeutralButton(activity.getString(R.string.nice),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
	}
	
	public static void displayGenericMessageDialog(
			WhatAmIdoing activity, String message) {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		// set title
		alertDialogBuilder.setTitle(activity.getString(R.string.info));
 
		alertDialogBuilder
				.setMessage(message)
				.setCancelable(true)
				.setNeutralButton(activity.getString(R.string.nice),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
	}
	
	
	public static void displayCancelOkMessageDialog(
			final Login activity, String message) {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		// set title
		alertDialogBuilder.setTitle(activity.getString(R.string.info));
 
		alertDialogBuilder
				.setMessage(message)
				.setCancelable(true)
				.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						//activity.resetPassword();
						
					}
					
				})
				.setNeutralButton(activity.getString(R.string.nice),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
	}
}