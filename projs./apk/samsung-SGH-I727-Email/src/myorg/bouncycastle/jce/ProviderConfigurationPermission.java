package myorg.bouncycastle.jce;

import java.security.BasicPermission;
import java.security.Permission;
import java.util.StringTokenizer;
import myorg.bouncycastle.util.Strings;

public class ProviderConfigurationPermission extends BasicPermission {

   private static final int ALL = 3;
   private static final String ALL_STR = "all";
   private static final int EC_IMPLICITLY_CA = 2;
   private static final String EC_IMPLICITLY_CA_STR = "ecimplicitlyca";
   private static final int THREAD_LOCAL_EC_IMPLICITLY_CA = 1;
   private static final String THREAD_LOCAL_EC_IMPLICITLY_CA_STR = "threadlocalecimplicitlyca";
   private final String actions;
   private final int permissionMask;


   public ProviderConfigurationPermission(String var1) {
      super(var1);
      this.actions = "all";
      this.permissionMask = 3;
   }

   public ProviderConfigurationPermission(String var1, String var2) {
      super(var1, var2);
      this.actions = var2;
      int var3 = this.calculateMask(var2);
      this.permissionMask = var3;
   }

   private int calculateMask(String var1) {
      String var2 = Strings.toLowerCase(var1);
      StringTokenizer var3 = new StringTokenizer(var2, " ,");
      int var4 = 0;

      while(var3.hasMoreTokens()) {
         String var5 = var3.nextToken();
         if(var5.equals("threadlocalecimplicitlyca")) {
            var4 |= 1;
         } else if(var5.equals("ecimplicitlyca")) {
            var4 |= 2;
         } else if(var5.equals("all")) {
            var4 |= 3;
         }
      }

      if(var4 == 0) {
         throw new IllegalArgumentException("unknown permissions passed to mask");
      } else {
         return var4;
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 instanceof ProviderConfigurationPermission) {
         ProviderConfigurationPermission var3 = (ProviderConfigurationPermission)var1;
         int var4 = this.permissionMask;
         int var5 = var3.permissionMask;
         if(var4 == var5) {
            String var6 = this.getName();
            String var7 = var3.getName();
            if(var6.equals(var7)) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String getActions() {
      return this.actions;
   }

   public int hashCode() {
      int var1 = this.getName().hashCode();
      int var2 = this.permissionMask;
      return var1 + var2;
   }

   public boolean implies(Permission var1) {
      boolean var2;
      if(!(var1 instanceof ProviderConfigurationPermission)) {
         var2 = false;
      } else {
         String var3 = this.getName();
         String var4 = var1.getName();
         if(!var3.equals(var4)) {
            var2 = false;
         } else {
            ProviderConfigurationPermission var5 = (ProviderConfigurationPermission)var1;
            int var6 = this.permissionMask;
            int var7 = var5.permissionMask;
            int var8 = var6 & var7;
            int var9 = var5.permissionMask;
            if(var8 == var9) {
               var2 = true;
            } else {
               var2 = false;
            }
         }
      }

      return var2;
   }
}
