package com.facebook.katana.ui;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.katana.ui.SectionedListAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SectionedListMultiAdapter extends SectionedListAdapter {

   private List<SectionedListAdapter> mAdapters;
   private int[] mAdaptersPositionStart;
   private int[] mAdaptersViewCountStart;
   private SectionedListMultiAdapter.AdapterDataObserver mObserver;
   private int mSectionsCount;
   private int mViewTypeCount;


   public SectionedListMultiAdapter() {
      ArrayList var1 = new ArrayList();
      this.mAdapters = var1;
      SectionedListMultiAdapter.AdapterDataObserver var2 = new SectionedListMultiAdapter.AdapterDataObserver((SectionedListMultiAdapter.1)null);
      this.mObserver = var2;
   }

   private int getAdapterIndexForSection(int var1) {
      for(int var2 = this.mAdapters.size() - 1; var2 >= 0; var2 += -1) {
         if(this.mAdaptersPositionStart[var2] <= var1) {
            return var2;
         }
      }

      throw new IndexOutOfBoundsException();
   }

   private int getAdapterSectionPosition(int var1, int var2) {
      int var3 = this.mAdaptersPositionStart[var1];
      return var2 - var3;
   }

   private void rebuildAdapterCache() {
      int[] var1 = new int[this.mAdapters.size()];
      this.mAdaptersPositionStart = var1;
      int[] var2 = new int[this.mAdapters.size()];
      this.mAdaptersViewCountStart = var2;
      this.mViewTypeCount = 0;
      this.mSectionsCount = 0;
      if(this.mAdapters.size() != 0) {
         int var3 = 0;

         while(true) {
            int var4 = this.mAdapters.size();
            if(var3 >= var4) {
               return;
            }

            SectionedListAdapter var5 = (SectionedListAdapter)this.mAdapters.get(var3);
            int var6 = var5.getSectionCount();
            int[] var7 = this.mAdaptersPositionStart;
            int var8 = this.mSectionsCount;
            var7[var3] = var8;
            int var9 = this.mSectionsCount + var6;
            this.mSectionsCount = var9;
            int[] var10 = this.mAdaptersViewCountStart;
            int var11 = this.mViewTypeCount;
            var10[var3] = var11;
            int var12 = this.mViewTypeCount;
            int var13 = var5.getViewTypeCount();
            int var14 = var12 + var13;
            this.mViewTypeCount = var14;
            ++var3;
         }
      }
   }

   public void addSectionedAdapter(SectionedListAdapter var1) {
      this.mAdapters.add(var1);
      SectionedListMultiAdapter.AdapterDataObserver var3 = this.mObserver;
      var1.registerDataSetObserver(var3);
      this.rebuildAdapterCache();
      this.notifyDataSetChanged();
   }

   public Object getChild(int var1, int var2) {
      int var3 = this.getAdapterIndexForSection(var1);
      int var4 = this.getAdapterSectionPosition(var3, var1);
      return ((SectionedListAdapter)this.mAdapters.get(var3)).getChild(var4, var2);
   }

   public View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5) {
      int var6 = this.getAdapterIndexForSection(var1);
      int var7 = this.getAdapterSectionPosition(var6, var1);
      SectionedListAdapter var8 = (SectionedListAdapter)this.mAdapters.get(var6);
      return var8.getChildView(var7, var2, var3, var4, var5);
   }

   public int getChildViewType(int var1, int var2) {
      int var3 = this.getAdapterIndexForSection(var1);
      int var4 = this.getAdapterSectionPosition(var3, var1);
      int var5 = this.mAdaptersViewCountStart[var3];
      int var6 = ((SectionedListAdapter)this.mAdapters.get(var3)).getChildViewType(var4, var2);
      return var5 + var6;
   }

   public int getChildrenCount(int var1) {
      int var2 = this.getAdapterIndexForSection(var1);
      int var3 = this.getAdapterSectionPosition(var2, var1);
      return ((SectionedListAdapter)this.mAdapters.get(var2)).getChildrenCount(var3);
   }

   public Object getSection(int var1) {
      int var2 = this.getAdapterIndexForSection(var1);
      int var3 = this.getAdapterSectionPosition(var2, var1);
      return ((SectionedListAdapter)this.mAdapters.get(var2)).getSection(var3);
   }

   public int getSectionCount() {
      return this.mSectionsCount;
   }

   public View getSectionHeaderView(int var1, View var2, ViewGroup var3) {
      int var4 = this.getAdapterIndexForSection(var1);
      int var5 = this.getAdapterSectionPosition(var4, var1);
      return ((SectionedListAdapter)this.mAdapters.get(var4)).getSectionHeaderView(var5, var2, var3);
   }

   public int getSectionHeaderViewType(int var1) {
      int var2 = this.getAdapterIndexForSection(var1);
      int var3 = this.getAdapterSectionPosition(var2, var1);
      int var4 = this.mAdaptersViewCountStart[var2];
      int var5 = ((SectionedListAdapter)this.mAdapters.get(var2)).getSectionHeaderViewType(var3);
      return var4 + var5;
   }

   public int getViewTypeCount() {
      return this.mViewTypeCount;
   }

   public boolean isEmpty() {
      boolean var1 = true;
      Iterator var2 = this.mAdapters.iterator();

      while(var2.hasNext()) {
         SectionedListAdapter var3 = (SectionedListAdapter)var2.next();
         if(var1 && var3.isEmpty()) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }

   public boolean isEnabled(int var1, int var2) {
      return true;
   }

   public void removeSectionedAdapter(SectionedListAdapter var1) {
      SectionedListMultiAdapter.AdapterDataObserver var2 = this.mObserver;
      var1.unregisterDataSetObserver(var2);
      this.mAdapters.remove(var1);
      this.rebuildAdapterCache();
      this.notifyDataSetChanged();
   }

   private class AdapterDataObserver extends DataSetObserver {

      private AdapterDataObserver() {}

      // $FF: synthetic method
      AdapterDataObserver(SectionedListMultiAdapter.1 var2) {
         this();
      }

      public void onChanged() {
         SectionedListMultiAdapter.this.rebuildAdapterCache();
         SectionedListMultiAdapter.this.notifyDataSetChanged();
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
