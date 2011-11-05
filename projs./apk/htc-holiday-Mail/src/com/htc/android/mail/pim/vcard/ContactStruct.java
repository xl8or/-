package com.htc.android.mail.pim.vcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class ContactStruct {

   public List<ContactStruct.IMData> IMList;
   public String anniversary;
   public String birthday;
   public String category;
   public List<String> company;
   public List<ContactStruct.ContactMethod> contactmethodList;
   public String last_update_time;
   public String name;
   public String notes;
   public List<String> orgType;
   public List<ContactStruct.PhoneData> phoneList;
   public byte[] photoBytes;
   public String photoType;
   public List<String> title;


   public ContactStruct() {
      ArrayList var1 = new ArrayList();
      this.orgType = var1;
      ArrayList var2 = new ArrayList();
      this.company = var2;
      ArrayList var3 = new ArrayList();
      this.title = var3;
   }

   public void addContactmethod(String var1, ArrayList<String> var2, String var3, String var4) {
      if(this.contactmethodList == null) {
         ArrayList var5 = new ArrayList();
         this.contactmethodList = var5;
      }

      Iterator var6 = var2.iterator();

      while(var6.hasNext()) {
         String var7 = (String)var6.next();
         ContactStruct.ContactMethod var8 = new ContactStruct.ContactMethod();
         var8.kind = var1;
         var8.data = var7;
         var8.type = var3;
         var8.label = var4;
         this.contactmethodList.add(var8);
      }

   }

   public void addContactmethodCustom(String var1, HashMap<String, String> var2, HashMap<String, String> var3) {
      if(this.contactmethodList == null) {
         ArrayList var4 = new ArrayList();
         this.contactmethodList = var4;
      }

      Iterator var5 = var2.entrySet().iterator();

      while(var5.hasNext()) {
         Entry var6 = (Entry)var5.next();
         ContactStruct.ContactMethod var7 = new ContactStruct.ContactMethod();
         var7.kind = var1;
         String var8 = (String)var6.getValue();
         var7.data = var8;
         String var9 = String.valueOf(0);
         var7.type = var9;
         Object var10 = var6.getKey();
         String var11 = (String)var3.get(var10);
         var7.label = var11;
         this.contactmethodList.add(var7);
      }

   }

   public void addIMList(String var1, String var2) {
      if(this.IMList == null) {
         ArrayList var3 = new ArrayList();
         this.IMList = var3;
      }

      ContactStruct.IMData var4 = new ContactStruct.IMData();
      var4.type = var1;
      var4.data = var2;
      this.IMList.add(var4);
   }

   public void addPhone(ArrayList<String> var1, String var2, String var3) {
      if(this.phoneList == null) {
         ArrayList var4 = new ArrayList();
         this.phoneList = var4;
      }

      Iterator var5 = var1.iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();
         ContactStruct.PhoneData var7 = new ContactStruct.PhoneData();
         var7.data = var6;
         var7.type = var2;
         var7.label = var3;
         this.phoneList.add(var7);
      }

   }

   public void addPhoneCustom(HashMap<String, String> var1, HashMap<String, String> var2) {
      if(this.phoneList == null) {
         ArrayList var3 = new ArrayList();
         this.phoneList = var3;
      }

      Iterator var4 = var1.entrySet().iterator();

      while(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         ContactStruct.PhoneData var6 = new ContactStruct.PhoneData();
         String var7 = (String)var5.getValue();
         var6.data = var7;
         String var8 = String.valueOf(0);
         var6.type = var8;
         Object var9 = var5.getKey();
         String var10 = (String)var2.get(var9);
         var6.label = var10;
         this.phoneList.add(var6);
      }

   }

   public static class PhoneData {

      public String data;
      public String label;
      public String type;


      public PhoneData() {}

      public boolean equals(Object var1) {
         boolean var2;
         if(this == var1) {
            var2 = true;
         } else if(var1 instanceof ContactStruct.PhoneData) {
            ContactStruct.PhoneData var3 = (ContactStruct.PhoneData)var1;
            String var4 = this.type;
            String var5 = var3.type;
            if(var4 == var5) {
               String var6 = this.data;
               String var7 = var3.data;
               if(var6 == var7) {
                  String var8 = this.label;
                  String var9 = var3.label;
                  if(var8 == var9) {
                     var2 = true;
                     return var2;
                  }
               }
            }

            var2 = false;
         } else {
            var2 = false;
         }

         return var2;
      }
   }

   public static class IMData {

      public String data;
      public String type;


      public IMData() {}

      public boolean equals(Object var1) {
         boolean var2;
         if(this == var1) {
            var2 = true;
         } else if(var1 instanceof ContactStruct.IMData) {
            ContactStruct.IMData var3 = (ContactStruct.IMData)var1;
            String var4 = this.type;
            String var5 = var3.type;
            if(var4 == var5) {
               String var6 = this.data;
               String var7 = var3.data;
               if(var6 == var7) {
                  var2 = true;
                  return var2;
               }
            }

            var2 = false;
         } else {
            var2 = false;
         }

         return var2;
      }
   }

   public static class ContactMethod {

      public String data;
      public String kind;
      public String label;
      public String type;


      public ContactMethod() {}

      public boolean equals(Object var1) {
         boolean var2;
         if(this == var1) {
            var2 = true;
         } else if(var1 instanceof ContactStruct.ContactMethod) {
            ContactStruct.ContactMethod var3 = (ContactStruct.ContactMethod)var1;
            String var4 = this.type;
            String var5 = var3.type;
            if(var4 == var5) {
               String var6 = this.data;
               String var7 = var3.data;
               if(var6 == var7) {
                  String var8 = this.label;
                  String var9 = var3.label;
                  if(var8 == var9) {
                     String var10 = this.kind;
                     String var11 = var3.kind;
                     if(var10 == var11) {
                        var2 = true;
                        return var2;
                     }
                  }
               }
            }

            var2 = false;
         } else {
            var2 = false;
         }

         return var2;
      }
   }
}
