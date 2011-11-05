package com.facebook.katana.model;

import com.facebook.katana.model.PrivacySetting;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class FriendList extends JMCachingDictDestination {

   public static long INVALID_FBID = 65535L;
   @JMAutogen.InferredType(
      jsonFieldName = "flid"
   )
   public final long flid;
   @JMAutogen.InferredType(
      jsonFieldName = "name"
   )
   public final String name;
   @JMAutogen.InferredType(
      jsonFieldName = "owner"
   )
   public final long owner;


   private FriendList() {
      long var1 = INVALID_FBID;
      this.flid = var1;
      this.name = null;
      long var3 = INVALID_FBID;
      this.owner = var3;
   }

   public FriendList(long param1, String param3, long param4) {
      // $FF: Couldn't be decompiled
   }

   public PrivacySetting toPrivacySetting() {
      String var1 = this.name;
      String var2 = Long.toString(this.flid);
      Object var3 = null;
      Object var4 = null;
      return new PrivacySetting((String)null, "CUSTOM", var1, var2, (String)var3, (String)var4, "SOME_FRIENDS");
   }
}
