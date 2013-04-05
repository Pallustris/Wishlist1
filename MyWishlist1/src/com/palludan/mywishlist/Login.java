package com.palludan.mywishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	/** Called when the user clicks the login button */
	public void login(View view) {
		LoginManager loginManager = new LoginManager();
		
		String username = ((EditText) findViewById(R.id.editUsername)).getText().toString(); // Get username
		String password = ((EditText) findViewById(R.id.editPassword)).getText().toString(); // Get password
		int sessionId = 0;
		
		// Call login manager to login
		sessionId = loginManager.login(username, password);
		if (sessionId != 0)
		{
			// Login succeeded
			// Go to next activity - list of wish lists
			Intent intent = new Intent(this, ListWishlistsActivity.class);
			String textSession = String.valueOf(sessionId);
			intent.putExtra(WishlistGlobals.EXTRA_SESSION, textSession);
			startActivity(intent);
		}
		else
		{
			// Login failed
		}
	}

	/** Called when the user clicks the new user button */
	public void newUser(View view) {
	    // Do something in response to button
	}
}
