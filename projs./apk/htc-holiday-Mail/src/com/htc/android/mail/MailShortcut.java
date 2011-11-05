package com.htc.android.mail;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.htc.android.mail.Account;
import com.htc.android.mail.AccountListDialogPicker;
import com.htc.android.mail.AccountPool;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailListTab;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.NewMailNotification;
import com.htc.android.mail.Util;
import com.htc.android.mail.ll;
import com.htc.android.mail.util.SparseLongArray;
import com.htc.util.icon.IconGenerator;
import com.htc.util.skin.HtcSkinUtil;

public class MailShortcut extends Activity {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final int DLG_SHOW_ACCOUNT_LIST = 0;
   private static final int REQUEST_CODE_ACCOUNT_LIST_SHORTCUT = 1;
   private static final int REQUEST_CODE_SHORTCUT_CREATE_ACCOUNT = 0;
   private static final String TAG = "MailShortcut";
   private static SparseLongArray mUnreadList = new SparseLongArray();
   private static IconGenerator sFactory = new IconGenerator();


   public MailShortcut() {}

   public static void clearMailShortcut(Context var0, long var1) {
      synchronized(MailShortcut.class){}

      try {
         if(DEBUG) {
            String var3 = "clearMailShortcut>" + var1;
            ll.d("MailShortcut", var3);
         }

         updateMailAPshortcut(var0, (boolean)1);
         if(var1 < 0L) {
            if(DEBUG) {
               ll.d("MailShortcut", "accountId return>");
            }
         } else {
            String var4 = "content://mail/accounts/" + var1 + "%";
            generateIcon(var0, var4, (Intent)null, (String)null, var1, 0, (boolean)1);
         }
      } finally {
         ;
      }

   }

   public static void clearMailShortcutInLine(Context var0, long var1) {
      Handler var3 = MailListTab.BackGroundHandler.getInstance();
      MailShortcut.3 var4 = new MailShortcut.3(var0, var1);
      var3.post(var4);
   }

   private static MailShortcut.ShortcutBitmap generateBitmap(Context var0, int var1) {
      MailShortcut.ShortcutBitmap var10;
      if(var1 > 0) {
         if(DEBUG) {
            ll.d("MailShortcut", "bubble text>");
         }

         IconGenerator var2 = sFactory;
         Drawable var3 = var0.getResources().getDrawable(2130837506);
         Resources var4 = var0.getResources();
         int var5 = getBubbleDrawableId(var0);
         Drawable var6 = var4.getDrawable(var5);
         Bitmap var9 = var2.MailcreateBubbleTextIcon(var0, var3, var6, -1, var1);
         var10 = new MailShortcut.ShortcutBitmap((MailShortcut.1)null);
         var10.bmp = var9;
         var10.canRecycled = (boolean)0;
      } else {
         if(DEBUG) {
            ll.d("MailShortcut", "default icon>");
         }

         Bitmap var11 = BitmapFactory.decodeResource(var0.getResources(), 2130837506);
         var10 = new MailShortcut.ShortcutBitmap((MailShortcut.1)null);
         var10.bmp = var11;
         var10.canRecycled = (boolean)0;
      }

      return var10;
   }

   private static void generateIcon(Context param0, String param1, Intent param2, String param3, long param4, int param6, boolean param7) {
      // $FF: Couldn't be decompiled
   }

   private static int getBubbleDrawableId(Context var0) {
      return HtcSkinUtil.getDrawableResIdentifier(var0, "common_notification_new_on", 34080172);
   }

