package com.htc.android.mail;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import com.htc.android.mail.Mail;
import com.htc.android.mail.Util;
import com.htc.android.mail.ll;
import com.htc.android.mail.mailservice.MailService;
import java.util.HashMap;

public class HtcMailCustomization {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "HtcMailCustomization";
   private String MAIL_PROVIDER_CUSTOMIZE_URI = "content://customization_settings/SettingTable/application_Mail";
   private String SELECTION_UPDATE = "\"key\" == \"user_update\"";


   public HtcMailCustomization() {}

   private Bundle byteArray2Bundle(byte[] var1) {
      Parcel var2 = Parcel.obtain();
      int var3 = var1.length;
      var2.unmarshall(var1, 0, var3);
      var2.setDataPosition(0);
      Bundle var4 = new Bundle();
      Bundle var5;
      if(var4 == null) {
         var5 = null;
      } else {
         var4.readFromParcel(var2);
         var5 = var4;
      }

      return var5;
   }

   private int convertInprotocol(String var1) {
      byte var2;
      if(var1 != null && var1 != "") {
         if(var1.equals("POP")) {
            var2 = 0;
         } else if(var1.equals("APOP")) {
            var2 = 1;
         } else if(var1.equals("IMAP")) {
            var2 = 2;
         } else if(var1.equals("EAS")) {
            var2 = 10;
         } else {
            var2 = 0;
         }
      } else {
         var2 = 0;
      }

      return var2;
   }

   private String covertDescription(Context var1, String var2) {
      String var3;
      if(var1 != null && var2 != null && !var2.equals("")) {
         String var7;
         label17: {
            String var6;
            try {
               Resources var4 = var1.getResources();
               int var5 = var1.getResources().getIdentifier(var2, "string", "com.htc.android.mail");
               var6 = var4.getString(var5);
            } catch (Exception var9) {
               var7 = var2;
               break label17;
            }

            var7 = var6;
         }

         var3 = var7;
      } else {
         var3 = "";
      }

      return var3;
   }

   private int covertResId(Context var1, String var2) {
      int var3;
      if(var1 != null && var2 != null && !var2.equals("0")) {
         var3 = var1.getResources().getIdentifier(var2, "drawable", "com.htc.android.mail");
      } else {
         var3 = 0;
      }

      return var3;
   }

   private HashMap<String, String> initWelecomeMessageMap(Bundle var1) {
      HashMap var2 = new HashMap();
      if(var1 != null) {
         Bundle var3 = var1.getBundle("welcome_message");
         String var4 = "plenty_set";
         String var5 = "installTo";
         int var6 = 0;

         while(true) {
            int var7 = var3.size();
            if(var6 >= var7) {
               break;
            }

            StringBuilder var8 = (new StringBuilder()).append(var4);
            int var9 = var6 + 1;
            String var10 = var8.append(var9).toString();
            String var11 = var3.getBundle(var10).getString(var5);
            if(var11 != null && !var11.equals("")) {
               var2.put(var11, var10);
            }

            ++var6;
         }
      }

      return var2;
   }

   private void installMessageToDB(Context param1, Bundle param2) {
      // $FF: Couldn't be decompiled
   }

   public void getBrowserHomepageUrlCustomizationData(Context var1) {
      Bundle var2 = this.getCustomizationData(var1, "content://customization_settings/SettingTable/application_Browser", (String)null);
      if(var2 != null) {
         Bundle var3 = var2.getBundle("homepage");
         if(var3 != null) {
            String var4 = var3.getString("url");
            String var5 = "test reuslt: url = " + var4;
            ll.d("HtcMailCustomization", var5);
         }
      }
   }

   public Bundle getCustomizationData(Context var1, String var2, String var3) {
      Bundle var4;
      if(var1 == null) {
         var4 = null;
      } else if(var2 == null) {
         var4 = null;
      } else {
         Uri var5 = Uri.parse(var2);
         ContentResolver var6 = var1.getContentResolver();
         Object var8 = null;
         Object var9 = null;
         Cursor var10 = var6.query(var5, (String[])null, var3, (String[])var8, (String)var9);
         if(var10 == null) {
            int var11 = Log.e("HtcMailCustomization", "Failed to get cursor");
            var4 = null;
         } else if(var10.getCount() == 0) {
            int var12 = Log.e("HtcMailCustomization", "cursor size is 0");
            var10.close();
            var4 = null;
         } else {
            int var13 = var10.getColumnIndex("value");
            if(-1 == var13) {
               int var14 = Log.e("HtcMailCustomization", "no customized data support");
               var10.close();
               var4 = null;
            } else {
               boolean var15 = var10.moveToFirst();
               boolean var25 = false;

               Bundle var18;
               label93: {
                  Bundle var17;
                  label92: {
                     try {
                        var25 = true;
                        byte[] var16 = var10.getBlob(var13);
                        var17 = this.byteArray2Bundle(var16);
                        var25 = false;
                        break label92;
                     } catch (Exception var26) {
                        String var20 = "load customize URI failed, get exception: " + var5 + ", " + var26;
                        int var21 = Log.e("HtcMailCustomization", var20);
                        var25 = false;
                     } finally {
                        if(var25) {
                           if(var10 != null) {
                              var10.close();
                           }

                        }
                     }

                     var18 = null;
                     if(var10 != null) {
                        var10.close();
                     }
                     break label93;
                  }

                  var18 = var17;
                  if(var10 != null) {
                     var10.close();
                  }
               }

               var4 = var18;
            }
         }
      }

      return var4;
   }

