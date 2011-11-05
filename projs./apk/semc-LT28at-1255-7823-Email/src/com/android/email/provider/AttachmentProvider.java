package com.android.email.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.android.email.Email;
import com.android.email.mail.internet.MimeUtility;
import com.android.email.provider.EmailContent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AttachmentProvider extends ContentProvider {

   public static final String AUTHORITY = "com.android.email.attachmentprovider";
   public static final Uri CONTENT_URI = Uri.parse("content://com.android.email.attachmentprovider");
   private static final String FORMAT_RAW = "RAW";
   private static final String FORMAT_THUMBNAIL = "THUMBNAIL";
   private static final int MIME_TYPE_COLUMN_FILENAME = 1;
   private static final int MIME_TYPE_COLUMN_MIME_TYPE;
   private static final String[] MIME_TYPE_PROJECTION;
   private static final String[] PROJECTION_QUERY;


   static {
      String[] var0 = new String[]{"mimeType", "fileName"};
      MIME_TYPE_PROJECTION = var0;
      String[] var1 = new String[]{"fileName", "size", "contentUri"};
      PROJECTION_QUERY = var1;
   }

   public AttachmentProvider() {}

   private Bitmap createImageThumbnail(InputStream var1) {
      Bitmap var2;
      Bitmap var3;
      try {
         var2 = BitmapFactory.decodeStream(var1);
      } catch (OutOfMemoryError var14) {
         StringBuilder var5 = (new StringBuilder()).append("createImageThumbnail failed with ");
         String var6 = var14.getMessage();
         String var7 = var5.append(var6).toString();
         int var8 = Log.d("Email", var7);
         var3 = null;
         return var3;
      } catch (Exception var15) {
         StringBuilder var10 = (new StringBuilder()).append("createImageThumbnail failed with ");
         String var11 = var15.getMessage();
         String var12 = var10.append(var11).toString();
         int var13 = Log.d("Email", var12);
         var3 = null;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   private Bitmap createThumbnail(String var1, InputStream var2) {
      Bitmap var3;
      if(MimeUtility.mimeTypeMatches(var1, "image/*")) {
         var3 = this.createImageThumbnail(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public static void deleteAllAttachmentFiles(Context var0, long var1, long var3) {
      Uri var5 = ContentUris.withAppendedId(EmailContent.Attachment.MESSAGE_ID_URI, var3);
      ContentResolver var6 = var0.getContentResolver();
      String[] var7 = EmailContent.Attachment.ID_PROJECTION;
      Object var8 = null;
      Object var9 = null;
      Cursor var10 = var6.query(var5, var7, (String)null, (String[])var8, (String)var9);

      try {
         while(var10.moveToNext()) {
            long var11 = var10.getLong(0);
            boolean var13 = getAttachmentFilename(var0, var1, var11).delete();
         }
      } finally {
         var10.close();
      }

   }

   public static void deleteAllMailboxAttachmentFiles(Context var0, long var1, long var3) {
      ContentResolver var5 = var0.getContentResolver();
      Uri var6 = EmailContent.Message.CONTENT_URI;
      String[] var7 = EmailContent.Message.ID_COLUMN_PROJECTION;
      String[] var8 = new String[1];
      String var9 = Long.toString(var3);
      var8[0] = var9;
      Cursor var10 = var5.query(var6, var7, "mailboxKey=?", var8, (String)null);

      try {
         while(var10.moveToNext()) {
            long var11 = var10.getLong(0);
            deleteAllAttachmentFiles(var0, var1, var11);
         }
      } finally {
         var10.close();
      }

   }

   public static File getAttachmentDirectory(Context var0, long var1) {
      String var3 = var1 + ".db_att";
      return var0.getDatabasePath(var3);
   }

   public static File getAttachmentFilename(Context var0, long var1, long var3) {
      File var5 = getAttachmentDirectory(var0, var1);
      String var6 = Long.toString(var3);
      return new File(var5, var6);
   }

   public static Uri getAttachmentThumbnailUri(long var0, long var2, int var4, int var5) {
      Builder var6 = CONTENT_URI.buildUpon();
      String var7 = Long.toString(var0);
      Builder var8 = var6.appendPath(var7);
      String var9 = Long.toString(var2);
      Builder var10 = var8.appendPath(var9).appendPath("THUMBNAIL");
      String var11 = Integer.toString(var4);
      Builder var12 = var10.appendPath(var11);
      String var13 = Integer.toString(var5);
      return var12.appendPath(var13).build();
   }

   public static Uri getAttachmentUri(long var0, long var2) {
      Builder var4 = CONTENT_URI.buildUpon();
      String var5 = Long.toString(var0);
      Builder var6 = var4.appendPath(var5);
      String var7 = Long.toString(var2);
      return var6.appendPath(var7).appendPath("RAW").build();
   }

   public static String inferMimeType(String var0, String var1) {
      String var2;
      if(!TextUtils.isEmpty(var1) && !"application/octet-stream".equalsIgnoreCase(var1)) {
         var2 = var1;
      } else {
         if(!TextUtils.isEmpty(var0)) {
            int var3 = var0.lastIndexOf(46);
            String var4 = null;
            if(var3 > 0) {
               int var5 = var0.length() - 1;
               if(var3 < var5) {
                  int var6 = var3 + 1;
                  var4 = var0.substring(var6).toLowerCase();
               }
            }

            if(!TextUtils.isEmpty(var4)) {
               var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var4);
               if(var1 == null) {
                  "application/" + var4;
               }

               var2 = var1;
               return var2;
            }
         }

         var2 = "application/octet-stream";
      }

      return var2;
   }

   public static Uri resolveAttachmentIdToContentUri(ContentResolver var0, Uri var1) {
      String[] var2 = new String[]{"_data"};
      Object var5 = null;
      Object var6 = null;
      Cursor var7 = var0.query(var1, var2, (String)null, (String[])var5, (String)var6);
      Uri var10;
      if(var7 != null) {
         boolean var13 = false;

         label69: {
            Uri var9;
            try {
               var13 = true;
               if(!var7.moveToFirst()) {
                  var13 = false;
                  break label69;
               }

               String var8 = var7.getString(0);
               if(var8 == null) {
                  Email.log("AttachmentProvider: attachment with null contentUri");
                  var13 = false;
                  break label69;
               }

               var9 = Uri.parse(var8);
               var13 = false;
            } finally {
               if(var13) {
                  var7.close();
               }
            }

            var10 = var9;
            var7.close();
            return var10;
         }

         var7.close();
      }

      var10 = var1;
      return var10;
   }

   public int delete(Uri var1, String var2, String[] var3) {
      return 0;
   }

   public String getType(Uri var1) {
      List var2 = var1.getPathSegments();
      String var3 = (String)var2.get(0);
      String var4 = (String)var2.get(1);
      String var5 = (String)var2.get(2);
      String var6;
      if("THUMBNAIL".equals(var5)) {
         var6 = "image/png";
      } else {
         long var7 = Binder.clearCallingIdentity();
         Uri var9 = EmailContent.Attachment.CONTENT_URI;
         long var10 = Long.parseLong(var4);
         Uri var12 = ContentUris.withAppendedId(var9, var10);
         ContentResolver var13 = this.getContext().getContentResolver();
         String[] var14 = MIME_TYPE_PROJECTION;
         Cursor var16 = var13.query(var12, var14, (String)null, (String[])null, (String)null);
         boolean var22 = false;

         String var18;
         label55: {
            try {
               var22 = true;
               if(var16.moveToFirst()) {
                  String var17 = var16.getString(0);
                  var18 = inferMimeType(var16.getString(1), var17);
                  var22 = false;
                  break label55;
               }

               var22 = false;
            } finally {
               if(var22) {
                  var16.close();
                  Binder.restoreCallingIdentity(var7);
               }
            }

            var16.close();
            Binder.restoreCallingIdentity(var7);
            var6 = null;
            return var6;
         }

         var16.close();
         Binder.restoreCallingIdentity(var7);
         var6 = var18;
      }

      return var6;
   }

   public Uri insert(Uri var1, ContentValues var2) {
      return null;
   }

   public boolean onCreate() {
      (new AttachmentProvider.1()).start();
      return true;
   }

   public ParcelFileDescriptor openFile(Uri param1, String param2) throws FileNotFoundException {
      // $FF: Couldn't be decompiled
   }

   public Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      if(var2 == null) {
         var2 = new String[]{"_id", "_data"};
      }

      List var6 = var1.getPathSegments();
      byte var8 = 0;
      String var9 = (String)var6.get(var8);
      byte var11 = 1;
      String var12 = (String)var6.get(var11);
      byte var14 = 2;
      String var15 = (String)var6.get(var14);
      long var16 = Binder.clearCallingIdentity();
      Uri var18 = EmailContent.Attachment.CONTENT_URI;
      long var19 = Long.parseLong(var12);
      Uri var21 = ContentUris.withAppendedId(var18, var19);
      ContentResolver var22 = this.getContext().getContentResolver();
      String[] var23 = PROJECTION_QUERY;
      Cursor var25 = var22.query(var21, var23, (String)null, (String[])null, (String)null);
      boolean var43 = false;

      int var27;
      String var26;
      String var28;
      MatrixCursor var37;
      label108: {
         try {
            var43 = true;
            if(var25.moveToFirst()) {
               var26 = var25.getString(0);
               var27 = var25.getInt(1);
               var28 = var25.getString(2);
               var43 = false;
               break label108;
            }

            var43 = false;
         } finally {
            if(var43) {
               var25.close();
               Binder.restoreCallingIdentity(var16);
            }
         }

         var37 = null;
         var25.close();
         Binder.restoreCallingIdentity(var16);
         return var37;
      }

      String var29 = var28;
      var25.close();
      Binder.restoreCallingIdentity(var16);
      MatrixCursor var30 = new MatrixCursor(var2);
      Object[] var33 = new Object[var2.length];
      int var34 = 0;

      for(int var35 = var2.length; var34 < var35; ++var34) {
         String var36 = var2[var34];
         if("_id".equals(var36)) {
            var33[var34] = var12;
         } else if("_data".equals(var36)) {
            var33[var34] = var29;
         } else if("_display_name".equals(var36)) {
            var33[var34] = var26;
         } else if("_size".equals(var36)) {
            Integer var39 = Integer.valueOf(var27);
            var33[var34] = var39;
         }
      }

      var30.addRow(var33);
      var37 = var30;
      return var37;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      return 0;
   }

   class 1 extends Thread {

      1() {}

      public void run() {
         File[] var1 = AttachmentProvider.this.getContext().getCacheDir().listFiles();
         if(var1 != null) {
            File[] var2 = var1;
            int var3 = var1.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               File var5 = var2[var4];
               String var6 = var5.getName();
               if(var6.endsWith(".tmp") || var6.startsWith("thmb_")) {
                  boolean var7 = var5.delete();
               }
            }

         }
      }
   }

   public static class AttachmentProviderColumns {

      public static final String DATA = "_data";
      public static final String DISPLAY_NAME = "_display_name";
      public static final String SIZE = "_size";
      public static final String _ID = "_id";


      public AttachmentProviderColumns() {}
   }
}
