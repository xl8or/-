package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.client.GDataClient;
import com.google.wireless.gdata2.client.QueryParams;
import java.util.Hashtable;
import java.util.Vector;

public class HttpQueryParams extends QueryParams {

   private GDataClient client;
   private Vector names;
   private Hashtable params;


   public HttpQueryParams(GDataClient var1) {
      this.client = var1;
      Vector var2 = new Vector(4);
      this.names = var2;
      Hashtable var3 = new Hashtable(7);
      this.params = var3;
   }

   public void clear() {
      this.names.removeAllElements();
      this.params.clear();
   }

   public String generateQueryUrl(String var1) {
      char var2 = 63;
      StringBuffer var3 = new StringBuffer(var1);
      if(var1.indexOf(var2) >= 0) {
         var2 = 38;
      }

      var3.append(var2);
      int var5 = 0;

      while(true) {
         int var6 = this.names.size();
         if(var5 >= var6) {
            return var3.toString();
         }

         if(var5 > 0) {
            StringBuffer var7 = var3.append('&');
         }

         String var8 = (String)this.names.elementAt(var5);
         String var9 = this.client.encodeUri(var8);
         StringBuffer var10 = var3.append(var9).append('=');
         GDataClient var11 = this.client;
         String var12 = this.getParamValue(var8);
         String var13 = var11.encodeUri(var12);
         var3.append(var13);
         ++var5;
      }
   }

   public String getParamValue(String var1) {
      return (String)this.params.get(var1);
   }

   public void setParamValue(String var1, String var2) {
      if(var2 != null) {
         if(!this.params.containsKey(var1)) {
            this.names.addElement(var1);
         }

         this.params.put(var1, var2);
      } else if(this.params.remove(var1) != null) {
         this.names.removeElement(var1);
      }
   }
}
