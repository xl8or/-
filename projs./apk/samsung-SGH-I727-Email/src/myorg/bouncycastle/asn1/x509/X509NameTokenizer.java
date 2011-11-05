package myorg.bouncycastle.asn1.x509;


public class X509NameTokenizer {

   private StringBuffer buf;
   private int index;
   private char seperator;
   private String value;


   public X509NameTokenizer(String var1) {
      this(var1, ',');
   }

   public X509NameTokenizer(String var1, char var2) {
      StringBuffer var3 = new StringBuffer();
      this.buf = var3;
      this.value = var1;
      this.index = -1;
      this.seperator = var2;
   }

   public boolean hasMoreTokens() {
      int var1 = this.index;
      int var2 = this.value.length();
      boolean var3;
      if(var1 != var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public String nextToken() {
      int var1 = this.index;
      int var2 = this.value.length();
      String var3;
      if(var1 == var2) {
         var3 = null;
      } else {
         int var4 = this.index + 1;
         boolean var5 = false;
         boolean var6 = false;
         this.buf.setLength(0);

         while(true) {
            int var7 = this.value.length();
            if(var4 == var7) {
               break;
            }

            char var8 = this.value.charAt(var4);
            if(var8 == 34) {
               if(!var6) {
                  if(!var5) {
                     boolean var9 = true;
                  } else {
                     boolean var10 = false;
                  }
               } else {
                  this.buf.append(var8);
               }

               var6 = false;
            } else if(!var6 && !var5) {
               if(var8 == 92) {
                  var6 = true;
               } else {
                  char var17 = this.seperator;
                  if(var8 == var17) {
                     break;
                  }

                  this.buf.append(var8);
               }
            } else {
               label44: {
                  if(var8 == 35) {
                     StringBuffer var12 = this.buf;
                     int var13 = this.buf.length() - 1;
                     if(var12.charAt(var13) == 61) {
                        StringBuffer var14 = this.buf.append('\\');
                        break label44;
                     }
                  }

                  if(var8 == 43 && this.seperator != 43) {
                     StringBuffer var16 = this.buf.append('\\');
                  }
               }

               this.buf.append(var8);
               var6 = false;
            }

            ++var4;
         }

         this.index = var4;
         var3 = this.buf.toString().trim();
      }

      return var3;
   }
}
