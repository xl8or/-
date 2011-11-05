package com.htc.android.mail.database;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContentProviderOperation.Builder;
import android.net.Uri;
import android.text.TextUtils;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailMessage;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.ll;
import com.htc.android.mail.database.ExchangeUtil;
import com.htc.android.mail.database.LocalStore;
import com.htc.android.mail.eassvc.pim.EASDeleteItems;
import com.htc.android.mail.eassvc.pim.EASMoveItems;
import java.util.ArrayList;
import java.util.Iterator;

public class ExchangeLocalStore extends LocalStore {

   private static boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "ExchangeLocalStore";
   private Account mAccount;
   private Context mContext;
   private ContentResolver mResolver;


   public ExchangeLocalStore(Context var1, Account var2) {
      this.mContext = var1;
      this.mAccount = var2;
      ContentResolver var3 = this.mContext.getContentResolver();
      this.mResolver = var3;
   }

   public void deleteMailLocal(Mailbox var1, ArrayList<MailMessage> var2, long var3, Boolean var5) {
      if(DEBUG) {
         ll.d("ExchangeLocalStore", "> deleteMailLocal");
      }

      long var6 = var1.id;
      ContentResolver var8 = this.mResolver;
      ArrayList var13 = ExchangeUtil.getMsgServerId(var2, var6, var8);
      if(var13 == null) {
         ll.e("ExchangeLocalStore", "deleteMailLocal error: mail message list empty");
      } else {
         EASDeleteItems var14 = new EASDeleteItems();
         long var15 = var1.id;
         var14.mailboxId = var15;
         String var17 = var1.serverId;
         var14.mailboxSvrId = var17;
         boolean var18 = var5.booleanValue();
         var14.addToTrack = var18;

         for(int var19 = var13.size() - 1; var19 >= 0; var19 += -1) {
            MailMessage var22 = (MailMessage)var13.get(var19);
            if(var22.uid != null && var22.uid.length() > 0) {
               ArrayList var23 = var14.mailSvrId;
               String var24 = var22.uid;
               var23.add(var24);
               ArrayList var26 = var14.mailSvrId_messageId;
               String var27 = String.valueOf(var22.id);
               var26.add(var27);
            } else {
               ArrayList var29 = var14.mailId;
               String var30 = Long.toString(var22.id);
               var29.add(var30);
            }
         }

         int var39;
         String var36;
         if(var14.mailSvrId != null && var14.mailSvrId.size() > 0) {
            String var32 = ExchangeUtil.combine(var14.mailSvrId, ",", (boolean)1);
            Object[] var33 = new Object[]{var32, null, null};
            Long var34 = Long.valueOf(var14.mailboxId);
            var33[1] = var34;
            Long var35 = Long.valueOf(var3);
            var33[2] = var35;
            var36 = String.format("_uid in (%s) AND _mailboxId=%d AND _account=%d", var33);
            ContentResolver var37 = this.mResolver;
            Uri var38 = MailProvider.sSummariesDeleteMailURI;
            var39 = var37.delete(var38, var36, (String[])null);
            if(DEBUG && var39 <= 0) {
               String var40 = "deleteMailLocal error. where: " + var36;
               ll.e("ExchangeLocalStore", var40);
            }
         }

         if(var14.mailId != null && var14.mailId.size() > 0) {
            String var41 = ExchangeUtil.combine(var14.mailId, ",", (boolean)1);
            Object[] var42 = new Object[]{var41, null};
            Long var43 = Long.valueOf(var14.mailboxId);
            var42[1] = var43;
            var36 = String.format("_id in (%s) AND _mailboxId=%d", var42);
            ContentResolver var44 = this.mResolver;
            Uri var45 = MailProvider.sSummariesDeleteMailURI;
            var39 = var44.delete(var45, var36, (String[])null);
            if(DEBUG && var39 <= 0) {
               String var46 = "deleteMailLocal error. where: " + var36;
               ll.e("ExchangeLocalStore", var46);
            }
         }

         if(var14.addToTrack) {
            int var47 = var14.mailSvrId.size();
            ContentValues[] var48 = new ContentValues[var47];

            int var64;
            for(byte var68 = 0; var68 < var47; var64 = var68 + 1) {
               ContentValues var49 = new ContentValues();
               var48[var68] = var49;
               ContentValues var50 = var48[var68];
               String var51 = (String)var14.mailSvrId_messageId.get(var68);
               String var53 = "_message";
               var50.put(var53, var51);
               ContentValues var55 = var48[var68];
               String var56 = (String)var14.mailSvrId.get(var68);
               String var58 = "_uid";
               var55.put(var58, var56);
               ContentValues var60 = var48[var68];
               String var61 = var14.mailboxSvrId;
               var60.put("_collectionId", var61);
               var48[var68].put("_delete", "1");
               ContentValues var62 = var48[var68];
               Long var63 = Long.valueOf(var3);
               var62.put("_accountId", var63);
            }

            if(var48.length > 0) {
               ContentResolver var65 = this.mResolver;
               Uri var66 = MailProvider.sEASTracking;
               var65.bulkInsert(var66, var48);
            }

            if(DEBUG) {
               ll.d("ExchangeLocalStore", "< deleteMailLocal");
            }
         }
      }
   }

