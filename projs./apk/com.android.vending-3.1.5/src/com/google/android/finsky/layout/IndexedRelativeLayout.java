package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.finsky.layout.AccessibleRelativeLayout;

public class IndexedRelativeLayout extends AccessibleRelativeLayout {

   protected int mItemIndex = -1;


   public IndexedRelativeLayout(Context var1) {
      super(var1);
   }

   public IndexedRelativeLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public IndexedRelativeLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public void setItemIndex(int var1) {
      this.mItemIndex = var1;
   }
}
