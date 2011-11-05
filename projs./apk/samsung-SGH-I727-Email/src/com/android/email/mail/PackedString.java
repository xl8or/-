package com.android.email.mail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class PackedString {

   private static final char DELIMITER_ELEMENT = '\u0001';
   private static final char DELIMITER_TAG = '\u0002';
   private static final HashMap<String, String> EMPTY_MAP = new HashMap();
   private HashMap<String, String> mExploded;
   private String mString;


   public PackedString(String var1) {
      this.mString = var1;
      this.mExploded = null;
   }

   private static HashMap<String, String> explode(String var0) {
      HashMap var1;
      if(var0 != null && var0.length() != 0) {
         HashMap var2 = new HashMap();
         int var3 = var0.length();
         int var4 = 0;

         int var6;
         for(int var5 = var0.indexOf(2); var4 < var3; var4 = var6 + 1) {
            var6 = var0.indexOf(1, var4);
            if(var6 == -1) {
               ;
            }

            String var8;
            String var9;
            if(var5 != -1 && var6 > var5) {
               var8 = var0.substring(var4, var5);
               int var11 = var5 + 1;
               var9 = var0.substring(var11, var6);
               int var12 = var6 + 1;
               var5 = var0.indexOf(2, var12);
            } else {
               var8 = var0.substring(var4, var6);
               var9 = Integer.toString(var2.size());
            }

            var2.put(var9, var8);
         }

         var1 = var2;
      } else {
         var1 = EMPTY_MAP;
      }

      return var1;
   }

   public String get(String var1) {
      if(this.mExploded == null) {
         HashMap var2 = explode(this.mString);
         this.mExploded = var2;
      }

      return (String)this.mExploded.get(var1);
   }

   public Map<String, String> unpack() {
      if(this.mExploded == null) {
         HashMap var1 = explode(this.mString);
         this.mExploded = var1;
      }

      HashMap var2 = this.mExploded;
      return new HashMap(var2);
   }

   public static class Builder {

      HashMap<String, String> mMap;


      public Builder() {
         HashMap var1 = new HashMap();
         this.mMap = var1;
      }

      public Builder(String var1) {
         HashMap var2 = PackedString.explode(var1);
         this.mMap = var2;
      }

      public String get(String var1) {
         return (String)this.mMap.get(var1);
      }

      public void put(String var1, String var2) {
         if(var2 == null) {
            this.mMap.remove(var1);
         } else {
            this.mMap.put(var1, var2);
         }
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         Iterator var2 = this.mMap.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            if(var1.length() > 0) {
               StringBuilder var4 = var1.append('\u0001');
            }

            String var5 = (String)var3.getValue();
            var1.append(var5);
            StringBuilder var7 = var1.append('\u0002');
            String var8 = (String)var3.getKey();
            var1.append(var8);
         }

         return var1.toString();
      }
   }
}
