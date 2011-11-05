package com.facebook.katana.features.composer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.katana.features.composer.AudienceOption;
import com.facebook.katana.model.FacebookGroup;
import com.facebook.katana.model.FriendList;
import com.facebook.katana.model.PrivacySetting;
import com.facebook.katana.service.method.AudienceSettings;
import com.facebook.katana.ui.SectionedListAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AudienceAdapter extends SectionedListAdapter {

   public boolean hasManuallySetAudience = 0;
   public boolean hasReceivedNewOptions = 0;
   private Map<AudienceOption.Type, List<AudienceOption>> mAudienceOptions;
   private final Context mContext;
   private int[] mCurrentlySelectedPosition;
   private PrivacySetting mDefaultPrivacy;
   private final LayoutInflater mInflater;
   private final boolean mIsMinor;
   private AudienceOption.Type[] mSections;
   private AudienceSettings mServerAudienceSettings;


   public AudienceAdapter(Context var1, boolean var2) {
      AudienceOption.Type[] var3 = new AudienceOption.Type[3];
      AudienceOption.Type var4 = AudienceOption.Type.PRIVACY_SETTING;
      var3[0] = var4;
      AudienceOption.Type var5 = AudienceOption.Type.FRIEND_LIST;
      var3[1] = var5;
      AudienceOption.Type var6 = AudienceOption.Type.GROUP;
      var3[2] = var6;
      this.mSections = var3;
      this.mContext = var1;
      this.mIsMinor = var2;
      LayoutInflater var7 = LayoutInflater.from(var1);
      this.mInflater = var7;
      ArrayList var8 = new ArrayList();
      if(var2) {
         PrivacySetting var9 = new PrivacySetting("FRIENDS_OF_FRIENDS");
         AudienceAdapter.PrivacySettingAudienceOption var10 = new AudienceAdapter.PrivacySettingAudienceOption(var9);
         var8.add(var10);
      } else {
         PrivacySetting var19 = new PrivacySetting("EVERYONE");
         AudienceAdapter.PrivacySettingAudienceOption var20 = new AudienceAdapter.PrivacySettingAudienceOption(var19);
         var8.add(var20);
      }

      PrivacySetting var12 = new PrivacySetting("ALL_FRIENDS");
      AudienceAdapter.PrivacySettingAudienceOption var13 = new AudienceAdapter.PrivacySettingAudienceOption(var12);
      var8.add(var13);
      HashMap var15 = new HashMap();
      this.mAudienceOptions = var15;
      Map var16 = this.mAudienceOptions;
      AudienceOption.Type var17 = AudienceOption.Type.PRIVACY_SETTING;
      var16.put(var17, var8);
   }

   public AudienceOption getChild(int var1, int var2) {
      Map var3 = this.mAudienceOptions;
      AudienceOption.Type var4 = this.mSections[var1];
      return (AudienceOption)((List)var3.get(var4)).get(var2);
   }

   public View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5) {
      View var6 = var4;
      if(var4 == null) {
         var6 = this.mInflater.inflate(2130903047, (ViewGroup)null);
      }

      LinearLayout var7 = (LinearLayout)var6;
      TextView var8 = (TextView)var7.findViewById(2131623955);
      ImageView var9 = (ImageView)var7.findViewById(2131623954);
      View var10 = var7.findViewById(2131623956);
      Map var11 = this.mAudienceOptions;
      AudienceOption.Type var12 = this.mSections[var1];
      AudienceOption var13 = (AudienceOption)((List)var11.get(var12)).get(var2);
      String var14 = var13.getLabel();
      var8.setText(var14);
      int var15 = var13.getIcon();
      var9.setImageResource(var15);
      boolean var16 = false;
      if(this.mCurrentlySelectedPosition != null) {
         if(this.mCurrentlySelectedPosition[0] == var1 && this.mCurrentlySelectedPosition[1] == var2) {
            var16 = true;
         } else {
            var16 = false;
         }
      }

      byte var17;
      if(var16) {
         var17 = 0;
      } else {
         var17 = 8;
      }

      var10.setVisibility(var17);
      return var7;
   }

   public int getChildViewType(int var1, int var2) {
      return 1;
   }

   public int getChildrenCount(int var1) {
      Map var2 = this.mAudienceOptions;
      AudienceOption.Type var3 = this.mSections[var1];
      List var4 = (List)var2.get(var3);
      int var5;
      if(var4 != null) {
         var5 = var4.size();
      } else {
         var5 = 0;
      }

      return var5;
   }

   public int[] getCurrentlySelectedPosition() {
      return this.mCurrentlySelectedPosition;
   }

   public PrivacySetting getDefaultPrivacy() {
      return this.mDefaultPrivacy;
   }

   public int getIconForSelectedOption() {
      int var1;
      if(this.mCurrentlySelectedPosition == null) {
         var1 = -1;
      } else {
         int var2 = this.mCurrentlySelectedPosition[0];
         int var3 = this.mCurrentlySelectedPosition[1];
         var1 = this.getChild(var2, var3).getIcon();
      }

      return var1;
   }

   public Object getSection(int var1) {
      Map var2 = this.mAudienceOptions;
      AudienceOption.Type var3 = this.mSections[var1];
      return var2.get(var3);
   }

   public int getSectionCount() {
      return this.mAudienceOptions.size();
   }

   public View getSectionHeaderView(int var1, View var2, ViewGroup var3) {
      View var4 = var2;
      if(var2 == null) {
         var4 = this.mInflater.inflate(2130903086, (ViewGroup)null);
      }

      TextView var5 = (TextView)var4;
      String var6 = new String();
      int[] var7 = AudienceAdapter.1.$SwitchMap$com$facebook$katana$features$composer$AudienceOption$Type;
      int var8 = this.mSections[var1].ordinal();
      switch(var7[var8]) {
      case 1:
         var6 = this.mContext.getString(2131361840);
         break;
      case 2:
         var6 = this.mContext.getString(2131361842);
         break;
      case 3:
         var6 = this.mContext.getString(2131361841);
      }

      var5.setText(var6);
      return var4;
   }

   public int getSectionHeaderViewType(int var1) {
      return 0;
   }

   public AudienceSettings getServerAudienceSettings() {
      return this.mServerAudienceSettings;
   }

   public int getViewTypeCount() {
      return 2;
   }

   public boolean isCurrentlySelectedAudienceGroup() {
      boolean var5;
      if(this.mCurrentlySelectedPosition != null) {
         AudienceOption.Type[] var1 = this.mSections;
         int var2 = this.mCurrentlySelectedPosition[0];
         AudienceOption.Type var3 = var1[var2];
         AudienceOption.Type var4 = AudienceOption.Type.GROUP;
         if(var3 == var4) {
            var5 = true;
            return var5;
         }
      }

      var5 = false;
      return var5;
   }

   public boolean isEmpty() {
      return false;
   }

   public boolean isEnabled(int var1, int var2) {
      return true;
   }

   public void setCurrentlySelectedPosition(int[] var1) {
      this.mCurrentlySelectedPosition = var1;
   }

   public void setServerAudienceSettings(AudienceSettings var1) {
      this.mServerAudienceSettings = var1;
   }

   public void updateDefaultSetting(PrivacySetting var1) {
      this.mDefaultPrivacy = var1;
      if(var1 != null) {
         String var3 = var1.value;
         if(this.mIsMinor && var3.equals("EVERYONE")) {
            var3 = "FRIENDS_OF_FRIENDS";
         }

         String var4 = var1.value;
         int var11;
         Iterator var14;
         if("CUSTOM".equals(var4)) {
            String var5 = var1.friends;
            if("SOME_FRIENDS".equals(var5)) {
               Map var6 = this.mAudienceOptions;
               AudienceOption.Type var7 = AudienceOption.Type.FRIEND_LIST;
               List var8 = (List)var6.get(var7);
               List var9 = Arrays.asList(this.mSections);
               AudienceOption.Type var10 = AudienceOption.Type.FRIEND_LIST;
               var11 = var9.indexOf(var10);
               int var12 = -1;
               int var13 = -1;
               var14 = var8.iterator();

               while(var14.hasNext()) {
                  AudienceOption var15 = (AudienceOption)var14.next();
                  ++var13;
                  long var16 = ((AudienceAdapter.FriendListAudienceOption)var15).getFriendList().flid;
                  long var18 = Long.valueOf(var1.allow).longValue();
                  if(var16 == var18) {
                     var12 = var13;
                     break;
                  }
               }

               if(var11 >= 0 && var12 >= 0) {
                  int[] var20 = new int[]{var11, var12};
                  this.mCurrentlySelectedPosition = var20;
                  this.notifyDataSetChanged();
                  return;
               }
            }
         }

         Map var22 = this.mAudienceOptions;
         AudienceOption.Type var23 = AudienceOption.Type.PRIVACY_SETTING;
         List var24 = (List)var22.get(var23);
         boolean var25 = false;
         var14 = var24.iterator();

         while(var14.hasNext()) {
            if(((AudienceAdapter.PrivacySettingAudienceOption)((AudienceOption)var14.next())).getPrivacyValue().equals(var3)) {
               var25 = true;
               break;
            }
         }

         if(!var25) {
            AudienceAdapter.PrivacySettingAudienceOption var26 = new AudienceAdapter.PrivacySettingAudienceOption(var1);
            var24.add(var26);
         }

         if(this.mCurrentlySelectedPosition == null || !this.hasManuallySetAudience) {
            List var31 = Arrays.asList(this.mSections);
            AudienceOption.Type var32 = AudienceOption.Type.PRIVACY_SETTING;
            var11 = var31.indexOf(var32);
            byte var41 = -1;
            byte var40 = -1;
            Map var33 = this.mAudienceOptions;
            AudienceOption.Type var34 = AudienceOption.Type.PRIVACY_SETTING;
            var14 = ((List)var33.get(var34)).iterator();

            while(var14.hasNext()) {
               AudienceOption var35 = (AudienceOption)var14.next();
               int var36 = var40 + 1;
               if(((AudienceAdapter.PrivacySettingAudienceOption)var35).getPrivacyValue().equals(var3)) {
                  break;
               }
            }

            if(var11 >= 0) {
               if(var41 >= 0) {
                  int[] var38 = new int[]{var11, var41};
                  this.mCurrentlySelectedPosition = var38;
                  this.notifyDataSetChanged();
               }
            }
         }
      }
   }

   public void updateFriendLists(List<FriendList> var1) {
      if(var1 != null) {
         if(var1.size() != 0) {
            ArrayList var2 = new ArrayList();
            Iterator var3 = var1.iterator();

            while(var3.hasNext()) {
               FriendList var4 = (FriendList)var3.next();
               AudienceAdapter.FriendListAudienceOption var5 = new AudienceAdapter.FriendListAudienceOption(var4);
               var2.add(var5);
            }

            Map var7 = this.mAudienceOptions;
            AudienceOption.Type var8 = AudienceOption.Type.FRIEND_LIST;
            var7.put(var8, var2);
         }
      }
   }

   public void updateGroups(List<FacebookGroup> var1) {
      if(var1 != null) {
         if(var1.size() != 0) {
            ArrayList var2 = new ArrayList();
            Iterator var3 = var1.iterator();

            while(var3.hasNext()) {
               FacebookGroup var4 = (FacebookGroup)var3.next();
               AudienceAdapter.GroupAudienceOption var5 = new AudienceAdapter.GroupAudienceOption(var4);
               var2.add(var5);
            }

            Map var7 = this.mAudienceOptions;
            AudienceOption.Type var8 = AudienceOption.Type.GROUP;
            var7.put(var8, var2);
         }
      }
   }

   public class PrivacySettingAudienceOption implements AudienceOption {

      PrivacySetting mSetting;


      public PrivacySettingAudienceOption(PrivacySetting var2) {
         this.mSetting = var2;
      }

      public int getIcon() {
         int var1;
         if(this.mSetting.value.equals("EVERYONE")) {
            var1 = 2130837514;
         } else if(this.mSetting.value.equals("ALL_FRIENDS")) {
            var1 = 2130837516;
         } else if(this.mSetting.value.equals("SELF")) {
            var1 = 2130837520;
         } else if(this.mSetting.value.equals("FRIENDS_OF_FRIENDS")) {
            var1 = 2130837515;
         } else if(this.mSetting.value.equals("CUSTOM")) {
            var1 = 2130837513;
         } else {
            var1 = -1;
         }

         return var1;
      }

      public String getLabel() {
         String var1;
         if(this.mSetting.value.equals("EVERYONE")) {
            var1 = AudienceAdapter.this.mContext.getString(2131362108);
         } else if(this.mSetting.value.equals("ALL_FRIENDS")) {
            var1 = AudienceAdapter.this.mContext.getString(2131362111);
         } else if(this.mSetting.value.equals("SELF")) {
            var1 = AudienceAdapter.this.mContext.getString(2131362112);
         } else if(this.mSetting.value.equals("FRIENDS_OF_FRIENDS")) {
            var1 = AudienceAdapter.this.mContext.getString(2131362109);
         } else if(this.mSetting.value.equals("CUSTOM")) {
            var1 = this.mSetting.description;
         } else {
            var1 = null;
         }

         return var1;
      }

      public PrivacySetting getPrivacySetting() {
         return this.mSetting;
      }

      public String getPrivacyValue() {
         return this.mSetting.value;
      }

      public AudienceOption.Type getType() {
         return AudienceOption.Type.PRIVACY_SETTING;
      }
   }

   public class FriendListAudienceOption implements AudienceOption {

      final FriendList mFriendList;


      public FriendListAudienceOption(FriendList var2) {
         this.mFriendList = var2;
      }

      public FriendList getFriendList() {
         return this.mFriendList;
      }

      public int getIcon() {
         return 2130837519;
      }

      public String getLabel() {
         return this.mFriendList.name;
      }

      public JSONObject getPrivacyObject() {
         JSONObject var4;
         JSONObject var5;
         try {
            JSONObject var1 = (new JSONObject()).put("value", "CUSTOM").put("friends", "SOME_FRIENDS");
            long var2 = this.mFriendList.flid;
            var4 = var1.put("allow", var2);
         } catch (JSONException var7) {
            var5 = null;
            return var5;
         }

         var5 = var4;
         return var5;
      }

      public AudienceOption.Type getType() {
         return AudienceOption.Type.FRIEND_LIST;
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$features$composer$AudienceOption$Type = new int[AudienceOption.Type.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$facebook$katana$features$composer$AudienceOption$Type;
            int var1 = AudienceOption.Type.PRIVACY_SETTING.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$facebook$katana$features$composer$AudienceOption$Type;
            int var3 = AudienceOption.Type.GROUP.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$facebook$katana$features$composer$AudienceOption$Type;
            int var5 = AudienceOption.Type.FRIEND_LIST.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var9) {
            ;
         }
      }
   }

   public class GroupAudienceOption implements AudienceOption {

      final FacebookGroup mGroup;


      public GroupAudienceOption(FacebookGroup var2) {
         this.mGroup = var2;
      }

      public FacebookGroup getGroup() {
         return this.mGroup;
      }

      public int getIcon() {
         return 2130837517;
      }

      public String getLabel() {
         return this.mGroup.mDisplayName;
      }

      public AudienceOption.Type getType() {
         return AudienceOption.Type.GROUP;
      }
   }
}
