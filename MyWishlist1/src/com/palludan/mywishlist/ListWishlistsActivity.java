package com.palludan.mywishlist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListWishlistsActivity extends Activity {
	
	ArrayList<Wish> wishlist = new ArrayList<Wish>();
	ArrayAdapter<Wish> adapter;
	int sessionId = 0;

	private void LoadWishlist()
	{
		wishlist.clear();

	    // Load Wishlist
		WishlistDbHelper myDbHelper = new WishlistDbHelper(this);
		SQLiteDatabase db = myDbHelper.getReadableDatabase();
		
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				WishlistContract.WishTable._ID,
		    };
		
		// How you want the results sorted in the resulting Cursor
		Cursor c = db.query(
		    WishlistContract.WishTable.TABLE_NAME,// The table to query
		    projection,                           // The columns to return
		    null,       	                     // The columns for the WHERE clause
		    null,		                        // The values for the WHERE clause
		    null,                                 // don't group the rows
		    null,                                 // don't filter by row groups
		    null 	                              // The sort order
		    );	

		if (c.moveToFirst())
		{
    		do {
    			long wishId = c.getLong(c.getColumnIndexOrThrow(WishlistContract.WishTable._ID));
    			Wish newWish2 = new Wish(wishId, this);
    			wishlist.add(newWish2);
    		} while (c.moveToNext());    			
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_wishlists);

	    ListView listWishlist = (ListView) findViewById(R.id.listWishs);
	    listWishlist.setOnItemClickListener(mWishClickedHandler);
	    listWishlist.setOnItemLongClickListener(mWishLongClickedHandler);
	    
	    // Get the message from the intent
	    Intent intent = getIntent();
	    String textSession = intent.getStringExtra(WishlistGlobals.EXTRA_SESSION);
	    sessionId = Integer.parseInt(textSession);

	    if (sessionId != 0)
	    {
	    	LoadWishlist();
		    // Set adapter
			adapter = new ArrayAdapter<Wish>(this, android.R.layout.simple_list_item_1, wishlist);
		    listWishlist.setAdapter(adapter);
	    }
	    else
	    {
	    	// Error
	    }
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_wishlists, menu);
		return true;
	}

	public void addWish(View view) {
		// Build intent for WishActivity
		Intent intent = new Intent(this, WishActivity.class);
		
		// Put sessionId in intent
		String textSession = String.valueOf(sessionId);
		intent.putExtra(WishlistGlobals.EXTRA_SESSION, textSession);
		
		// Put wishId in intent = 0 because of new wish
		String textWishId = String.valueOf(0);
		intent.putExtra(WishlistGlobals.EXTRA_WISHID, textWishId);
		
		// Start activity with result
		startActivityForResult(intent, WishlistGlobals.RET_ADDWISH);
	}

	// Create a message handling object as an anonymous class.
	private OnItemClickListener mWishClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView parent, View v, int position, long id) {
			// Build intent for WishActivity
			Intent intent = new Intent(parent.getContext(), WishActivity.class);
			
			// Put sessionId in intent
			String textSession = String.valueOf(sessionId);
			intent.putExtra(WishlistGlobals.EXTRA_SESSION, textSession);
			
			// Put wishId in intent = 0 because of new wish
			String textWishId = String.valueOf(wishlist.get(position).GetId());
			intent.putExtra(WishlistGlobals.EXTRA_WISHID, textWishId);
			
			// Start activity with result
			startActivityForResult(intent, WishlistGlobals.RET_EDITWISH);
	    }	    
	};
	
	private OnItemLongClickListener mWishLongClickedHandler = new OnItemLongClickListener() {
	    public boolean onItemLongClick(AdapterView parent, View v, int position, long id) {
	    	wishlist.get(position).DeleteFromDb();
	    	wishlist.remove(position);
	    	adapter.notifyDataSetChanged();
	    	return true;
	    }
	};
	
	// Called when wishActivity returns
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
			case WishlistGlobals.RET_ADDWISH:
				// Handle returns from add wish activity
				// resultCode != 0 means new wish is saved, resultCode == 0 means new wish was not saved 
				if (resultCode == RESULT_OK)
				{
					// Wish is added
					LoadWishlist();					
					adapter.notifyDataSetChanged();
				}
				break;
				
			case WishlistGlobals.RET_EDITWISH:
				// Handle returns from edit wish activity
				// resultCode != 0 means changes are saved, resultCode == 0 means changes were not saved 
				if (resultCode == RESULT_OK)
				{
					// Wish has changed
					LoadWishlist();
					adapter.notifyDataSetChanged();
				}				
				break;
				
			default:
				// Error - should not get here
		}
	}
}
