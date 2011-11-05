package com.htc.android.mail.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import com.htc.android.mail.Account;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailMessage;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.database.LocalStore;
import java.util.ArrayList;

public class POP3LocalStore extends LocalStore {

   private Account mAccount;
   private Context mContext;
   private ContentResolver mResolver;


   public POP3LocalStore(Context var1, Account var2) {
      this.mContext = var1;
      this.mAccount = var2;
      ContentResolver var3 = this.mContext.getContentResolver();
      this.mResolver = var3;
   }

   public void deleteMailLocal(Mailbox var1, ArrayList<MailMessage> var2) {
      Mailbox var3 = this.mAccount.getMailboxs().getTrashMailbox();
      long var4 = var1.id;
      long var6 = var3.id;
      if(var4 != var6) {
         this.moveMailLocal(var1, var2, var3);
      } else {
         ArrayList var12 = new ArrayList();
         String var14 = ",";
         byte var15 = 1;
         String var16 = MailCommon.combineMessageId(var2, var14, (boolean)var15);
         Object[] var17 = new Object[]{var16, null};
         Long var18 = Long.valueOf(var1.id);
         var17[1] = var18;
         String var19 = String.format("_id in (%s) AND _mailboxId = \'%d\' AND +_local = 0", var17);
         ContentResolver var20 = this.mResolver;
         Uri var21 = MailProvider.sMessagesURI;
         String[] var22 = new String[]{"_id", "_uid"};
         Cursor var23 = var20.query(var21, var22, var19, (String[])null, (String)null);
         if(var23 != null) {
            try {
               while(var23.moveToNext()) {
                  int var24 = var23.getColumnIndexOrThrow("_uid");
                  String var25 = var23.getString(var24);
                  int var26 = var23.getColumnIndexOrThrow("_id");
                  long var27 = var23.getLong(var26);
                  ContentValues var29 = new ContentValues();
                  Long var30 = Long.valueOf(this.mAccount.id);
                  var29.put("_accountId", var30);
                  Long var31 = Long.valueOf(var27);
                  var29.put("_messageId", var31);
                  Integer var32 = Integer.valueOf(1);
                  var29.put("_request", var32);
                  Long var33 = Long.valueOf(var1.id);
                  var29.put("_fromMailboxId", var33);
                  String var35 = "_uid";
                  var29.put(var35, var25);
                  boolean var39 = var12.add(var29);
               }
            } finally {
               if(!var23.isClosed()) {
                  var23.close();
               }

            }
         }

         Object[] var41 = new Object[]{var16, null};
         Long var42 = Long.valueOf(var1.id);
         var41[1] = var42;
         String var43 = String.format("_id in (%s) AND _mailboxId = \'%d\'", var41);
         ContentResolver var44 = this.mResolver;
         Uri var45 = MailProvider.sSummariesDeleteMailURI;
         var44.delete(var45, var43, (String[])null);
         if(var12.size() > 0) {
            ContentResolver var47 = this.mResolver;
            Uri var48 = MailProvider.sPendingRequestURI;
            ContentValues[] var49 = new ContentValues[0];
            ContentValues[] var52 = (ContentValues[])var12.toArray(var49);
            int var56 = var47.bulkInsert(var48, var52);
         }
      }
   }

