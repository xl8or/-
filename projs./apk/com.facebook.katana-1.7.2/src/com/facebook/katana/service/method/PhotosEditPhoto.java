package com.facebook.katana.service.method;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.util.Map;
import org.codehaus.jackson.JsonParser;

public class PhotosEditPhoto extends ApiMethod {

   public PhotosEditPhoto(Context var1, Intent var2, String var3, String var4, String var5, ApiMethodListener var6) {
      String var7 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "photos.editPhoto", var7, var6);
      this.mParams.put("session_key", var3);
      Map var13 = this.mParams;
      StringBuilder var14 = (new StringBuilder()).append("");
      long var15 = System.currentTimeMillis();
      String var17 = var14.append(var15).toString();
      var13.put("call_id", var17);
      this.mParams.put("pid", var4);
      if(var5 != null) {
         this.mParams.put("caption", var5);
      }
   }

   protected void parseJSON(JsonParser var1) {
      Uri var2 = PhotosProvider.PHOTOS_PID_CONTENT_URI;
      String var3 = (String)this.mParams.get("pid");
      Uri var4 = Uri.withAppendedPath(var2, var3);
      ContentValues var5 = new ContentValues();
      String var6 = (String)this.mParams.get("caption");
      var5.put("caption", var6);
      this.mContext.getContentResolver().update(var4, var5, (String)null, (String[])null);
   }
}
