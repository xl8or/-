package com.facebook.katana.activity.profilelist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.activity.profilelist.ProfileListCursorAdapter;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import java.util.ArrayList;
import java.util.List;

public abstract class ProfileListImageCacheAdapter extends ProfileListCursorAdapter {

   protected final Context mContext;
   protected final LayoutInflater mInflater;
   protected final ProfileImagesCache mUserImagesCache;


   public ProfileListImageCacheAdapter(Context var1, ProfileImagesCache var2, Cursor var3) {
      this.mContext = var1;
      LayoutInflater var4 = LayoutInflater.from(var1);
      this.mInflater = var4;
      this.mUserImagesCache = var2;
      ArrayList var5 = new ArrayList();
      this.mViewHolders = var5;
      this.refreshData(var3);
   }

   public View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5) {
      FacebookProfile var6 = (FacebookProfile)this.getChild(var1, var2);
      View var7 = var4;
      ViewHolder var8;
      if(var4 == null) {
         var7 = this.inflateChildView(var6);
         var8 = new ViewHolder(var7, 2131623987);
         this.mViewHolders.add(var8);
         var7.setTag(var8);
      } else {
         var8 = (ViewHolder)var4.getTag();
      }

      Long var10 = Long.valueOf(var6.mId);
      var8.setItemId(var10);
      String var11 = var6.mImageUrl;
      if(var11 != null && var11.length() != 0) {
         ProfileImagesCache var12 = this.mUserImagesCache;
         Context var13 = this.mContext;
         long var14 = var6.mId;
         Bitmap var16 = var12.get(var13, var14, var11);
         if(var16 != null) {
            var8.mImageView.setImageBitmap(var16);
         } else {
            var8.mImageView.setImageResource(2130837747);
         }
      } else {
         var8.mImageView.setImageResource(2130837747);
      }

      TextView var17 = (TextView)var7.findViewById(2131624192);
      String var18 = var6.mDisplayName;
      var17.setText(var18);
      return var7;
   }

   protected abstract Object getItemType(Cursor var1);

   public View getSectionHeaderView(int var1, View var2, ViewGroup var3) {
      View var4 = var2;
      if(var2 == null) {
         var4 = this.mInflater.inflate(2130903086, (ViewGroup)null);
      }

      TextView var5 = (TextView)var4;
      String var6 = ((ProfileListCursorAdapter.Section)this.mSections.get(var1)).getTitle();
      var5.setText(var6);
      return var4;
   }

   protected abstract String getTitleForType(Object var1);

   protected View inflateChildView(FacebookProfile var1) {
      View var2 = this.mInflater.inflate(2130903144, (ViewGroup)null);
      View var3 = ((ViewStub)var2.findViewById(2131624200)).inflate();
      return var2;
   }

   public void refreshData(Cursor var1) {
      this.mCursor = var1;
      ArrayList var2 = new ArrayList();
      this.mSections = var2;
      if(var1 != null) {
         int var3 = -1;
         int var4 = 0;
         int var5 = var1.getCount();
         int var6 = 0;
         boolean var7 = var1.moveToFirst();

         Object var8;
         boolean var10;
         for(var8 = null; var6 < var5; var10 = var1.moveToNext()) {
            Object var9 = this.getItemType(var1);
            if(!var9.equals(var8)) {
               if(var4 > 0) {
                  List var11 = this.mSections;
                  String var12 = this.getTitleForType(var8);
                  ProfileListCursorAdapter.Section var13 = new ProfileListCursorAdapter.Section(var12, var3, var4);
                  var11.add(var13);
               }

               var3 = var6;
               var4 = 0;
            }

            ++var6;
            ++var4;
         }

         if(var4 > 0) {
            List var17 = this.mSections;
            String var18 = this.getTitleForType(var8);
            ProfileListCursorAdapter.Section var19 = new ProfileListCursorAdapter.Section(var18, var3, var4);
            var17.add(var19);
         }

         this.notifyDataSetChanged();
      }
   }
}
