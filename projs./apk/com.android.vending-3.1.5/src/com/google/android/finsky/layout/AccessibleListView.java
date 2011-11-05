package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ListView;

public class AccessibleListView extends ListView {

   public AccessibleListView(Context var1) {
      super(var1);
   }

   public AccessibleListView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public AccessibleListView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1) {
      boolean var2 = super.dispatchPopulateAccessibilityEvent(var1);
      View var3 = this.getSelectedView();
      boolean var5;
      if(var3 != null) {
         View var4 = var3.findFocus();
         if(var4 == null) {
            var5 = var2;
            return var5;
         }

         var1.getText().clear();
         var2 = var4.dispatchPopulateAccessibilityEvent(var1);
      }

      var5 = var2;
      return var5;
   }
}
