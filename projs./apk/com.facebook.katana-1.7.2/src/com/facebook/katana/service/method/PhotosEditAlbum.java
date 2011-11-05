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

public class PhotosEditAlbum extends ApiMethod {

   protected String mVisibility;


   public PhotosEditAlbum(Context var1, Intent var2, String var3, String var4, String var5, String var6, String var7, String var8, ApiMethodListener var9) {
      String var10 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "photos.editAlbum", var10, var9);
      this.mParams.put("session_key", var3);
      Map var16 = this.mParams;
      StringBuilder var17 = (new StringBuilder()).append("");
      long var18 = System.currentTimeMillis();
      String var20 = var17.append(var18).toString();
      var16.put("call_id", var20);
      this.mParams.put("name", var5);
      this.mParams.put("aid", var4);
      this.mParams.put("location", var6);
      this.mParams.put("description", var7);
      this.mVisibility = var8;
      if(var8 != null) {
         if(!var8.equals("custom")) {
            this.mParams.put("visible", var8);
         }
      }
   }

   protected void parseJSON(JsonParser var1) {
      ContentValues var2 = new ContentValues();
      String var3 = (String)this.mParams.get("name");
      var2.put("name", var3);
      String var4 = (String)this.mParams.get("description");
      var2.put("description", var4);
      String var5 = (String)this.mParams.get("location");
      var2.put("location", var5);
      String var6 = this.mVisibility;
      var2.put("visibility", var6);
      Uri var7 = PhotosProvider.ALBUMS_AID_CONTENT_URI;
      String var8 = (String)this.mParams.get("aid");
      Uri var9 = Uri.withAppendedPath(var7, var8);
      this.mContext.getContentResolver().update(var9, var2, (String)null, (String[])null);
   }
}
