package com.facebook.katana.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

@JMAutogen.IgnoreUnexpectedJsonFields
public class FacebookPhoto extends JMCachingDictDestination {

   @JMAutogen.InferredType(
      jsonFieldName = "aid"
   )
   private final String mAlbumId = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "caption",
      type = StringUtils.JMNulledString.class
   )
   private final String mCaption = null;
   @JMAutogen.InferredType(
      jsonFieldName = "created"
   )
   private final long mCreated = 65535L;
   private final byte[] mImageBytes = null;
   @JMAutogen.InferredType(
      jsonFieldName = "object_id"
   )
   private final long mObjectId = 65535L;
   @JMAutogen.InferredType(
      jsonFieldName = "owner"
   )
   private final long mOwner = 65535L;
   @JMAutogen.InferredType(
      jsonFieldName = "pid"
   )
   private final String mPhotoId = null;
   @JMAutogen.InferredType(
      jsonFieldName = "src"
   )
   private final String mSrcUrl = null;
   @JMAutogen.InferredType(
      jsonFieldName = "src_big"
   )
   private final String mSrcUrlBig = null;
   @JMAutogen.InferredType(
      jsonFieldName = "src_small"
   )
   private final String mSrcUrlSmall = null;
   @JMAutogen.InferredType(
      jsonFieldName = "position"
   )
   public final long position = 65535L;


   private FacebookPhoto() {}

   public FacebookPhoto(String param1, String param2, long param3, String param5, String param6, String param7, String param8, long param9, byte[] param11, long param12, long param14) {
      // $FF: Couldn't be decompiled
   }

   public static FacebookPhoto parseJson(JsonParser var0) throws FacebookApiException, JsonParseException, IOException, JMException {
      return (FacebookPhoto)JMParser.parseObjectJson(var0, FacebookPhoto.class);
   }

   public static FacebookPhoto readFromContentProvider(Context var0, Uri var1) {
      ContentResolver var2 = var0.getContentResolver();
      String[] var3 = FacebookPhoto.PhotoQuery.PROJECTION;
      Cursor var5 = var2.query(var1, var3, (String)null, (String[])null, (String)null);
      FacebookPhoto var19;
      if(var5.moveToFirst()) {
         String var6 = var5.getString(1);
         String var7 = var5.getString(0);
         long var8 = var5.getLong(2);
         String var10 = var5.getString(4);
         String var11 = var5.getString(5);
         String var12 = var5.getString(6);
         String var13 = var5.getString(7);
         long var14 = var5.getLong(3);
         byte[] var16 = var5.getBlob(8);
         long var17 = var5.getLong(9);
         var19 = new FacebookPhoto(var6, var7, var8, var10, var11, var12, var13, var14, var16, 65535L, var17);
      } else {
         var19 = null;
      }

      var5.close();
      return var19;
   }

   public static FacebookPhoto readFromContentProvider(Context var0, String var1) {
      Uri var2 = Uri.withAppendedPath(PhotosProvider.PHOTOS_PID_CONTENT_URI, var1);
      return readFromContentProvider(var0, var2);
   }

   public String getAlbumId() {
      return this.mAlbumId;
   }

   public String getCaption() {
      return this.mCaption;
   }

   public long getCreated() {
      return this.mCreated;
   }

   public long getCreatedMs() {
      return this.mCreated * 1000L;
   }

   public byte[] getImageBytes() {
      return this.mImageBytes;
   }

   public long getObjectId() {
      return this.mObjectId;
   }

   public long getOwnerId() {
      return this.mOwner;
   }

   public String getPhotoId() {
      return this.mPhotoId;
   }

   public String getSrc() {
      return this.mSrcUrl;
   }

   public String getSrcBig() {
      return this.mSrcUrlBig;
   }

   public String getSrcSmall() {
      return this.mSrcUrlSmall;
   }

   private interface PhotoQuery {

      int INDEX_ALBUM_ID = 0;
      int INDEX_CAPTION = 4;
      int INDEX_CREATED = 3;
      int INDEX_OWNER = 2;
      int INDEX_PHOTO_ID = 1;
      int INDEX_POSITION = 9;
      int INDEX_SRC = 5;
      int INDEX_SRC_BIG = 6;
      int INDEX_SRC_SMALL = 7;
      int INDEX_THUMBNAIL = 8;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"aid", "pid", "owner", "created", "caption", "src", "src_big", "src_small", "thumbnail", "position"};
         PROJECTION = var0;
      }
   }
}
