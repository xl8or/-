package com.android.i18n.addressinput;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

class JsoMap extends JSONObject {

   protected JsoMap() {}

   private JsoMap(JSONObject var1, String[] var2) throws JSONException {
      super(var1, var2);
   }

   private JsoMap(JSONTokener var1) throws JSONException {
      super(var1);
   }

   static JsoMap buildJsoMap(String var0) throws JSONException {
      JSONTokener var1 = new JSONTokener(var0);
      return new JsoMap(var1);
   }

   static JsoMap createEmptyJsoMap() {
      return new JsoMap();
   }

   private Object getObject(String var1) throws JSONException {
      return super.get(var1);
   }

   boolean containsKey(String var1) {
      return super.has(var1);
   }

   void delKey(String var1) {
      super.remove(var1);
   }

   public String get(String param1) {
      // $FF: Couldn't be decompiled
   }

   public int getInt(String param1) {
      // $FF: Couldn't be decompiled
   }

   JSONArray getKeys() {
      return super.names();
   }

   JsoMap getObj(String var1) throws ClassCastException, IllegalArgumentException {
      Object var2;
      label36: {
         JSONObject var3;
         ArrayList var5;
         JsoMap var10;
         try {
            var2 = super.get(var1);
            if(!(var2 instanceof JSONObject)) {
               break label36;
            }

            var3 = (JSONObject)var2;
            int var4 = var3.length();
            var5 = new ArrayList(var4);
            Iterator var6 = var3.keys();

            while(var6.hasNext()) {
               Object var7 = var6.next();
               var5.add(var7);
            }
         } catch (JSONException var13) {
            var10 = null;
            return var10;
         }

         String[] var11 = new String[var5.size()];
         String[] var12 = (String[])var5.toArray(var11);
         var10 = new JsoMap(var3, var12);
         return var10;
      }

      if(var2 instanceof Integer) {
         throw new IllegalArgumentException();
      } else {
         throw new ClassCastException();
      }
   }

   String map() throws ClassCastException, IllegalArgumentException {
      StringBuilder var1 = new StringBuilder("JsoMap[\n");
      JSONArray var2 = this.getKeys();
      int var3 = 0;

      while(true) {
         int var4 = var2.length();
         if(var3 >= var4) {
            StringBuilder var11 = var1.append(']');
            return var1.toString();
         }

         String var5;
         try {
            var5 = var2.getString(var3);
         } catch (JSONException var12) {
            throw new RuntimeException(var12);
         }

         StringBuilder var7 = var1.append('(').append(var5).append(':');
         String var8 = this.getObj(var5).string();
         StringBuilder var9 = var7.append(var8).append(')').append('\n');
         ++var3;
      }
   }

   void mergeData(JsoMap param1) {
      // $FF: Couldn't be decompiled
   }

   void put(String var1, String var2) {
      try {
         super.put(var1, var2);
      } catch (JSONException var5) {
         throw new RuntimeException(var5);
      }
   }

   void putInt(String var1, int var2) {
      try {
         super.put(var1, var2);
      } catch (JSONException var5) {
         throw new RuntimeException(var5);
      }
   }

   void putObj(String var1, JSONObject var2) {
      try {
         super.put(var1, var2);
      } catch (JSONException var5) {
         throw new RuntimeException(var5);
      }
   }

   String string() throws ClassCastException, IllegalArgumentException {
      StringBuilder var1 = new StringBuilder("JsoMap[\n");
      JSONArray var2 = this.getKeys();
      int var3 = 0;

      while(true) {
         int var4 = var2.length();
         if(var3 >= var4) {
            StringBuilder var11 = var1.append(']');
            return var1.toString();
         }

         String var5;
         try {
            var5 = var2.getString(var3);
         } catch (JSONException var12) {
            throw new RuntimeException(var12);
         }

         StringBuilder var7 = var1.append('(').append(var5).append(':');
         String var8 = this.get(var5);
         StringBuilder var9 = var7.append(var8).append(')').append('\n');
         ++var3;
      }
   }
}
