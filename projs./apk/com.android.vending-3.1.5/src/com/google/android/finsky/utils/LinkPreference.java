package com.google.android.finsky.utils;

import android.content.Context;
import android.preference.Preference;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class LinkPreference extends Preference {

   private CharSequence mLink;


   public LinkPreference(Context var1) {
      super(var1);
   }

   public LinkPreference(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public LinkPreference(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void onBindView(View var1) {
      super.onBindView(var1);
      TextView var2 = (TextView)var1.findViewById(2131755225);
      if(var2 != null) {
         CharSequence var3 = this.mLink;
         var2.setText(var3);
         MovementMethod var4 = LinkMovementMethod.getInstance();
         var2.setMovementMethod(var4);
      }
   }

   public void setLink(CharSequence var1) {
      if(var1 != null || this.mLink == null) {
         if(var1 == null) {
            return;
         }

         CharSequence var2 = this.mLink;
         if(var1.equals(var2)) {
            return;
         }
      }

      this.mLink = var1;
      this.notifyChanged();
   }
}