   public void emptyMailbox(Context var1, long var2, long var4) {
      super.emptyMailbox(var1, var2, var4);
      String var6 = "empty mailbox: " + var2 + ", " + var4;
      ll.d("ExchangeLocalStore", var6);
      ContentResolver var7 = this.mResolver;
      String var8 = ExchangeUtil.getMailboxSvrIdbyMailboxId(var4, var7);
      if(TextUtils.isEmpty(var8)) {
         ll.e("ExchangeLocalStore", "emptyMailbox failed: Can not get mailbox server id");
      } else {
         Object[] var9 = new Object[2];
         Long var10 = Long.valueOf(var2);
         var9[0] = var10;
         var9[1] = var8;
         String var11 = String.format("_accountId=%d AND _collectionId=\'%s\' AND _delete=2", var9);
         ContentResolver var12 = this.mResolver;
         Uri var13 = MailProvider.sEASTracking;
         var12.delete(var13, var11, (String[])null);
         ContentValues var15 = new ContentValues();
         Long var16 = Long.valueOf(var2);
         var15.put("_accountId", var16);
         Integer var17 = Integer.valueOf(0);
         var15.put("_message", var17);
         Integer var18 = Integer.valueOf(2);
         var15.put("_delete", var18);
         var15.put("_collectionId", var8);
         ContentResolver var19 = this.mResolver;
         Uri var20 = MailProvider.sEASTracking;
         var19.insert(var20, var15);
      }
   }

   public void moveMailLocal(Mailbox param1, ArrayList<MailMessage> param2, Mailbox param3, EASMoveItems param4, boolean param5) {
      // $FF: Couldn't be decompiled
   }

   public void updFlagMailToTracking(long var1, MailMessage var3, Mailbox var4) {
      if(DEBUG) {
         StringBuilder var5 = (new StringBuilder()).append("updFlagMailToTracking: ");
         String var6 = var3.uid;
         String var7 = var5.append(var6).toString();
         ll.d("ExchangeLocalStore", var7);
      }

      try {
         String var8 = this.mAccount.easSvrProtocol;
         if("Unknown".equals(var8)) {
            ll.e("ExchangeLocalStore", "update flag to tracking fail: Exchange protocol unknown");
            return;
         }

         if(Double.valueOf(this.mAccount.easSvrProtocol).doubleValue() < 12.0D) {
            ll.e("ExchangeLocalStore", "update flag to tracking fail: Exchange protocol does not support flag");
            return;
         }
      } catch (Exception var36) {
         var36.printStackTrace();
         return;
      }

      long var9 = var3.id;
      ContentResolver var11 = this.mResolver;
      String var12 = ExchangeUtil.getUidByMessageId(var9, (boolean)0, var11);
      var3.uid = var12;
      if(TextUtils.isEmpty(var3.uid)) {
         StringBuilder var13 = (new StringBuilder()).append("update flag to tracking failed: message server null ");
         long var14 = var3.id;
         String var16 = var13.append(var14).toString();
         ll.e("ExchangeLocalStore", var16);
      } else {
         Object[] var17 = new Object[3];
         Long var18 = Long.valueOf(var1);
         var17[0] = var18;
         String var19 = var4.serverId;
         var17[1] = var19;
         String var20 = var3.uid;
         var17[2] = var20;
         String var21 = String.format("_accountId=%d AND _collectionId=\'%s\' AND _uid=\'%s\' AND _modify=2", var17);
         ContentResolver var22 = this.mResolver;
         Uri var23 = MailProvider.sEASTracking;
         int var24 = var22.delete(var23, var21, (String[])null);
         if(DEBUG) {
            String var25 = "updFlagMailToTracking: delete exist count: " + var24;
            ll.d("ExchangeLocalStore", var25);
         }

         ContentValues var26 = new ContentValues();
         Long var27 = Long.valueOf(var3.id);
         var26.put("_message", var27);
         String var28 = var3.uid;
         var26.put("_uid", var28);
         String var29 = var4.serverId;
         var26.put("_collectionId", var29);
         Integer var30 = Integer.valueOf(2);
         var26.put("_modify", var30);
         Integer var31 = Integer.valueOf(var3.flags);
         var26.put("_param", var31);
         Long var32 = Long.valueOf(var1);
         var26.put("_accountId", var32);
         ContentResolver var33 = this.mResolver;
         Uri var34 = MailProvider.sEASTracking;
         var33.insert(var34, var26);
      }
   }

