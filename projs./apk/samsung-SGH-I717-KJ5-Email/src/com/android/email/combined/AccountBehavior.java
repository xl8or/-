package com.android.email.combined;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import com.android.email.activity.setup.AccountSetupBasicsPremium;
import com.android.email.activity.setup.AccountSetupOther;
import com.android.email.combined.AccountFacade;
import com.android.email.combined.EmailException;
import com.android.email.combined.common.Cache;
import com.android.email.provider.EmailContent;
import com.seven.Z7.shared.PreferenceConstants;
import com.seven.util.SamsungUrlEncryptionUtils;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountBehavior implements PreferenceConstants.GeneralPreferences {

   private static final String TAG = "AccountBehavior";
   public static final String TYPE_MSG = "type_msg";
   private static AccountBehavior mInstance = null;
   private Cache mCache;
   private Context mContext;
   private Set<AccountFacade.AccountListener> mListeners;
   private ConcurrentHashMap<AccountFacade.AccountListener, Object> mListenersMap;
   private final String ringtoneUri;


   public AccountBehavior(Context var1) {
      Cache var2 = new Cache();
      this.mCache = var2;
      this.ringtoneUri = "content://media/internal/audio/media/28";
      ConcurrentHashMap var3 = new ConcurrentHashMap();
      this.mListenersMap = var3;
      Set var4 = this.mListenersMap.keySet();
      this.mListeners = var4;
      this.mContext = var1;
   }

   public static AccountBehavior getInstance(Context param0) {
      // $FF: Couldn't be decompiled
   }

   private final String getPwFromEmailOfPref(String var1) {
      SharedPreferences var2 = this.mContext.getSharedPreferences("sharedPreferenceCB", 0);
      String var3 = var2.getString("email_and_pw", "");
      String var4;
      if(var3 != null && var3.length() != 0) {
         String var6;
         try {
            byte[] var5 = SamsungUrlEncryptionUtils.base64Decode(var3);
            var6 = new String(var5);
         } catch (Exception var20) {
            var4 = "";
            return var4;
         }

         if(var6.indexOf("||") == -1) {
            var4 = "";
         } else {
            String[] var9 = var6.split("||");
            String var10 = "";
            if(var9.length == 2) {
               String var11 = var9[0];
               if(var1.equals(var11)) {
                  var10 = var9[1];
               }
            } else {
               int var16 = var6.indexOf("||");
               String var17 = var6.substring(0, var16);
               int var18 = "||".length() + var16;
               String var19 = var6.substring(var18);
               if(var1.equals(var17)) {
                  var10 = var19;
               }
            }

            Editor var12 = var2.edit();
            Editor var13 = var12.remove("email_and_pw");
            boolean var14 = var12.commit();
            var4 = var10;
         }
      } else {
         var4 = "";
      }

      return var4;
   }

   private boolean isRegisterSevenAccountId(int param1) {
      // $FF: Couldn't be decompiled
   }

   private int onAccountSave(int var1, String var2) {
      ContentResolver var3 = this.mContext.getContentResolver();
      Uri var4 = EmailContent.Account.CONTENT_URI;
      ContentValues var5 = this.toContentValueAccount(var2);
      int var6 = Integer.parseInt((String)var3.insert(var4, var5).getPathSegments().get(1));
      ContentResolver var7 = this.mContext.getContentResolver();
      Uri var8 = EmailContent.AccountCB.CONTENT_URI;
      ContentValues var9 = this.toContentValueAccountCB(var6, var1);
      var7.insert(var8, var9);
      String var11 = this.getPwFromEmailOfPref(var2);
      ContentResolver var12 = this.mContext.getContentResolver();
      Uri var13 = EmailContent.HostAuth.CONTENT_URI;
      long var14 = (long)var6;
      ContentValues var16 = this.toContentValuesHostAuth(var14, var11);
      int var17 = Integer.parseInt((String)var12.insert(var13, var16).getPathSegments().get(1));
      ContentResolver var18 = this.mContext.getContentResolver();
      Uri var19 = EmailContent.HostAuth.CONTENT_URI;
      long var20 = (long)var6;
      ContentValues var22 = this.toContentValuesHostAuth(var20, var11);
      int var23 = Integer.parseInt((String)var18.insert(var19, var22).getPathSegments().get(1));
      ContentValues var24 = new ContentValues();
      Integer var25 = Integer.valueOf(var17);
      var24.put("hostAuthKeyRecv", var25);
      Integer var26 = Integer.valueOf(var23);
      var24.put("hostAuthKeySend", var26);
      ContentResolver var27 = this.mContext.getContentResolver();
      Uri var28 = EmailContent.Account.CONTENT_URI;
      String[] var29 = new String[1];
      String var30 = Integer.toString(var6);
      var29[0] = var30;
      var27.update(var28, var24, "_id=?", var29);
      return var6;
   }

   private ContentValues toContentValueAccount(String var1) {
      String var2 = var1.split("@")[0];
      ContentValues var3 = new ContentValues();
      var3.put("displayName", var2);
      var3.put("emailAddress", var1);
      var3.put("senderName", var2);
      Integer var4 = Integer.valueOf(-1);
      var3.put("syncLookback", var4);
      Integer var5 = Integer.valueOf(-1);
      var3.put("syncInterval", var5);
      Integer var6 = Integer.valueOf(2049);
      var3.put("flags", var6);
      Integer var7 = Integer.valueOf(0);
      var3.put("isDefault", var7);
      var3.put("ringtoneUri", "content://media/internal/audio/media/28");
      String var8 = UUID.randomUUID().toString();
      var3.put("compatibilityUuid", var8);
      Integer var9 = Integer.valueOf(0);
      var3.put("securityFlags", var9);
      Integer var10 = Integer.valueOf(0);
      var3.put("newMessageCount", var10);
      return var3;
   }

   private ContentValues toContentValueAccountCB(int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void addListener(AccountFacade.AccountListener var1) {
      synchronized(this){}

      try {
         this.mListenersMap.put(var1, this);
      } finally {
         ;
      }

   }

   public int deleteAccountForEmail(int param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean isActiveListener(AccountFacade.AccountListener var1) {
      synchronized(this){}
      boolean var6 = false;

      boolean var2;
      try {
         var6 = true;
         var2 = this.mListenersMap.containsKey(var1);
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      return var2;
   }

   public boolean isPremiumUser(long param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean isPremiumUser(Context var1) {
      String[] var2 = this.mCache.getKeys();
      boolean var3;
      if(var2 == null) {
         var3 = false;
      } else {
         int var4 = 0;

         while(true) {
            int var5 = var2.length;
            if(var4 >= var5) {
               var3 = false;
               break;
            }

            Cache var6 = this.mCache;
            String var7 = var2[var4];
            if(((Boolean)var6.get(var7)).booleanValue()) {
               var3 = true;
               break;
            }

            ++var4;
         }
      }

      return var3;
   }

   public boolean isPremiumUser(Context var1, long var2) {
      return this.isPremiumUser(var2);
   }

   public void notifyAccountFailed(EmailException var1) {
      synchronized(this){}

      try {
         Iterator var2 = this.mListeners.iterator();

         while(var2.hasNext()) {
            ((AccountFacade.AccountListener)var2.next()).onAccountFailed(var1);
         }
      } finally {
         ;
      }

   }

   public void notifyAccountFinished() {
      synchronized(this){}

      try {
         Iterator var1 = this.mListeners.iterator();

         while(var1.hasNext()) {
            ((AccountFacade.AccountListener)var1.next()).onAccountFinished();
         }
      } finally {
         ;
      }

   }

   public void notifyAccountFontSize(int var1) {
      synchronized(this){}

      try {
         Iterator var2 = this.mListeners.iterator();

         while(var2.hasNext()) {
            ((AccountFacade.AccountListener)var2.next()).onAccountFontSize(var1);
         }
      } finally {
         ;
      }

   }

   public void notifyAccountStarted() {
      synchronized(this){}

      try {
         Iterator var1 = this.mListeners.iterator();

         while(var1.hasNext()) {
            ((AccountFacade.AccountListener)var1.next()).onAccountStarted();
         }
      } finally {
         ;
      }

   }

   public void notifyAddedAccount(int var1, String var2) {
      synchronized(this){}

      try {
         if(!this.isRegisterSevenAccountId(var1)) {
            int var3 = this.onAccountSave(var1, var2);
            Iterator var4 = this.mListeners.iterator();

            while(true) {
               AccountFacade.AccountListener var5;
               if(var4.hasNext()) {
                  var5 = (AccountFacade.AccountListener)var4.next();
                  if(!(var5 instanceof AccountSetupOther)) {
                     continue;
                  }

                  var5.onAccountAdded(var3, var1);
                  break;
               }

               var4 = this.mListeners.iterator();

               do {
                  if(!var4.hasNext()) {
                     return;
                  }

                  var5 = (AccountFacade.AccountListener)var4.next();
               } while(!(var5 instanceof AccountSetupBasicsPremium));

               var5.onAccountAdded(var3, var1);
               break;
            }
         }
      } finally {
         ;
      }

   }

   public void notifyRemovedAccount(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void removeListener(AccountFacade.AccountListener var1) {
      synchronized(this){}

      try {
         this.mListenersMap.remove(var1);
      } finally {
         ;
      }

   }

   public ContentValues toContentValuesHostAuth(long var1, String var3) {
      ContentValues var4 = new ContentValues();
      var4.put("protocol", "");
      var4.put("address", "");
      Integer var5 = Integer.valueOf(0);
      var4.put("port", var5);
      Integer var6 = Integer.valueOf(-1);
      var4.put("flags", var6);
      var4.put("login", "");
      var4.put("password", var3);
      var4.put("domain", "");
      Long var7 = Long.valueOf(var1);
      var4.put("accountKey", var7);
      return var4;
   }

   private static class LocalMailboxInfo {

      private static final int COLUMN_ACCOUNT_KEY = 2;
      private static final int COLUMN_DISPLAY_NAME = 1;
      private static final int COLUMN_ID = 0;
      private static final int COLUMN_TYPE = 3;
      private static final String[] PROJECTION;
      long mAccountKey;
      String mDisplayName;
      long mId;
      int mType;


      static {
         String[] var0 = new String[]{"_id", "displayName", "accountKey", "type"};
         PROJECTION = var0;
      }

      public LocalMailboxInfo(Cursor var1) {
         long var2 = var1.getLong(0);
         this.mId = var2;
         String var4 = var1.getString(1);
         this.mDisplayName = var4;
         long var5 = var1.getLong(2);
         this.mAccountKey = var5;
         int var7 = var1.getInt(3);
         this.mType = var7;
      }
   }
}
