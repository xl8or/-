package com.android.exchange.adapter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.android.email.provider.AttachmentProvider;
import com.android.email.provider.EmailContent;
import com.android.exchange.AbstractSyncService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

public class LoadMoreUtility {

   private static final int MESSAGE_BODY_MAX_SIZE = 1048576;


   public LoadMoreUtility() {}

   public static void updateAttachment(Context var0, EmailContent.Message var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      File var3 = var0.getFilesDir();
      String var4 = var2.append(var3).append("/tempFile").toString();
      FileInputStream var5 = new FileInputStream(var4);
      if(var5 != null) {
         long var6 = var1.mId;
         EmailContent.Attachment[] var8 = EmailContent.Attachment.restoreAttachmentsWithMessageId(var0, var6);
         if(var8 == null) {
            var5.close();
         } else if(var8 != null && var8.length < 1) {
            var5.close();
         } else {
            long var9 = var1.mAccountKey;
            File var11 = AttachmentProvider.getAttachmentDirectory(var0, var9);
            if(!var11.exists()) {
               boolean var12 = var11.mkdirs();
            }

            long var13 = var1.mAccountKey;
            long var15 = var8[0].mId;
            File var17 = AttachmentProvider.getAttachmentFilename(var0, var13, var15);
            boolean var18 = var17.createNewFile();
            FileOutputStream var19 = new FileOutputStream(var17);
            long var20 = (long)IOUtils.copy((InputStream)var5, (OutputStream)var19);
            var5.close();
            var19.close();
            StringBuilder var22 = new StringBuilder();
            File var23 = var0.getFilesDir();
            String var24 = var22.append(var23).append("/tempFile").toString();
            File var25 = new File(var24);
            if(var25.exists()) {
               boolean var26 = var25.delete();
            }

            long var27 = var1.mAccountKey;
            long var29 = var8[0].mId;
            String var31 = AttachmentProvider.getAttachmentUri(var27, var29).toString();
            ContentValues var32 = new ContentValues();
            Long var33 = Long.valueOf(var20);
            var32.put("size", var33);
            var32.put("contentUri", var31);
            Uri var34 = EmailContent.Attachment.CONTENT_URI;
            long var35 = var8[0].mId;
            Uri var37 = ContentUris.withAppendedId(var34, var35);
            var0.getContentResolver().update(var37, var32, (String)null, (String[])null);
         }
      }
   }

   public static void updateEmail(AbstractSyncService param0, Context param1, EmailContent.Message param2, EmailContent.Body param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static void updateInlineAttachment(AbstractSyncService param0, Context param1, EmailContent.Message param2) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
