package com.facebook.katana.model;

import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookPhonebookContact extends JMCachingDictDestination {

   @JMAutogen.InferredType(
      jsonFieldName = "email"
   )
   public final String email;
   public List<String> emails;
   @JMAutogen.InferredType(
      jsonFieldName = "is_friend"
   )
   public final boolean isFriend;
   @JMAutogen.InferredType(
      jsonFieldName = "name"
   )
   public String name;
   @JMAutogen.InferredType(
      jsonFieldName = "cell"
   )
   public final String phone;
   public List<String> phones;
   @JMAutogen.InferredType(
      jsonFieldName = "record_id"
   )
   public final long recordId;
   @JMAutogen.InferredType(
      jsonFieldName = "uid"
   )
   public final long userId;


   private FacebookPhonebookContact() {
      this.name = null;
      this.recordId = 65535L;
      this.isFriend = (boolean)0;
      this.userId = 65535L;
      this.phone = null;
      this.phones = null;
      this.email = null;
      this.emails = null;
   }

   public FacebookPhonebookContact(String var1, long var2, List<String> var4, List<String> var5) {
      this.name = var1;
      this.recordId = var2;
      this.emails = var4;
      if(this.emails != null && this.emails.size() > 0) {
         String var6 = (String)this.emails.get(0);
         this.email = var6;
      } else {
         this.email = null;
      }

      this.phones = var5;
      if(this.phones != null && this.phones.size() > 0) {
         String var7 = (String)this.phones.get(0);
         this.phone = var7;
      } else {
         this.phone = null;
      }

      this.userId = 65535L;
      this.isFriend = (boolean)0;
   }

   protected FacebookPhonebookContact(String var1, long var2, boolean var4, long var5, String var7, String var8) {
      ArrayList var9 = new ArrayList();
      var9.add(var8);
      ArrayList var11 = new ArrayList();
      var11.add(var7);
      this.name = var1;
      this.recordId = var2;
      this.isFriend = var4;
      this.userId = var5;
      this.phone = var7;
      this.phones = var11;
      this.email = var8;
      this.emails = var9;
   }

   public static String jsonEncode(List<FacebookPhonebookContact> var0) {
      String var22;
      String var24;
      try {
         JSONArray var1 = new JSONArray();
         Iterator var2 = var0.iterator();

         while(true) {
            if(!var2.hasNext()) {
               var22 = var1.toString();
               break;
            }

            FacebookPhonebookContact var25 = (FacebookPhonebookContact)var2.next();
            JSONObject var3 = new JSONObject();
            if(var25.name != null) {
               String var4 = var25.name;
               var3.put("name", var4);
            }

            List var6 = var25.emails;
            if(var6 != null && var6.size() > 0) {
               JSONArray var7 = new JSONArray();
               Iterator var8 = var6.iterator();

               while(var8.hasNext()) {
                  String var9 = (String)var8.next();
                  var7.put(var9);
               }

               var3.put("emails", var7);
            }

            var6 = var25.phones;
            if(var6 != null && var6.size() > 0) {
               JSONArray var27 = new JSONArray();
               Iterator var26 = var6.iterator();

               while(var26.hasNext()) {
                  String var16 = (String)var26.next();
                  var27.put(var16);
               }

               var3.put("phones", var27);
            }

            if(var25.recordId != 65535L) {
               String var19 = String.valueOf(var25.recordId);
               var3.put("record_id", var19);
            }

            var1.put(var3);
         }
      } catch (JSONException var23) {
         Object[] var12 = new Object[1];
         String var13 = var23.getMessage();
         var12[0] = var13;
         String var14 = String.format("How do we get a JSONException when *encoding* data? %s", var12);
         Log.e("JMCachingDictDestination", var14);
         var24 = "";
         return var24;
      }

      var24 = var22;
      return var24;
   }

   public String getContactAddress() {
      String var1 = "";
      if(this.email != null) {
         var1 = this.email;
      } else if(this.phone != null) {
         var1 = this.phone;
      }

      return var1;
   }
}
