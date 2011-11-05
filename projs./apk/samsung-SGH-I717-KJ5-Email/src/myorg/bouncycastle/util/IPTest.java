package myorg.bouncycastle.util;

import junit.framework.TestCase;
import myorg.bouncycastle.util.IPAddress;

public class IPTest extends TestCase {

   private static final String[] invalidIP4v;
   private static final String[] invalidIP6v;
   private static final String[] validIP4v;
   private static final String[] validIP6v;


   static {
      String[] var0 = new String[]{"0.0.0.0", "255.255.255.255", "192.168.0.0"};
      validIP4v = var0;
      String[] var1 = new String[]{"0.0.0.0.1", "256.255.255.255", "1", "A.B.C", "1:.4.6.5"};
      invalidIP4v = var1;
      String[] var2 = new String[]{"0:0:0:0:0:0:0:0", "FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF", "0:1:2:3:FFFF:5:FFFF:1"};
      validIP6v = var2;
      String[] var3 = new String[]{"0.0.0.0:1", "FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFFF"};
      invalidIP6v = var3;
   }

   public IPTest() {}

   private void testIP(String[] var1, String[] var2) {
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 >= var4) {
            int var7 = 0;

            while(true) {
               int var8 = var2.length;
               if(var7 >= var8) {
                  return;
               }

               if(IPAddress.isValid(var2[var7])) {
                  StringBuilder var9 = (new StringBuilder()).append("Invalid input string accepted: ");
                  String var10 = var2[var7];
                  fail(var9.append(var10).append(".").toString());
               }

               ++var7;
            }
         }

         if(!IPAddress.isValid(var1[var3])) {
            StringBuilder var5 = (new StringBuilder()).append("Valid input string not accepted: ");
            String var6 = var1[var3];
            fail(var5.append(var6).append(".").toString());
         }

         ++var3;
      }
   }

   public String getName() {
      return "IPTest";
   }

   public void testIPv4() {
      String[] var1 = validIP4v;
      String[] var2 = invalidIP4v;
      this.testIP(var1, var2);
   }

   public void testIPv6() {
      String[] var1 = validIP6v;
      String[] var2 = invalidIP6v;
      this.testIP(var1, var2);
   }
}
