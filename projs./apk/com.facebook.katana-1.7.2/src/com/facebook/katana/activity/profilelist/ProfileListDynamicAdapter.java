package com.facebook.katana.activity.profilelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.activity.profilelist.ProfileListActivity;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProfileListDynamicAdapter extends ProfileListActivity.ProfileListAdapter {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   protected final Context mContext;
   protected final LayoutInflater mInflater;
   protected List<? extends FacebookProfile> mProfiles;
   protected AsyncTask<List<? extends FacebookProfile>, Integer, List<? extends FacebookProfile>> mSortTask;
   protected final ProfileImagesCache mUserImagesCache;


   static {
      byte var0;
      if(!ProfileListDynamicAdapter.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public ProfileListDynamicAdapter(Context var1, ProfileImagesCache var2) {
      this.mContext = var1;
      LayoutInflater var3 = LayoutInflater.from(var1);
      this.mInflater = var3;
      this.mUserImagesCache = var2;
      ArrayList var4 = new ArrayList();
      this.mViewHolders = var4;
   }

   public Object getChild(int var1, int var2) {
      if(!$assertionsDisabled && var1 != 0) {
         throw new AssertionError();
      } else {
         return this.mProfiles.get(var2);
      }
   }

   public View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5) {
      FacebookProfile var6 = (FacebookProfile)this.getChild(var1, var2);
      View var7 = var4;
      ViewHolder var8;
      if(var4 == null) {
         var7 = this.mInflater.inflate(2130903139, (ViewGroup)null);
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

   public int getChildViewType(int var1, int var2) {
      return 1;
   }

   public int getChildrenCount(int var1) {
      if(!$assertionsDisabled && var1 != 0) {
         throw new AssertionError();
      } else {
         return this.mProfiles.size();
      }
   }

   public Object getSection(int var1) {
      return null;
   }

   public int getSectionCount() {
      return 1;
   }

   public View getSectionHeaderView(int var1, View var2, ViewGroup var3) {
      View var4 = var2;
      if(var2 == null) {
         Context var5 = this.mContext;
         var4 = new View(var5);
      }

      return var4;
   }

   public int getSectionHeaderViewType(int var1) {
      return 0;
   }

   public int getViewTypeCount() {
      return 2;
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.mProfiles != null && this.mProfiles.size() != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isEnabled(int var1, int var2) {
      return true;
   }

   public void updateProfileList(List<? extends FacebookProfile> var1) {
      ProfileListDynamicAdapter.SortProfilesTask var2 = new ProfileListDynamicAdapter.SortProfilesTask((ProfileListDynamicAdapter.1)null);
      this.mSortTask = var2;
      AsyncTask var3 = this.mSortTask;
      List[] var4 = new List[]{var1};
      var3.execute(var4);
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class SortProfilesTask extends AsyncTask<List<? extends FacebookProfile>, Integer, List<? extends FacebookProfile>> {

      // $FF: synthetic field
      static final boolean $assertionsDisabled;


      static {
         byte var0;
         if(!ProfileListDynamicAdapter.class.desiredAssertionStatus()) {
            var0 = 1;
         } else {
            var0 = 0;
         }

         $assertionsDisabled = (boolean)var0;
      }

      private SortProfilesTask() {}

      // $FF: synthetic method
      SortProfilesTask(ProfileListDynamicAdapter.1 var2) {
         this();
      }

      protected List<? extends FacebookProfile> doInBackground(List<? extends FacebookProfile> ... var1) {
         if(!$assertionsDisabled && var1.length != 1) {
            throw new AssertionError();
         } else {
            ProfileListDynamicAdapter.SortProfilesTask.1 var2 = new ProfileListDynamicAdapter.SortProfilesTask.1();
            List var3 = var1[0];
            ArrayList var4 = new ArrayList(var3);
            Collections.sort(var4, var2);
            return var4;
         }
      }

      protected void onPostExecute(List<? extends FacebookProfile> var1) {
         ProfileListDynamicAdapter.this.mProfiles = var1;
         ProfileListDynamicAdapter.this.notifyDataSetChanged();
      }

      class 1 implements Comparator<FacebookProfile> {

         1() {}

         public int compare(FacebookProfile var1, FacebookProfile var2) {
            String var3 = var1.mDisplayName;
            String var4 = var2.mDisplayName;
            return var3.compareTo(var4);
         }
      }
   }
}
