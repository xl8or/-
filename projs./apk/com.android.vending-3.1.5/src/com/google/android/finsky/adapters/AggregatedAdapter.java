package com.google.android.finsky.adapters;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import java.util.ArrayList;

public class AggregatedAdapter<T extends BaseAdapter> extends BaseAdapter {

   private T[] mAdapters;
   private boolean mCachedAllItemsEnabled = 1;
   private int mCachedCount = 0;
   private boolean mCachedHasStableIds = 1;
   private ArrayList<AggregatedAdapter.IndexTranslation> mCachedTranslations;
   private DataSetObserver mChildObserver;
   private DataSetObservable mDataSetObservable;
   private boolean mDirty = 1;


   public AggregatedAdapter(T[] var1) {
      DataSetObservable var2 = new DataSetObservable();
      this.mDataSetObservable = var2;
      AggregatedAdapter.1 var3 = new AggregatedAdapter.1();
      this.mChildObserver = var3;
      this.mAdapters = var1;
      this.registerAsListener();
   }

   private void refreshCachedData() {
      // $FF: Couldn't be decompiled
   }

   private void registerAsListener() {
      int var1 = 0;

      while(true) {
         int var2 = this.mAdapters.length;
         if(var1 >= var2) {
            return;
         }

         BaseAdapter var3 = this.mAdapters[var1];
         DataSetObserver var4 = this.mChildObserver;
         var3.registerDataSetObserver(var4);
         ++var1;
      }
   }

   private AggregatedAdapter.IndexTranslation translate(int var1) {
      this.refreshCachedData();
      return (AggregatedAdapter.IndexTranslation)this.mCachedTranslations.get(var1);
   }

   public boolean areAllItemsEnabled() {
      this.refreshCachedData();
      return this.mCachedAllItemsEnabled;
   }

   public T[] getAdapters() {
      return this.mAdapters;
   }

   public int getCount() {
      this.refreshCachedData();
      return this.mCachedCount;
   }

   public Object getItem(int var1) {
      AggregatedAdapter.IndexTranslation var2 = this.translate(var1);
      ListAdapter var3 = var2.targetAdapter;
      int var4 = var2.translatedIndex;
      return var3.getItem(var4);
   }

   public long getItemId(int var1) {
      AggregatedAdapter.IndexTranslation var2 = this.translate(var1);
      ListAdapter var3 = var2.targetAdapter;
      int var4 = var2.translatedIndex;
      return var3.getItemId(var4);
   }

   public int getItemViewType(int var1) {
      AggregatedAdapter.IndexTranslation var2 = this.translate(var1);
      ListAdapter var3 = var2.targetAdapter;
      int var4 = var2.translatedIndex;
      return var3.getItemViewType(var4);
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      AggregatedAdapter.IndexTranslation var4 = this.translate(var1);
      ListAdapter var5 = var4.targetAdapter;
      int var6 = var4.translatedIndex;
      return var5.getView(var6, var2, var3);
   }

   public boolean hasStableIds() {
      this.refreshCachedData();
      return this.mCachedHasStableIds;
   }

   public boolean isEmpty() {
      this.refreshCachedData();
      boolean var1;
      if(this.mCachedCount == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isEnabled(int var1) {
      AggregatedAdapter.IndexTranslation var2 = this.translate(var1);
      ListAdapter var3 = var2.targetAdapter;
      int var4 = var2.translatedIndex;
      return var3.isEnabled(var4);
   }

   public void registerDataSetObserver(DataSetObserver var1) {
      this.mDataSetObservable.registerObserver(var1);
   }

   public void unregisterDataSetObserver(DataSetObserver var1) {
      this.mDataSetObservable.unregisterObserver(var1);
   }

   class 1 extends DataSetObserver {

      1() {}

      public void onChanged() {
         boolean var1 = (boolean)(AggregatedAdapter.this.mDirty = (boolean)1);
         AggregatedAdapter.this.mDataSetObservable.notifyChanged();
      }

      public void onInvalidated() {
         boolean var1 = (boolean)(AggregatedAdapter.this.mDirty = (boolean)1);
         AggregatedAdapter.this.mDataSetObservable.notifyInvalidated();
      }
   }

   private static class IndexTranslation {

      private ListAdapter targetAdapter;
      private int translatedIndex;


      private IndexTranslation(ListAdapter var1, int var2) {
         this.targetAdapter = var1;
         this.translatedIndex = var2;
      }

      // $FF: synthetic method
      IndexTranslation(ListAdapter var1, int var2, AggregatedAdapter.1 var3) {
         this(var1, var2);
      }
   }
}
