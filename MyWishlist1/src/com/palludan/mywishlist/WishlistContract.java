package com.palludan.mywishlist;

import android.provider.BaseColumns;

public class WishlistContract {

	private WishlistContract() {}
	
	public static abstract class WishTable implements BaseColumns {
		public static final String TABLE_NAME = "wish";
		public static final String COLUMN_NAME_WISHLIST_ID = "wishlistId";
		public static final String COLUMN_NAME_USER_ID = "userId";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_URL = "url";
		public static final String COLUMN_NAME_QTY = "qty";
		public static final String COLUMN_NAME_PRICE = "price";
		public static final String COLUMN_NAME_COLOR = "color";
		public static final String COLUMN_NAME_SIZE = "size";
	}
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	
	public static final String SQL_CREATE_WISH =
	    "CREATE TABLE " + WishlistContract.WishTable.TABLE_NAME + " (" +
	    WishlistContract.WishTable._ID + " INTEGER PRIMARY KEY," +
	    WishlistContract.WishTable.COLUMN_NAME_WISHLIST_ID + TEXT_TYPE + COMMA_SEP +
	    WishlistContract.WishTable.COLUMN_NAME_USER_ID + TEXT_TYPE + COMMA_SEP +
	    WishlistContract.WishTable.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
	    WishlistContract.WishTable.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
	    WishlistContract.WishTable.COLUMN_NAME_QTY + TEXT_TYPE + COMMA_SEP +
	    WishlistContract.WishTable.COLUMN_NAME_PRICE + TEXT_TYPE + COMMA_SEP +
	    WishlistContract.WishTable.COLUMN_NAME_COLOR + TEXT_TYPE + COMMA_SEP +
	    WishlistContract.WishTable.COLUMN_NAME_SIZE + TEXT_TYPE +
	    " )";

	public static final String SQL_DELETE_WISH =
	    "DROP TABLE IF EXISTS " + WishlistContract.WishTable.TABLE_NAME;
}
