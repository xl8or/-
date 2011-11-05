package com.htc.android.mail;

import android.text.TextUtils;
import com.htc.widget.HtcListItemSeparable;

public class HtcSimpleSeparable implements HtcListItemSeparable {

   protected boolean mDrawOnThis;
   protected String mSeparateID;


   public HtcSimpleSeparable(String var1) {
      this.mSeparateID = var1;
      this.mDrawOnThis = (boolean)1;
   }

   public HtcSimpleSeparable(String var1, boolean var2) {
      this.mSeparateID = var1;
      this.mDrawOnThis = var2;
   }

   public final String getSeparateID() {
      return this.mSeparateID;
   }

   public final boolean getShouldDrawOnThis() {
      return this.mDrawOnThis;
   }

   public void setSeparateID(String var1) {
      this.mSeparateID = var1;
   }

   public void setSeparateID(boolean var1) {
      this.mDrawOnThis = var1;
   }

   public boolean shouldDrawOnThis() {
      return this.mDrawOnThis;
   }

   public boolean shouldSeparate(Object var1) {
      boolean var2;
      if(var1 == null) {
         var2 = true;
      } else if(!(var1 instanceof HtcSimpleSeparable)) {
         var2 = true;
      } else {
         String var3 = ((HtcSimpleSeparable)var1).getSeparateID();
         if(this.mSeparateID == null && var3 == null) {
            var2 = false;
         } else if((this.mSeparateID != null || var3 == null) && (this.mSeparateID == null || var3 != null)) {
            if(TextUtils.isEmpty(this.mSeparateID) && TextUtils.isEmpty(var3)) {
               var2 = false;
            } else if((!TextUtils.isEmpty(this.mSeparateID) || TextUtils.isEmpty(var3)) && (TextUtils.isEmpty(this.mSeparateID) || !TextUtils.isEmpty(var3))) {
               if(!this.mSeparateID.equals(var3)) {
                  var2 = true;
               } else {
                  var2 = false;
               }
            } else {
               var2 = true;
            }
         } else {
            var2 = true;
         }
      }

      return var2;
   }
}
