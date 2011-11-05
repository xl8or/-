package com.facebook.katana.webview;

import android.net.Uri;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class FacebookRpcCall {

   public final String method;
   protected final Uri uri;
   public final UUID uuid;


   public FacebookRpcCall(Uri var1) {
      this.uri = var1;
      List var2 = this.uri.getPathSegments();
      if(var2.size() == 3) {
         UUID var3 = UUID.fromString((String)var2.get(1));
         this.uuid = var3;
      } else {
         this.uuid = null;
      }

      String var4 = var1.getLastPathSegment();
      this.method = var4;
   }

   static String jsComposeFacebookRpcCall(String var0, String var1, UUID var2, UUID var3, String var4, Map<String, Serializable> var5) {
      StringBuilder var6 = new StringBuilder();
      Object[] var7 = new Object[]{var0, var1};
      String var8 = String.format("\'%s://%s/", var7);
      var6.append(var8);
      if(var2 != null) {
         String var10 = var2.toString();
         StringBuilder var11 = var6.append(var10).append("/");
      }

      if(var3 != null) {
         String var12 = var3.toString();
         StringBuilder var13 = var6.append(var12).append("/");
      }

      StringBuilder var14 = var6.append(var4).append("/\'");
      boolean var15 = true;
      Iterator var16 = var5.entrySet().iterator();

      while(var16.hasNext()) {
         Entry var17 = (Entry)var16.next();
         if(var15) {
            StringBuilder var18 = var6.append(" + \'?\' + ");
            var15 = false;
         } else {
            StringBuilder var25 = var6.append(" + \'&\' + ");
         }

         String var19 = (String)var17.getKey();
         Serializable var20 = (Serializable)var17.getValue();
         StringBuilder var21 = var6.append("\'");
         String var22 = Uri.encode(var19);
         StringBuilder var23 = var21.append(var22).append("=\' + ");
         if(var20 instanceof FacebookRpcCall.JsVariable) {
            StringBuilder var24 = var6.append("encodeURIComponent(").append(var20).append(")");
         } else {
            StringBuilder var26 = var6.append("\'");
            String var27 = Uri.encode(var20.toString());
            StringBuilder var28 = var26.append(var27).append("\'");
         }
      }

      return var6.toString();
   }

   public String getParameterByName(String var1) {
      String var2;
      if(this.uri != null) {
         var2 = this.uri.getQueryParameter(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   static class JsVariable implements Serializable {

      private static final long serialVersionUID = 5755809994871655283L;
      private String mVarName;


      public JsVariable(String var1) {
         this.mVarName = var1;
      }

      public String toString() {
         return this.mVarName;
      }
   }
}
