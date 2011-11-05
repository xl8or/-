package com.android.email.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class Welcome extends Activity {

   public static final String BADGE_APPS_CLASS = "class";
   public static final String BADGE_APPS_COUNT = "badgecount";
   public static final String BADGE_APPS_PACKAGE = "package";
   public static final String[] BADGE_APPS_PROJECTION;
   public static final String BADGE_AUTHORITY = "com.sec.badge";
   public static final String BADGE_TABLE_APPS = "apps";
   protected static final String BODY_DATABASE_NAME = "EmailProviderBody.db";
   private static final boolean EMAIL_COMBINDED = true;
   private static final String Z7_ENGINE_FILE = "SevenEngine.apk";
   private static final String Z7_ENGINE_SAVE_TEMP = "/data/data/com.android.email/files";
   private static final String Z7_ENGINE_VERSION_XML = "z7_engine_ver.xml";
   public final String TAG = "Email/Welcome";
   private SQLiteDatabase db;


   static {
      String[] var0 = new String[]{"badgecount"};
      BADGE_APPS_PROJECTION = var0;
   }

   public Welcome() {}

   // $FF: synthetic method
   static SQLiteDatabase access$000(Welcome var0) {
      return var0.db;
   }

   // $FF: synthetic method
   static SQLiteDatabase access$002(Welcome var0, SQLiteDatabase var1) {
      var0.db = var1;
      return var1;
   }

   public static void actionStart(Activity var0) {
      Intent var1 = new Intent(var0, Welcome.class);
      Intent var2 = var1.addFlags(67108864);
      var0.startActivity(var1);
   }

   private int getSevenVersionInfo() {
      int var1 = -1;

      int var8;
      label34: {
         int var7;
         try {
            InputStream var2 = this.getResources().getAssets().open("z7_engine_ver.xml");
            XmlPullParserFactory var3 = XmlPullParserFactory.newInstance();
            var3.setNamespaceAware((boolean)1);
            XmlPullParser var4 = var3.newPullParser();
            var4.setInput(var2, "UTF-8");
            int var5 = var4.getEventType();

            String var6;
            for(var6 = ""; var5 != 1; var5 = var4.next()) {
               if(var5 == 4) {
                  var6 = var4.getText();
               }
            }

            if(var6 == null || var6.equals("")) {
               break label34;
            }

            var7 = Integer.parseInt(var6);
         } catch (Exception var10) {
            var8 = -1;
            return var8;
         }

         var1 = var7;
      }

      var8 = var1;
      return var8;
   }

   public void onCreate(Bundle param1) {
      // $FF: Couldn't be decompiled
   }

   public void showDialogInstall(Activity var1, Uri var2) {
      Builder var3 = (new Builder(var1)).setMessage(2131166952).setTitle(2131166953);
      Welcome.4 var4 = new Welcome.4(var2);
      Builder var5 = var3.setPositiveButton(2131166262, var4);
      Welcome.3 var6 = new Welcome.3();
      Builder var7 = var5.setNegativeButton(2131166263, var6);
      Welcome.2 var8 = new Welcome.2();
      var7.setOnKeyListener(var8).create().show();
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.dismiss();
         Welcome.this.finish();
      }
   }

   class 4 implements OnClickListener {

      // $FF: synthetic field
      final Uri val$apkUri;


      4(Uri var2) {
         this.val$apkUri = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         var1.dismiss();
         Intent var3 = new Intent("android.intent.action.VIEW");
         Uri var4 = this.val$apkUri;
         var3.setDataAndType(var4, "application/vnd.android.package-archive");
         Welcome.this.startActivity(var3);
         Welcome.this.finish();
      }
   }

   class 1 extends Thread {

      1() {}

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   class 2 implements OnKeyListener {

      2() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         boolean var4;
         if(var2 == 4) {
            var1.dismiss();
            Welcome.this.finish();
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }
   }
}
