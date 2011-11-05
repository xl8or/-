package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.widget.RelativeLayout;

public class AccessibleRelativeLayout extends RelativeLayout {

   public AccessibleRelativeLayout(Context var1) {
      super(var1);
   }

   public AccessibleRelativeLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public AccessibleRelativeLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1) {
      boolean var2 = super.dispatchPopulateAccessibilityEvent(var1);
      var1.getText().clear();
      return var2;
   }
}
