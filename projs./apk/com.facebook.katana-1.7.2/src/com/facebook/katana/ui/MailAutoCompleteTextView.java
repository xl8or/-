package com.facebook.katana.ui;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MailAutoCompleteTextView extends AutoCompleteTextView {

   public MailAutoCompleteTextView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   protected CharSequence convertSelectionToString(Object var1) {
      Cursor var2 = (Cursor)var1;
      int var3 = var2.getColumnIndexOrThrow("display_name");
      return var2.getString(var3);
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      byte var6;
      if(var2.getKeyCode() == 66) {
         Cursor var3 = ((CursorAdapter)this.getAdapter()).getCursor();
         if(var3 != null && var3.getCount() == 1) {
            OnItemClickListener var4 = this.getOnItemClickListener();
            Object var5 = null;
            var4.onItemClick((AdapterView)null, (View)var5, 0, 65535L);
            this.setText((CharSequence)null);
         }

         var6 = 1;
      } else {
         var6 = super.onKeyDown(var1, var2);
      }

      return (boolean)var6;
   }
}
