package com.watamidoing;

import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.waid.R;
import com.watamidoing.contentproviders.Authentication;
import com.watamidoing.contentproviders.DatabaseHandler;
import com.watamidoing.utils.ConnectionResult;
import com.watamidoing.utils.HttpConnectionHelper;
import com.watamidoing.utils.UtilsWhatAmIdoing;
import com.watamidoing.view.WhatAmIdoing;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class Login extends Activity {
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;
	private int problemsConnecting = 0;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private Activity activity;

	

	
	@Override
	protected void onResume() {
		
		super.onResume();
		Authentication auth =  DatabaseHandler.getInstance(activity).getDefaultAuthentication();
		if (auth != null) {
			startCamera();
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		this.activity = this;
		Log.i("com.whatamidoing", "---------------------------1");
		super.onCreate(savedInstanceState);
		Log.i("com.whatamidoing", "---------------------------2");
		setContentView(R.layout.activity_login);
		Log.i("com.whatamidoing", "---------------------------3");
		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});
		
	
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		 
		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
						
					}
				});
		findViewById(R.id.forgotten_password_button).setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						resetPassword();
						
					}
				});
	}

	
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!emailValidator(mEmail)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}
	
	public boolean emailValidator(String email) 
	{
	    Pattern pattern;
	    Matcher matcher;
	    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	    pattern = Pattern.compile(EMAIL_PATTERN);
	    matcher = pattern.matcher(email);
	    return matcher.matches();
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {


		@Override
		protected Boolean doInBackground(Void... params) {
		
			String result = null;
			String loginUrl = getResources().getString(R.string.login_url);
			HttpConnectionHelper connectionHelper = new HttpConnectionHelper();
			try {
				String urlVal = loginUrl + "?email=" + mEmail + "&password="+ mPassword;
				ConnectionResult connectionResult = connectionHelper.connect(urlVal);
				
				if ((connectionResult != null) && (connectionResult.getStatusCode() == HttpURLConnection.HTTP_OK)) {
					String newAuthSuccessMessage = getResources().getString(R.string.new_auth_success_message);
					String authSuccessMessage = getResources().getString(R.string.auth_success_message);
					Log.i("Login.UserLoginTask.doInBackgroud","results from login["+connectionResult.getResult()+"]");
					if (authSuccessMessage.equalsIgnoreCase(connectionResult.getResult()) ||
							newAuthSuccessMessage.equalsIgnoreCase(connectionResult.getResult())) {
						SessionParser sessionParser = connectionHelper.getPlaySession();
					
						if (sessionParser != null) {
							Authentication auth = DatabaseHandler.getInstance(getApplicationContext()).getAuthentication(mEmail);
							
							if (auth == null) {
								auth = new Authentication(mEmail,sessionParser.getToken(),sessionParser.getPlaySession());
							} else {
								auth.setPlaySession(sessionParser.getPlaySession());
								auth.setToken(sessionParser.getToken());
							}

							//Saves or updates
							DatabaseHandler.getInstance(getApplicationContext()).putAuthentication(auth);
							result = connectionResult.getResult();
						} else {
							Log.i("login.userlogin.task", "cookie not set result=["
									+ result + "]");
							result = null;
						}
					} else {
						Log.i("login.userlogin.task", "failure result=["
								+ result + "]");
						result = null;
					}
				} else {
					
					if (connectionResult == null) {
						Log.i("login.userlogin.task", "Problems connecting");
						problemsConnecting = -1;
						} else {
							Log.i("login.userlogin.task","Problems status cost ["+ connectionResult.getStatusCode() + "] result=[" + connectionResult.getResult() + "]");
					}
					
					result = null;
				}
			} finally {
				connectionHelper.closeConnection();
			}

			return result == null ? false : true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				startCamera();
				// finish();
			} else {
				
				if (problemsConnecting == -1) {
					UtilsWhatAmIdoing.displayNetworkProblemsDialog(activity);
				} else {
					mPasswordView
						.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();
				}
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	private void startCamera() {
		Intent intent = new Intent(this, WhatAmIdoing.class);
		startActivity(intent);
	}




	public void resetPassword() {
		UtilsWhatAmIdoing.displayForgotPasswordLinkDialog(this);
	}

	
}
