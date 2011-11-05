package com.htc.android.mail.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.htc.android.mail.Account;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailMessage;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.database.LocalStore;
import java.util.ArrayList;

public class ImapLocalStore extends LocalStore {

   private Account mAccount;
   private Context mContext;
   private ContentResolver mResolver;


   public ImapLocalStore(Context var1, Account var2) {
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
         String[] var22 = new String[]{"_id", "_uid", "_messageid"};
         Cursor var23 = var20.query(var21, var22, var19, (String[])null, (String)null);
         if(var23 != null) {
            try {
               while(var23.moveToNext()) {
                  int var24 = var23.getColumnIndexOrThrow("_uid");
                  String var25 = var23.getString(var24);
                  int var26 = var23.getColumnIndexOrThrow("_id");
                  long var27 = var23.getLong(var26);
                  int var29 = var23.getColumnIndexOrThrow("_messageid");
                  String var30 = var23.getString(var29);
                  ContentValues var31 = new ContentValues();
                  Long var32 = Long.valueOf(this.mAccount.id);
                  String var34 = "_accountId";
                  var31.put(var34, var32);
                  Long var36 = Long.valueOf(var27);
                  String var38 = "_messageId";
                  var31.put(var38, var36);
                  String var41 = "_msgId";
                  var31.put(var41, var30);
                  Integer var43 = Integer.valueOf(1);
                  String var45 = "_request";
                  var31.put(var45, var43);
                  Long var47 = Long.valueOf(var1.id);
                  String var49 = "_fromMailboxId";
                  var31.put(var49, var47);
                  String var52 = "_uid";
                  var31.put(var52, var25);
                  boolean var56 = var12.add(var31);
               }
            } finally {
               if(!var23.isClosed()) {
                  var23.close();
               }

            }
         }

         if(var12.size() > 0) {
            ContentResolver var58 = this.mResolver;
            Uri var59 = MailProvider.sPendingRequestURI;
            ContentValues[] var60 = new ContentValues[0];
            ContentValues[] var63 = (ContentValues[])var12.toArray(var60);
            int var67 = var58.bulkInsert(var59, var63);
         }

         Object[] var68 = new Object[]{var16, null};
         Long var69 = Long.valueOf(var1.id);
         var68[1] = var69;
         String var70 = String.format("_id in (%s) AND _mailboxId = \'%d\'", var68);
         ContentValues var71 = new ContentValues();
         Integer var72 = Integer.valueOf(1);
         String var74 = "_del";
         var71.put(var74, var72);
         ContentResolver var76 = this.mResolver;
         Uri var77 = MailProvider.sSummariesDeleteMailURI;
         Object var82 = null;
         var76.update(var77, var71, var70, (String[])var82);
      }
   }

   public void emptyMailbox(Context var1, long var2, long var4) {
      super.emptyMailbox(var1, var2, var4);
      ContentValues var6 = new ContentValues();
      Long var7 = Long.valueOf(var2);
      var6.put("_accountId", var7);
      Integer var8 = Integer.valueOf(8);
      var6.put("_request", var8);
      Long var9 = Long.valueOf(var4);
      var6.put("_fromMailboxId", var9);
      ContentResolver var10 = this.mResolver;
      Uri var11 = MailProvider.sPendingRequestURI;
      var10.insert(var11, var6);
   }

   public void moveMailLocal(Mailbox var1, ArrayList<MailMessage> var2, Mailbox var3) {
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      String var7 = ",";
      byte var8 = 1;
      String var9 = MailCommon.combineMessageId(var2, var7, (boolean)var8);
      Object[] var10 = new Object[]{var9, null};
      Long var11 = Long.valueOf(var1.id);
      var10[1] = var11;
      String var12 = String.format("_id in (%s) AND _mailboxId = \'%d\' AND +_local = 0", var10);
      ContentResolver var13 = this.mResolver;
      Uri var14 = MailProvider.sMessagesURI;
      String[] var15 = new String[]{"_id", "_uid", "_messageid"};
      Cursor var16 = var13.query(var14, var15, var12, (String[])null, (String)null);
      if(var16 != null) {
         try {
            while(var16.moveToNext()) {
               int var17 = var16.getColumnIndexOrThrow("_uid");
               String var18 = var16.getString(var17);
               int var19 = var16.getColumnIndexOrThrow("_id");
               long var20 = var16.getLong(var19);
               int var22 = var16.getColumnIndexOrThrow("_messageid");
               String var23 = var16.getString(var22);
               if(var18 != null) {
                  ContentValues var24 = new ContentValues();
                  Long var25 = Long.valueOf(var1.id);
                  String var27 = "_mailboxId";
                  var24.put(var27, var25);
                  String var30 = "_uid";
                  var24.put(var30, var18);
                  Integer var32 = Integer.valueOf(1);
                  String var34 = "_del";
                  var24.put(var34, var32);
                  Long var36 = Long.valueOf(this.mAccount.id);
                  String var38 = "_account";
                  var24.put(var38, var36);
                  boolean var42 = var4.add(var24);
               }

               ContentValues var43 = new ContentValues();
               Long var44 = Long.valueOf(this.mAccount.id);
               String var46 = "_accountId";
               var43.put(var46, var44);
               Long var48 = Long.valueOf(var20);
               String var50 = "_messageId";
               var43.put(var50, var48);
               String var53 = "_msgId";
               var43.put(var53, var23);
               Integer var55 = Integer.valueOf(2);
               String var57 = "_request";
               var43.put(var57, var55);
               Long var59 = Long.valueOf(var1.id);
               String var61 = "_fromMailboxId";
               var43.put(var61, var59);
               String var64 = "_uid";
               var43.put(var64, var18);
               Long var66 = Long.valueOf(var3.id);
               String var68 = "_toMailboxId";
               var43.put(var68, var66);
               boolean var72 = var5.add(var43);
            }
         } finally {
            if(!var16.isClosed()) {
               var16.close();
            }

         }
      }

      if(var4.size() > 0) {
         ContentResolver var74 = this.mResolver;
         Uri var75 = MailProvider.sNoNotifyMessagesURI;
         ContentValues[] var76 = new ContentValues[0];
         ContentValues[] var79 = (ContentValues[])var4.toArray(var76);
         int var83 = var74.bulkInsert(var75, var79);
      }

      Object[] var84 = new Object[]{var9, null};
      Long var85 = Long.valueOf(var1.id);
      var84[1] = var85;
      String var86 = String.format("_id in (%s) AND _mailboxId = \'%d\'", var84);
      ContentValues var87 = new ContentValues();
      String var88 = (String)false;
      String var90 = "_uid";
      var87.put(var90, var88);
      Long var92 = Long.valueOf(var3.id);
      String var94 = "_mailboxId";
      var87.put(var94, var92);
      Integer var96 = Integer.valueOf(0);
      String var98 = "_sync";
      var87.put(var98, var96);
      if(!var3.isServerFolder) {
         Integer var100 = Integer.valueOf(1);
         String var102 = "_local";
         var87.put(var102, var100);
      }

      Mailbox var104 = this.mAccount.getMailboxs().getTrashMailbox();
      long var105 = var3.id;
      long var107 = var104.id;
      if(var105 != var107) {
         if(var5.size() > 0) {
            ContentResolver var109 = this.mResolver;
            Uri var110 = MailProvider.sPendingRequestURI;
            ContentValues[] var111 = new ContentValues[0];
            ContentValues[] var114 = (ContentValues[])var5.toArray(var111);
            int var118 = var109.bulkInsert(var110, var114);
         }

         ContentResolver var119 = this.mResolver;
         Uri var120 = MailProvider.sSummariesMoveMailURI;
         Object var125 = null;
         var119.update(var120, var87, var86, (String[])var125);
      } else {
         ContentResolver var127 = this.mResolver;
         Uri var128 = MailProvider.sSummariesDeleteMailURI;
         var127.delete(var128, var86, (String[])null);
         if(var5.size() > 0) {
            ContentResolver var130 = this.mResolver;
            Uri var131 = MailProvider.sPendingRequestURI;
            ContentValues[] var132 = new ContentValues[0];
            ContentValues[] var135 = (ContentValues[])var5.toArray(var132);
            int var139 = var130.bulkInsert(var131, var135);
         }
      }
   }

   protected void setMailLocalReadStatus(Context var1, Mailbox var2, ArrayList<MailMessage> var3, boolean var4) {
      byte var5;
      if(var4) {
         var5 = 4;
      } else {
         var5 = 3;
      }

      super.setMailLocalReadStatus(var1, var2, var3, var4);
      ArrayList var6 = new ArrayList();
      String var8 = ",";
      byte var9 = 1;
      String var10 = MailCommon.combineMessageId(var3, var8, (boolean)var9);
      Object[] var11 = new Object[]{var10, null};
      Long var12 = Long.valueOf(var2.id);
      var11[1] = var12;
      String var13 = String.format("_id IN (%s) AND _mailboxId = \'%d\'  AND +_local = 0", var11);
      ContentResolver var14 = this.mResolver;
      Uri var15 = MailProvider.sMessagesURI;
      String[] var16 = new String[]{"_id", "_uid", "_messageid"};
      Cursor var17 = var14.query(var15, var16, var13, (String[])null, (String)null);
      if(var17 != null) {
         try {
            while(var17.moveToNext()) {
               int var18 = var17.getColumnIndexOrThrow("_id");
               long var19 = var17.getLong(var18);
               int var21 = var17.getColumnIndexOrThrow("_uid");
               String var22 = var17.getString(var21);
               int var23 = var17.getColumnIndexOrThrow("_messageid");
               String var24 = var17.getString(var23);
               ContentValues var25 = new ContentValues();
               Long var26 = Long.valueOf(this.mAccount.id);
               String var28 = "_accountId";
               var25.put(var28, var26);
               Long var30 = Long.valueOf(var19);
               String var32 = "_messageId";
               var25.put(var32, var30);
               String var35 = "_msgId";
               var25.put(var35, var24);
               Integer var37 = Integer.valueOf(var5);
               String var39 = "_request";
               var25.put(var39, var37);
               Long var41 = Long.valueOf(var2.id);
               String var43 = "_fromMailboxId";
               var25.put(var43, var41);
               String var46 = "_uid";
               var25.put(var46, var22);
               boolean var50 = var6.add(var25);
            }
         } finally {
            if(!var17.isClosed()) {
               var17.close();
            }

         }
      }

      if(var6.size() > 0) {
         ContentResolver var52 = this.mResolver;
         Uri var53 = MailProvider.sPendingRequestURI;
         ContentValues[] var54 = new ContentValues[0];
         ContentValues[] var57 = (ContentValues[])var6.toArray(var54);
         int var61 = var52.bulkInsert(var53, var57);
      }
   }
}
