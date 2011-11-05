package com.facebook.katana.activity.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookChatUser;
import com.facebook.katana.ui.SectionedListAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BuddyListSectionedAdapter extends SectionedListAdapter {

   private static final int ACTIVE_SECTION_INDEX = 0;
   private static final int AVAILABLE_SECTION_INDEX = 1;
   private static final int IDLE_SECTION_INDEX = 2;
   private static final int SECTION_NUMBER = 3;
   private final List<Integer> activeSections;
   private final Context mContext;
   private Map<Long, FacebookChatUser.UnreadConversation> mConversations;
   private boolean mQueryView;
   private final ProfileImagesCache mUserImagesCache;
   private final List<ViewHolder<Long>> mViewHolders;
   private final ArrayList<FacebookChatUser>[] sectionChildren;
   private final String[] sectionData;


   public BuddyListSectionedAdapter(Context var1, ProfileImagesCache var2) {
      ArrayList var3 = new ArrayList(3);
      this.activeSections = var3;
      this.mQueryView = (boolean)0;
      this.mContext = var1;
      ArrayList var4 = new ArrayList();
      this.mViewHolders = var4;
      this.mUserImagesCache = var2;
      ArrayList[] var5 = new ArrayList[3];
      this.sectionChildren = var5;

      for(int var6 = 0; var6 < 3; ++var6) {
         ArrayList[] var7 = this.sectionChildren;
         ArrayList var8 = new ArrayList();
         var7[var6] = var8;
      }

      String[] var9 = new String[3];
      this.sectionData = var9;
      String[] var10 = this.sectionData;
      String var11 = var1.getString(2131361829);
      var10[0] = var11;
      String[] var12 = this.sectionData;
      String var13 = var1.getString(2131361830);
      var12[1] = var13;
      String[] var14 = this.sectionData;
      String var15 = var1.getString(2131361831);
      var14[2] = var15;
   }

   public Object getChild(int var1, int var2) {
      int var3 = ((Integer)this.activeSections.get(var1)).intValue();
      return this.sectionChildren[var3].get(var2);
   }

   public View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5) {
      int var6 = ((Integer)this.activeSections.get(var1)).intValue();
      View var7;
      if(var4 != null && var4.getTag() != null) {
         var7 = var4;
         List var27 = this.mViewHolders;
         Object var28 = var4.getTag();
         var27.remove(var28);
      } else {
         var7 = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903050, (ViewGroup)null);
      }

      ViewHolder var8 = new ViewHolder(var7, 2131623962);
      var7.setTag(var8);
      this.mViewHolders.add(var8);
      FacebookChatUser var10 = (FacebookChatUser)this.sectionChildren[var6].get(var2);
      Long var11 = Long.valueOf(var10.mUserId);
      var8.setItemId(var11);
      String var12 = var10.mImageUrl;
      if(var12 != null) {
         ProfileImagesCache var13 = this.mUserImagesCache;
         Context var14 = this.mContext;
         long var15 = var11.longValue();
         Bitmap var17 = var13.get(var14, var15, var12);
         if(var17 != null) {
            var8.mImageView.setImageBitmap(var17);
         } else {
            var8.mImageView.setImageResource(2130837747);
         }
      } else {
         var8.mImageView.setImageResource(2130837747);
      }

      String var18 = var10.getDisplayName();
      if(var18 == null) {
         var18 = this.mContext.getString(2131361895);
      }

      ((TextView)var7.findViewById(2131623964)).setText(var18);
      ImageView var19 = (ImageView)var7.findViewById(2131623968);
      int[] var20 = BuddyListSectionedAdapter.1.$SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence;
      int var21 = var10.mPresence.ordinal();
      switch(var20[var21]) {
      case 1:
         var19.setImageResource(2130837566);
         break;
      case 2:
         var19.setImageResource(2130837572);
         break;
      default:
         var19.setImageResource(2130837573);
      }

      if(this.mConversations.containsKey(var11) && ((FacebookChatUser.UnreadConversation)this.mConversations.get(var11)).mUnreadCount > 0) {
         TextView var22 = (TextView)var7.findViewById(2131623966);
         StringBuilder var23 = new StringBuilder();
         int var24 = ((FacebookChatUser.UnreadConversation)this.mConversations.get(var11)).mUnreadCount;
         String var25 = var23.append(var24).append("").toString();
         var22.setText(var25);
         ((TextView)var7.findViewById(2131623966)).setVisibility(0);
         TextView var30 = (TextView)var7.findViewById(2131623967);
         String var26;
         if(((FacebookChatUser.UnreadConversation)this.mConversations.get(var11)).mMessage == null) {
            var26 = "";
         } else {
            var26 = ((FacebookChatUser.UnreadConversation)this.mConversations.get(var11)).mMessage;
         }

         var30.setText(var26);
         ((TextView)var7.findViewById(2131623967)).setVisibility(0);
         ((LinearLayout)var7.findViewById(2131623965)).setVisibility(0);
      } else {
         ((TextView)var7.findViewById(2131623966)).setVisibility(8);
         ((TextView)var7.findViewById(2131623967)).setVisibility(8);
         ((LinearLayout)var7.findViewById(2131623965)).setVisibility(8);
      }

      return var7;
   }

   public int getChildViewType(int var1, int var2) {
      return 1;
   }

   public int getChildrenCount(int var1) {
      int var2 = ((Integer)this.activeSections.get(var1)).intValue();
      return this.sectionChildren[var2].size();
   }

   public Object getSection(int var1) {
      int var2 = ((Integer)this.activeSections.get(var1)).intValue();
      return this.sectionData[var2];
   }

   public int getSectionCount() {
      return this.activeSections.size();
   }

   public View getSectionHeaderView(int var1, View var2, ViewGroup var3) {
      int var4 = ((Integer)this.activeSections.get(var1)).intValue();
      View var5;
      if(var2 != null && var2.getTag() != null) {
         var5 = var2;
         List var6 = this.mViewHolders;
         Object var7 = var2.getTag();
         var6.remove(var7);
      } else {
         var5 = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903048, (ViewGroup)null);
      }

      if(this.sectionData[var4].length() == 0) {
         var5.setVisibility(8);
      } else {
         TextView var9 = (TextView)var5;
         String var10 = this.sectionData[var4];
         var9.setText(var10);
      }

      return var5;
   }

   public int getSectionHeaderViewType(int var1) {
      return 0;
   }

   public int getViewTypeCount() {
      return 2;
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.sectionChildren.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isEnabled(int var1, int var2) {
      boolean var3;
      if(var2 != -1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void redraw(Collection<FacebookChatUser> var1, Map<Long, FacebookChatUser.UnreadConversation> var2, boolean var3) {
      this.mConversations = var2;
      this.mQueryView = var3;
      this.activeSections.clear();

      for(int var4 = 0; var4 < 3; ++var4) {
         this.sectionChildren[var4].clear();
      }

      Iterator var5 = var1.iterator();

      while(var5.hasNext()) {
         FacebookChatUser var6 = (FacebookChatUser)var5.next();
         if(var6 != null) {
            long var7 = var6.mUserId;
            boolean var9 = false;
            Map var10 = this.mConversations;
            Long var11 = Long.valueOf(var7);
            if(var10.containsKey(var11)) {
               boolean var12 = this.sectionChildren[0].add(var6);
               var9 = true;
            }

            if(!this.mQueryView || !var9) {
               FacebookChatUser.Presence var13 = var6.mPresence;
               FacebookChatUser.Presence var14 = FacebookChatUser.Presence.AVAILABLE;
               if(var13 == var14) {
                  boolean var15 = this.sectionChildren[1].add(var6);
               } else {
                  FacebookChatUser.Presence var16 = var6.mPresence;
                  FacebookChatUser.Presence var17 = FacebookChatUser.Presence.IDLE;
                  if(var16 == var17) {
                     boolean var18 = this.sectionChildren[2].add(var6);
                  }
               }
            }
         }
      }

      if(this.sectionChildren[0].size() > 0) {
         List var19 = this.activeSections;
         Integer var20 = Integer.valueOf(0);
         var19.add(var20);
      }

      if(this.sectionChildren[1].size() > 0) {
         List var22 = this.activeSections;
         Integer var23 = Integer.valueOf(1);
         var22.add(var23);
      }

      if(this.sectionChildren[2].size() > 0) {
         List var25 = this.activeSections;
         Integer var26 = Integer.valueOf(2);
         var25.add(var26);
      }

      this.notifyDataSetChanged();
   }

   public void updatePresenceIcon(Long var1, int var2) {
      Iterator var3 = this.mViewHolders.iterator();

      while(var3.hasNext()) {
         ViewHolder var4 = (ViewHolder)var3.next();
         Long var5 = (Long)var4.getItemId();
         if(var5 != null && var5.equals(var1)) {
            var4.mImageView.setImageResource(var2);
         }
      }

   }

   public void updateUserImage(ProfileImage var1) {
      Iterator var2 = this.mViewHolders.iterator();

      while(var2.hasNext()) {
         ViewHolder var3 = (ViewHolder)var2.next();
         Long var4 = (Long)var3.getItemId();
         if(var4 != null) {
            Long var5 = Long.valueOf(var1.id);
            if(var4.equals(var5)) {
               ImageView var6 = var3.mImageView;
               Bitmap var7 = var1.getBitmap();
               var6.setImageBitmap(var7);
            }
         }
      }

   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence = new int[FacebookChatUser.Presence.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence;
            int var1 = FacebookChatUser.Presence.AVAILABLE.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence;
            int var3 = FacebookChatUser.Presence.IDLE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }
}
