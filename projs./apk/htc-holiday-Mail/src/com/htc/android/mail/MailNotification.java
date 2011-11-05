package com.htc.android.mail;

import android.content.ContentValues;
import android.content.Context;
import android.content.IContentProvider;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import com.htc.android.mail.MailProvider;

public abstract class MailNotification {

   protected int mAccountId;
   protected long mCurrTime;
   protected String mDesc;
   protected int mIconResource;
   protected Context mNotifyContext;
   protected Uri mNotifyUri;
   protected long mRowId;
   protected Intent mTarget;
   protected String mTicker;
   protected String mTitle;
   protected int mType;


   public MailNotification() {}

   protected static boolean flashOnJogball() {
      boolean var0;
      switch(62) {
      case -88:
      case 24:
      case 57:
         var0 = true;
         break;
      default:
         var0 = false;
      }

      return var0;
   }

   protected static boolean flashOnLed() {
      return true;
   }

   public static Uri insertDatabase(long var0, ContentValues var2) {
      Uri var3 = null;

      Uri var6;
      try {
         IContentProvider var4 = MailProvider.instance();
         Uri var5 = MailProvider.sNotificationURI;
         var6 = var4.insert(var5, var2);
      } catch (RemoteException var8) {
         return var3;
      }

      var3 = var6;
      return var3;
   }

   public void setContext(Context var1) {
      this.mNotifyContext = var1;
   }

   public abstract int showNotification(long var1);

   public interface NotificationType {

      int ACCOUNT_ERROR_TYPE = 2;
      int NEW_MAIL_TYPE = 1;
      int SEND_FAIL_TYPE = 3;

   }
}