   public void moveMailLocal(Mailbox var1, ArrayList<MailMessage> var2, Mailbox var3) {
      String var5 = ",";
      byte var6 = 1;
      String var7 = MailCommon.combineMessageId(var2, var5, (boolean)var6);
      StringBuilder var8 = new StringBuilder();
      ArrayList var9 = new ArrayList();
      ArrayList var10 = new ArrayList();
      Object[] var11 = new Object[]{var7, null};
      Long var12 = Long.valueOf(var1.id);
      var11[1] = var12;
      String var13 = String.format("_id in (%s) AND _mailboxId = \'%d\' AND +_local = 0", var11);
      ContentResolver var14 = this.mResolver;
      Uri var15 = MailProvider.sMessagesURI;
      String[] var16 = new String[]{"_id", "_uid"};
      Cursor var17 = var14.query(var15, var16, var13, (String[])null, (String)null);
      if(var17 != null) {
         try {
            while(var17.moveToNext()) {
               int var18 = var17.getColumnIndexOrThrow("_uid");
               String var19 = var17.getString(var18);
               int var20 = var17.getColumnIndexOrThrow("_id");
               long var21 = var17.getLong(var20);
               if(var19 != null) {
                  ContentValues var23 = new ContentValues();
                  Long var24 = Long.valueOf(var1.id);
                  String var26 = "_mailboxId";
                  var23.put(var26, var24);
                  String var29 = "_uid";
                  var23.put(var29, var19);
                  Integer var31 = Integer.valueOf(1);
                  String var33 = "_del";
                  var23.put(var33, var31);
                  Long var35 = Long.valueOf(this.mAccount.id);
                  String var37 = "_account";
                  var23.put(var37, var35);
                  boolean var41 = var9.add(var23);
                  if(var8.length() != 0) {
                     String var43 = ",";
                     StringBuilder var44 = var8.append(var43);
                     String var45 = DatabaseUtils.sqlEscapeString(var19);
                     var44.append(var45);
                  } else {
                     String var60 = DatabaseUtils.sqlEscapeString(var19);
                     StringBuilder var63 = var8.append(var60);
                  }
               }

               if(this.mAccount.deleteFromServer == 1) {
                  ContentValues var47 = new ContentValues();
                  Long var48 = Long.valueOf(this.mAccount.id);
                  var47.put("_accountId", var48);
                  Long var49 = Long.valueOf(var21);
                  var47.put("_messageId", var49);
                  Integer var50 = Integer.valueOf(1);
                  var47.put("_request", var50);
                  Long var51 = Long.valueOf(var1.id);
                  var47.put("_fromMailboxId", var51);
                  String var53 = "_uid";
                  var47.put(var53, var19);
                  Long var55 = Long.valueOf(var3.id);
                  var47.put("_toMailboxId", var55);
                  boolean var58 = var10.add(var47);
               }
            }
         } finally {
            if(!var17.isClosed()) {
               var17.close();
            }

         }
      }

      if(var1.isServerFolder) {
         if(var9.size() > 0) {
            ContentResolver var64 = this.mResolver;
            Uri var65 = MailProvider.sNoNotifyMessagesURI;
            ContentValues[] var66 = new ContentValues[0];
            ContentValues[] var69 = (ContentValues[])var9.toArray(var66);
            int var73 = var64.bulkInsert(var65, var69);
         }

         if(this.mAccount.deleteFromServer == 1 && var10.size() > 0) {
            ContentResolver var74 = this.mResolver;
            Uri var75 = MailProvider.sPendingRequestURI;
            ContentValues[] var76 = new ContentValues[0];
            ContentValues[] var79 = (ContentValues[])var10.toArray(var76);
            int var83 = var74.bulkInsert(var75, var79);
         }
      }

      if(var3.isServerFolder && var8.length() > 0) {
         Object[] var84 = new Object[2];
         Long var85 = Long.valueOf(this.mAccount.id);
         var84[0] = var85;
         String var86 = var8.toString();
         var84[1] = var86;
         String var87 = String.format("_account = \'%d\' AND _uid in (%s) AND +_del <> -1", var84);
         ContentResolver var88 = this.mResolver;
         Uri var89 = MailProvider.sNoNotifyMessagesURI;
         var88.delete(var89, var87, (String[])null);
         Object[] var91 = new Object[2];
         Long var92 = Long.valueOf(this.mAccount.id);
         var91[0] = var92;
         String var93 = var8.toString();
         var91[1] = var93;
         String var94 = String.format("_accountId = \'%d\' AND _uid in (%s)", var91);
         ContentResolver var95 = this.mResolver;
         Uri var96 = MailProvider.sPendingRequestURI;
         var95.delete(var96, var94, (String[])null);
      }

      Object[] var98 = new Object[]{var7, null};
      Long var99 = Long.valueOf(var1.id);
      var98[1] = var99;
      String var100 = String.format("_id in (%s) AND _mailboxId = \'%d\' AND +_del = -1", var98);
      ContentValues var101 = new ContentValues();
      Long var102 = Long.valueOf(var3.id);
      String var104 = "_mailboxId";
      var101.put(var104, var102);
      ContentResolver var106 = this.mResolver;
      Uri var107 = MailProvider.sSummariesMoveMailURI;
      Object var112 = null;
      var106.update(var107, var101, var100, (String[])var112);
   }
}
