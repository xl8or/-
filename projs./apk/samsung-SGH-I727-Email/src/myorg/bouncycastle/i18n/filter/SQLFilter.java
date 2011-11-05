package myorg.bouncycastle.i18n.filter;

import myorg.bouncycastle.i18n.filter.Filter;

public class SQLFilter implements Filter {

   public SQLFilter() {}

   public String doFilter(String var1) {
      StringBuffer var2 = new StringBuffer(var1);
      int var3 = 0;

      while(true) {
         int var4 = var2.length();
         if(var3 >= var4) {
            return var2.toString();
         }

         switch(var2.charAt(var3)) {
         case 10:
            int var21 = var3 + 1;
            var2.replace(var3, var21, "\\n");
            ++var3;
            break;
         case 13:
            int var19 = var3 + 1;
            var2.replace(var3, var19, "\\r");
            ++var3;
            break;
         case 34:
            int var7 = var3 + 1;
            var2.replace(var3, var7, "\\\"");
            ++var3;
            break;
         case 39:
            int var5 = var3 + 1;
            var2.replace(var3, var5, "\\\'");
            ++var3;
            break;
         case 45:
            int var11 = var3 + 1;
            var2.replace(var3, var11, "\\-");
            ++var3;
            break;
         case 47:
            int var13 = var3 + 1;
            var2.replace(var3, var13, "\\/");
            ++var3;
            break;
         case 59:
            int var17 = var3 + 1;
            var2.replace(var3, var17, "\\;");
            ++var3;
            break;
         case 61:
            int var9 = var3 + 1;
            var2.replace(var3, var9, "\\=");
            ++var3;
            break;
         case 92:
            int var15 = var3 + 1;
            var2.replace(var3, var15, "\\\\");
            ++var3;
         }

         ++var3;
      }
   }

   public String doFilterUrl(String var1) {
      return this.doFilter(var1);
   }
}
