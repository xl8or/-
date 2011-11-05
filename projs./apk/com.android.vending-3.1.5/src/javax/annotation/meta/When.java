package javax.annotation.meta;


public enum When {

   // $FF: synthetic field
   private static final When[] $VALUES;
   ALWAYS("ALWAYS", 0),
   MAYBE("MAYBE", 2),
   NEVER("NEVER", 3),
   UNKNOWN("UNKNOWN", 1);


   static {
      When[] var0 = new When[4];
      When var1 = ALWAYS;
      var0[0] = var1;
      When var2 = UNKNOWN;
      var0[1] = var2;
      When var3 = MAYBE;
      var0[2] = var3;
      When var4 = NEVER;
      var0[3] = var4;
      $VALUES = var0;
   }

   private When(String var1, int var2) {}
}
