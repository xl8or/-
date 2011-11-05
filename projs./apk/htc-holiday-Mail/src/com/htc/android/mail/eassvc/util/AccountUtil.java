package com.htc.android.mail.eassvc.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.eassvc.calendar.CalendarManager;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.common.ExchangeSyncSources;
import com.htc.android.mail.eassvc.pim.EASMail;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import com.htc.android.mail.eassvc.util.EASEventBroadcaster;
import com.htc.android.mail.eassvc.util.EASLog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AccountUtil {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   static final String TAG = "EAS_AccountUtil";


   public AccountUtil() {}

   public static void addExcahngeCalendar(Context var0, ExchangeAccount var1) {
      long var2 = var1.accountId;
      int[] var4 = Account.getDecodedColorIdx(getAccountColorIdx(var0, var2));
      int[][] var5 = MailCommon.account_drawable;
      int var6 = var4[0];
      int[] var7 = var5[var6];
      int var8 = var4[1];
      int var9 = var7[var8];

      try {
         String var10 = var1.accountName;
         String var11 = var1.accountType;
         CalendarManager.insertNewCalendar(var0, var10, var11, var9);
      } catch (Exception var12) {
         var12.printStackTrace();
      }
   }

   static boolean checkIfAccount(Context var0) {
      ContentResolver var1 = var0.getContentResolver();
      Uri var2 = EASMail.EASACCOUNTS_URI;
      Object var3 = null;
      Object var4 = null;
      Object var5 = null;
      Cursor var6 = var1.query(var2, (String[])null, (String)var3, (String[])var4, (String)var5);
      boolean var7;
      if(var6 == null) {
         var7 = false;
      } else {
         int var8 = var6.getCount();
         if(var6 != null && !var6.isClosed()) {
            var6.close();
         }

         if(var8 > 0) {
            var7 = true;
         } else {
            var7 = false;
         }
      }

      return var7;
   }

   public static final String combine(List<String> var0, String var1, boolean var2) {
      StringBuilder var3 = new StringBuilder();
      String var4;
      if(var0.size() <= 0) {
         var4 = "";
      } else {
         if(var0.size() > 0 && (String)var0.get(0) != null) {
            if(var2) {
               String var5 = DatabaseUtils.sqlEscapeString((String)var0.get(0));
               var3.append(var5);
            } else {
               String var12 = (String)var0.get(0);
               var3.append(var12);
            }
         }

         int var7 = 1;

         while(true) {
            int var8 = var0.size();
            if(var7 >= var8) {
               var4 = var3.toString();
               break;
            }

            if((String)var0.get(var7) != null) {
               var3.append(var1);
               if(var2) {
                  String var10 = DatabaseUtils.sqlEscapeString((String)var0.get(var7));
                  var3.append(var10);
               } else {
                  String var14 = (String)var0.get(var7);
                  var3.append(var14);
               }
            }

            ++var7;
         }
      }

      return var4;
   }

   public static boolean createAccount(Context var0, ExchangeAccount var1) {
      if(DEBUG) {
         EASLog.d("EAS_AccountUtil", var1, "- createAccount()");
      }

      long var4;
      boolean var6;
      try {
         deleteAccount(var0, var1);
         String[] var3 = insertDefaultMailbox(var0);
         var4 = insertAccount(var0, var1, var3);
         var1.accountId = var4;
         updateDefaultMailboxAccount(var0, var3, var4);
      } catch (Exception var7) {
         var7.printStackTrace();
         var6 = false;
         return var6;
      }

      if(var4 > 0L) {
         var6 = true;
      } else {
         EASLog.e("EAS_AccountUtil", var1, "createAccount(): fail");
         var6 = false;
      }

      return var6;
   }

   public static boolean deleteAccount(Context param0, ExchangeAccount param1) {
      // $FF: Couldn't be decompiled
   }

   public static void enableAccount(Context param0, ExchangeAccount param1, boolean param2) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public static int getAccountColorIdx(Context param0, long param1) {
      // $FF: Couldn't be decompiled
   }

   public static File getAccountConfigPath(Context var0, long var1) {
      if(var1 < 0L) {
         EASLog.d("EAS_AccountUtil", var1, "- getAccountConfigPath(): accountId < 0");
         Thread var3 = Thread.currentThread();
         Thread.dumpStack();
      }

      StringBuilder var4 = new StringBuilder();
      File var5 = var0.getDir("config", 0);
      StringBuilder var6 = var4.append(var5);
      String var7 = File.separator;
      StringBuilder var8 = var6.append(var7);
      String var9 = String.valueOf(var1);
      String var10 = var8.append(var9).toString();
      File var11 = new File(var10);
      if(!var11.exists()) {
         boolean var12 = var11.mkdir();
      }

      return new File(var10);
   }

   public static int getAccountCount(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static long getCalenderIdByAccountId(Context param0, long param1) {
      // $FF: Couldn't be decompiled
   }

   private static long insertAccount(Context var0, ExchangeAccount var1, String[] var2) throws Exception {
      if(DEBUG) {
         EASLog.d("EAS_AccountUtil", var1, "- createAccount_insert_account()");
      }

      long var3 = 65535L;
      Object var5 = null;
      if(var1 != null && var2 != null && var2.length >= 5) {
         String var7;
         label59: {
            String var6;
            label58: {
               try {
                  if(var1.password != null) {
                     var6 = Account.encodePwd(var1.password);
                     break label58;
                  }
               } catch (Exception var42) {
                  var42.printStackTrace();
                  throw var42;
               }

               var7 = (String)var5;
               break label59;
            }

            var7 = var6;
         }

         Object var8 = null;
         Object var9 = null;
         Bundle var10 = ExchangeSyncSources.getMailCommonSettingFromCustomization(var1.accountId, var0);
         String var11;
         String var12;
         if(var10 != null) {
            var11 = var10.getString("refreshMailWhenOpenFolder");
            var12 = var10.getString("downloadMessageWhenScroll");
         } else {
            var12 = (String)var9;
            var11 = (String)var8;
         }

         String var13;
         if(TextUtils.isEmpty(var11)) {
            var13 = MailProvider.refreshMailWhenOpenFolder;
         } else {
            var13 = var11;
         }

         String var14;
         if(TextUtils.isEmpty(var12)) {
            var14 = MailProvider.downloadMessageWhenScroll;
         } else {
            var14 = var12;
         }

         try {
            ContentValues var43 = new ContentValues();
            String var15 = var1.displayName;
            var43.put("_desc", var15);
            String var16 = var1.userName;
            var43.put("_name", var16);
            String var17 = var1.emailAddress;
            var43.put("_emailaddress", var17);
            String var18 = var1.userName;
            var43.put("_username", var18);
            String var19 = var1.userName;
            var43.put("_outusername", var19);
            var43.put("_password", var7);
            var43.put("_outpassword", var7);
            Integer var20 = Integer.valueOf(1);
            var43.put("_del", var20);
            var43.put("_provider", "Exchange");
            var43.put("_providerGroup", "Exchange");
            String var21 = Integer.toString(1);
            var43.put("_deleteFromServer", var21);
            var7 = "_useSSLin";
            String var22;
            if(var1.requireSSL == 1) {
               var22 = "1";
            } else {
               var22 = "0";
            }

            var43.put(var7, var22);
            String var23 = var1.serverName;
            var43.put("_inserver", var23);
            String var24 = var1.domainName;
            var43.put("_easDomain", var24);
            String var25 = var1.protocolVer;
            var43.put("_easSvrProtocol", var25);
            String var26 = Integer.toString(var1.heartBeatInterval);
            var43.put("_easHeartBeatInternal", var26);
            String var27 = var1.deviceID;
            var43.put("_easDeviceID", var27);
            String var28 = var1.deviceType;
            var43.put("_easDeviceType", var28);
            var43.put("_refreshMailWhenOpenFolder", var13);
            var43.put("_downloadMessageWhenScroll", var14);
            var43.put("_protocol", "4");
            var43.put("_providerid", "5");
            String var29 = var2[0];
            var43.put("_defaultfolderId", var29);
            String var30 = var2[1];
            var43.put("_trashfolderId", var30);
            String var31 = var2[2];
            var43.put("_sentfolderId", var31);
            String var32 = var2[3];
            var43.put("_draftfolderId", var32);
            String var33 = var2[4];
            var43.put("_outfolderId", var33);
            if(!checkIfAccount(var0)) {
               EASEventBroadcaster.broadcaseResetDefaultAccount(var0);
               Integer var34 = Integer.valueOf(1);
               var43.put("_defaultaccount", var34);
            }

            ContentResolver var35 = var0.getContentResolver();
            Uri var36 = EASMail.EASACCOUNTS_URI;
            var3 = ContentUris.parseId(var35.insert(var36, var43));
            if(var3 <= 0L) {
               EASLog.e("EAS_AccountUtil", var1, "Insert exchange account failed");
            }

            return var3;
         } catch (Exception var41) {
            var41.printStackTrace();
            throw var41;
         }
      } else {
         EASLog.e("EAS_AccountUtil", var1, "parameter are null, can\'t create exchange account");
         throw new Exception("parameter are null, can\'t create exchange account");
      }
   }

   private static String[] insertDefaultMailbox(Context var0) throws Exception {
      if(DEBUG) {
         EASLog.d("EAS_AccountUtil", "- createAccount_insert_defaultMailbox()");
      }

      String[] var1 = new String[5];
      int var2 = 0;

      while(var2 <= 4) {
         try {
            String var3 = "";
            String var4 = "1";
            String var5 = "-1";
            String var6 = "-1";
            String var7 = "-1";
            String var8 = "-1";
            String var9 = "1";
            String var10 = "1";
            Object var11 = null;
            Object var12 = null;
            String var13;
            switch(var2) {
            case 0:
               var13 = Integer.toString(2);
               var8 = var10;
               var10 = "Inbox";
               var3 = var9;
               var9 = null;
               var12 = null;
               break;
            case 1:
               var13 = Integer.toString(4);
               var8 = var10;
               var10 = "Trash";
               var3 = var9;
               var9 = (String)var12;
               var12 = null;
               break;
            case 2:
               var13 = Integer.toString(5);
               var8 = "0";
               var10 = "Sent";
               var3 = "2";
               var9 = (String)var12;
               var12 = null;
               break;
            case 3:
               var13 = Integer.toString(3);
               var8 = "0";
               var10 = "Draft";
               var3 = "2";
               var9 = (String)var12;
               var12 = null;
               break;
            case 4:
               var13 = Integer.toString(6);
               var8 = "0";
               var10 = "Out";
               var3 = "2";
               var9 = (String)var12;
               var12 = null;
               break;
            default:
               var13 = var8;
               var8 = var10;
               var10 = var3;
               var3 = var9;
               var9 = (String)var12;
               var12 = var11;
            }

            ContentValues var14 = new ContentValues();
            var14.put("_undecodename", var10);
            var14.put("_decodename", var10);
            var14.put("_shortname", var10);
            var14.put("_serverfolder", var4);
            var14.put("_account", var5);
            var14.put("_serverid", var6);
            var14.put("_parentid", var7);
            var14.put("_type", var13);
            var14.put("_movegroup", var3);
            var14.put("_showsender", var8);
            Integer var15 = Integer.valueOf((int)var12);
            var14.put("_defaultfolder", var15);
            Boolean var16 = Boolean.valueOf((boolean)var9);
            var14.put("_default_sync", var16);
            ContentResolver var17 = var0.getContentResolver();
            Uri var18 = EASSyncCommon.EASMAILBOXS_URI;
            long var25 = ContentUris.parseId(var17.insert(var18, var14));
            if(var25 <= 0L) {
               if(DEBUG) {
                  StringBuilder var19 = (new StringBuilder()).append("Insert default Mailbox table: ");
                  String var20 = Integer.toString(var2);
                  String var21 = var19.append(var20).append(" error").toString();
                  EASLog.e("EAS_AccountUtil", var21);
               }
            } else {
               String var22 = Long.toString(var25);
               var1[var2] = var22;
            }

            ++var2;
         } catch (Exception var24) {
            var24.printStackTrace();
            throw var24;
         }
      }

      return var1;
   }

   public static boolean isAccountDeleted(Context param0, long param1) {
      // $FF: Couldn't be decompiled
   }

   public static void markAsDelete(Context var0, long var1) {
      try {
         String var3 = " _id = " + var1;
         String var4 = new String(var3);
         ContentValues var5 = new ContentValues();
         Integer var6 = Integer.valueOf(1);
         var5.put("_del", var6);
         ContentResolver var7 = var0.getContentResolver();
         Uri var8 = EASMail.EASACCOUNTS_URI;
         var7.update(var8, var5, var4, (String[])null);
      } catch (Exception var10) {
         var10.printStackTrace();
      }
   }

   public static void removeDeletedAccount(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static void updateAccount(Context var0, ExchangeAccount var1, boolean var2) throws Exception {
      String var3 = "EAS_AccountUtil";
      if(DEBUG) {
         EASLog.d("EAS_AccountUtil", var1, "- updateAccount");
      }

      if(var1 != null && var0 != null) {
         String var4 = null;

         label75: {
            String var5;
            try {
               if(var1.password == null) {
                  break label75;
               }

               var5 = Account.encodePwd(var1.password);
            } catch (Exception var28) {
               throw var28;
            }

            var4 = var5;
         }

         ContentResolver var6 = var0.getContentResolver();

         try {
            ContentValues var7 = new ContentValues();
            String var8 = var1.userName;
            var7.put("_name", var8);
            String var9 = var1.emailAddress;
            var7.put("_emailaddress", var9);
            String var10 = var1.userName;
            var7.put("_username", var10);
            var7.put("_password", var4);
            String var11 = "_useSSLin";
            if(var1.requireSSL == 1) {
               var3 = "1";
            } else {
               var3 = "0";
            }

            var7.put(var11, var3);
            String var12 = var1.serverName;
            var7.put("_inserver", var12);
            String var13 = var1.domainName;
            var7.put("_easDomain", var13);
            String var14 = var1.protocolVer;
            var7.put("_easSvrProtocol", var14);
            String var15 = Integer.toString(var1.heartBeatInterval);
            var7.put("_easHeartBeatInternal", var15);
            String var16 = var1.deviceID;
            var7.put("_easDeviceID", var16);
            String var17 = var1.deviceType;
            var7.put("_easDeviceType", var17);
            if(var2) {
               Integer var18 = Integer.valueOf(-1);
               var7.put("_del", var18);
            }

            if(var1.accountId < 0L) {
               StringBuilder var19 = (new StringBuilder()).append("updateAccount() failed: Can\'t retrieve exchange account ");
               long var20 = var1.accountId;
               String var22 = var19.append(var20).toString();
               EASLog.d("EAS_AccountUtil", var1, var22);
            } else {
               if(TextUtils.isEmpty(var1.emailAddress) && DEBUG) {
                  EASLog.d("EAS_AccountUtil", "!!Attention, email address empty");
               }

               if(TextUtils.isEmpty(var1.serverName) && DEBUG) {
                  EASLog.d("EAS_AccountUtil", "!!Attentiln, serverName empty");
               }

               if(TextUtils.isEmpty(var1.userName) && DEBUG) {
                  EASLog.d("EAS_AccountUtil", "!!Attention, userName empty");
               }

               if(TextUtils.isEmpty(var1.password) && DEBUG) {
                  EASLog.d("EAS_AccountUtil", "!!Attention, password empty");
               }

               Builder var23 = EASMail.EASACCOUNTS_URI.buildUpon();
               String var24 = String.valueOf(var1.accountId);
               var23.appendEncodedPath(var24);
               Uri var26 = var23.build();
               if(var6.update(var26, var7, (String)null, (String[])null) < 1) {
                  if(DEBUG) {
                     EASLog.d("EAS_AccountUtil", var1, "updateAccount fail");
                  }
               }
            }
         } catch (Exception var27) {
            throw var27;
         }
      } else {
         EASLog.e("EAS_AccountUtil", var1, "updateAccount failed: parameter is null");
      }
   }

   private static void updateDefaultMailboxAccount(Context var0, String[] var1, long var2) throws Exception {
      if(var2 <= 0L) {
         EASLog.e("EAS_AccountUtil", var2, "error!! updateDefaultMailboxAccount, accountId <=0");
         throw new Exception("updateDefaultMailboxAccount, accountId <=0");
      } else if(var1 == null) {
         EASLog.e("EAS_AccountUtil", var2, "error!! updateDefaultMailboxAccount, parameter is null");
         throw new Exception("updateDefaultMailboxAccount, parameter is null");
      } else {
         if(DEBUG) {
            StringBuilder var4 = (new StringBuilder()).append("updateDefaultMailboxAccount(), length: ");
            int var5 = var1.length;
            String var6 = var4.append(var5).toString();
            EASLog.d("EAS_AccountUtil", var2, var6);
         }

         int var19;
         try {
            ContentValues var7 = new ContentValues();
            String var8 = Long.toString(var2);
            var7.put("_account", var8);
            ArrayList var9 = new ArrayList();
            int var10 = 0;

            while(true) {
               int var11 = var1.length;
               if(var10 >= var11) {
                  String var14 = combine(var9, ",", (boolean)1);
                  Object[] var15 = new Object[]{var14};
                  String var16 = String.format("_id in (%s)", var15);
                  ContentResolver var17 = var0.getContentResolver();
                  Uri var18 = EASSyncCommon.EASMAILBOXS_URI;
                  var19 = var17.update(var18, var7, var16, (String[])null);
                  break;
               }

               String var12 = var1[var10];
               var9.add(var12);
               ++var10;
            }
         } catch (Exception var25) {
            var25.printStackTrace();
            throw var25;
         }

         if(DEBUG) {
            StringBuilder var21 = (new StringBuilder()).append("updateDefaultmailboxAccount, update number: ");
            String var22 = Integer.toString(var19);
            String var23 = var21.append(var22).toString();
            EASLog.d("EAS_AccountUtil", var2, var23);
         }
      }
   }
}
