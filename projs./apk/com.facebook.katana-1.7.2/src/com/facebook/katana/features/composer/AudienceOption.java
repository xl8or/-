package com.facebook.katana.features.composer;


public interface AudienceOption {

   int getIcon();

   String getLabel();

   AudienceOption.Type getType();

   public static enum Type {

      // $FF: synthetic field
      private static final AudienceOption.Type[] $VALUES;
      FRIEND_LIST("FRIEND_LIST", 2),
      GROUP("GROUP", 1),
      PRIVACY_SETTING("PRIVACY_SETTING", 0);


      static {
         AudienceOption.Type[] var0 = new AudienceOption.Type[3];
         AudienceOption.Type var1 = PRIVACY_SETTING;
         var0[0] = var1;
         AudienceOption.Type var2 = GROUP;
         var0[1] = var2;
         AudienceOption.Type var3 = FRIEND_LIST;
         var0[2] = var3;
         $VALUES = var0;
      }

      private Type(String var1, int var2) {}
   }
}
