package com.facebook.katana.model;

import com.facebook.katana.model.FacebookPhonebookContact;

public class FacebookPhonebookContactUser extends FacebookPhonebookContact {

   public final String imageUrl;


   public FacebookPhonebookContactUser(String var1, String var2, String var3, long var4, String var6) {
      super(var1, 65535L, (boolean)0, var4, var3, var2);
      this.imageUrl = var6;
   }
}
