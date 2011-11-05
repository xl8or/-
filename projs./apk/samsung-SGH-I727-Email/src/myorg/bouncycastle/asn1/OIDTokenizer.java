package myorg.bouncycastle.asn1;


public class OIDTokenizer {

   private int index;
   private String oid;


   public OIDTokenizer(String var1) {
      this.oid = var1;
      this.index = 0;
   }

   public boolean hasMoreTokens() {
      boolean var1;
      if(this.index != -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String nextToken() {
      String var1;
      if(this.index == -1) {
         var1 = null;
      } else {
         String var2 = this.oid;
         int var3 = this.index;
         int var4 = var2.indexOf(46, var3);
         if(var4 == -1) {
            String var5 = this.oid;
            int var6 = this.index;
            String var7 = var5.substring(var6);
            this.index = -1;
            var1 = var7;
         } else {
            String var8 = this.oid;
            int var9 = this.index;
            String var10 = var8.substring(var9, var4);
            int var11 = var4 + 1;
            this.index = var11;
            var1 = var10;
         }
      }

      return var1;
   }
}
