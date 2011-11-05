package com.facebook.katana.model;

import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.types.JMString;
import java.util.List;
import java.util.Map;

public class FacebookUserFull extends FacebookUser {

   @JMAutogen.ExplicitType(
      jsonFieldName = "about_me",
      type = StringUtils.JMNulledString.class
   )
   public final String mAboutMe = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "activities",
      type = FacebookUserFull.JMTrimWhiteString.class
   )
   public final String mActivities = null;
   @JMAutogen.ListType(
      jsonFieldName = "affiliations",
      listElementTypes = {FacebookUserFull.Affiliation.class}
   )
   public final List<FacebookUserFull.Affiliation> mAffiliations = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "birthday",
      type = StringUtils.JMNulledString.class
   )
   public final String mBirthday = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "profile_blurb",
      type = StringUtils.JMNulledString.class
   )
   public final String mBlurb = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "books",
      type = StringUtils.JMNulledString.class
   )
   public final String mBooks = null;
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
   public final String mCurrentLocation = null;
   @JMAutogen.InferredType(
      jsonFieldName = "current_location"
   )
   private final Map<String, Object> mCurrentLocation_internal = null;
   public final String mHometownLocation = null;
   @JMAutogen.InferredType(
      jsonFieldName = "hometown_location"
   )
   private final Map<String, Object> mHometownLocation_internal = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "interests",
      type = StringUtils.JMNulledString.class
   )
   public final String mInterests = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "pic",
      type = StringUtils.JMNulledString.class
   )
   public final String mLargePic = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "movies",
      type = StringUtils.JMNulledString.class
   )
   public final String mMovies = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "music",
      type = StringUtils.JMNulledString.class
   )
   public final String mMusic = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "other_phone",
      type = StringUtils.JMNulledString.class
   )
   public final String mOtherPhone = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "political",
      type = StringUtils.JMNulledString.class
   )
   public final String mPoliticalViews = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "quotes",
      type = FacebookUserFull.JMTrimWhiteAndNLString.class
   )
   public final String mQuotes = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "relationship_status",
      type = StringUtils.JMNulledString.class
   )
   public final String mRelationshipStatus = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "religion",
      type = StringUtils.JMNulledString.class
   )
   public final String mReligion = null;
   protected FacebookUser mSignificantOther;
   @JMAutogen.InferredType(
      jsonFieldName = "significant_other_id"
   )
   public final long mSignificantOtherId = 65535L;
   @JMAutogen.ExplicitType(
      jsonFieldName = "tv",
      type = StringUtils.JMNulledString.class
   )
   public final String mTv = null;
   @JMAutogen.ExplicitType(
      jsonFieldName = "profile_url",
      type = StringUtils.JMNulledString.class
   )
   public final String mUrl = null;


   private FacebookUserFull() {}

   protected static String parse_location(Map<String, Object> var0) {
      StringBuilder var1 = new StringBuilder();
      Object var2 = var0.get("city");
      Object var3 = var0.get("state");
      String var5;
      if(var2 != null && var3 != null) {
         StringBuilder var4 = var1.append(var2).append(", ").append(var3);
         var5 = var1.toString();
      } else if(var2 != null) {
         var5 = var2.toString();
      } else if(var3 != null) {
         var5 = var3.toString();
      } else {
         var5 = null;
      }

      return var5;
   }

   protected static String trimWhite(String var0, boolean var1) {
      String var2;
      if(var1) {
         var2 = var0.replaceAll("\n", ", ");
      } else {
         var2 = var0.replaceAll("[\r||\f]", "");
      }

      StringBuffer var3 = new StringBuffer(128);
      int var4 = 0;

      char var6;
      while(true) {
         int var5 = var2.length();
         if(var4 >= var5) {
            break;
         }

         var6 = var2.charAt(var4);
         if(var6 != 32 && var6 != 44 && var6 != 10) {
            int var7 = var2.length();
            CharSequence var8 = var2.subSequence(var4, var7);
            var3.append(var8);
            break;
         }

         ++var4;
      }

      for(int var10 = var3.length() - 1; var10 >= 0; var10 += -1) {
         var6 = var3.charAt(var10);
         if(var6 != 32 && var6 != 44 && var6 != 10) {
            break;
         }

         var3.deleteCharAt(var10);
      }

      return var3.toString();
   }

   public FacebookUser getSignificantOther() {
      return this.mSignificantOther;
   }

   protected void postprocess() throws JMException {
      if(this.mCurrentLocation_internal != null) {
         String var1 = parse_location(this.mCurrentLocation_internal);
         this.setString("mCurrentLocation", var1);
      }

      if(this.mHometownLocation_internal != null) {
         String var2 = parse_location(this.mHometownLocation_internal);
         this.setString("mHometownLocation", var2);
      }
   }

   public void setSignificantOther(FacebookUser var1) {
      this.mSignificantOther = var1;
   }

   public static class JMTrimWhiteAndNLString extends JMString {

      public JMTrimWhiteAndNLString() {}

      public String formatString(String var1) {
         String var2 = FacebookUserFull.trimWhite(var1, (boolean)0);
         String var3;
         if(var2.length() == 0) {
            var3 = null;
         } else {
            var3 = var2;
         }

         return var3;
      }
   }

   public static class Affiliation extends JMCachingDictDestination {

      @JMAutogen.InferredType(
         jsonFieldName = "name"
      )
      public final String mAffiliationName = null;
      @JMAutogen.InferredType(
         jsonFieldName = "status"
      )
      public final String mStatus = null;
      @JMAutogen.InferredType(
         jsonFieldName = "type"
      )
      public final String mType = null;


      private Affiliation() {}
   }

   public static class JMTrimWhiteString extends JMString {

      public JMTrimWhiteString() {}

      public String formatString(String var1) {
         String var2 = FacebookUserFull.trimWhite(var1, (boolean)1);
         String var3;
         if(var2.length() == 0) {
            var3 = null;
         } else {
            var3 = var2;
         }

         return var3;
      }
   }
}
