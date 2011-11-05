package com.facebook.katana.activity.events;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.TimeUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class EventBirthdaysAdapter extends BaseAdapter {

   private final Context mContext;
   private final LayoutInflater mInflater;
   private List<EventBirthdaysAdapter.Item> mItems;
   private final ProfileImagesCache mUserImagesCache;
   private final List<ViewHolder<Long>> mViewHolders;
   private final String mWhere;


   public EventBirthdaysAdapter(Context var1, ProfileImagesCache var2, String var3) {
      this.mContext = var1;
      this.mUserImagesCache = var2;
      ArrayList var4 = new ArrayList();
      this.mViewHolders = var4;
      LayoutInflater var5 = LayoutInflater.from(var1);
      this.mInflater = var5;
      ArrayList var6 = new ArrayList();
      this.mItems = var6;
      this.mWhere = var3;
      this.setItemsInfo();
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
      List var4 = this.mItems;
      EventBirthdaysAdapter.Item var6 = (EventBirthdaysAdapter.Item)var4.get(var1);
      View var7;
      if(var2 == null) {
         var7 = this.mInflater.inflate(2130903068, (ViewGroup)null);
         ViewHolder var8 = new ViewHolder;
         int var11 = 2131624028;
         var8.<init>(var7, var11);
         List var12 = this.mViewHolders;
         var12.add(var8);
         var7.setTag(var8);
      } else {
         var7 = var2;
      }

      int var16 = 2131624037;
      TextView var17 = (TextView)var7.findViewById(var16);
      String var18 = var6.getBar();
      String var19 = null;
      if(var1 > 0) {
         List var20 = this.mItems;
         int var21 = var1 - 1;
         var19 = ((EventBirthdaysAdapter.Item)var20.get(var21)).getBar();
      }

      if(var19 != null && var19.equals(var18)) {
         byte var61 = 8;
         var17.setVisibility(var61);
      } else {
         var17.setText(var18);
         byte var23 = 0;
         var17.setVisibility(var23);
      }

      ViewHolder var24 = (ViewHolder)var7.getTag();
      Long var25 = Long.valueOf(var6.getUserId());
      var24.setItemId(var25);
      String var26 = var6.getImageURL();
      if(var26 != null) {
         ProfileImagesCache var27 = this.mUserImagesCache;
         Context var28 = this.mContext;
         long var29 = var6.getUserId();
         Bitmap var36 = var27.get(var28, var29, var26);
         if(var36 != null) {
            ImageView var37 = var24.mImageView;
            var37.setImageBitmap(var36);
         } else {
            var24.mImageView.setImageResource(2130837747);
         }
      } else {
         var24.mImageView.setImageResource(2130837747);
      }

      int var40 = 2131624030;
      TextView var41 = (TextView)var7.findViewById(var40);
      String var42 = var6.getDisplayName();
      var41.setText(var42);
      int var46 = 2131624038;
      TextView var47 = (TextView)var7.findViewById(var46);
      int var48 = var6.getYear();
      char var49 = '\uffff';
      if(var48 != var49) {
         int var50 = var6.getYear();
         long var51 = var6.getStartingTime() * 1000L;
         int var53 = TimeUtils.getAge(var50, var51);
         Context var54 = this.mContext;
         Object[] var55 = new Object[1];
         Integer var56 = Integer.valueOf(var53);
         var55[0] = var56;
         String var57 = var54.getString(2131361824, var55);
         var47.setText(var57);
      } else {
         String var63 = "";
         var47.setText(var63);
      }

      return var7;
   }

   public void setItemsInfo() {
      this.mItems.clear();
      Activity var1 = (Activity)this.mContext;
      Uri var2 = ConnectionsProvider.FRIENDS_BIRTHDAY_CONTENT_URI;
      String[] var3 = EventBirthdaysAdapter.BirthdaysQuery.PROJECTION;
      String var4 = this.mWhere;
      Cursor var5 = var1.managedQuery(var2, var3, var4, (String[])null, (String)null);
      boolean var6 = var5.moveToFirst();

      boolean var39;
      for(int var7 = var5.getCount(); var7 > 0; var39 = var5.moveToNext()) {
         byte var9 = 3;
         int var10 = var5.getInt(var9);
         byte var12 = 4;
         int var13 = var5.getInt(var12);
         long var14 = TimeUtils.timeInSeconds(var10 - 1, var13, (boolean)0);
         if(TimeUtils.getTimePeriod(1000L * var14) == -1) {
            var14 = TimeUtils.timeInSeconds(var10 - 1, var13, (boolean)1);
         }

         byte var17 = 1;
         long var18 = var5.getLong(var17);
         byte var21 = 2;
         String var22 = var5.getString(var21);
         byte var24 = 5;
         int var25 = var5.getInt(var24);
         byte var27 = 6;
         String var28 = var5.getString(var27);
         Context var29 = this.mContext;
         long var30 = 1000L * var14;
         byte var33 = 100;
         String var36 = TimeUtils.getTimeAsStringForTimePeriod(var29, var33, var30);
         EventBirthdaysAdapter.Item var37 = new EventBirthdaysAdapter.Item(var18, var22, var10, var13, var25, var28, var14, var36);
         this.mItems.add(var37);
         var7 += -1;
      }

      List var40 = this.mItems;
      EventBirthdaysAdapter.byTime var41 = new EventBirthdaysAdapter.byTime();
      Collections.sort(var40, var41);
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

   public class byTime implements Comparator<EventBirthdaysAdapter.Item> {

      public byTime() {}

      public int compare(EventBirthdaysAdapter.Item var1, EventBirthdaysAdapter.Item var2) {
         long var3 = var1.getStartingTime();
         long var5 = var2.getStartingTime();
         return (int)(var3 - var5);
      }
   }

   protected static class Item {

      private final String mBar;
      private final int mDay;
      private final String mDisplayName;
      private final String mImageUrl;
      private final int mMonth;
      private final long mStartingTime;
      private final long mUserId;
      private final int mYear;


      public Item(long param1, String param3, int param4, int param5, int param6, String param7, long param8, String param10) {
         // $FF: Couldn't be decompiled
      }

      public String getBar() {
         return this.mBar;
      }

      public int getDay() {
         return this.mDay;
      }

      public String getDisplayName() {
         return this.mDisplayName;
      }

      public String getImageURL() {
         return this.mImageUrl;
      }

      public int getMonth() {
         return this.mMonth;
      }

      public long getStartingTime() {
         return this.mStartingTime;
      }

      public long getUserId() {
         return this.mUserId;
      }

      public int getYear() {
         return this.mYear;
      }
   }

   private interface BirthdaysQuery {

      int INDEX_BIRTHDAY_MONTH = 3;
      int INDEX_BIRTHDAY_YEAR = 5;
      int INDEX_NORMALIZED_BIRTHDAY_DAY = 4;
      int INDEX_USER_DISPLAY_NAME = 2;
      int INDEX_USER_ID = 1;
      int INDEX_USER_IMAGE_URL = 6;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"_id", "user_id", "display_name", "birthday_month", "normalized_birthday_day", "birthday_year", "user_image_url"};
         PROJECTION = var0;
      }
   }
}
