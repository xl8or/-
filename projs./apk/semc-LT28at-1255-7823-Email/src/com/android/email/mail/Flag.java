package com.android.email.mail;


public enum Flag {

   // $FF: synthetic field
   private static final Flag[] $VALUES;
   ANSWERED("ANSWERED", 2),
   DELETED("DELETED", 0),
   DRAFT("DRAFT", 4),
   FLAGGED("FLAGGED", 3),
   RECENT("RECENT", 5),
   SEEN("SEEN", 1),
   X_DESTROYED("X_DESTROYED", 6),
   X_DOWNLOADED_FULL("X_DOWNLOADED_FULL", 9),
   X_DOWNLOADED_PARTIAL("X_DOWNLOADED_PARTIAL", 10),
   X_SEND_FAILED("X_SEND_FAILED", 7),
   X_SEND_IN_PROGRESS("X_SEND_IN_PROGRESS", 8),
   X_STORE_1("X_STORE_1", 11),
   X_STORE_2("X_STORE_2", 12);


   static {
      Flag[] var0 = new Flag[13];
      Flag var1 = DELETED;
      var0[0] = var1;
      Flag var2 = SEEN;
      var0[1] = var2;
      Flag var3 = ANSWERED;
      var0[2] = var3;
      Flag var4 = FLAGGED;
      var0[3] = var4;
      Flag var5 = DRAFT;
      var0[4] = var5;
      Flag var6 = RECENT;
      var0[5] = var6;
      Flag var7 = X_DESTROYED;
      var0[6] = var7;
      Flag var8 = X_SEND_FAILED;
      var0[7] = var8;
      Flag var9 = X_SEND_IN_PROGRESS;
      var0[8] = var9;
      Flag var10 = X_DOWNLOADED_FULL;
      var0[9] = var10;
      Flag var11 = X_DOWNLOADED_PARTIAL;
      var0[10] = var11;
      Flag var12 = X_STORE_1;
      var0[11] = var12;
      Flag var13 = X_STORE_2;
      var0[12] = var13;
      $VALUES = var0;
   }

   private Flag(String var1, int var2) {}
}
