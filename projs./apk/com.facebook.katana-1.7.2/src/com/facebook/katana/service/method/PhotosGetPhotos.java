package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.service.method.PhotoSyncModel;
import com.facebook.katana.util.Factory;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class PhotosGetPhotos extends FqlQuery {

   private static final int MAX_PHOTOS = 1000000;
   private static final boolean SIMULATE_FAILURE;
   private static String mTempAlbumId;
   private static ArrayList<FacebookPhoto> mTempPhotos;
   private final String mAlbumId;
   private final boolean mIsLastQuery;
   private final List<FacebookPhoto> mPhotos;


   public PhotosGetPhotos(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5, String var6) {
      String var7 = buildQueryFromSourceQuery(var5, var6);
      super(var1, var2, var3, var7, var4);
      ArrayList var13 = new ArrayList();
      this.mPhotos = var13;
      this.mIsLastQuery = (boolean)1;
      this.mAlbumId = null;
   }

   public PhotosGetPhotos(Context var1, Intent var2, String var3, String var4, String[] var5, long var6, int var8, int var9, ApiMethodListener var10) {
      String var11 = buildQuery(var4, var5, var6, var8, var9);
      super(var1, var2, var3, var11, var10);
      this.mAlbumId = var4;
      ArrayList var17 = new ArrayList();
      this.mPhotos = var17;
      byte var18;
      if(var9 < 0) {
         var18 = 1;
      } else {
         var18 = 0;
      }

      this.mIsLastQuery = (boolean)var18;
      if(var8 == 0) {
         if(this.mAlbumId != null) {
            mTempPhotos = new ArrayList();
            mTempAlbumId = this.mAlbumId;
         }
      }
   }

   private static String buildQuery(String var0, String[] var1, long var2, int var4, int var5) {
      String var8;
      if(var1 != null) {
         StringBuilder var6 = (new StringBuilder()).append("SELECT pid,aid,owner,src_small,src_big,src,caption,created,position FROM photo WHERE ").append("pid IN(");
         String var7 = photoIdsParameterString(var1);
         var8 = var6.append(var7).append(")").toString();
      } else {
         var8 = "SELECT pid,aid,owner,src_small,src_big,src,caption,created,position FROM photo WHERE " + "aid=\'" + var0 + "\'";
      }

      if(65535L != var2) {
         var8 = var8 + " AND owner = " + var2;
      }

      String var9 = var8 + " ORDER BY position ASC";
      String var10;
      if(var5 >= 0) {
         var10 = var9 + " LIMIT " + var4 + "," + var5;
      } else {
         var10 = var9 + " LIMIT " + var4 + "," + 1000000;
      }

      return var10;
   }

   private static String buildQueryFromSourceQuery(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      StringBuilder var3 = var2.append("SELECT pid,aid,owner,src_small,src_big,src,caption,created,position").append(" FROM photo WHERE ").append("pid IN (SELECT ").append(var1).append(" FROM ").append(var0).append(") ORDER BY position ASC");
      return var2.toString();
   }

   private static String listToCommaString(List<?> var0, boolean var1) {
      StringBuffer var2 = new StringBuffer(64);
      boolean var3 = true;
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         if(!var3) {
            StringBuffer var6 = var2.append(",");
         } else {
            var3 = false;
         }

         if(var1) {
            StringBuffer var7 = var2.append("\'").append(var5).append("\'");
         } else {
            var2.append(var5);
         }
      }

      return var2.toString();
   }

   private static String photoIdsParameterString(String[] var0) {
      return listToCommaString(Arrays.asList(var0), (boolean)1);
   }

   protected void dispatchOnOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
      this.mListener.onOperationComplete(var1, var2, var3, var4);
   }

   public List<FacebookPhoto> getPhotos() {
      return this.mPhotos;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         FacebookApiException var4 = new FacebookApiException(var1);
         if(var4.getErrorCode() != -1) {
            throw var4;
         }
      } else {
         JsonToken var5 = JsonToken.START_ARRAY;
         if(var2 != var5) {
            throw new IOException("Malformed JSON");
         } else {
            while(true) {
               JsonToken var6 = JsonToken.END_ARRAY;
               if(var2 == var6) {
                  if(this.mAlbumId != null) {
                     String var11 = this.mAlbumId;
                     String var12 = mTempAlbumId;
                     if(var11.equals(var12)) {
                        ArrayList var13 = mTempPhotos;
                        List var14 = this.mPhotos;
                        var13.addAll(var14);
                     }
                  }

                  boolean var16;
                  if(this.mAlbumId != null) {
                     var16 = true;
                  } else {
                     var16 = false;
                  }

                  boolean var17;
                  if(var16 && this.mIsLastQuery) {
                     var17 = true;
                  } else {
                     var17 = false;
                  }

                  Factory var20;
                  if(var16) {
                     Context var18 = this.mContext;
                     String var19 = this.mAlbumId;
                     var20 = PhotoSyncModel.cursorFactoryForPhotosForAlbum(var18, var19);
                  } else {
                     Context var30 = this.mContext;
                     List var31 = this.mPhotos;
                     var20 = PhotoSyncModel.cursorFactoryForPhotos(var30, var31);
                  }

                  Context var21 = this.mContext;
                  List var22 = this.mPhotos;
                  String var23 = this.mAlbumId;
                  byte var24 = 1;
                  PhotoSyncModel.doSync(var21, var22, var20, (boolean)1, (boolean)var24, (boolean)0, var23);
                  if(!var17) {
                     return;
                  } else {
                     Context var25 = this.mContext;
                     ArrayList var26 = mTempPhotos;
                     String var27 = this.mAlbumId;
                     byte var28 = 1;
                     byte var29 = 1;
                     PhotoSyncModel.doSync(var25, var26, var20, (boolean)1, (boolean)var28, (boolean)var29, var27);
                     return;
                  }
               }

               JsonToken var7 = JsonToken.START_OBJECT;
               if(var2 == var7) {
                  List var8 = this.mPhotos;
                  FacebookPhoto var9 = FacebookPhoto.parseJson(var1);
                  var8.add(var9);
               }

               var2 = var1.nextToken();
            }
         }
      }
   }
}
