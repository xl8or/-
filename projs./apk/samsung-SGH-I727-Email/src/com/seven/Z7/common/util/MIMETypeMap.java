package com.seven.Z7.common.util;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Properties;

public class MIMETypeMap {

   private static final String TAG = "MIMETypeMap";
   private static SoftReference<Properties> mMimeTypesReference;


   private MIMETypeMap() {
      throw new RuntimeException("MIMETypeMap not instantiable");
   }

   public static String getExtension(String var0) {
      String var1;
      if(var0 == null) {
         var1 = "";
      } else {
         String var2 = var0.toLowerCase();
         String var3 = MimeTypeMap.getSingleton().getExtensionFromMimeType(var2);
         if(!TextUtils.isEmpty(var3)) {
            var1 = var3;
         } else {
            Properties var4 = getReference();
            Iterator var5 = var4.keySet().iterator();

            while(true) {
               if(var5.hasNext()) {
                  String var6 = (String)var5.next();
                  String var7 = var4.getProperty(var6);
                  if(!var2.equals(var7)) {
                     continue;
                  }

                  var1 = var6;
                  break;
               }

               var1 = "";
               break;
            }
         }
      }

      return var1;
   }

   public static String getMimeType(String var0) {
      String var1 = var0.toLowerCase();
      String var2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var1);
      String var3;
      if(!TextUtils.isEmpty(var2)) {
         var3 = var2;
      } else {
         String var4 = getReference().getProperty(var1);
         if(var4 != null) {
            var3 = var4;
         } else {
            var3 = "";
         }
      }

      return var3;
   }

   private static Properties getReference() {
      // $FF: Couldn't be decompiled
   }
}
