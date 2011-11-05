package com.android.exchange.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.email.Controller;
import com.android.email.provider.EmailContent;
import com.android.exchange.SearchRequest;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;

public class EasEmailSearchAdapter {

   public EasEmailSearchAdapter() {}

   private static Serializer appendBaseBodyPreference(SearchRequest var0, Serializer var1) throws IOException {
      String var2 = var0.getOptionsBodyPreferenceType();
      String var3 = var0.getOptionsBodyPreferenceTruncationSize();
      Serializer var4;
      if(TextUtils.isEmpty(var2) && TextUtils.isEmpty(var3)) {
         var4 = var1;
      } else {
         Serializer var5 = var1.start(1093);
         if(!TextUtils.isEmpty(var2)) {
            var1 = appendData(1094, var2, var1);
         }

         if(!TextUtils.isEmpty(var3)) {
            var1 = appendData(1095, var3, var1);
         }

         Serializer var6 = var1.end();
         var4 = var1;
      }

      return var4;
   }

   private static Serializer appendData(int var0, String var1, Serializer var2) throws IOException {
      return var2.data(var0, var1);
   }

   private static Serializer appendDate(int var0, String var1, Serializer var2) throws IOException {
      Serializer var3 = var2.start(var0);
      Serializer var4 = appendEmptyTag(143, var3);
      return appendData(978, var1, var4).end();
   }

   private static Serializer appendEmptyTag(int var0, Serializer var1) throws IOException {
      return var1.start(var0).end();
   }

   private static Serializer appendOpaqueData(int var0, byte[] var1, Serializer var2) throws IOException {
      return var2.dataOpaque(var0, var1);
   }

   private static Serializer appendSearchOptions(EmailContent.Account var0, SearchRequest var1, Serializer var2) throws IOException {
      boolean var3 = var1.getOptionsRebuildResults();
      boolean var4 = var1.getOptionsDeepTraversal();
      String var5 = var1.getOptionsRange();
      String var6 = var1.getOptionsOptionsMIMESupport();
      double var7 = Double.parseDouble(var0.mProtocolVersion);
      Serializer var9;
      if(var7 == 2.5D) {
         if(TextUtils.isEmpty(var5)) {
            var9 = var2;
            return var9;
         }
      } else if(!var3 && !var4 && TextUtils.isEmpty(var5) && TextUtils.isEmpty(var6)) {
         var9 = var2;
         return var9;
      }

      var2 = var2.start(970);
      if(var3 && var7 >= 12.0D) {
         Serializer var10 = appendEmptyTag(985, var2);
      }

      if(var4 && var7 >= 12.0D) {
         Serializer var11 = appendEmptyTag(983, var2);
      }

      if(!TextUtils.isEmpty(var5)) {
         appendData(971, var5, var2);
      }

      if(var7 >= 12.0D) {
         Serializer var13 = appendBaseBodyPreference(var1, var2);
         if(!TextUtils.isEmpty(var6)) {
            appendData(34, var6, var13);
         }
      }

      Serializer var15 = var2.end();
      var9 = var2;
      return var9;
   }

   private static Serializer appendSearchQuery(Context var0, EmailContent.Account var1, SearchRequest var2, Serializer var3) throws IOException {
      String var4 = var2.getQueryClass();
      String var5 = var2.getQueryText();
      String var6 = var2.getQueryFreeText();
      long[] var7 = var2.getQueryCollectionIds();
      String var8 = var2.getQueryGreaterThan();
      String var9 = var2.getQueryLessThan();
      String var10 = var2.getQueryConvIdText();
      Serializer var11;
      if(Double.parseDouble(var1.mProtocolVersion) == 2.5D) {
         if(TextUtils.isEmpty(var5)) {
            var11 = var3;
         } else {
            var11 = appendData(969, var5, var3);
         }
      } else if(TextUtils.isEmpty(var4) && TextUtils.isEmpty(var6) && (var7 == null || var7.length <= 0) && TextUtils.isEmpty(var8) && TextUtils.isEmpty(var9)) {
         var11 = var3;
      } else {
         var3 = var3.start(969).start(979);
         if(!TextUtils.isEmpty(var4)) {
            appendData(16, var4, var3);
         }

         if(var7 != null) {
            long[] var13 = var7;
            int var28 = var7.length;

            for(int var14 = 0; var14 < var28; ++var14) {
               long var15 = var13[var14];
               String var17 = "fzhang request QueryCollectionId" + var15;
               int var18 = Log.e("EasSyncService", var17);
               EmailContent.Mailbox var27 = EmailContent.Mailbox.restoreMailboxWithId(var0, var15);
               if(var27 != null && !TextUtils.isEmpty(var27.mServerId) && var10 == null) {
                  String var19 = var27.mServerId;
                  appendData(18, var19, var3);
               }
            }
         }

         if(!TextUtils.isEmpty(var6)) {
            appendData(981, var6, var3);
         }

         if(!TextUtils.isEmpty(var8)) {
            appendDate(987, var8, var3);
         }

         if(!TextUtils.isEmpty(var9)) {
            appendDate(986, var9, var3);
         }

         if(var10 != null) {
            byte[] var24 = Controller.getInstance(var0).getConversationID(var10);
            appendOpaqueData(992, var24, var3);
         }

         Serializer var26 = var3.end().end();
         var11 = var3;
      }

      return var11;
   }

   public static Serializer buildEasEmailSearchRequest(Context var0, EmailContent.Account var1, SearchRequest var2) throws IOException {
      Serializer var3 = new Serializer();
      String var4 = var2.getStoreName();
      if(TextUtils.isEmpty(var4)) {
         String var5 = var0.getString(2131166876);
         throw new IOException(var5);
      } else {
         Serializer var6 = var3.start(965).start(967).data(968, var4);
         Serializer var7 = appendSearchQuery(var0, var1, var2, var3);
         Serializer var8 = appendSearchOptions(var1, var2, var7);
         var8.end().end().done();
         return var8;
      }
   }
}
