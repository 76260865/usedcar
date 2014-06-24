package com.jason.usedcar.db;

import android.content.Context;

public class DbWrapper {
    private DBHelper mDbHelper;

    public DbWrapper(Context context) {
        mDbHelper = DBHelper.getInstance(context);
    }
    
}
