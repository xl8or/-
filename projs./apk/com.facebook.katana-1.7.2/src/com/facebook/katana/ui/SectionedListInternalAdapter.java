package com.facebook.katana.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import com.facebook.katana.ui.SectionedListAdapter;

class SectionedListInternalAdapter extends BaseAdapter implements SectionIndexer {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   protected final SectionedListAdapter mRealAdapter;


   static {
      byte var0;
      if(!SectionedListInternalAdapter.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public SectionedListInternalAdapter(SectionedListAdapter var1) {
      this.mRealAdapter = var1;
   }

   public int getCount() {
      int var1;
      if(this.mRealAdapter.isEmpty()) {
         var1 = 0;
      } else {
         var1 = this.mRealAdapter.getCount();
      }

      return var1;
   }

   public Object getItem(int var1) {
      return this.mRealAdapter.getItem(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public int getItemViewType(int var1) {
      return this.mRealAdapter.getItemViewType(var1);
   }

   public int getPositionForSection(int var1) {
      return this.mRealAdapter.getPositionForSection(var1);
   }

   public int getSectionForPosition(int var1) {
      int[] var2 = this.mRealAdapter.decodeFlatPosition(var1);
      if(!$assertionsDisabled && var2.length != 2) {
         throw new AssertionError();
      } else {
         return var2[1];
      }
   }

   public Object[] getSections() {
      int var1 = this.mRealAdapter.getSectionCount();
      Object[] var2 = new Object[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         Object var4 = this.mRealAdapter.getSection(var3);
         var2[var3] = var4;
      }

      return var2;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      return this.mRealAdapter.getView(var1, var2, var3);
   }

   public int getViewTypeCount() {
      return this.mRealAdapter.getViewTypeCount();
   }

   public boolean isEnabled(int var1) {
      boolean var2;
      boolean var3;
      try {
         var2 = this.mRealAdapter.isEnabled(var1);
      } catch (ArrayIndexOutOfBoundsException var5) {
         var3 = true;
         return var3;
      }

      var3 = var2;
      return var3;
   }
}