   public void updReadMailToTracking(long var1, ArrayList<MailMessage> var3, Mailbox var4, int var5) {
      if(DEBUG) {
         StringBuilder var6 = (new StringBuilder()).append("updateReadMailToTracking: ");
         int var7 = var3.size();
         String var8 = var6.append(var7).toString();
         ll.d("ExchangeLocalStore", var8);
      }

      long var9 = var4.id;
      ContentResolver var11 = this.mResolver;
      ArrayList var16 = ExchangeUtil.getMsgServerId(var3, var9, var11);
      if(var16 == null) {
         ll.e("ExchangeLocalStore", "update read to tracking failed: message list null");
      } else {
         ArrayList var17 = new ArrayList();
         Iterator var18 = var16.iterator();

         while(var18.hasNext()) {
            MailMessage var19 = (MailMessage)var18.next();
            if(!TextUtils.isEmpty(var19.uid)) {
               String var20 = var19.uid;
               var17.add(var20);
            }
         }

         String var22 = ExchangeUtil.combine(var17, ",", (boolean)1);
         Object[] var23 = new Object[3];
         Long var24 = Long.valueOf(var1);
         var23[0] = var24;
         String var25 = var4.serverId;
         var23[1] = var25;
         var23[2] = var22;
         String var26 = String.format("_accountId=%d AND _collectionId=\'%s\' AND _modify=1 AND _uid in (%s)", var23);
         ContentResolver var27 = this.mResolver;
         Uri var28 = MailProvider.sEASTracking;
         Object var32 = null;
         int var33 = var27.delete(var28, var26, (String[])var32);
         if(DEBUG) {
            String var34 = "updateReadMailToTracking: delete exist count: " + var33;
            ll.d("ExchangeLocalStore", var34);
         }

         ArrayList var35 = new ArrayList();
         Iterator var36 = var16.iterator();

         while(var36.hasNext()) {
            MailMessage var37 = (MailMessage)var36.next();
            if(!TextUtils.isEmpty(var37.uid)) {
               ContentValues var38 = new ContentValues();
               Long var39 = Long.valueOf(var37.id);
               var38.put("_message", var39);
               String var40 = var37.uid;
               var38.put("_uid", var40);
               String var41 = var4.serverId;
               var38.put("_collectionId", var41);
               Integer var42 = Integer.valueOf(1);
               var38.put("_modify", var42);
               Integer var43 = Integer.valueOf(var5);
               var38.put("_param", var43);
               Long var44 = Long.valueOf(var1);
               var38.put("_accountId", var44);
               Builder var45 = ContentProviderOperation.newInsert(MailProvider.sEASTracking);
               var45.withValues(var38);
               ContentProviderOperation var47 = var45.build();
               var35.add(var47);
            }
         }

         if(var35 != null) {
            try {
               if(var35.size() > 0) {
                  this.mResolver.applyBatch("mail", var35);
               }
            } catch (Exception var50) {
               var50.printStackTrace();
            }
         }
      }
   }
}
