package com.facebook.katana.model;

import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import java.util.Map;

public class FacebookGroup extends FacebookProfile {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   protected int mUnreadCount;
   @JMAutogen.InferredType(
      jsonFieldName = "update_time"
   )
   public final long mUpdateTime;


   static {
      byte var0;
      if(!FacebookGroup.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public FacebookGroup() {
      Object var2 = null;
      super(65535L, (String)null, (String)var2, 3);
      this.mUpdateTime = 0L;
   }

   protected static void postprocessJMAutogenFields(Map<String, Object> var0) {
      Object var1 = var0.remove("id");
      if(!$assertionsDisabled && var1 == null) {
         throw new AssertionError();
      } else {
         var0.put("gid", var1);
         Object var3 = var0.remove("pic_square");
         if(!$assertionsDisabled && var3 == null) {
            throw new AssertionError();
         } else {
            var0.put("pic_small", var3);
            Object var5 = var0.remove("type");
         }
      }
   }

   public int getUnreadCount() {
      return this.mUnreadCount;
   }

   public void setUnreadCount(int var1) {
      this.mUnreadCount = var1;
   }
}
