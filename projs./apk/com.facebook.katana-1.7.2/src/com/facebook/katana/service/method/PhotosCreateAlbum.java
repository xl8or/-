package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class PhotosCreateAlbum extends ApiMethod {

   private FacebookAlbum mAlbum;
   private Uri mAlbumUri;


   public PhotosCreateAlbum(Context var1, Intent var2, String var3, String var4, String var5, String var6, String var7, ApiMethodListener var8) {
      String var9 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "photos.createAlbum", var9, var8);
      this.mParams.put("session_key", var3);
      Map var15 = this.mParams;
      StringBuilder var16 = (new StringBuilder()).append("");
      long var17 = System.currentTimeMillis();
      String var19 = var16.append(var17).toString();
      var15.put("call_id", var19);
      this.mParams.put("name", var4);
      if(var5 != null) {
         this.mParams.put("location", var5);
      }

      if(var6 != null) {
         this.mParams.put("description", var6);
      }

      if(var7 != null) {
         this.mParams.put("visible", var7);
      }
   }

   private static Uri insert(Context var0, FacebookAlbum var1) {
      ContentValues var2 = new ContentValues();
      String var3 = var1.getAlbumId();
      var2.put("aid", var3);
      String var4 = var1.getCoverPhotoId();
      var2.put("cover_pid", var4);
      Long var5 = Long.valueOf(var1.getOwner());
      var2.put("owner", var5);
      String var6 = var1.getName();
      var2.put("name", var6);
      Long var7 = Long.valueOf(var1.getCreated());
      var2.put("created", var7);
      Long var8 = Long.valueOf(var1.getModified());
      var2.put("modified", var8);
      String var9 = var1.getDescription();
      var2.put("description", var9);
      String var10 = var1.getLocation();
      var2.put("location", var10);
      Integer var11 = Integer.valueOf(var1.getSize());
      var2.put("size", var11);
      String var12 = var1.getVisibility();
      var2.put("visibility", var12);
      String var13 = var1.getType();
      var2.put("type", var13);
      ContentResolver var14 = var0.getContentResolver();
      Uri var15 = PhotosProvider.ALBUMS_CONTENT_URI;
      return var14.insert(var15, var2);
   }

   public FacebookAlbum getAlbum() {
      return this.mAlbum;
   }

   public Uri getAlbumUri() {
      return this.mAlbumUri;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      FacebookAlbum var2 = new FacebookAlbum(var1);
      this.mAlbum = var2;
      Context var3 = this.mContext;
      FacebookAlbum var4 = this.mAlbum;
      Uri var5 = insert(var3, var4);
      this.mAlbumUri = var5;
   }
}
