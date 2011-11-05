package com.facebook.katana.model;

import com.facebook.katana.model.FacebookPhotoTagBase;
import com.facebook.katana.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookPhotoWithTag extends FacebookPhotoTagBase {

   public final long subject;


   public FacebookPhotoWithTag(long var1) {
      this.subject = var1;
   }

   public JSONObject toJSON() {
      JSONObject var3;
      JSONObject var4;
      try {
         JSONObject var1 = new JSONObject();
         String var2 = Long.toString(this.subject);
         var3 = var1.put("tag_uid", var2);
      } catch (JSONException var6) {
         Log.e("", "inconceivable JSON exception", var6);
         var4 = null;
         return var4;
      }

      var4 = var3;
      return var4;
   }
}
