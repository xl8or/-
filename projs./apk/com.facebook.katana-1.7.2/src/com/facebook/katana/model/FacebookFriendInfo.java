package com.facebook.katana.model;

import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Utils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FacebookFriendInfo extends FacebookUser {

   @JMAutogen.ExplicitType(
      jsonFieldName = "birthday_date",
      type = StringUtils.JMNulledString.class
   )
   private final String mBirthday = null;
   public final int mBirthdayDay = -1;
   public final int mBirthdayMonth = -1;
   public final int mBirthdayYear = -1;
   @JMAutogen.ExplicitType(
      jsonFieldName = "cell",
      type = StringUtils.JMNulledString.class
   )
   public final String mCellPhone = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "contact_email",
      type = StringUtils.JMNulledString.class
   )
   public final String mContactEmail = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "other_phone",
      type = StringUtils.JMNulledString.class
   )
   public final String mOtherPhone = null;


   public FacebookFriendInfo() {}

   public long computeHash() {
      Object[] var1 = new Object[10];
      Long var2 = Long.valueOf(this.mUserId);
      var1[0] = var2;
      String var3 = this.mFirstName;
      var1[1] = var3;
      String var4 = this.mLastName;
      var1[2] = var4;
      String var5 = this.mImageUrl;
      var1[3] = var5;
      String var6 = this.mCellPhone;
      var1[4] = var6;
      String var7 = this.mOtherPhone;
      var1[5] = var7;
      String var8 = this.mContactEmail;
      var1[6] = var8;
      Integer var9 = Integer.valueOf(this.mBirthdayMonth);
      var1[7] = var9;
      Integer var10 = Integer.valueOf(this.mBirthdayDay);
      var1[8] = var10;
      Integer var11 = Integer.valueOf(this.mBirthdayYear);
      var1[9] = var11;
      return Utils.hashItemsLong(var1);
   }

   public boolean hasPhoneNumber() {
      boolean var1;
      if(this.mCellPhone == null && this.mOtherPhone == null) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   protected void postprocess() throws JMException {
      if(this.mBirthday != null) {
         Locale var1 = Locale.US;
         SimpleDateFormat var2 = new SimpleDateFormat("M/d/y", var1);
         boolean var3 = false;
         String var4 = this.mBirthday;
         ParsePosition var5 = new ParsePosition(0);
         Date var6 = var2.parse(var4, var5);
         if(var6 != null) {
            var3 = true;
         } else {
            Locale var13 = Locale.US;
            SimpleDateFormat var14 = new SimpleDateFormat("M/d", var13);
            String var15 = this.mBirthday;
            ParsePosition var16 = new ParsePosition(0);
            var6 = var14.parse(var15, var16);
         }

         if(var6 != null) {
            long var7 = (long)(var6.getMonth() + 1);
            this.setLong("mBirthdayMonth", var7);
            long var9 = (long)var6.getDate();
            this.setLong("mBirthdayDay", var9);
            if(var3) {
               long var11 = (long)(var6.getYear() + 1900);
               this.setLong("mBirthdayYear", var11);
            }
         }
      }
   }
}
