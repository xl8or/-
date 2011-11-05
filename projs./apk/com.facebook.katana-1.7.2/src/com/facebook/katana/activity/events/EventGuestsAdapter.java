package com.facebook.katana.activity.events;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EventGuestsAdapter extends BaseAdapter {

   private final Context mContext;
   private final LayoutInflater mInflater;
   private final List<EventGuestsAdapter.Item> mItems;
   private final ProfileImagesCache mUserImagesCache;
   private final List<ViewHolder<Long>> mViewHolders;


   public EventGuestsAdapter(Context var1, ProfileImagesCache var2) {
      this.mContext = var1;
      this.mUserImagesCache = var2;
      ArrayList var3 = new ArrayList();
      this.mViewHolders = var3;
      LayoutInflater var4 = LayoutInflater.from(var1);
      this.mInflater = var4;
      ArrayList var5 = new ArrayList();
      this.mItems = var5;
   }

   public int getCount() {
      return this.mItems.size();
   }

   public Object getItem(int var1) {
      return this.mItems.get(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      EventGuestsAdapter.Item var4 = (EventGuestsAdapter.Item)this.mItems.get(var1);
      View var5;
      if(var2 == null) {
         var5 = this.mInflater.inflate(2130903068, (ViewGroup)null);
         ViewHolder var6 = new ViewHolder(var5, 2131624028);
         this.mViewHolders.add(var6);
         var5.setTag(var6);
      } else {
         var5 = var2;
      }

      label24: {
         TextView var8 = (TextView)var5.findViewById(2131624037);
         String var9 = var4.getStatus();
         var8.setText(var9);
         if(var1 != 0) {
            String var10 = var4.getStatus();
            List var11 = this.mItems;
            int var12 = var1 - 1;
            String var13 = ((EventGuestsAdapter.Item)var11.get(var12)).getStatus();
            if(var10.equals(var13)) {
               var8.setVisibility(8);
               break label24;
            }
         }

         var8.setVisibility(0);
      }

      ViewHolder var14 = (ViewHolder)var5.getTag();
      Long var15 = Long.valueOf(var4.getUser().mUserId);
      var14.setItemId(var15);
      String var16 = var4.getUser().mImageUrl;
      if(var16 != null) {
         ProfileImagesCache var17 = this.mUserImagesCache;
         Context var18 = this.mContext;
         long var19 = var4.getUser().mUserId;
         Bitmap var21 = var17.get(var18, var19, var16);
         if(var21 != null) {
            var14.mImageView.setImageBitmap(var21);
         } else {
            var14.mImageView.setImageResource(2130837747);
         }
      } else {
         var14.mImageView.setImageResource(2130837747);
      }

      TextView var22 = (TextView)var5.findViewById(2131624030);
      String var23 = var4.getUser().getDisplayName();
      var22.setText(var23);
      return var5;
   }

   public void setItemsInfo(Map<FacebookEvent.RsvpStatus, List<FacebookUser>> var1) {
      this.mItems.clear();
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         int var4 = ((FacebookEvent.RsvpStatus)var3.getKey()).status.ordinal();
         String var5 = StringUtils.rsvpStatusToString(this.mContext, var4);
         Iterator var6 = ((List)var3.getValue()).iterator();

         while(var6.hasNext()) {
            FacebookUser var7 = (FacebookUser)var6.next();
            if(var7 != null) {
               List var8 = this.mItems;
               EventGuestsAdapter.Item var9 = new EventGuestsAdapter.Item(var5, var7, var4);
               var8.add(var9);
            }
         }
      }

      List var11 = this.mItems;
      EventGuestsAdapter.byStatusAndName var12 = new EventGuestsAdapter.byStatusAndName();
      Collections.sort(var11, var12);
      this.notifyDataSetChanged();
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

   public class byStatusAndName implements Comparator<EventGuestsAdapter.Item> {

      public byStatusAndName() {}

      public int compare(EventGuestsAdapter.Item var1, EventGuestsAdapter.Item var2) {
         int var3 = var1.getWeight();
         int var4 = var2.getWeight();
         int var5 = var3 - var4;
         int var6;
         if(var5 != 0) {
            var6 = var5;
         } else {
            String var7 = var1.getUser().getDisplayName();
            String var8 = var2.getUser().getDisplayName();
            var6 = var7.compareTo(var8);
         }

         return var6;
      }
   }

   protected static class Item {

      private final String mStatus;
      private final FacebookUser mUser;
      private final int mWeight;


      public Item(String var1, FacebookUser var2, int var3) {
         this.mStatus = var1;
         this.mUser = var2;
         this.mWeight = var3;
      }

      public String getStatus() {
         return this.mStatus;
      }

      public FacebookUser getUser() {
         return this.mUser;
      }

      public int getWeight() {
         return this.mWeight;
      }
   }
}
