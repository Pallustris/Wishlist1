package com.palludan.mywishlist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class WishActivity extends Activity {

	Wish wishEdited;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wish);
		// Show the Up button in the action bar.
		setupActionBar();
		
	    // Get the intent
	    Intent intent = getIntent();
	    
	    // Read sessionId from intent
	    String textSession = intent.getStringExtra(WishlistGlobals.EXTRA_SESSION);
	    int sessionId = Integer.parseInt(textSession);

	    // Read wishId from intent
	    String textWishId = intent.getStringExtra(WishlistGlobals.EXTRA_WISHID);
	    int wishId = Integer.parseInt(textWishId);
	    
	    if (sessionId != 0)
	    {
    		wishEdited = new Wish(wishId, this);
	    }
	    else
	    {
	    	// Error - should never come here
	    }
	    
	    EditText editText = ((EditText) findViewById(R.id.editWishDescription));
	    editText.setText(wishEdited.GetDescription(), TextView.BufferType.EDITABLE);
	    
	    editText = ((EditText) findViewById(R.id.editWishUrl));
	    editText.setText(wishEdited.GetUrl(), TextView.BufferType.EDITABLE);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wish, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void save(View view) {
		// Read from fields
		String description = ((EditText) findViewById(R.id.editWishDescription)).getText().toString(); // Get username
		String url = ((EditText) findViewById(R.id.editWishUrl)).getText().toString(); // Get password
		
		// Save in wish object
		if (wishEdited != null)
		{
			wishEdited.SetDescription(description);
			wishEdited.SetUrl(url);
		}
		
		// Save wish object to db
		long newWishId = wishEdited.SaveToDb();
		if (newWishId != 0)
		{
			// Set the result code and finish
			setResult(Activity.RESULT_OK);
		}
		else
		{
			// Set the result code and finish
			setResult(Activity.RESULT_CANCELED);			
		}
		
		finish();
	}
}
