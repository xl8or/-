package com.facebook.katana.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.PhotosProvider;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FacebookAlbum {

   public static final String TYPE_MOBILE = "mobile";
   public static final String TYPE_NORMAL = "normal";
   public static final String TYPE_PROFILE = "profile";
   public static final String TYPE_WALL = "wall";
   private final String mAlbumCoverPhotoId;
   private String mAlbumCoverPhotoUrl;
   private final String mAlbumId;
   private boolean mCoverChanged;
   private final long mCreated;
   private final String mDescription;
   private final byte[] mImageBytes;
   private final String mLink;
   private final String mLocation;
   private final long mModified;
   private final String mName;
   private final long mObjectId;
   private final long mOwner;
   private final int mSize;
   private final String mType;
   private final String mVisibility;


   public FacebookAlbum(String param1, String param2, String param3, long param4, String param6, long param7, long param9, String param11, String param12, String param13, int param14, String param15, String param16, byte[] param17, long param18) {
      // $FF: Couldn't be decompiled
   }

   public FacebookAlbum(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      byte var2 = 0;
      this.mCoverChanged = (boolean)var2;
      String var3 = null;
      long var4 = 65535L;
      String var6 = null;
      String var7 = null;
      String var8 = null;
      String var9 = null;
      String var10 = null;
      String var11 = null;
      String var12 = null;
      long var13 = 65535L;
      long var15 = 65535L;
      int var17 = '\uffff';
      int var18 = -1;
      String var19 = null;
      long var20 = 65535L;
      JsonToken var22 = var1.nextToken();

      while(true) {
         JsonToken var23 = JsonToken.END_OBJECT;
         if(var22 == var23) {
            if(var18 > 0) {
               FacebookApiException var65 = new FacebookApiException(var18, var19);
               throw var65;
            }

            this.mAlbumId = var3;
            this.mOwner = var4;
            this.mName = var6;
            this.mAlbumCoverPhotoId = var10;
            this.mCreated = var13;
            this.mModified = var15;
            this.mDescription = var7;
            this.mLocation = var8;
            this.mLink = var9;
            this.mSize = var17;
            this.mVisibility = var11;
            if(var12 == null) {
               String var83 = "normal";
               this.mType = var83;
            } else {
               this.mType = var12;
            }

            Object var84 = null;
            this.mImageBytes = (byte[])var84;
            this.mObjectId = var20;
            return;
         }

         JsonToken var26 = JsonToken.VALUE_STRING;
         String var29;
         if(var22 == var26) {
            var29 = var1.getCurrentName();
            String var31 = "aid";
            if(var29.equals(var31)) {
               var3 = var1.getText();
            } else {
               String var33 = "cover_pid";
               if(var29.equals(var33)) {
                  var10 = var1.getText();
               } else {
                  String var35 = "name";
                  if(var29.equals(var35)) {
                     var6 = var1.getText();
                  } else {
                     String var37 = "description";
                     if(var29.equals(var37)) {
                        var7 = var1.getText();
                     } else {
                        String var39 = "location";
                        if(var29.equals(var39)) {
                           var8 = var1.getText();
                        } else {
                           String var41 = "link";
                           if(var29.equals(var41)) {
                              var9 = var1.getText();
                           } else {
                              String var43 = "visible";
                              if(var29.equals(var43)) {
                                 var11 = var1.getText();
                              } else {
                                 String var45 = "type";
                                 if(var29.equals(var45)) {
                                    var12 = var1.getText();
                                 } else {
                                    String var47 = "error_msg";
                                    if(var29.equals(var47)) {
                                       var19 = var1.getText();
                                    } else {
                                       String var49 = "owner";
                                       if(var29.equals(var49)) {
                                          var4 = Long.parseLong(var1.getText());
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         } else {
            JsonToken var50 = JsonToken.VALUE_NUMBER_INT;
            if(var22 == var50) {
               var29 = var1.getCurrentName();
               String var54 = "owner";
               if(var29.equals(var54)) {
                  var4 = var1.getLongValue();
               } else {
                  String var56 = "created";
                  if(var29.equals(var56)) {
                     var13 = var1.getLongValue();
                  } else {
                     String var58 = "modified";
                     if(var29.equals(var58)) {
                        var15 = var1.getLongValue();
                     } else {
                        String var60 = "size";
                        if(var29.equals(var60)) {
                           var17 = var1.getIntValue();
                        } else {
                           String var62 = "error_code";
                           if(var29.equals(var62)) {
                              var18 = var1.getIntValue();
                           } else {
                              String var64 = "object_id";
                              if(var29.equals(var64)) {
                                 var20 = var1.getLongValue();
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var22 = var1.nextToken();
      }
   }

   public static FacebookAlbum readFromContentProvider(Context var0, Uri var1) {
      ContentResolver var2 = var0.getContentResolver();
      String[] var3 = FacebookAlbum.AlbumQuery.ALBUM_PROJECTION;
      Cursor var5 = var2.query(var1, var3, (String)null, (String[])null, (String)null);
      FacebookAlbum var24;
      if(var5.moveToFirst()) {
         String var6 = var5.getString(0);
         String var7 = var5.getString(2);
         String var8 = var5.getString(3);
         long var9 = var5.getLong(1);
         String var11 = var5.getString(7);
         long var12 = var5.getLong(5);
         long var14 = var5.getLong(6);
         String var16 = var5.getString(8);
         String var17 = var5.getString(9);
         int var18 = var5.getInt(10);
         String var19 = var5.getString(11);
         String var20 = var5.getString(12);
         byte[] var21 = var5.getBlob(4);
         long var22 = var5.getLong(13);
         var24 = new FacebookAlbum(var6, var7, var8, var9, var11, var12, var14, var16, var17, (String)null, var18, var19, var20, var21, var22);
      } else {
         var24 = null;
      }

      var5.close();
      return var24;
   }

   public static FacebookAlbum readFromContentProvider(Context var0, String var1) {
      Uri var2 = Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, var1);
      return readFromContentProvider(var0, var2);
   }

   public String getAlbumId() {
      return this.mAlbumId;
   }

   public String getCoverPhotoId() {
      return this.mAlbumCoverPhotoId;
   }

   public String getCoverPhotoUrl() {
      return this.mAlbumCoverPhotoUrl;
   }

   public long getCreated() {
      return this.mCreated;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public byte[] getImageBytes() {
      return this.mImageBytes;
   }

   public String getLink() {
      return this.mLink;
   }

   public String getLocation() {
      return this.mLocation;
   }

   public long getModified() {
      return this.mModified;
   }

   public String getName() {
      return this.mName;
   }

   public long getObjectId() {
      return this.mObjectId;
   }

   public long getOwner() {
      return this.mOwner;
   }

   public int getSize() {
      return this.mSize;
   }

   public String getType() {
      return this.mType;
   }

   public String getVisibility() {
      return this.mVisibility;
   }

   public boolean hasCoverChanged() {
      return this.mCoverChanged;
   }

   public void setCoverChanged(boolean var1) {
      this.mCoverChanged = var1;
   }

   public void setCoverPhotoUrl(String var1) {
      this.mAlbumCoverPhotoUrl = var1;
   }

   private interface AlbumQuery {

      String[] ALBUM_PROJECTION;
      int INDEX_ALBUM_ID = 0;
      int INDEX_COVER_PHOTO_ID = 2;
      int INDEX_COVER_PHOTO_URL = 3;
      int INDEX_COVER_THUMBNAIL = 4;
      int INDEX_CREATED = 5;
      int INDEX_DESCRIPTION = 8;
      int INDEX_LOCATION = 9;
      int INDEX_MODIFIED = 6;
      int INDEX_NAME = 7;
      int INDEX_OBJECT_ID = 13;
      int INDEX_OWNER = 1;
      int INDEX_SIZE = 10;
      int INDEX_TYPE = 12;
      int INDEX_VISIBILITY = 11;


      static {
         String[] var0 = new String[]{"aid", "owner", "cover_pid", "cover_url", "thumbnail", "created", "modified", "name", "description", "location", "size", "visibility", "type", "object_id"};
         ALBUM_PROJECTION = var0;
      }
   }
}
