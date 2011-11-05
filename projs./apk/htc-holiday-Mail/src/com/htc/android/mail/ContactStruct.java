package com.htc.android.mail;

import java.util.ArrayList;
import java.util.List;

public class ContactStruct {

   public String company;
   public List<ContactStruct.ContactMethod> contactmethodList;
   public String name;
   public String notes;
   public List<ContactStruct.PhoneData> phoneList;
   public byte[] photoBytes;
   public String photoType;
   public String title;


   public ContactStruct() {}

   public void addContactmethod(String var1, String var2, String var3, String var4) {
      if(this.contactmethodList == null) {
         ArrayList var5 = new ArrayList();
         this.contactmethodList = var5;
      }

      ContactStruct.ContactMethod var6 = new ContactStruct.ContactMethod();
      var6.kind = var1;
      var6.data = var2;
      var6.type = var3;
      var6.label = var4;
      this.contactmethodList.add(var6);
   }

   public void addPhone(String var1, String var2, String var3) {
      if(this.phoneList == null) {
         ArrayList var4 = new ArrayList();
         this.phoneList = var4;
      }

      ContactStruct.PhoneData var5 = new ContactStruct.PhoneData();
      var5.data = var1;
      var5.type = var2;
      var5.label = var3;
      this.phoneList.add(var5);
   }

   public static class ContactMethod {

      public String data;
      public String kind;
      public String label;
      public String type;


      public ContactMethod() {}
   }

   public static class PhoneData {

      public String data;
      public String label;
      public String type;


      public PhoneData() {}
   }
}
