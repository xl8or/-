package com.facebook.katana.model;


public enum FacebookStreamType {

   // $FF: synthetic field
   private static final FacebookStreamType[] $VALUES;
   EVENT_WALL_STREAM("EVENT_WALL_STREAM", 5),
   GROUP_WALL_STREAM("GROUP_WALL_STREAM", 2),
   NEWSFEED_STREAM("NEWSFEED_STREAM", 0),
   PAGE_WALL_STREAM("PAGE_WALL_STREAM", 4),
   PLACE_ACTIVITY_STREAM("PLACE_ACTIVITY_STREAM", 3),
   PROFILE_WALL_STREAM("PROFILE_WALL_STREAM", 1);


   static {
      FacebookStreamType[] var0 = new FacebookStreamType[6];
      FacebookStreamType var1 = NEWSFEED_STREAM;
      var0[0] = var1;
      FacebookStreamType var2 = PROFILE_WALL_STREAM;
      var0[1] = var2;
      FacebookStreamType var3 = GROUP_WALL_STREAM;
      var0[2] = var3;
      FacebookStreamType var4 = PLACE_ACTIVITY_STREAM;
      var0[3] = var4;
      FacebookStreamType var5 = PAGE_WALL_STREAM;
      var0[4] = var5;
      FacebookStreamType var6 = EVENT_WALL_STREAM;
      var0[5] = var6;
      $VALUES = var0;
   }

   private FacebookStreamType(String var1, int var2) {}
}
