package myorg.bouncycastle.jce.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Permission;
import myorg.bouncycastle.jce.ProviderConfigurationPermission;
import myorg.bouncycastle.jce.provider.asymmetric.ec.EC5Util;
import myorg.bouncycastle.jce.spec.ECParameterSpec;

public class ProviderUtil {

   private static Permission BC_EC_LOCAL_PERMISSION = new ProviderConfigurationPermission("myBC", "threadLocalEcImplicitlyCa");
   private static Permission BC_EC_PERMISSION = new ProviderConfigurationPermission("myBC", "ecImplicitlyCa");
   private static final long MAX_MEMORY = Runtime.getRuntime().maxMemory();
   private static volatile ECParameterSpec ecImplicitCaParams;
   private static ThreadLocal threadSpec = new ThreadLocal();


   public ProviderUtil() {}

   public static ECParameterSpec getEcImplicitlyCa() {
      ECParameterSpec var0 = (ECParameterSpec)threadSpec.get();
      ECParameterSpec var1;
      if(var0 != null) {
         var1 = var0;
      } else {
         var1 = ecImplicitCaParams;
      }

      return var1;
   }

   static int getReadLimit(InputStream var0) throws IOException {
      int var1;
      if(var0 instanceof ByteArrayInputStream) {
         var1 = var0.available();
      } else if(MAX_MEMORY > 2147483647L) {
         var1 = Integer.MAX_VALUE;
      } else {
         var1 = (int)MAX_MEMORY;
      }

      return var1;
   }

   static void setParameter(String var0, Object var1) {
      SecurityManager var2 = System.getSecurityManager();
      if(var0.equals("threadLocalEcImplicitlyCa")) {
         if(var2 != null) {
            Permission var3 = BC_EC_LOCAL_PERMISSION;
            var2.checkPermission(var3);
         }

         ECParameterSpec var4;
         if(!(var1 instanceof ECParameterSpec) && var1 != null) {
            var4 = EC5Util.convertSpec((java.security.spec.ECParameterSpec)var1, (boolean)0);
         } else {
            var4 = (ECParameterSpec)var1;
         }

         if(var4 == null) {
            threadSpec.remove();
         } else {
            threadSpec.set(var4);
         }
      } else if(var0.equals("ecImplicitlyCa")) {
         if(var2 != null) {
            Permission var5 = BC_EC_PERMISSION;
            var2.checkPermission(var5);
         }

         if(!(var1 instanceof ECParameterSpec) && var1 != null) {
            ecImplicitCaParams = EC5Util.convertSpec((java.security.spec.ECParameterSpec)var1, (boolean)0);
         } else {
            ecImplicitCaParams = (ECParameterSpec)var1;
         }
      }
   }
}
