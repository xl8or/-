package com.facebook.katana.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

public class ExtendableListAdapter<T extends Object> implements WrapperListAdapter {

   public static final int MORE_LOADER_POSITION = 254;
   private Context mContext;
   private ExtendableListAdapter.LoadMoreCallback mLoadMoreCallback;
   private int mLoadMoreTextResId = -1;
   private ListAdapter mWrappedAdapter;


   public ExtendableListAdapter(Context var1, ListAdapter var2, ExtendableListAdapter.LoadMoreCallback var3) {
      this.mContext = var1;
      this.mWrappedAdapter = var2;
      this.mLoadMoreCallback = var3;
   }

   private boolean isMoreLoaderShown() {
      return this.mLoadMoreCallback.hasMore();
   }

   private int translatePosition(int var1) {
      int var2 = this.getCount();
      if(var1 >= 0 && var1 < var2) {
         int var7 = this.mWrappedAdapter.getCount();
         int var8;
         if(var1 == var7) {
            var8 = -1;
         } else {
            var8 = var1;
         }

         return var8;
      } else {
         Object[] var3 = new Object[2];
         Integer var4 = Integer.valueOf(var1);
         var3[0] = var4;
         Integer var5 = Integer.valueOf(var2);
         var3[1] = var5;
         String var6 = String.format("Invalid index: %d of %d", var3);
         throw new ArrayIndexOutOfBoundsException(var6);
      }
   }

   public boolean areAllItemsEnabled() {
      return false;
   }

   public int getCount() {
      int var1;
      if(this.mWrappedAdapter.getCount() == 0) {
         var1 = 0;
      } else {
         int var2 = this.mWrappedAdapter.getCount();
         byte var3;
         if(this.isMoreLoaderShown()) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         var1 = var2 + var3;
      }

      return var1;
   }

   public Object getItem(int var1) {
      Object var2;
      if(this.translatePosition(var1) >= 0) {
         var2 = this.mWrappedAdapter.getItem(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public long getItemId(int var1) {
      int var2 = this.translatePosition(var1);
      long var3;
      if(var2 >= 0) {
         var3 = this.mWrappedAdapter.getItemId(var2);
      } else {
         var3 = 65534L;
      }

      return var3;
   }

   public int getItemViewType(int var1) {
      int var2 = this.translatePosition(var1);
      int var3;
      if(var2 >= 0) {
         var3 = this.mWrappedAdapter.getItemViewType(var2);
      } else {
         var3 = this.mWrappedAdapter.getViewTypeCount();
      }

      return var3;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      View var7;
      if(this.translatePosition(var1) == -1) {
         View var4;
         if(var2 != null) {
            var4 = var2;
         } else {
            var4 = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903100, (ViewGroup)null);
         }

         if(this.isMoreLoaderShown()) {
            var4.setVisibility(0);
            if(this.mLoadMoreTextResId != -1) {
               TextView var5 = (TextView)var4.findViewById(2131624112);
               int var6 = this.mLoadMoreTextResId;
               var5.setText(var6);
            }

            this.mLoadMoreCallback.loadMore();
         } else {
            var4.setVisibility(8);
         }

         var7 = var4;
      } else {
         var7 = this.mWrappedAdapter.getView(var1, var2, var3);
      }

      return var7;
   }

   public int getViewTypeCount() {
      return this.mWrappedAdapter.getViewTypeCount() + 1;
   }

   public ListAdapter getWrappedAdapter() {
      return this.mWrappedAdapter;
   }

   public boolean hasStableIds() {
      return this.mWrappedAdapter.hasStableIds();
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.getCount() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isEnabled(int var1) {
      int var2 = this.translatePosition(var1);
      byte var3;
      if(var2 == -1) {
         var3 = 1;
      } else {
         var3 = this.mWrappedAdapter.isEnabled(var2);
      }

      return (boolean)var3;
   }

   public void registerDataSetObserver(DataSetObserver var1) {
      this.mWrappedAdapter.registerDataSetObserver(var1);
   }

   public void setLoadMoreTextResId(int var1) {
      this.mLoadMoreTextResId = var1;
   }

   public void unregisterDataSetObserver(DataSetObserver var1) {
      this.mWrappedAdapter.unregisterDataSetObserver(var1);
   }

   public interface LoadMoreCallback {

      boolean hasMore();

      void loadMore();
   }
}