   public Bundle getMailCustomizationData(Context var1) {
      String var2 = this.MAIL_PROVIDER_CUSTOMIZE_URI;
      return this.getCustomizationData(var1, var2, (String)null);
   }

   @Deprecated
   public void insertMailProviderCustomizationData(Context var1, SQLiteDatabase var2, Bundle var3) {
      if(var3 != null) {
         String var5 = "provider";
         Bundle var6 = var3.getBundle(var5);
         if(var6 != null && var6.size() > 0) {
            String var7 = "provider";
            String var8 = "domain";
            String var9 = "inprotocol";
            String var10 = "description";
            String var11 = "resid";
            String var12 = "plenty_set";
            ContentValues var13 = new ContentValues();
            StringBuilder var14 = (new StringBuilder()).append("Insert Mail provider from customization count:");
            int var15 = var6.size();
            String var16 = var14.append(var15).toString();
            ll.d("HtcMailCustomization", var16);
            int var17 = 0;

            while(true) {
               int var18 = var6.size();
               if(var17 >= var18) {
                  return;
               }

               StringBuilder var21 = new StringBuilder();
               StringBuilder var23 = var21.append(var12);
               int var24 = var17 + 1;
               String var25 = var23.append(var24).toString();
               Bundle var28 = var6.getBundle(var25);
               String var29 = var28.getString(var7);
               String var30 = var28.getString(var8);
               String var31 = var28.getString(var9);
               String var32 = var28.getString(var10);
               String var33 = var28.getString(var11);
               var13.clear();
               String var35 = "_provider";
               var13.put(var35, var29);
               String var38 = "_domain";
               var13.put(var38, var30);
               Integer var42 = Integer.valueOf(this.convertInprotocol(var31));
               var13.put("_inprotocol", var42);
               String var46 = this.covertDescription(var1, var32);
               var13.put("_description", var46);
               Integer var50 = Integer.valueOf(this.covertResId(var1, var33));
               var13.put("_resid", var50);
               String var52 = "providers";
               Object var53 = null;
               var2.insert(var52, (String)var53, var13);
               StringBuilder var57 = (new StringBuilder()).append("insert provider done:");
               String var59 = var57.append(var29).toString();
               ll.d("TAG", var59);
               ++var17;
            }
         } else {
            ll.w("HtcMailCustomization", "providerList.size() 0>");
         }
      }
   }

   public void insertMailProviderCustomizationData(Context param1, Bundle param2) {
      // $FF: Couldn't be decompiled
   }

