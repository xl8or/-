package com.facebook.katana.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import com.facebook.katana.model.FacebookPhonebookContact;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class PhonebookUtils {

   public PhonebookUtils() {}

   public static List<FacebookPhonebookContact> extractAddressBook(Context var0) {
      HashMap var1 = new HashMap();
      HashSet var2 = new HashSet();
      String[] var3 = new String[]{"_id", "display_name"};
      String[] var4 = new String[]{"contact_id", "data1"};
      String[] var5 = new String[]{"contact_id", "data1"};
      ContentResolver var6 = var0.getContentResolver();
      Uri var7 = Contacts.CONTENT_URI;
      Cursor var8 = var6.query(var7, var3, (String)null, (String[])null, (String)null);
      Uri var9 = Phone.CONTENT_URI;
      Cursor var12 = var6.query(var9, var4, (String)null, (String[])null, (String)null);
      Uri var13 = Email.CONTENT_URI;
      Cursor var16 = var6.query(var13, var5, (String)null, (String[])null, (String)null);

      while(var8.moveToNext()) {
         int var17 = var8.getColumnIndex("_id");
         Long var18 = Long.valueOf(var8.getLong(var17));
         int var19 = var8.getColumnIndex("display_name");
         String var20 = var8.getString(var19);
         long var21 = var18.longValue();
         ArrayList var23 = new ArrayList();
         ArrayList var24 = new ArrayList();
         FacebookPhonebookContact var25 = new FacebookPhonebookContact(var20, var21, var23, var24);
         var1.put(var18, var25);
      }

      var8.close();

      while(var16.moveToNext()) {
         int var27 = var16.getColumnIndex("contact_id");
         Long var28 = Long.valueOf(var16.getLong(var27));
         if(var1.get(var28) != null) {
            int var29 = var16.getColumnIndex("data1");
            String var38 = var16.getString(var29);
            if(!var2.contains(var38)) {
               var2.add(var38);
               boolean var31 = ((FacebookPhonebookContact)var1.get(var28)).emails.add(var38);
            }
         }
      }

      var16.close();

      while(var12.moveToNext()) {
         int var32 = var12.getColumnIndex("contact_id");
         Long var33 = Long.valueOf(var12.getLong(var32));
         if(var1.get(var33) != null) {
            int var34 = var12.getColumnIndex("data1");
            String var39 = var12.getString(var34);
            if(!var2.contains(var39)) {
               var2.add(var39);
               boolean var36 = ((FacebookPhonebookContact)var1.get(var33)).phones.add(var39);
            }
         }
      }

      var12.close();
      Collection var37 = var1.values();
      return new ArrayList(var37);
   }
}
