package org.apache.harmony.javax.security.auth;

import java.security.BasicPermission;

public final class AuthPermission extends BasicPermission {

   private static final String CREATE_LOGIN_CONTEXT = "createLoginContext";
   private static final String CREATE_LOGIN_CONTEXT_ANY = "createLoginContext.*";
   private static final long serialVersionUID = 5806031445061587174L;


   public AuthPermission(String var1) {
      String var2 = init(var1);
      super(var2);
   }

   public AuthPermission(String var1, String var2) {
      String var3 = init(var1);
      super(var3, var2);
   }

   private static String init(String var0) {
      if(var0 == null) {
         throw new NullPointerException("auth.13");
      } else {
         String var1;
         if("createLoginContext".equals(var0)) {
            var1 = "createLoginContext.*";
         } else {
            var1 = var0;
         }

         return var1;
      }
   }
}
