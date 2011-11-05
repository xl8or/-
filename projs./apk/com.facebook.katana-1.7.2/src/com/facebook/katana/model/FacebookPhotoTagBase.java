package com.facebook.katana.model;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class FacebookPhotoTagBase {

   public FacebookPhotoTagBase() {}

   public static String encode(List<? extends FacebookPhotoTagBase> var0) {
      JSONArray var1 = new JSONArray();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         FacebookPhotoTagBase var3 = (FacebookPhotoTagBase)var2.next();
         if(var3.toJSON() != null) {
            JSONObject var4 = var3.toJSON();
            var1.put(var4);
         }
      }

      return var1.toString();
   }

   public abstract JSONObject toJSON();
}
