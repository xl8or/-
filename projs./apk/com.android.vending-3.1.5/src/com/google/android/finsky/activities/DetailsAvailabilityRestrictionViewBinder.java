package com.google.android.finsky.activities;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.utils.FinskyLog;

public class DetailsAvailabilityRestrictionViewBinder {

   public DetailsAvailabilityRestrictionViewBinder() {}

   public void bind(View var1, Document var2, int var3, int var4, DfeToc var5) {
      if(var2.isAvailable(var5)) {
         var1.setVisibility(8);
      } else {
         var1.setVisibility(0);
         TextView var6 = (TextView)var1.findViewById(2131755152);
         int var7 = var2.getAvailabilityRestriction();
         int var8 = 2131231117;
         switch(var7) {
         case 2:
            var8 = 2131231118;
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         default:
            break;
         case 8:
            var8 = 2131231123;
            break;
         case 9:
            var8 = 2131231121;
            break;
         case 10:
            var8 = 2131231119;
            break;
         case 11:
            var8 = 2131231120;
            break;
         case 12:
            var8 = 2131231122;
         }

         var6.setText(var8);
         String var9 = "Item is not available. Reason: " + var7;
         Object[] var10 = new Object[0];
         FinskyLog.d(var9, var10);
         LayoutParams var11 = (LayoutParams)((ImageView)var1.findViewById(2131755151)).getLayoutParams();
         var11.width = var3;
         var11.rightMargin = var4;
      }
   }

   public void init(Context var1) {}

   public void onDestroyView() {}
}
