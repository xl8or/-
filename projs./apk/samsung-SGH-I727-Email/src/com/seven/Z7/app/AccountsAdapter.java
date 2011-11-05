package com.seven.Z7.app;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.widget.CursorAdapter;
import android.widget.SpinnerAdapter;

public abstract class AccountsAdapter extends CursorAdapter implements SpinnerAdapter {

   public static final int ACCOUNT_ID_INDEX = 1;
   public static final String[] ACCOUNT_PROJECTION;
   public static final int NAME_ID_INDEX = 3;
   public static final int NAME_INDEX = 2;
   public static final int PROVISION_NAME_INDEX = 6;
   public static final int SCOPE_INDEX = 5;
   public static final String TAG = "AccountsAdapter";
   public static final int USER_NAME_INDEX = 4;
   protected LayoutInflater mInflater;


   static {
      String[] var0 = new String[]{"_id", "account_id", "name", "name_id", "user_name", "scope", "provision_name"};
      ACCOUNT_PROJECTION = var0;
   }

   public AccountsAdapter(Context var1, Cursor var2) {
      super(var1, var2);
      LayoutInflater var3 = LayoutInflater.from(var1);
      this.mInflater = var3;
   }
}
