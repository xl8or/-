package org.codehaus.jackson.annotate;


public enum JsonMethod {

   // $FF: synthetic field
   private static final JsonMethod[] $VALUES;
   ALL("ALL", 5),
   CREATOR("CREATOR", 2),
   FIELD("FIELD", 3),
   GETTER("GETTER", 0),
   NONE("NONE", 4),
   SETTER("SETTER", 1);


   static {
      JsonMethod[] var0 = new JsonMethod[6];
      JsonMethod var1 = GETTER;
      var0[0] = var1;
      JsonMethod var2 = SETTER;
      var0[1] = var2;
      JsonMethod var3 = CREATOR;
      var0[2] = var3;
      JsonMethod var4 = FIELD;
      var0[3] = var4;
      JsonMethod var5 = NONE;
      var0[4] = var5;
      JsonMethod var6 = ALL;
      var0[5] = var6;
      $VALUES = var0;
   }

   private JsonMethod(String var1, int var2) {}

   public boolean creatorEnabled() {
      JsonMethod var1 = CREATOR;
      boolean var3;
      if(this != var1) {
         JsonMethod var2 = ALL;
         if(this != var2) {
            var3 = false;
            return var3;
         }
      }

      var3 = true;
      return var3;
   }

   public boolean fieldEnabled() {
      JsonMethod var1 = FIELD;
      boolean var3;
      if(this != var1) {
         JsonMethod var2 = ALL;
         if(this != var2) {
            var3 = false;
            return var3;
         }
      }

      var3 = true;
      return var3;
   }

   public boolean getterEnabled() {
      JsonMethod var1 = GETTER;
      boolean var3;
      if(this != var1) {
         JsonMethod var2 = ALL;
         if(this != var2) {
            var3 = false;
            return var3;
         }
      }

      var3 = true;
      return var3;
   }

   public boolean setterEnabled() {
      JsonMethod var1 = SETTER;
      boolean var3;
      if(this != var1) {
         JsonMethod var2 = ALL;
         if(this != var2) {
            var3 = false;
            return var3;
         }
      }

      var3 = true;
      return var3;
   }
}
