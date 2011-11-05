package com.facebook.katana;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import com.facebook.katana.ProfileInfoAdapter;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.model.FacebookUserFull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserInfoAdapter extends ProfileInfoAdapter {

   static Map<String, Integer> affiliationStringMap = new HashMap();
   protected static Map<String, int[]> relationship_i18n_decoder;
   protected final boolean mLimitedInfo;


   static {
      Map var0 = affiliationStringMap;
      Integer var1 = Integer.valueOf(2131361792);
      var0.put("college", var1);
      Map var3 = affiliationStringMap;
      Integer var4 = Integer.valueOf(2131361793);
      var3.put("high school", var4);
      Map var6 = affiliationStringMap;
      Integer var7 = Integer.valueOf(2131361795);
      var6.put("regional", var7);
      Map var9 = affiliationStringMap;
      Integer var10 = Integer.valueOf(2131361796);
      var9.put("test", var10);
      Map var12 = affiliationStringMap;
      Integer var13 = Integer.valueOf(2131361794);
      var12.put("work", var13);
      relationship_i18n_decoder = new UserInfoAdapter.1();
      Map var15 = relationship_i18n_decoder;
      int[] var16 = new int[]{2131362161};
      var15.put("Single", var16);
      Map var18 = relationship_i18n_decoder;
      int[] var19 = new int[]{2131362152, 2131362157};
      var18.put("In a Relationship", var19);
      Map var21 = relationship_i18n_decoder;
      int[] var22 = new int[]{2131362158, 2131362159};
      var21.put("Married", var22);
      Map var24 = relationship_i18n_decoder;
      int[] var25 = new int[]{2131362150, 2131362151};
      var24.put("Engaged", var25);
      Map var27 = relationship_i18n_decoder;
      int[] var28 = new int[]{2131362153, 2131362154};
      var27.put("It\'s Complicated", var28);
      Map var30 = relationship_i18n_decoder;
      int[] var31 = new int[]{2131362155, 2131362156};
      var30.put("In an Open Relationship", var31);
      Map var33 = relationship_i18n_decoder;
      int[] var34 = new int[]{2131362162};
      var33.put("widowed", var34);
      Map var36 = relationship_i18n_decoder;
      int[] var37 = new int[]{2131362160};
      var36.put("separated", var37);
      Map var39 = relationship_i18n_decoder;
      int[] var40 = new int[]{2131362147};
      var39.put("divorced", var40);
      Map var42 = relationship_i18n_decoder;
      int[] var43 = new int[]{2131362145, 2131362146};
      var42.put("In a civil union", var43);
      Map var45 = relationship_i18n_decoder;
      int[] var46 = new int[]{2131362148, 2131362149};
      var45.put("In a domestic partnership", var46);
   }

   public UserInfoAdapter(Context var1, StreamPhotosCache var2, boolean var3, boolean var4) {
      super(var1, var2, var3);
      this.mLimitedInfo = var4;
   }

   protected static String formatRelationshipStatusString(Context var0, FacebookUserFull var1) {
      String var2 = var1.mRelationshipStatus;
      String var4;
      if(var2 != null) {
         int[] var3 = (int[])relationship_i18n_decoder.get(var2);
         if(var3 == null) {
            var4 = var2;
         } else {
            FacebookUser var5 = var1.getSignificantOther();
            if(var5 == null) {
               int var6 = var3[0];
               var4 = var0.getString(var6);
            } else {
               int var7 = var3[1];
               Object[] var8 = new Object[1];
               String var9 = var5.getDisplayName();
               var8[0] = var9;
               var4 = var0.getString(var7, var8);
            }
         }
      } else {
         var4 = null;
      }

      return var4;
   }

   public int getItemViewType(int var1) {
      byte var2;
      switch(((ProfileInfoAdapter.Item)this.mItems.get(var1)).mType) {
      case 0:
         var2 = 0;
         break;
      case 1:
      default:
         var2 = 3;
         break;
      case 2:
      case 3:
         var2 = 1;
         break;
      case 4:
         var2 = 2;
      }

      return var2;
   }

   public int getViewTypeCount() {
      return 4;
   }

   public void setUserInfo(FacebookUserFull var1, boolean var2) {
      this.mItems.clear();
      if(this.mShowProfilePhoto) {
         List var3 = this.mItems;
         String var4 = var1.mDisplayName;
         String var5 = var1.mBlurb;
         String var6 = var1.mLargePic;
         ProfileInfoAdapter.Item var7 = new ProfileInfoAdapter.Item(0, var4, var5, var6);
         var3.add(var7);
      }

      if(this.mLimitedInfo && !var2) {
         String var9 = var1.mDisplayName;
         if(var9 == null || var9.length() == 0) {
            var9 = this.mContext.getString(2131361895);
         }

         List var10 = this.mItems;
         Context var11 = this.mContext;
         Object[] var12 = new Object[]{var9};
         String var13 = var11.getString(2131362134, var12);
         ProfileInfoAdapter.Item var14 = new ProfileInfoAdapter.Item(1, var9, var13);
         var10.add(var14);
      }

      if(var1.mCellPhone != null) {
         List var16 = this.mItems;
         String var17 = this.mContext.getString(2131362123);
         String var18 = PhoneNumberUtils.formatNumber(var1.mCellPhone);
         ProfileInfoAdapter.Item var19 = new ProfileInfoAdapter.Item(2, var17, var18);
         var16.add(var19);
      }

      if(var1.mOtherPhone != null) {
         List var21 = this.mItems;
         String var22 = this.mContext.getString(2131362140);
         String var23 = PhoneNumberUtils.formatNumber(var1.mOtherPhone);
         ProfileInfoAdapter.Item var24 = new ProfileInfoAdapter.Item(2, var22, var23);
         var21.add(var24);
      }

      if(var1.mContactEmail != null) {
         List var26 = this.mItems;
         String var27 = this.mContext.getString(2131362125);
         String var28 = var1.mContactEmail;
         ProfileInfoAdapter.Item var29 = new ProfileInfoAdapter.Item(3, var27, var28);
         var26.add(var29);
      }

      List var31 = var1.mAffiliations;
      if(var31 != null && var31.size() > 0) {
         StringBuilder var32 = new StringBuilder(64);
         StringBuilder var33 = new StringBuilder;
         byte var35 = 64;
         var33.<init>(var35);
         Iterator var36 = var31.iterator();

         while(var36.hasNext()) {
            FacebookUserFull.Affiliation var37 = (FacebookUserFull.Affiliation)var36.next();
            if(var37.mType.equals("work")) {
               if(var33.length() > 0) {
                  String var39 = ", ";
                  var33.append(var39);
               }

               String var41 = var37.mAffiliationName;
               StringBuilder var44 = var33.append(var41);
            } else {
               if(var32.length() > 0) {
                  StringBuilder var45 = var32.append("\n");
               }

               Map var46 = affiliationStringMap;
               String var47 = var37.mType;
               Integer var48 = (Integer)var46.get(var47);
               String var51;
               if(var48 != null) {
                  Context var49 = this.mContext;
                  int var50 = var48.intValue();
                  var51 = var49.getString(var50);
               } else {
                  var51 = var37.mType;
               }

               StringBuilder var52 = var32.append(var51).append(": ");
               String var53 = var37.mAffiliationName;
               var52.append(var53);
            }
         }

         if(var32.length() > 0) {
            List var55 = this.mItems;
            String var56 = this.mContext.getString(2131362120);
            String var57 = var32.toString();
            ProfileInfoAdapter.Item var58 = new ProfileInfoAdapter.Item(1, var56, var57);
            var55.add(var58);
         }

         if(var33.length() > 0) {
            List var60 = this.mItems;
            String var61 = this.mContext.getString(2131361794);
            String var62 = var33.toString();
            ProfileInfoAdapter.Item var63 = new ProfileInfoAdapter.Item(1, var61, var62);
            var60.add(var63);
         }
      }

      if(var1.mBirthday != null) {
         List var65 = this.mItems;
         String var66 = this.mContext.getString(2131362121);
         String var67 = var1.mBirthday;
         ProfileInfoAdapter.Item var68 = new ProfileInfoAdapter.Item(1, var66, var67);
         var65.add(var68);
      }

      Context var70 = this.mContext;
      String var72 = formatRelationshipStatusString(var70, var1);
      if(var72 != null) {
         if(var1.getSignificantOther() == null) {
            List var73 = this.mItems;
            String var74 = this.mContext.getString(2131362144);
            ProfileInfoAdapter.Item var75 = new ProfileInfoAdapter.Item(1, var74, var72);
            var73.add(var75);
         } else {
            List var132 = this.mItems;
            String var133 = this.mContext.getString(2131362144);
            String var134 = var1.getSignificantOther().mImageUrl;
            long var135 = var1.getSignificantOther().mUserId;
            ProfileInfoAdapter.Item var137 = new ProfileInfoAdapter.Item(4, var133, var72, var134, var135);
            var132.add(var137);
         }
      }

      if(var1.mCurrentLocation != null) {
         List var77 = this.mItems;
         String var78 = this.mContext.getString(2131362124);
         String var79 = var1.mCurrentLocation;
         ProfileInfoAdapter.Item var80 = new ProfileInfoAdapter.Item(1, var78, var79);
         var77.add(var80);
      }

      if(var1.mHometownLocation != null) {
         List var82 = this.mItems;
         String var83 = this.mContext.getString(2131362129);
         String var84 = var1.mHometownLocation;
         ProfileInfoAdapter.Item var85 = new ProfileInfoAdapter.Item(1, var83, var84);
         var82.add(var85);
      }

      if(var1.mReligion != null) {
         List var87 = this.mItems;
         String var88 = this.mContext.getString(2131362163);
         String var89 = var1.mReligion;
         ProfileInfoAdapter.Item var90 = new ProfileInfoAdapter.Item(1, var88, var89);
         var87.add(var90);
      }

      if(var1.mPoliticalViews != null) {
         List var92 = this.mItems;
         String var93 = this.mContext.getString(2131362142);
         String var94 = var1.mPoliticalViews;
         ProfileInfoAdapter.Item var95 = new ProfileInfoAdapter.Item(1, var93, var94);
         var92.add(var95);
      }

      if(var1.mActivities != null) {
         List var97 = this.mItems;
         String var98 = this.mContext.getString(2131362116);
         String var99 = var1.mActivities;
         ProfileInfoAdapter.Item var100 = new ProfileInfoAdapter.Item(1, var98, var99);
         var97.add(var100);
      }

      if(var1.mInterests != null) {
         List var102 = this.mItems;
         String var103 = this.mContext.getString(2131362133);
         String var104 = var1.mInterests;
         ProfileInfoAdapter.Item var105 = new ProfileInfoAdapter.Item(1, var103, var104);
         var102.add(var105);
      }

      if(var1.mTv != null) {
         List var107 = this.mItems;
         String var108 = this.mContext.getString(2131362166);
         String var109 = var1.mTv;
         ProfileInfoAdapter.Item var110 = new ProfileInfoAdapter.Item(1, var108, var109);
         var107.add(var110);
      }

      if(var1.mMovies != null) {
         List var112 = this.mItems;
         String var113 = this.mContext.getString(2131362137);
         String var114 = var1.mMovies;
         ProfileInfoAdapter.Item var115 = new ProfileInfoAdapter.Item(1, var113, var114);
         var112.add(var115);
      }

      if(var1.mBooks != null) {
         List var117 = this.mItems;
         String var118 = this.mContext.getString(2131362122);
         String var119 = var1.mBooks;
         ProfileInfoAdapter.Item var120 = new ProfileInfoAdapter.Item(1, var118, var119);
         var117.add(var120);
      }

      if(var1.mQuotes != null) {
         List var122 = this.mItems;
         String var123 = this.mContext.getString(2131362143);
         String var124 = var1.mQuotes;
         ProfileInfoAdapter.Item var125 = new ProfileInfoAdapter.Item(1, var123, var124);
         var122.add(var125);
      }

      if(var1.mAboutMe != null) {
         List var127 = this.mItems;
         String var128 = this.mContext.getString(2131362115);
         String var129 = var1.mAboutMe;
         ProfileInfoAdapter.Item var130 = new ProfileInfoAdapter.Item(1, var128, var129);
         var127.add(var130);
      }

      this.notifyDataSetChanged();
   }

   static class 1 extends HashMap<String, int[]> {

      private static final long serialVersionUID = 1L;


      1() {}

      public int[] get(Object var1) {
         String var2 = var1.toString().toLowerCase();
         return (int[])super.get(var2);
      }

      public int[] put(String var1, int[] var2) {
         String var3 = var1.toLowerCase();
         return (int[])super.put(var3, var2);
      }
   }
}
