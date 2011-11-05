package com.facebook.katana;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.provider.ContactsContract.RawContacts;
import com.facebook.katana.binding.AppSession;

public class RemoveRawContactsService extends IntentService {

   private String mUsername;


   public RemoveRawContactsService() {
      super("RemoveRawContactsService");
   }

   protected void onHandleIntent(Intent var1) {
      if(this.mUsername != null) {
         Builder var2 = RawContacts.CONTENT_URI.buildUpon();
         String var3 = this.mUsername;
         Uri var4 = var2.appendQueryParameter("account_name", var3).appendQueryParameter("account_type", "com.facebook.auth.login").appendQueryParameter("caller_is_syncadapter", "true").build();
         int var5 = this.getContentResolver().delete(var4, (String)null, (String[])null);
      }
   }

   public void onStart(Intent var1, int var2) {
      AppSession var3 = AppSession.getActiveSession(this, (boolean)0);
      if(var3 != null) {
         String var4 = var3.getSessionInfo().username;
         this.mUsername = var4;
      }

      super.onStart(var1, var2);
   }
}
