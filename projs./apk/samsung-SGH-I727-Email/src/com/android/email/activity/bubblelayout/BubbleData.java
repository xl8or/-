package com.android.email.activity.bubblelayout;

import android.text.TextUtils;
import android.text.util.Rfc822Token;
import android.text.util.Rfc822Tokenizer;
import com.android.email.ContactInfoCache;
import com.android.email.mail.Address;

public class BubbleData {

   private String mAddress;
   private String mContactID;
   private boolean mIsFromContact;
   private boolean mIsValidAddress;
   private String mName;


   public BubbleData(String var1) {
      this(var1, (String)null, (String)null, (boolean)0);
   }

   public BubbleData(String var1, String var2) {
      this(var1, (String)null, var2, (boolean)0);
   }

   public BubbleData(String var1, String var2, String var3, boolean var4) {
      this.mAddress = var1;
      this.mName = var2;
      this.mContactID = var3;
      if(Address.isAllValid(this.mAddress)) {
         this.mIsValidAddress = (boolean)1;
      } else {
         this.mIsValidAddress = (boolean)0;
      }

      if(TextUtils.isEmpty(this.mName)) {
         Rfc822Token[] var5 = Rfc822Tokenizer.tokenize(this.mAddress);
         if(var5.length > 0) {
            String var6 = var5[0].getAddress().toLowerCase();
            this.mAddress = var6;
            String var7 = var5[0].getName();
            this.mName = var7;
         }
      }

      String var8 = this.mAddress;
      String var9 = this.mContactID;
      ContactInfoCache.CacheEntry var10 = getContactInfoCacheFromContact(var8, var9, (boolean)0);
      if(var10 != null && var10.name != null) {
         this.mIsFromContact = (boolean)1;
         if(TextUtils.isEmpty(this.mName)) {
            String var11 = var10.name;
            this.mName = var11;
         }

         if(this.mContactID == null) {
            String var12 = Long.toString(var10.data_id);
            this.mContactID = var12;
         }
      } else {
         this.mIsFromContact = (boolean)0;
      }
   }

   private static ContactInfoCache.CacheEntry getContactInfoCacheFromContact(String param0, String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public String getAddress() {
      return this.mAddress;
   }

   public String getName() {
      return this.mName;
   }

   public boolean isFromContact() {
      return this.mIsFromContact;
   }

   public boolean isValidAddress() {
      return this.mIsValidAddress;
   }

   public void refreshContactInfo() {
      String var1 = this.mAddress;
      String var2 = this.mContactID;
      ContactInfoCache.CacheEntry var3 = getContactInfoCacheFromContact(var1, var2, (boolean)1);
      if(var3 != null && var3.name != null) {
         this.mIsFromContact = (boolean)1;
         String var4 = var3.name;
         this.mName = var4;
         if(this.mContactID == null) {
            String var5 = Long.toString(var3.data_id);
            this.mContactID = var5;
         }
      } else {
         this.mIsFromContact = (boolean)0;
      }
   }
}
