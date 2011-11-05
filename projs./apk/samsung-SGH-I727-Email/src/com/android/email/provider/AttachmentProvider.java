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
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.android.email.Email;
import com.android.email.mail.MessagingException;
import com.android.email.mail.internet.MimeUtility;
import com.android.email.provider.EmailContent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.io.IOUtils;

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

   public static boolean IsAllAttachmentFilesExist(Context var0, long var1, long var3) {
      boolean var5 = true;
      Uri var6 = ContentUris.withAppendedId(EmailContent.Attachment.MESSAGE_ID_URI, var3);
      ContentResolver var7 = var0.getContentResolver();
      String[] var8 = EmailContent.Attachment.ID_PROJECTION;
      Object var9 = null;
      Object var10 = null;
      Cursor var11 = var7.query(var6, var8, (String)null, (String[])var9, (String)var10);

      while(true) {
         boolean var17 = false;

         boolean var14;
         try {
            var17 = true;
            if(!var11.moveToNext()) {
               var17 = false;
               break;
            }

            long var12 = var11.getLong(0);
            var14 = getAttachmentFilename(var0, var1, var12).exists();
            var17 = false;
         } finally {
            if(var17) {
               var11.close();
            }
         }

         if(!var14) {
            var5 = false;
            break;
         }
      }

      var11.close();
      return var5;
   }

   public static boolean changeMimeType(Context var0, long var1, long var3, String var5) {
      String var6 = getAttachmentUri(var1, var3).toString();
      ContentValues var7 = new ContentValues();
      var7.put("mimeType", var5);
      Uri var8 = ContentUris.withAppendedId(EmailContent.Attachment.CONTENT_URI, var3);
      var0.getContentResolver().update(var8, var7, (String)null, (String[])null);
      Email.log("changeMimeType: accountId = " + var1 + " attachmentId = " + var3 + " uri_target = " + var8 + " newMimeType = " + var5);
      return true;
   }

   public static boolean copyOneAttachmentInAccount(Context param0, long param1, Uri param3, long param4) throws MessagingException, IOException {
      // $FF: Couldn't be decompiled
   }

   private Bitmap createImageThumbnail(InputStream var1) {
      Bitmap var2 = null;

      Bitmap var3;
      try {
         var3 = BitmapFactory.decodeStream(var1);
      } catch (OutOfMemoryError var6) {
         return var2;
      } catch (Exception var7) {
         return var2;
      }

      var2 = var3;
      return var2;
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
      } catch (Exception var20) {
         String var15 = "" + var20;
         int var16 = Log.v("=====", var15);
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

   public static HashSet<Long> getAllAttachmentId(Context var0, long var1) {
      HashSet var3 = new HashSet();
      Uri var4 = ContentUris.withAppendedId(EmailContent.Attachment.MESSAGE_ID_URI, var1);
      ContentResolver var5 = var0.getContentResolver();
      String[] var6 = EmailContent.Attachment.ID_PROJECTION;
      Object var7 = null;
      Object var8 = null;
      Cursor var9 = var5.query(var4, var6, (String)null, (String[])var7, (String)var8);

      try {
         while(var9.moveToNext()) {
            Long var10 = Long.valueOf(var9.getLong(0));
            var3.add(var10);
         }
      } finally {
         var9.close();
      }

      return var3;
   }

   public static File getAttachmentDirectory(Context var0, long var1) {
      File var3 = var0.getCacheDir();
      String var4 = var1 + ".db_att";
      return new File(var3, var4);
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

   public static HashSet<Long> getFileNotExistAttachmentId(Context var0, long var1, long var3) {
      HashSet var5 = new HashSet();
      Uri var6 = ContentUris.withAppendedId(EmailContent.Attachment.MESSAGE_ID_URI, var3);
      ContentResolver var7 = var0.getContentResolver();
      String[] var8 = EmailContent.Attachment.ID_PROJECTION;
      Object var9 = null;
      Object var10 = null;
      Cursor var11 = var7.query(var6, var8, (String)null, (String[])var9, (String)var10);

      try {
         while(var11.moveToNext()) {
            long var12 = var11.getLong(0);
            if(!getAttachmentFilename(var0, var1, var12).exists()) {
               Long var14 = Long.valueOf(var12);
               var5.add(var14);
            }
         }
      } finally {
         var11.close();
      }

      return var5;
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

   public static boolean makeOneAttachmentInAccount(Context param0, long param1, Uri param3, long param4) throws MessagingException, IOException {
      // $FF: Couldn't be decompiled
   }

   public static boolean moveAllAttachmentToOtherAccount(Context var0, long var1, long var3, long var5) {
      Uri var7 = EmailContent.Attachment.MESSAGE_ID_URI;
      Uri var10 = ContentUris.withAppendedId(var7, var3);
      ContentResolver var11 = var0.getContentResolver();
      String[] var12 = EmailContent.Attachment.ID_PROJECTION;
      Cursor var13 = var11.query(var10, var12, (String)null, (String[])null, (String)null);
      boolean var14;
      if(var13 != null && var13.getCount() > 0) {
         int var15 = var13.getCount();

         while(var13.moveToNext()) {
            long var16 = var13.getLong(0);
            Context var18 = var0;
            long var19 = var1;
            long var21 = var5;

            byte var24;
            label33: {
               byte var23;
               try {
                  var23 = moveOneAttachmentToOtherAccount(var18, var19, var16, var21);
               } catch (MessagingException var32) {
                  var24 = 0;
                  break label33;
               } catch (IOException var33) {
                  var24 = 0;
                  break label33;
               }

               var24 = var23;
            }

            if(var24 == 1) {
               var15 += -1;
            }
         }

         if(var15 > 0) {
            var14 = false;
         } else if(!IsAllAttachmentFilesExist(var0, var5, var3)) {
            var14 = false;
         } else {
            var14 = true;
         }
      } else {
         var14 = true;
      }

      return var14;
   }

   public static boolean moveOneAttachmentToOtherAccount(Context var0, long var1, long var3, long var5) throws MessagingException, IOException {
      File var7 = getAttachmentDirectory(var0, var5);
      if(!var7.exists()) {
         boolean var8 = var7.mkdirs();
      }

      File var9 = getAttachmentFilename(var0, var1, var3);
      boolean var10;
      if(var9 != null && var9.exists()) {
         File var11 = getAttachmentFilename(var0, var5, var3);
         FileInputStream var12 = new FileInputStream(var9);
         FileOutputStream var13 = new FileOutputStream(var11);
         if(var12 == null && var13 == null) {
            var10 = false;
         } else if(var13 == null) {
            var12.close();
            var10 = false;
         } else if(var12 == null) {
            var13.close();
            var10 = false;
         } else {
            long var14 = (long)IOUtils.copy((InputStream)var12, (OutputStream)var13);
            var12.close();
            var13.close();
            String var16 = getAttachmentUri(var5, var3).toString();
            ContentValues var17 = new ContentValues();
            Long var18 = Long.valueOf(var14);
            var17.put("size", var18);
            var17.put("contentUri", var16);
            Uri var19 = ContentUris.withAppendedId(EmailContent.Attachment.CONTENT_URI, var3);
            var0.getContentResolver().update(var19, var17, (String)null, (String[])null);
            boolean var21 = var9.delete();
            var10 = true;
         }
      } else {
         var10 = false;
      }

      return var10;
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
         Uri var7 = EmailContent.Attachment.CONTENT_URI;
         long var8 = Long.parseLong(var4);
         Uri var10 = ContentUris.withAppendedId(var7, var8);
         ContentResolver var11 = this.getContext().getContentResolver();
         String[] var12 = MIME_TYPE_PROJECTION;
         Object var14 = null;
         Object var15 = null;
         Cursor var16 = var11.query(var10, var12, (String)null, (String[])var14, (String)var15);
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
               }
            }

            var16.close();
            var6 = null;
            return var6;
         }

         var16.close();
         var6 = var18;
      }

      return var6;
   }

   public Uri insert(Uri var1, ContentValues var2) {
      return null;
   }

   public boolean onCreate() {
      File[] var1 = this.getContext().getCacheDir().listFiles();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         File var4 = var1[var3];
         String var5 = var4.getName();
         if(var5.endsWith(".tmp") || var5.startsWith("thmb_")) {
            boolean var6 = var4.delete();
         }
      }

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
      Uri var16 = EmailContent.Attachment.CONTENT_URI;
      long var17 = Long.parseLong(var12);
      Uri var19 = ContentUris.withAppendedId(var16, var17);
      ContentResolver var20 = this.getContext().getContentResolver();
      String[] var21 = PROJECTION_QUERY;
      Cursor var23 = var20.query(var19, var21, (String)null, (String[])null, (String)null);
      boolean var41 = false;

      int var25;
      String var24;
      String var26;
      MatrixCursor var35;
      label108: {
         try {
            var41 = true;
            if(var23.moveToFirst()) {
               var24 = var23.getString(0);
               var25 = var23.getInt(1);
               var26 = var23.getString(2);
               var41 = false;
               break label108;
            }

            var41 = false;
         } finally {
            if(var41) {
               var23.close();
            }
         }

         var35 = null;
         var23.close();
         return var35;
      }

      String var27 = var26;
      var23.close();
      MatrixCursor var28 = new MatrixCursor(var2);
      Object[] var31 = new Object[var2.length];
      int var32 = 0;

      for(int var33 = var2.length; var32 < var33; ++var32) {
         String var34 = var2[var32];
         if("_id".equals(var34)) {
            var31[var32] = var12;
         } else if("_data".equals(var34)) {
            var31[var32] = var27;
         } else if("_display_name".equals(var34)) {
            var31[var32] = var24;
         } else if("_size".equals(var34)) {
            Integer var37 = Integer.valueOf(var25);
            var31[var32] = var37;
         }
      }

      var28.addRow(var31);
      var35 = var28;
      return var35;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      return 0;
   }

   public static class AttachmentProviderColumns {

      public static final String DATA = "_data";
      public static final String DISPLAY_NAME = "_display_name";
      public static final String SIZE = "_size";
      public static final String _ID = "_id";


      public AttachmentProviderColumns() {}
   }
}
