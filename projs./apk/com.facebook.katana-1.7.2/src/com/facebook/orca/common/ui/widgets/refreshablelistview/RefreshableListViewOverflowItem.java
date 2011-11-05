package com.facebook.orca.common.ui.widgets.refreshablelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class RefreshableListViewOverflowItem extends View {

   public RefreshableListViewOverflowItem(Context var1) {
      super(var1);
      this.init();
   }

   public RefreshableListViewOverflowItem(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init();
   }

   public RefreshableListViewOverflowItem(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init();
   }

   private void init() {
      float var1 = this.getContext().getResources().getDisplayMetrics().density;
      int var2 = (int)(500.0F * var1);
      this.setMinimumHeight(var2);
   }
}
