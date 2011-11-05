package com.facebook.katana.activity.places;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.facebook.katana.activity.places.PlacesNearbyActivity;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.util.StringUtils;
import java.util.List;

public class PlacesNearbyAdapter extends BaseAdapter {

   public static final int ADD_PLACE_BUTTON_ID = 255;
   private String mAddPlaceString = "";
   private boolean mAddPlaceVisible;
   private List<FacebookPlace> mPlaces;
   private PlacesNearbyActivity mPlacesNearbyActivity;


   public PlacesNearbyAdapter(PlacesNearbyActivity var1, List<FacebookPlace> var2) {
      this.mPlacesNearbyActivity = var1;
      this.mPlaces = var2;
   }

   public int getCount() {
      int var1 = this.mPlaces.size();
      byte var2;
      if(this.mAddPlaceVisible) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      return var1 + var2;
   }

   public Object getItem(int var1) {
      int var2 = this.mPlaces.size();
      Object var3;
      if(var1 == var2) {
         var3 = null;
      } else {
         var3 = this.mPlaces.get(var1);
      }

      return var3;
   }

   public long getItemId(int var1) {
      int var2 = this.mPlaces.size();
      long var3;
      if(var1 == var2) {
         var3 = 65535L;
      } else {
         var3 = ((FacebookPlace)this.getItem(var1)).mPageId;
      }

      return var3;
   }

   public int getItemViewType(int var1) {
      int var2 = this.mPlaces.size();
      byte var3;
      if(var1 >= var2) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      return var3;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      View var4 = var2;
      int var5 = this.mPlaces.size();
      if(var1 == var5 && this.mAddPlaceVisible) {
         if(var2 == null) {
            var4 = ((LayoutInflater)this.mPlacesNearbyActivity.getSystemService("layout_inflater")).inflate(2130903040, (ViewGroup)null);
         }

         TextView var6 = (TextView)var4.findViewById(2131623937);
         String var7 = this.mAddPlaceString;
         var6.setText(var7);
      } else {
         if(var2 == null) {
            var4 = ((LayoutInflater)this.mPlacesNearbyActivity.getSystemService("layout_inflater")).inflate(2130903131, (ViewGroup)null);
         }

         FacebookPlace var8 = (FacebookPlace)this.getItem(var1);
         TextView var9 = (TextView)var4.findViewById(2131624173);
         String var10 = var8.mName;
         var9.setText(var10);
         TextView var11 = (TextView)var4.findViewById(2131624174);
         if(StringUtils.isBlank(var8.mDisplaySubtext)) {
            var11.setVisibility(8);
         } else {
            var11.setVisibility(0);
            String var12 = var8.mDisplaySubtext;
            var11.setText(var12);
         }

         if(var8.getDealInfo() != null) {
            var4.findViewById(2131624176).setVisibility(0);
         } else {
            var4.findViewById(2131624176).setVisibility(8);
         }
      }

      return var4;
   }

   public int getViewTypeCount() {
      return 2;
   }

   public boolean hasStableIds() {
      return true;
   }

   public void setAddPlaceString(String var1) {
      this.mAddPlaceString = var1;
   }

   public void setAddPlaceVisibility(boolean var1) {
      boolean var2 = this.mAddPlaceVisible;
      if(var1 != var2) {
         this.mAddPlaceVisible = var1;
         this.notifyDataSetChanged();
      }
   }

   public void setList(List<FacebookPlace> var1) {
      this.mPlaces = var1;
      this.notifyDataSetChanged();
   }
}
