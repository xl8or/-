package com.facebook.katana.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import com.facebook.katana.ui.SectionedListAdapter;
import com.facebook.katana.ui.SectionedListInternalAdapter;
import com.facebook.katana.view.FacebookListView;

public class SectionedListView extends FacebookListView {

   protected BaseAdapter mInternalAdapter;
   protected SectionedListAdapter mRealAdapter;


   public SectionedListView(Context var1) {
      super(var1);
   }

   public SectionedListView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public SectionedListView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public ListAdapter getAdapter() {
      return this.mInternalAdapter;
   }

   public SectionedListAdapter getSectionedListAdapter() {
      return this.mRealAdapter;
   }

   public void setAdapter(ListAdapter var1) {}

   public void setSectionedListAdapter(SectionedListAdapter var1) {
      if(this.mRealAdapter != null) {
         this.mRealAdapter.setInternalListAdapter((BaseAdapter)null);
      }

      this.mRealAdapter = var1;
      SectionedListInternalAdapter var2 = new SectionedListInternalAdapter(var1);
      this.mInternalAdapter = var2;
      SectionedListAdapter var3 = this.mRealAdapter;
      BaseAdapter var4 = this.mInternalAdapter;
      var3.setInternalListAdapter(var4);
      BaseAdapter var5 = this.mInternalAdapter;
      super.setAdapter(var5);
   }
}