   @Deprecated
   public void insertMailProviderSettingCustomizationData(Context var1, SQLiteDatabase var2, Bundle var3) {
      if(var3 != null) {
         String var5 = "provider_setting";
         Bundle var6 = var3.getBundle(var5);
         if(var6 != null) {
            if(var6.size() > 0) {
               String var7 = "provider";
               String var8 = "domain";
               String var9 = "inserver";
               String var10 = "inport";
               String var11 = "outserver";
               String var12 = "outport";
               String var13 = "inprotocol";
               String var14 = "useSSLin";
               String var15 = "useSSLout";
               String var16 = "smtpauth";
               String var17 = "plenty_set";
               ContentValues var18 = new ContentValues();
               StringBuilder var19 = (new StringBuilder()).append("Insert Mail provider setting from customization count:");
               int var20 = var6.size();
               String var21 = var19.append(var20).toString();
               ll.d("HtcMailCustomization", var21);
               int var22 = 0;

               while(true) {
                  int var23 = var6.size();
                  if(var22 >= var23) {
                     return;
                  }

                  StringBuilder var26 = new StringBuilder();
                  StringBuilder var28 = var26.append(var17);
                  int var29 = var22 + 1;
                  String var30 = var28.append(var29).toString();
                  Bundle var33 = var6.getBundle(var30);
                  String var36 = var33.getString(var7);
                  String var39 = var33.getString(var8);
                  String var42 = var33.getString(var9);
                  String var45 = var33.getString(var10);
                  String var48 = var33.getString(var11);
                  String var51 = var33.getString(var12);
                  String var54 = var33.getString(var13);
                  String var57 = var33.getString(var14);
                  String var60 = var33.getString(var15);
                  String var63 = var33.getString(var16);
                  if(var63 == null) {
                     var63 = "1";
                  }

                  var18.clear();
                  String var65 = "_provider";
                  var18.put(var65, var36);
                  String var68 = "_domain";
                  var18.put(var68, var39);
                  String var71 = "_inserver";
                  var18.put(var71, var42);
                  Integer var73 = Integer.valueOf(var45);
                  var18.put("_inport", var73);
                  String var75 = "_outserver";
                  var18.put(var75, var48);
                  Integer var77 = Integer.valueOf(var51);
                  var18.put("_outport", var77);
                  Integer var80 = Integer.valueOf(this.convertInprotocol(var54));
                  var18.put("_inprotocol", var80);
                  Integer var81 = Integer.valueOf(var57);
                  var18.put("_usesslin", var81);
                  Integer var82 = Integer.valueOf(var60);
                  var18.put("_usesslout", var82);
                  Integer var83 = Integer.valueOf(var63);
                  var18.put("_smtpauth", var83);
                  String var85 = "providersettings";
                  Object var86 = null;
                  var2.insert(var85, (String)var86, var18);
                  StringBuilder var90 = (new StringBuilder()).append("insert provider done:");
                  String var92 = var90.append(var36).toString();
                  ll.d("TAG", var92);
                  ++var22;
               }
            }
         }
      }
   }

   public void insertMailProviderSettingCustomizationData(Context param1, Bundle param2) {
      // $FF: Couldn't be decompiled
   }

   public void loadCustSignature(Context var1, Bundle var2) {
      if(var2 != null) {
         Bundle var3 = var2.getBundle("mail_signature_localization");
         if(var3 != null) {
            if(var3.size() > 0) {
               String var4 = "plenty_set";
               String var5 = "locale";
               int var6 = 0;

               while(true) {
                  int var7 = var3.size();
                  if(var6 >= var7) {
                     return;
                  }

                  StringBuilder var8 = (new StringBuilder()).append(var4);
                  int var9 = var6 + 1;
                  String var10 = var8.append(var9).toString();
                  Bundle var11 = var3.getBundle(var10);
                  String var12 = var11.getString(var5);
                  String var13 = var11.getString("signature");
                  if(DEBUG) {
                     String var14 = "locale:" + var12 + ", signature:" + var13;
                     ll.d("HtcMailCustomization", var14);
                  }

                  if(var12 != null && var13 != null) {
                     Util.writeSignatureToPref(var1, var12, var13);
                  }

                  ++var6;
               }
            }
         }
      }
   }

   @Deprecated
   public void preInstallAccount(Context param1, SQLiteDatabase param2, Bundle param3) {
      // $FF: Couldn't be decompiled
   }

   public void preInstallAccount(Context param1, Bundle param2) {
      // $FF: Couldn't be decompiled
   }

   public void preInstallMessage(Context var1, Bundle var2) {
      if(var2 != null) {
         Bundle var3 = var2.getBundle("welcome_message");
         if(var3 != null) {
            if(var3.size() > 0) {
               String var4 = "plenty_set";
               StringBuilder var5 = (new StringBuilder()).append("Insert Mail message from customization count:");
               int var6 = var3.size();
               String var7 = var5.append(var6).toString();
               ll.d("HtcMailCustomization", var7);
               int var8 = 0;

               while(true) {
                  int var9 = var3.size();
                  if(var8 >= var9) {
                     return;
                  }

                  StringBuilder var10 = (new StringBuilder()).append(var4);
                  int var11 = var8 + 1;
                  String var12 = var10.append(var11).toString();
                  Bundle var13 = var3.getBundle(var12);
                  this.installMessageToDB(var1, var13);
                  ++var8;
               }
            }
         }
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final Context val$context;


      2(Context var2) {
         this.val$context = var2;
      }

      public void run() {
         MailService.actionReschedulePeak(this.val$context);
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final Context val$context;


      1(Context var2) {
         this.val$context = var2;
      }

      public void run() {
         MailService.actionReschedulePeak(this.val$context);
      }
   }
}
