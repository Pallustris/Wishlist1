package com.palludan.mywishlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Wish {
	private long wishId; 
	private String description;
	private String url;
	private Context context;

	public Wish(long newWishId, Context newContext)
	{
		context = newContext;
		wishId = newWishId;
		description = "";
		url = "";

		if (wishId != 0)
    	{
			WishlistDbHelper myDbHelper = new WishlistDbHelper(context);
    		SQLiteDatabase db = myDbHelper.getReadableDatabase();
    		
    		// Define a projection that specifies which columns from the database
    		// you will actually use after this query.
    		String[] projection = {
    		    WishlistContract.WishTable._ID,
    		    WishlistContract.WishTable.COLUMN_NAME_DESCRIPTION,
    		    WishlistContract.WishTable.COLUMN_NAME_URL
    		    };

			String selection = WishlistContract.WishTable._ID + " LIKE ?";
			String[] selectionArgs = { String.valueOf(wishId) };  		
    		
    		// How you want the results sorted in the resulting Cursor
    		Cursor c = db.query(
    		    WishlistContract.WishTable.TABLE_NAME,// The table to query
    		    projection,                           // The columns to return
    		    selection,                            // The columns for the WHERE clause
    		    selectionArgs,                        // The values for the WHERE clause
    		    null,                                 // don't group the rows
    		    null,                                 // don't filter by row groups
    		    null 	                              // The sort order
    		    );	
    		
    		if (c.moveToFirst())
    		{
    			description = c.getString(c.getColumnIndexOrThrow(WishlistContract.WishTable.COLUMN_NAME_DESCRIPTION));
    			url = c.getString(c.getColumnIndexOrThrow(WishlistContract.WishTable.COLUMN_NAME_URL));
    		}
    	}
	}
	
	public long SaveToDb()
	{
		WishlistDbHelper mDbHelper = new WishlistDbHelper(context);
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(WishlistContract.WishTable.COLUMN_NAME_DESCRIPTION, description);
		values.put(WishlistContract.WishTable.COLUMN_NAME_URL, url);


		if (wishId == 0)
		{
			// Insert the new row, returning the primary key value of the new row
			wishId = db.insert(
			         WishlistContract.WishTable.TABLE_NAME,
			         null,
			         values);
			return wishId;
		}
		else
		{
			// Which row to update, based on the ID
			String selection = WishlistContract.WishTable._ID + " LIKE ?";
			String[] selectionArgs = { String.valueOf(wishId) };

			int count = db.update(
			    WishlistContract.WishTable.TABLE_NAME,
			    values,
			    selection,
			    selectionArgs);
			if (count == 1)
				return wishId;
			else
				return 0;
		}
	}
	
	public void DeleteFromDb()
	{
		WishlistDbHelper mDbHelper = new WishlistDbHelper(context);
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		String selection = WishlistContract.WishTable._ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(wishId) };
		db.delete(
				WishlistContract.WishTable.TABLE_NAME,
				selection, 
				selectionArgs);
	}

	public long GetId()
	{
		return wishId;
	}
	
	public void SetId(long id)
	{
		wishId = id;
	}
	
	public String GetUrl()
	{
		return url;
	}
	
	public void SetUrl(String Url)
	{
		url = Url;
	}

	public String GetDescription()
	{
		return description;
	}
	
	public void SetDescription(String Description)
	{
		description = Description;
	}
	
	public String toString()
	{
		return description;
	}

}
