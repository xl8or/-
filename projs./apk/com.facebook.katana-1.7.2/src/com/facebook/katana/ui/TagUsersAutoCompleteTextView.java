package com.facebook.katana.ui;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.activity.media.UserHolder;

public class TagUsersAutoCompleteTextView extends AutoCompleteTextView {

   public TagUsersAutoCompleteTextView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   protected CharSequence convertSelectionToString(Object var1) {
      return ((UserHolder)var1).getDisplayName();
   }

   public boolean enoughToFilter() {
      return true;
   }

   protected void onFocusChanged(boolean var1, int var2, Rect var3) {
      super.onFocusChanged(var1, var2, var3);
      if(var1) {
         Editable var4 = this.getText();
         this.performFiltering(var4, 0);
      }
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      byte var5;
      if(var2.getKeyCode() == 66) {
         if(this.getAdapter().getCount() == 1) {
            OnItemClickListener var3 = this.getOnItemClickListener();
            Object var4 = null;
            var3.onItemClick((AdapterView)null, (View)var4, 0, 65535L);
            this.setText((CharSequence)null);
         }

         var5 = 1;
      } else {
         var5 = super.onKeyDown(var1, var2);
      }

      return (boolean)var5;
   }
}
