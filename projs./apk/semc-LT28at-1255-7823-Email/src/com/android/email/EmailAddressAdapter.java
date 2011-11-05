package com.android.email;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import com.android.email.mail.Address;
import com.android.email.provider.EmailContent;

public class EmailAddressAdapter extends ResourceCursorAdapter {

   public static final int DATA_INDEX = 2;
   public static final int ID_INDEX = 0;
   public static final int NAME_INDEX = 1;
   protected static final String[] PROJECTION;
   protected static final String SORT_ORDER = "times_contacted DESC, display_name";
   protected final ContentResolver mContentResolver;


   static {
      String[] var0 = new String[]{"_id", "display_name", "data1"};
      PROJECTION = var0;
   }

   public EmailAddressAdapter(Context var1) {
      super(var1, 2130903073, (Cursor)null);
      ContentResolver var2 = var1.getContentResolver();
      this.mContentResolver = var2;
   }

   public void bindView(View var1, Context var2, Cursor var3) {
      TextView var4 = (TextView)var1.findViewById(2131558525);
      TextView var5 = (TextView)var1.findViewById(2131558526);
      String var6 = var3.getString(1);
      var4.setText(var6);
      String var7 = var3.getString(2);
      var5.setText(var7);
   }

   public final String convertToString(Cursor var1) {
      String var2 = var1.getString(1);
      String var3 = var1.getString(2);
      return (new Address(var3, var2)).toString();
   }

   public Cursor runQueryOnBackgroundThread(CharSequence var1) {
      String var2;
      if(var1 == null) {
         var2 = "";
      } else {
         var2 = var1.toString();
      }

      Uri var3 = android.provider.ContactsContract.CommonDataKinds.Email.CONTENT_FILTER_URI;
      String var4 = Uri.encode(var2);
      Uri var5 = Uri.withAppendedPath(var3, var4);
      ContentResolver var6 = this.mContentResolver;
      String[] var7 = PROJECTION;
      Object var8 = null;
      Cursor var9 = var6.query(var5, var7, (String)null, (String[])var8, "times_contacted DESC, display_name");
      if(var9 != null) {
         int var10 = var9.getCount();
      }

      return var9;
   }

   public void setAccount(EmailContent.Account var1) {}
}
