package com.facebook.katana.activity.places;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class PlacesInfoAdapter extends BaseAdapter {

   private final Context mContext;
   private final List<PlacesInfoAdapter.Item> mItems;


   public PlacesInfoAdapter(Context var1) {
      this.mContext = var1;
      ArrayList var2 = new ArrayList();
      this.mItems = var2;
   }

   public int getCount() {
      return this.mItems.size();
   }

   public Object getItem(int var1) {
      return Integer.valueOf(var1);
   }

   public PlacesInfoAdapter.Item getItemByPosition(int var1) {
      return (PlacesInfoAdapter.Item)this.mItems.get(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public int getItemViewType(int var1) {
      byte var2;
      switch(((PlacesInfoAdapter.Item)this.mItems.get(var1)).mType) {
      case 0:
         var2 = 0;
         break;
      case 1:
         var2 = 1;
         break;
      default:
         var2 = -1;
      }

      return var2;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      View var4 = null;
      PlacesInfoAdapter.Item var5 = (PlacesInfoAdapter.Item)this.mItems.get(var1);
      if(var2 == null) {
         LayoutInflater var6 = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
         switch(var5.mType) {
         case 0:
            var4 = var6.inflate(2130903096, (ViewGroup)null);
            break;
         case 1:
            var4 = var6.inflate(2130903096, (ViewGroup)null);
         }
      } else {
         var4 = var2;
      }

      switch(var5.mType) {
      case 0:
         if(!StringUtils.isBlank(var5.getTitle())) {
            TextView var7 = (TextView)var4.findViewById(2131624101);
            String var8 = var5.getTitle();
            var7.setText(var8);
         } else {
            ((TextView)var4.findViewById(2131624101)).setVisibility(8);
         }

         if(!StringUtils.isBlank(var5.getSubTitle())) {
            TextView var9 = (TextView)var4.findViewById(2131624102);
            String var10 = var5.getSubTitle();
            var9.setText(var10);
         } else {
            ((TextView)var4.findViewById(2131624102)).setVisibility(8);
         }
         break;
      case 1:
         if(!StringUtils.isBlank(var5.getTitle())) {
            TextView var11 = (TextView)var4.findViewById(2131624101);
            String var12 = var5.getTitle();
            var11.setText(var12);
         } else {
            ((TextView)var4.findViewById(2131624101)).setVisibility(8);
         }

         if(!StringUtils.isBlank(var5.getSubTitle())) {
            TextView var13 = (TextView)var4.findViewById(2131624102);
            String var14 = var5.getSubTitle();
            var13.setText(var14);
         } else {
            ((TextView)var4.findViewById(2131624102)).setVisibility(8);
         }
      }

      return var4;
   }

   public int getViewTypeCount() {
      return 2;
   }

   public void setPlaceInfo(FacebookPlace var1) {
      this.mItems.clear();
      if(!StringUtils.isBlank(var1.mDescription)) {
         List var2 = this.mItems;
         String var3 = this.mContext.getString(2131362131);
         String var4 = var1.mDescription;
         PlacesInfoAdapter.Item var5 = new PlacesInfoAdapter.Item(0, var3, var4);
         var2.add(var5);
      }

      FacebookPage var7 = var1.getPageInfo();
      if(var7 != null && var7.mFanCount != -1 && var7.mFanCount != 0) {
         List var8 = this.mItems;
         Resources var9 = this.mContext.getResources();
         int var10 = var7.mFanCount;
         Object[] var11 = new Object[1];
         Integer var12 = Integer.valueOf(var7.mFanCount);
         var11[0] = var12;
         String var13 = var9.getQuantityString(2131427329, var10, var11);
         PlacesInfoAdapter.Item var14 = new PlacesInfoAdapter.Item(0, var13, (String)null);
         var8.add(var14);
      }

      if(var1.mCheckinCount != 0) {
         List var16 = this.mItems;
         Resources var17 = this.mContext.getResources();
         int var18 = var1.mCheckinCount;
         Object[] var19 = new Object[1];
         Integer var20 = Integer.valueOf(var1.mCheckinCount);
         var19[0] = var20;
         String var21 = var17.getQuantityString(2131427330, var18, var19);
         PlacesInfoAdapter.Item var22 = new PlacesInfoAdapter.Item(0, var21, (String)null);
         var16.add(var22);
      }

      if(var7 != null && var7.mLocation != null) {
         String var24 = StringUtils.formatLocation(var7.mLocation);
         if(!StringUtils.isBlank(var24)) {
            List var25 = this.mItems;
            String var26 = this.mContext.getString(2131362132);
            PlacesInfoAdapter.Item var27 = new PlacesInfoAdapter.Item(1, var26, var24);
            var25.add(var27);
         }
      }

      this.notifyDataSetChanged();
   }

   protected static class Item {

      public static final int TYPE_INFO = 0;
      public static final int TYPE_LOCATION = 1;
      private final String mSubTitle;
      private final String mTitle;
      private final int mType;


      public Item(int var1, String var2, String var3) {
         this.mType = var1;
         this.mTitle = var2;
         this.mSubTitle = var3;
      }

      public String getSubTitle() {
         return this.mSubTitle;
      }

      public String getTitle() {
         return this.mTitle;
      }

      public int getType() {
         return this.mType;
      }
   }
}
