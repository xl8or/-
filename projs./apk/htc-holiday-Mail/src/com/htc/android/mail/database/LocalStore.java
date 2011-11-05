package com.htc.android.mail.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.htc.android.mail.Account;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailMessage;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.eassvc.pim.EASMoveItems;
import java.util.ArrayList;
import java.util.Map;

public class LocalStore {

   private static Map<Long, LocalStore> mLocalStoreMap;


   public LocalStore() {}

   public static LocalStore getInstance(Context param0, Account param1) {
      // $FF: Couldn't be decompiled
   }

   public void deleteMailLocal(Mailbox var1, ArrayList<MailMessage> var2) {}

   public void deleteMailLocal(Mailbox var1, ArrayList<MailMessage> var2, long var3, Boolean var5) {}

   public void emptyMailbox(Context var1, long var2, long var4) {
      ContentResolver var6 = var1.getApplicationContext().getContentResolver();
      String var7 = "_mailboxId = " + var4;
      Uri var8 = MailProvider.sMessagesURI;
      var6.delete(var8, var7, (String[])null);
   }

   public void markStarLocal(Context var1, MailMessage var2) {
      long var3 = var2.id;
      ContentValues var5 = new ContentValues();
      Integer var6 = Integer.valueOf(var2.flags);
      var5.put("_flags", var6);
      ContentResolver var7 = var1.getContentResolver();
      Uri var8 = MailProvider.sSummariesMarkStarURI;
      String var9 = String.valueOf(var3);
      Uri var10 = Uri.withAppendedPath(var8, var9);
      var7.update(var10, var5, (String)null, (String[])null);
   }

   public void moveMailLocal(Mailbox var1, ArrayList<MailMessage> var2, Mailbox var3) {}

   public void moveMailLocal(Mailbox var1, ArrayList<MailMessage> var2, Mailbox var3, EASMoveItems var4, boolean var5) {}

   protected void setMailLocalReadStatus(Context var1, Mailbox var2, ArrayList<MailMessage> var3, boolean var4) {
      byte var5;
      if(var4) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      ContentValues var6 = new ContentValues();
      Integer var7 = Integer.valueOf(var5);
      var6.put("_read", var7);
      String var8 = MailCommon.combineMessageId(var3, ",", (boolean)1);
      Object[] var9 = new Object[]{var8, null};
      Long var10 = Long.valueOf(var2.id);
      var9[1] = var10;
      String var11 = String.format("_Id IN (%s) AND _mailboxId = %d", var9);
      ContentResolver var12 = var1.getApplicationContext().getContentResolver();
      Uri var13 = MailProvider.sSummariesReadURI;
      var12.update(var13, var6, var11, (String[])null);
   }

   public void setReadMailLocal(Context var1, Mailbox var2, ArrayList<MailMessage> var3, boolean var4) {
      this.setMailLocalReadStatus(var1, var2, var3, (boolean)1);
   }

   public void setUnreadMailLocal(Context var1, Mailbox var2, ArrayList<MailMessage> var3, boolean var4) {
      this.setMailLocalReadStatus(var1, var2, var3, (boolean)0);
   }
}