   private Intent returnToShortCut(long var1, String var3) {
      if(DEBUG) {
         String var4 = "returnToShortCut1>" + var1;
         ll.d("MailShortcut", var4);
      }

      Uri var5 = MailProvider.sAccountsURI;
      String var6 = "" + var1;
      Uri var7 = Uri.withAppendedPath(var5, var6);
      Intent var8 = new Intent("android.intent.action.VIEW");
      var8.setDataAndType(var7, "vnd.android.cursor.dir/mail_tab");
      Intent var10 = var8.setFlags(67108864);
      Intent var11 = var8.putExtra("shortcut", (boolean)1);
      Intent var12 = new Intent();
      var12.putExtra("android.intent.extra.shortcut.INTENT", var8);
      if(var1 < 0L) {
         CharSequence var14 = this.getText(2131361857);
         var12.putExtra("android.intent.extra.shortcut.NAME", var14);
      } else {
         var12.putExtra("android.intent.extra.shortcut.NAME", var3);
         Account var19 = Account.getAccount(this.getApplicationContext(), var1);
         int var20 = 0;
         if(var19 != null) {
            if(NewMailNotification.sVersion == 2) {
               long var21 = var19.id;
               var20 = MailCommon.getUnreadNumberFromNotification(this, var21);
            } else if(NewMailNotification.sVersion == 1) {
               long var32 = var19.id;
               var20 = MailCommon.getUnreadNumber(this, var32, 9223372036854775802L);
            }
         }

         if(DEBUG) {
            String var23 = "account>" + var1 + ",new mail>>" + var20;
            ll.d("MailShortcut", var23);
         }

         if(var20 > 0) {
            IconGenerator var24 = sFactory;
            Drawable var25 = this.getResources().getDrawable(2130837506);
            Resources var26 = this.getResources();
            int var27 = getBubbleDrawableId(this.getApplicationContext());
            Drawable var28 = var26.getDrawable(var27);
            Bitmap var30 = var24.MailcreateBubbleTextIcon(this, var25, var28, -1, var20);
            var12.putExtra("android.intent.extra.shortcut.ICON", var30);
            return var12;
         }
      }

      try {
         ShortcutIconResource var16 = ShortcutIconResource.fromContext(this, 2130837506);
         var12.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", var16);
      } catch (OutOfMemoryError var38) {
         int var35 = Log.e("MailShortcut", "loadContactPhoto: OutOfMemoryError");
      } catch (Exception var39) {
         int var37 = Log.e("MailShortcut", "loadContactPhoto: exception");
      }

      return var12;
   }

