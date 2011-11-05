package com.google.android.finsky.layout;

import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.layout.SlidingPanel;
import com.google.android.finsky.layout.SlidingPanel.SlidingPanelHeader;

class SlidingPanel$SlidingPanelHeader$2 implements OnClickListener {

   // $FF: synthetic field
   final SlidingPanelHeader this$1;
   // $FF: synthetic field
   final int val$tabIndex;


   SlidingPanel$SlidingPanelHeader$2(SlidingPanelHeader var1, int var2) {
      this.this$1 = var1;
      this.val$tabIndex = var2;
   }

   public void onClick(View var1) {
      SlidingPanel var2 = this.this$1.this$0;
      SlidingPanel var3 = this.this$1.this$0;
      int var4 = this.val$tabIndex;
      int var5 = SlidingPanel.access$600(var3, var4);
      var2.smoothScrollTo(var5);
      SlidingPanel var6 = this.this$1.this$0;
      int var7 = this.val$tabIndex;
      SlidingPanel.access$1000(var6, var7);
   }
}
