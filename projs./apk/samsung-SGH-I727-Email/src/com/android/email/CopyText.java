package com.android.email;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import com.android.email.TextSelector;
import com.android.email.ToolTipItem;

class CopyText extends ToolTipItem implements OnClickListener {

   static final String LOGTAG = "CopyText";
   TextSelector mTextSelector = null;


   CopyText(TextSelector var1, int var2) {
      if(var1 == null) {
         int var3 = Log.e("CopyText", "CopyText : ts is null");
      } else {
         String var4 = var1.getTopWindow().getContext().getString(var2);
         this.mstrText = var4;
         StringBuilder var5 = (new StringBuilder()).append("CopyText : String - ");
         String var6 = this.mstrText;
         String var7 = var5.append(var6).toString();
         int var8 = Log.v("CopyText", var7);
      }

      this.mTextSelector = var1;
   }

   public void onClick(View var1) {
      this.onItemSelected();
   }

   public void onItemSelected() {
      if(this.mTextSelector != null) {
         WebView var1 = this.mTextSelector.getTopWindow();
         if(var1 != null) {
            if(var1.getWebTextSelectionControls() != null) {
               boolean var2 = var1.getWebTextSelectionControls().copy();
               this.mTextSelector.clearSelection();
            }
         }
      }
   }
}