   private static void updateMailAPshortcut(Context param0, boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public static void updateMailAPshortcutInLine(Context var0, boolean var1) {
      Handler var2 = MailListTab.BackGroundHandler.getInstance();
      MailShortcut.1 var3 = new MailShortcut.1(var0, var1);
      var2.post(var3);
   }

   public static void updateMailShortcut(Context param0, long param1) {
      // $FF: Couldn't be decompiled
   }

   public static void updateMailShortcutInLine(Context var0, long var1) {
      Handler var3 = MailListTab.BackGroundHandler.getInstance();
      MailShortcut.2 var4 = new MailShortcut.2(var0, var1);
      var3.post(var4);
   }

   public static void updateMailShortcutNewId(Context param0, long param1, long param3) {
      // $FF: Couldn't be decompiled
   }

   public static void updateMailShortcutNewIdInLine(Context var0, long var1, long var3) {
      Handler var5 = MailListTab.BackGroundHandler.getInstance();
      MailShortcut.4 var11 = new MailShortcut.4(var0, var1, var3);
      var5.post(var11);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(DEBUG) {
         String var4 = "onActivityResult>" + var1 + "," + var2 + "," + var3;
         ll.d("MailShortcut", var4);
      }

      switch(var1) {
      case 0:
         if(DEBUG) {
            ll.d("MailShortcut", "SHORTCUT_CREATE_ACCOUNT>");
         }

         if(var2 == -1 && var3 != null) {
            int var6 = var3.getIntExtra("AccountId", -1);
            String var7 = var3.getStringExtra("AccountName");
            long var8 = (long)var6;
            Intent var10 = this.returnToShortCut(var8, var7);
            this.setResult(-1, var10);
         } else if(DEBUG) {
            ll.w("MailShortcut", " no create account>");
         }

         this.finish();
         return;
      case 1:
         if(var2 == -1) {
            if(var3 == null) {
               return;
            }

            long var11 = var3.getLongExtra("accountId", 65535L);
            if(var11 != 65535L) {
               AccountPool var13 = AccountPool.getInstance(this);
               Context var14 = this.getApplicationContext();
               Account var15 = var13.getAccount(var14, var11);
               Context var16 = this.getApplicationContext();
               String var17 = var15.getDescription(var16);
               if(DEBUG) {
                  String var18 = "choose account = +accountId++accountName" + var17;
                  ll.d("MailShortcut", var18);
               }

               Intent var19 = this.returnToShortCut(var11, var17);
               this.setResult(-1, var19);
               Intent var20 = new Intent("com.htc.launcher.ThemeChooser.action.silder_change");
               Intent var21 = var20.putExtra("silder_state", (boolean)0);
               this.sendBroadcast(var20);
            }
         }

         this.finish();
         return;
      default:
         if(DEBUG) {
            String var5 = "unknow >" + var1;
            ll.d("MailShortcut", var5);
         }
      }
   }

   public final void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(DEBUG) {
         ll.d("MailShortcut", "onCreate >");
      }

      boolean var2 = this.requestWindowFeature(1);
      int var3 = Util.getAccountCountPref(this.getApplicationContext());
      if(DEBUG) {
         String var4 = "accountCount>" + var3;
         ll.d("MailShortcut", var4);
      }

      if(var3 == 0) {
         Intent var5 = new Intent(this, Mail.class);
         Intent var6 = var5.putExtra("fromMailWidget", (boolean)1);
         this.startActivityForResult(var5, 0);
      } else if(var3 == 1) {
         if(DEBUG) {
            ll.d("MailShortcut", "only one account for shortcut>");
         }

         ContentResolver var7 = this.getContentResolver();
         Uri var8 = MailProvider.sAccountsURI;
         String[] var9 = new String[]{"_id, _desc"};
         Cursor var10 = var7.query(var8, var9, "_del = -1", (String[])null, (String)null);
         if(var10 != null) {
            if(var10.moveToFirst()) {
               long var11 = var10.getLong(0);
               String var13 = var10.getString(1);
               Intent var14 = this.returnToShortCut(var11, var13);
               this.setResult(-1, var14);
               this.finish();
               Intent var15 = new Intent("com.htc.launcher.ThemeChooser.action.silder_change");
               Intent var16 = var15.putExtra("silder_state", (boolean)0);
               this.sendBroadcast(var15);
               var10.close();
            }
         }
      } else {
         Intent var17 = new Intent("android.intent.action.VIEW");
         var17.setClass(this, AccountListDialogPicker.class);
         Intent var19 = var17.putExtra("show_separate_account_only", (boolean)1);
         Intent var20 = var17.putExtra("show_exchange_only", (boolean)0);
         this.startActivityForResult(var17, 1);
      }
   }

   private static class ShortcutBitmap {

      public Bitmap bmp;
      public boolean canRecycled;


      private ShortcutBitmap() {}

      // $FF: synthetic method
      ShortcutBitmap(MailShortcut.1 var1) {
         this();
      }
   }

   static class 2 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final Context val$context;


      2(Context var1, long var2) {
         this.val$context = var1;
         this.val$accountId = var2;
      }

      public void run() {
         Context var1 = this.val$context;
         long var2 = this.val$accountId;
         MailShortcut.updateMailShortcut(var1, var2);
      }
   }

   static class 1 implements Runnable {

      // $FF: synthetic field
      final boolean val$checkCache;
      // $FF: synthetic field
      final Context val$context;


      1(Context var1, boolean var2) {
         this.val$context = var1;
         this.val$checkCache = var2;
      }

      public void run() {
         Context var1 = this.val$context;
         boolean var2 = this.val$checkCache;
         MailShortcut.updateMailAPshortcut(var1, var2);
      }
   }

   static class 4 implements Runnable {

      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final long val$newAccountId;
      // $FF: synthetic field
      final long val$oldAccountId;


      4(Context var1, long var2, long var4) {
         this.val$context = var1;
         this.val$oldAccountId = var2;
         this.val$newAccountId = var4;
      }

      public void run() {
         Context var1 = this.val$context;
         long var2 = this.val$oldAccountId;
         long var4 = this.val$newAccountId;
         MailShortcut.updateMailShortcutNewId(var1, var2, var4);
      }
   }

   static class 3 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final Context val$context;


      3(Context var1, long var2) {
         this.val$context = var1;
         this.val$accountId = var2;
      }

      public void run() {
         Context var1 = this.val$context;
         long var2 = this.val$accountId;
         MailShortcut.clearMailShortcut(var1, var2);
      }
   }
}
