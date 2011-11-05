package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.util.Map;
import org.codehaus.jackson.JsonParser;

public class PhotosDeleteAlbum extends ApiMethod {

   private final long mOwnerId;


   public PhotosDeleteAlbum(Context var1, Intent var2, String var3, long var4, String var6, ApiMethodListener var7) {
      String var8 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "photos.deleteAlbum", var8, var7);
      this.mOwnerId = var4;
      this.mParams.put("session_key", var3);
      Map var14 = this.mParams;
      StringBuilder var15 = (new StringBuilder()).append("");
      long var16 = System.currentTimeMillis();
      String var18 = var15.append(var16).toString();
      var14.put("call_id", var18);
      this.mParams.put("aid", var6);
   }

   protected void parseJSON(JsonParser var1) {
      Uri var2 = PhotosProvider.ALBUMS_OWNER_CONTENT_URI;
      String var3 = Long.toString(this.mOwnerId);
      Uri var4 = Uri.withAppendedPath(var2, var3);
      ContentResolver var5 = this.mContext.getContentResolver();
      String[] var6 = new String[1];
      String var7 = (String)this.mParams.get("aid");
      var6[0] = var7;
      var5.delete(var4, "aid IN(?)", var6);
   }
}
