package com.android.email.activity.setup;

import android.content.Context;
import android.preference.RingtonePreference;
import android.util.AttributeSet;

public class RingToneSelectDialog extends RingtonePreference {

   Context mContext;


   public RingToneSelectDialog(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
   }

   protected void onClick() {}

   void onCreate() {}
}
