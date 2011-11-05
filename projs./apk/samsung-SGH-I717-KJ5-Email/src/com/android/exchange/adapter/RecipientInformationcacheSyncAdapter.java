package com.android.exchange.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;
import com.android.email.mail.DeviceAccessException;
import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.AbstractSyncParser;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class RecipientInformationcacheSyncAdapter extends AbstractSyncAdapter {

   private static final String RIC_WINDOW_SIZE = "10";
   public static final String TAG = "RecipientInformation Cache";
   boolean mIsLooping;


   public RecipientInformationcacheSyncAdapter(EmailContent.Mailbox var1, EasSyncService var2) {
      EmailContent.Mailbox var3 = var2.mMailbox;
      super(var3, var2);
      this.mIsLooping = (boolean)0;
   }

   public static void main(String[] var0) {}

   public void cleanup() {}

   public String getCollectionName() {
      return null;
   }

   public boolean isSyncable() {
      return true;
   }

   public boolean parse(InputStream var1) throws IOException {
      RecipientInformationcacheSyncAdapter.RecipientInformationcacheSyncParser var2 = new RecipientInformationcacheSyncAdapter.RecipientInformationcacheSyncParser(var1, this);
      boolean var3 = false;

      label13: {
         boolean var4;
         try {
            var4 = var2.parse();
         } catch (DeviceAccessException var6) {
            var6.printStackTrace();
            break label13;
         }

         var3 = var4;
      }

      boolean var5 = var2.isLooping();
      this.mIsLooping = var5;
      return var3;
   }

   public boolean sendLocalChanges(Serializer var1) throws IOException {
      return false;
   }

   public void sendSyncOptions(Double var1, Serializer var2) throws IOException {
      if(var1.doubleValue() >= 14.0D) {
         Serializer var3 = var2.data(21, "10");
         Serializer var4 = var2.start(23);
         Serializer var5 = var2.end();
      }
   }

   public void wipe() {
      ContentValues var1 = new ContentValues();
      var1.put("syncKey", "0");
      ContentResolver var2 = this.mContext.getContentResolver();
      Uri var3 = EmailContent.Mailbox.CONTENT_URI;
      String[] var4 = new String[1];
      String var5 = Long.toString(this.mMailbox.mId);
      var4[0] = var5;
      var2.update(var3, var1, "_id=?", var4);
      ContentResolver var7 = this.mContext.getContentResolver();
      Uri var8 = EmailContent.RecipientInformationCache.CONTENT_URI;
      String[] var9 = new String[1];
      String var10 = Long.toString(this.mAccount.mId);
      var9[0] = var10;
      int var11 = var7.delete(var8, "accountkey=?", var9);
      String var12 = "RIC adapter-afterdel wipe" + var11;
      int var13 = Log.i("RecipientInformation Cache", var12);
   }

   private class RecipientInformationcacheSyncParser extends AbstractSyncParser {

      ArrayList<EmailContent.RecipientInformationCache> RIcacheids;
      ArrayList<EmailContent.RecipientInformationCache> RIcacheids_Del;
      ArrayList<EmailContent.RecipientInformationCache> RIcacheids_Modi;


      public RecipientInformationcacheSyncParser(InputStream var2, AbstractSyncAdapter var3) throws IOException {
         super(var2, var3);
         ArrayList var4 = new ArrayList();
         this.RIcacheids = var4;
         ArrayList var5 = new ArrayList();
         this.RIcacheids_Modi = var5;
         ArrayList var6 = new ArrayList();
         this.RIcacheids_Del = var6;
      }

      private void addData(EmailContent.RecipientInformationCache var1) throws IOException {
         while(this.nextTag(29) != 3) {
            switch(this.tag) {
            case 91:
               String var2 = this.getValue();
               var1.riEmailAddress = var2;
               break;
            case 94:
               String var3 = this.getValue();
               var1.riFileAs = var3;
               break;
            case 125:
               String var4 = this.getValue();
               var1.riAlias = var4;
               break;
            case 126:
               String var5 = this.getValue();
               var1.riWeightedRank = var5;
            }
         }

      }

      private void addParser(ArrayList<EmailContent.RecipientInformationCache> var1) throws IOException {
         EmailContent.RecipientInformationCache var2 = new EmailContent.RecipientInformationCache();
         long var3 = this.mAccount.mId;
         var2.riAccountKey = var3;
         long var5 = this.mMailbox.mId;
         var2.riMailboxKey = var5;

         while(this.nextTag(7) != 3) {
            switch(this.tag) {
            case 13:
               String var7 = this.getValue();
               var2.riServerId = var7;
               break;
            case 29:
               this.addData(var2);
            }
         }

         var1.add(var2);
      }

      private void changeParser(ArrayList<EmailContent.RecipientInformationCache> var1) throws IOException {
         EmailContent.RecipientInformationCache var2 = new EmailContent.RecipientInformationCache();
         long var3 = this.mAccount.mId;
         var2.riAccountKey = var3;
         long var5 = this.mMailbox.mId;
         var2.riMailboxKey = var5;

         while(this.nextTag(7) != 3) {
            switch(this.tag) {
            case 13:
               String var7 = this.getValue();
               var2.riServerId = var7;
               break;
            case 29:
               this.addData(var2);
            }
         }

         var1.add(var2);
      }

      private void deleteParser(ArrayList<EmailContent.RecipientInformationCache> var1) throws IOException {
         EmailContent.RecipientInformationCache var2 = new EmailContent.RecipientInformationCache();
         long var3 = this.mAccount.mId;
         var2.riAccountKey = var3;
         long var5 = this.mMailbox.mId;
         var2.riMailboxKey = var5;

         while(this.nextTag(9) != 3) {
            switch(this.tag) {
            case 13:
               String var7 = this.getValue();
               var2.riServerId = var7;
            }
         }

         var1.add(var2);
      }

      public void commandsParser() throws IOException {
         while(this.nextTag(22) != 3) {
            if(this.tag == 7) {
               ArrayList var1 = this.RIcacheids;
               this.addParser(var1);
               RecipientInformationcacheSyncAdapter.this.incrementChangeCount();
            } else if(this.tag != 9 && this.tag != 33) {
               if(this.tag == 8) {
                  ArrayList var3 = this.RIcacheids_Modi;
                  this.changeParser(var3);
                  RecipientInformationcacheSyncAdapter.this.incrementChangeCount();
               } else {
                  this.skipTag();
               }
            } else {
               ArrayList var2 = this.RIcacheids_Del;
               this.deleteParser(var2);
               RecipientInformationcacheSyncAdapter.this.incrementChangeCount();
            }
         }

      }

      public void commit() throws IOException {
         new ArrayList();
         Iterator var2 = this.RIcacheids.iterator();

         while(var2.hasNext()) {
            EmailContent.RecipientInformationCache var3 = (EmailContent.RecipientInformationCache)var2.next();
            ContentValues var4 = new ContentValues();
            String var5 = var3.riServerId;
            var4.put("server_id", var5);
            Long var6 = Long.valueOf(var3.riAccountKey);
            var4.put("accountkey", var6);
            String var7 = var3.riEmailAddress;
            var4.put("email_address", var7);
            String var8 = var3.riFileAs;
            var4.put("fileas", var8);
            String var9 = var3.riAlias;
            var4.put("alias", var9);
            String var10 = var3.riWeightedRank;
            var4.put("weightedrank", var10);
            ContentResolver var11 = this.mContext.getContentResolver();
            Uri var12 = EmailContent.RecipientInformationCache.CONTENT_URI;
            var11.insert(var12, var4);
         }

         int var29;
         String var28;
         if(!this.RIcacheids_Modi.isEmpty()) {
            for(var2 = this.RIcacheids_Modi.iterator(); var2.hasNext(); var29 = Log.i("RecipientInformation Cache", var28)) {
               EmailContent.RecipientInformationCache var14 = (EmailContent.RecipientInformationCache)var2.next();
               String[] var15 = new String[2];
               String var16 = var14.riServerId;
               var15[0] = var16;
               String var17 = Long.toString(var14.riAccountKey);
               var15[1] = var17;
               ContentValues var18 = new ContentValues();
               String var19 = var14.riWeightedRank;
               var18.put("weightedrank", var19);
               StringBuilder var20 = (new StringBuilder()).append("RIC adapter-commit");
               String var21 = var14.riServerId;
               String var22 = var20.append(var21).toString();
               int var23 = Log.i("RecipientInformation Cache", var22);
               ContentResolver var24 = this.mContext.getContentResolver();
               Uri var25 = EmailContent.RecipientInformationCache.CONTENT_URI;
               String var26 = "server_id=? AND accountkey=?".toString();
               int var27 = var24.update(var25, var18, var26, var15);
               var28 = "RIC adapter-after modify commit" + var27;
            }
         }

         if(!this.RIcacheids_Del.isEmpty()) {
            int var38;
            String var37;
            for(var2 = this.RIcacheids_Del.iterator(); var2.hasNext(); var38 = Log.i("RecipientInformation Cache", var37)) {
               EmailContent.RecipientInformationCache var30 = (EmailContent.RecipientInformationCache)var2.next();
               String[] var31 = new String[2];
               String var32 = var30.riServerId;
               var31[0] = var32;
               String var33 = Long.toString(var30.riAccountKey);
               var31[1] = var33;
               ContentResolver var34 = this.mContext.getContentResolver();
               Uri var35 = EmailContent.RecipientInformationCache.CONTENT_URI;
               int var36 = var34.delete(var35, "server_id=? AND accountkey=?", var31);
               var37 = "RIC adapter-afterdel commit" + var36;
            }

         }
      }

      public void moveResponseParser() throws IOException {}

      public void responsesParser() throws IOException {}

      public void wipe() {
         ContentValues var1 = new ContentValues();
         var1.put("syncKey", "0");
         ContentResolver var2 = this.mContext.getContentResolver();
         Uri var3 = EmailContent.Mailbox.CONTENT_URI;
         String[] var4 = new String[1];
         String var5 = Long.toString(this.mMailbox.mId);
         var4[0] = var5;
         var2.update(var3, var1, "_id=?", var4);
         ContentResolver var7 = this.mContext.getContentResolver();
         Uri var8 = EmailContent.RecipientInformationCache.CONTENT_URI;
         String[] var9 = new String[1];
         String var10 = Long.toString(this.mAccount.mId);
         var9[0] = var10;
         int var11 = var7.delete(var8, "accountkey=?", var9);
         String var12 = "RIC adapter-afterdel wipe" + var11;
         int var13 = Log.i("RecipientInformation Cache", var12);
      }
   }
}
