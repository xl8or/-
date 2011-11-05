package com.google.android.finsky.layout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;
import java.util.List;

public class AccessibleTextView extends TextView {

   public AccessibleTextView(Context var1) {
      super(var1);
   }

   public AccessibleTextView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public AccessibleTextView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1) {
      if(this.isShown()) {
         CharSequence var2 = this.getText();
         if(TextUtils.isEmpty(var2)) {
            var2 = this.getHint();
         }

         if(!TextUtils.isEmpty(var2)) {
            if(var2.length() > 500) {
               var2 = var2.subSequence(0, 501);
            }

            List var3 = var1.getText();
            String var4 = var2.toString().toLowerCase();
            var3.add(var4);
         }
      }

      return false;
   }
}
