package com.facebook.katana.platform;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.view.View;
import android.widget.QuickContactBadge;

public class PlatformFastTrack {

   private static final String SELECTION = "account_type=\'com.facebook.auth.login\' AND account_name=? AND sourceid=?";


   public PlatformFastTrack() {}

   protected static Uri getContactLookupUri(ContentResolver var0, String var1, long var2) {
      Uri var4 = Data.CONTENT_URI;
      String[] var5 = new String[]{"contact_id", "lookup"};
      String[] var6 = new String[]{var1, null};
      String var7 = String.valueOf(var2);
      var6[1] = var7;
      Cursor var8 = var0.query(var4, var5, "account_type=\'com.facebook.auth.login\' AND account_name=? AND sourceid=?", var6, (String)null);
      Uri var13;
      if(var8 != null) {
         label79: {
            boolean var16 = false;

            Uri var12;
            try {
               var16 = true;
               if(!var8.moveToFirst()) {
                  var16 = false;
                  break label79;
               }

               long var9 = var8.getLong(0);
               String var11 = var8.getString(1);
               var12 = Contacts.getLookupUri(var9, var11);
               var16 = false;
            } finally {
               if(var16) {
                  if(var8 != null) {
                     var8.close();
                  }

               }
            }

            var13 = var12;
            if(var8 != null) {
               var8.close();
            }

            return var13;
         }
      }

      if(var8 != null) {
         var8.close();
      }

      var13 = null;
      return var13;
   }

   public static void prepareBadge(View var0, String var1, long var2, String[] var4) {
      QuickContactBadge var5 = (QuickContactBadge)var0;
      Uri var6 = getContactLookupUri(var0.getContext().getContentResolver(), var1, var2);
      var5.assignContactUri(var6);
      if(var4.length > 0) {
         var5.setExcludeMimes(var4);
      }
   }
}
