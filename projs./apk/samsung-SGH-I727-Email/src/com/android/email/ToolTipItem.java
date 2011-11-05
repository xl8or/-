package com.android.email;

import android.graphics.Rect;

class ToolTipItem {

   static final int TYPE_IMG = 1;
   static final int TYPE_STRING;
   Rect bounds;
   String mstrText;
   int type;


   ToolTipItem() {
      this("ToolTip");
   }

   ToolTipItem(String var1) {
      this.type = 0;
      this.mstrText = var1;
      Rect var2 = new Rect();
      this.bounds = var2;
   }

   public void onItemSelected() {}
}
