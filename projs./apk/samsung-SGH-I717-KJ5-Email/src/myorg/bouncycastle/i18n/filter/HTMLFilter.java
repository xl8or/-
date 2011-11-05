package myorg.bouncycastle.i18n.filter;

import myorg.bouncycastle.i18n.filter.Filter;

public class HTMLFilter implements Filter {

   public HTMLFilter() {}

   public String doFilter(String var1) {
      StringBuffer var2 = new StringBuffer(var1);
      int var3 = 0;

      while(true) {
         int var4 = var2.length();
         if(var3 >= var4) {
            return var2.toString();
         }

         switch(var2.charAt(var3)) {
         case 34:
            int var18 = var3 + 1;
            var2.replace(var3, var18, "&#34");
            break;
         case 35:
            int var14 = var3 + 1;
            var2.replace(var3, var14, "&#35");
            break;
         case 36:
         case 42:
         case 44:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 61:
         default:
            var3 += -3;
            break;
         case 37:
            int var22 = var3 + 1;
            var2.replace(var3, var22, "&#37");
            break;
         case 38:
            int var16 = var3 + 1;
            var2.replace(var3, var16, "&#38");
            break;
         case 39:
            int var20 = var3 + 1;
            var2.replace(var3, var20, "&#39");
            break;
         case 40:
            int var10 = var3 + 1;
            var2.replace(var3, var10, "&#40");
            break;
         case 41:
            int var12 = var3 + 1;
            var2.replace(var3, var12, "&#41");
            break;
         case 43:
            int var26 = var3 + 1;
            var2.replace(var3, var26, "&#43");
            break;
         case 45:
            int var28 = var3 + 1;
            var2.replace(var3, var28, "&#45");
            break;
         case 59:
            int var24 = var3 + 1;
            var2.replace(var3, var24, "&#59");
            break;
         case 60:
            int var6 = var3 + 1;
            var2.replace(var3, var6, "&#60");
            break;
         case 62:
            int var8 = var3 + 1;
            var2.replace(var3, var8, "&#62");
         }

         int var5 = var3 + 4;
      }
   }

   public String doFilterUrl(String var1) {
      return this.doFilter(var1);
   }
}
