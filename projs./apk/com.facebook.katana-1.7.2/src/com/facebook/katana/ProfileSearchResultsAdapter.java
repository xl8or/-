package com.facebook.katana;

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
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ProfileSearchResultsAdapter extends ProfileListCursorAdapter {

   protected final Context mContext;
   protected final LayoutInflater mInflater;
   protected final StreamPhotosCache mPhotosCache;
   private final List<ViewHolder<String>> mViewHolders;


   public ProfileSearchResultsAdapter(Context var1, Cursor var2, StreamPhotosCache var3) {
      this.mContext = var1;
      this.mPhotosCache = var3;
      LayoutInflater var4 = LayoutInflater.from(var1);
      this.mInflater = var4;
      ArrayList var5 = new ArrayList();
      this.mSections = var5;
      ArrayList var6 = new ArrayList();
      this.mViewHolders = var6;
      this.refreshData(var2);
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

      String var10 = var6.mImageUrl;
      var8.setItemId(var10);
      if(var10 != null && var10.length() != 0) {
         StreamPhotosCache var11 = this.mPhotosCache;
         Context var12 = this.mContext;
         Bitmap var13 = var11.get(var12, var10, 2);
         if(var13 != null) {
            var8.mImageView.setImageBitmap(var13);
         } else {
            var8.mImageView.setImageResource(2130837747);
         }
      } else {
         var8.mImageView.setImageResource(2130837747);
      }

      TextView var14 = (TextView)var7.findViewById(2131624192);
      String var15 = var6.mDisplayName;
      var14.setText(var15);
      return var7;
   }

   public int getCount() {
      int var1;
      if(this.getCursor() == null) {
         var1 = 0;
      } else {
         var1 = this.getCursor().getCount();
      }

      return var1;
   }

   public View getSectionHeaderView(int var1, View var2, ViewGroup var3) {
      View var5;
      if(((ProfileListCursorAdapter.Section)this.mSections.get(var1)).getTitle() == null) {
         Context var4 = this.mContext;
         var5 = new View(var4);
      } else {
         View var6 = var2;
         if(var2 == null) {
            var6 = this.mInflater.inflate(2130903086, (ViewGroup)null);
         }

         TextView var7 = (TextView)var6;
         String var8 = ((ProfileListCursorAdapter.Section)this.mSections.get(var1)).getTitle();
         var7.setText(var8);
         var5 = var6;
      }

      return var5;
   }

   protected View inflateChildView(FacebookProfile var1) {
      View var2 = this.mInflater.inflate(2130903144, (ViewGroup)null);
      View var3 = ((ViewStub)var2.findViewById(2131624200)).inflate();
      return var2;
   }

   public abstract void refreshData(Cursor var1);

   public void updatePhoto(Bitmap var1, String var2) {
      Iterator var3 = this.mViewHolders.iterator();

      while(var3.hasNext()) {
         ViewHolder var4 = (ViewHolder)var3.next();
         String var5 = (String)var4.getItemId();
         if(var5 != null && var5.equals(var2)) {
            var4.mImageView.setImageBitmap(var1);
         }
      }

   }
}
