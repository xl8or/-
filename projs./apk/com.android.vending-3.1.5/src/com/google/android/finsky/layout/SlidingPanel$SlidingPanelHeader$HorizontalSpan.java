package com.google.android.finsky.layout;

import com.google.android.finsky.layout.SlidingPanel.SlidingPanelHeader;

private class SlidingPanel$SlidingPanelHeader$HorizontalSpan {

   int left;
   int right;
   // $FF: synthetic field
   final SlidingPanelHeader this$1;


   public SlidingPanel$SlidingPanelHeader$HorizontalSpan(SlidingPanelHeader var1, int var2, int var3) {
      this.this$1 = var1;
      this.left = var2;
      this.right = var3;
   }

   public int getCenter() {
      int var1 = this.left;
      int var2 = this.right;
      return (var1 + var2) / 2;
   }

   public boolean intersectsWith(int var1, int var2) {
      boolean var3 = true;
      boolean var4;
      if(this.left > var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      boolean var5;
      if(this.right < var1) {
         var5 = true;
      } else {
         var5 = false;
      }

      if(var4 || var5) {
         var3 = false;
      }

      return var3;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("[");
      int var2 = this.left;
      StringBuilder var3 = var1.append(var2).append(":");
      int var4 = this.right;
      return var3.append(var4).append("]").toString();
   }
}
