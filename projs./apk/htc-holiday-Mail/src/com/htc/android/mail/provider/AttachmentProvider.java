package com.htc.android.mail.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import java.io.FileNotFoundException;

public class AttachmentProvider extends ContentProvider {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final int GLOBAL_PARTS = 2;
   private static final int GLOBAL_PARTS_ID = 3;
   private static final int PARTS = 0;
   private static final int PARTS_ID = 1;
   private static final String TAG = "AttachmentProvider";
   public static final Uri sGlobalPartsURI = Uri.parse("content://com.htc.android.mail.attachmentprovider/searchSvrParts");
   public static final Uri sPartsURI = Uri.parse("content://com.htc.android.mail.attachmentprovider/parts");
   private static final UriMatcher sURLMatcher = new UriMatcher(-1);


   static {
      sURLMatcher.addURI("com.htc.android.mail.attachmentprovider", "parts", 0);
      sURLMatcher.addURI("com.htc.android.mail.attachmentprovider", "parts/#", 1);
      sURLMatcher.addURI("com.htc.android.mail.attachmentprovider", "searchSvrParts", 2);
      sURLMatcher.addURI("com.htc.android.mail.attachmentprovider", "searchSvrParts/#", 3);
   }

   public AttachmentProvider() {}

   public int delete(Uri var1, String var2, String[] var3) {
      return 0;
   }

   public String getType(Uri var1) {
      long var2 = ContentUris.parseId(var1);
      String var4;
      if(var2 == 65535L) {
         var4 = null;
      } else {
         Uri var5;
         if(sURLMatcher.match(var1) == 3) {
            var5 = MailProvider.sSearchSvrPartsURI;
         } else {
            var5 = MailProvider.sPartsURI;
         }

         Uri var6 = ContentUris.withAppendedId(var5, var2);
         ContentResolver var7 = this.getContext().getContentResolver();
         String[] var8 = new String[]{"_mimetype"};
         Object var9 = null;
         Object var10 = null;
         Cursor var11 = var7.query(var6, var8, (String)null, (String[])var9, (String)var10);
         boolean var15 = false;

         String var12;
         label67: {
            try {
               var15 = true;
               if(var11.moveToFirst()) {
                  var12 = var11.getString(0);
                  var15 = false;
                  break label67;
               }

               var15 = false;
            } finally {
               if(var15) {
                  var11.close();
               }
            }

            var11.close();
            var4 = null;
            return var4;
         }

         var4 = var12;
         var11.close();
      }

      return var4;
   }

   public Uri insert(Uri var1, ContentValues var2) {
      return null;
   }

   public boolean onCreate() {
      return true;
   }

   public ParcelFileDescriptor openFile(Uri param1, String param2) throws FileNotFoundException {
      // $FF: Couldn't be decompiled
   }

   public Cursor query(Uri param1, String[] param2, String param3, String[] param4, String param5) {
      // $FF: Couldn't be decompiled
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      return 0;
   }

   public static class AttachmentProviderColumns {

      public static final String DATA = "_data";
      public static final String DISPLAY_NAME = "_display_name";
      public static final String MIME_TYPE = "mime_type";
      public static final String SIZE = "_size";
      public static final String _ID = "_id";
      public static final String _MIME_TYPE = "_mime_type";


      public AttachmentProviderColumns() {}
   }
}
