package com.google.android.finsky.local;


public enum AutoUpdateState {

   // $FF: synthetic field
   private static final AutoUpdateState[] $VALUES;
   DEFAULT("DEFAULT", 0),
   DISABLED("DISABLED", 1),
   ENABLED("ENABLED", 2);


   static {
      AutoUpdateState[] var0 = new AutoUpdateState[3];
      AutoUpdateState var1 = DEFAULT;
      var0[0] = var1;
      AutoUpdateState var2 = DISABLED;
      var0[1] = var2;
      AutoUpdateState var3 = ENABLED;
      var0[2] = var3;
      $VALUES = var0;
   }

   private AutoUpdateState(String var1, int var2) {}
}
