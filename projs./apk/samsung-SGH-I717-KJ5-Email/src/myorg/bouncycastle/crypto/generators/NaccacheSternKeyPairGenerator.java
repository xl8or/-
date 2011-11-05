package myorg.bouncycastle.crypto.generators;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.NaccacheSternKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.NaccacheSternKeyParameters;
import myorg.bouncycastle.crypto.params.NaccacheSternPrivateKeyParameters;

public class NaccacheSternKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {

   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static int[] smallPrimes = new int[]{3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557};
   private NaccacheSternKeyGenerationParameters param;


   public NaccacheSternKeyPairGenerator() {}

   private static Vector findFirstPrimes(int var0) {
      Vector var1 = new Vector(var0);

      for(int var2 = 0; var2 != var0; ++var2) {
         BigInteger var3 = BigInteger.valueOf((long)smallPrimes[var2]);
         var1.addElement(var3);
      }

      return var1;
   }

   private static BigInteger generatePrime(int var0, int var1, SecureRandom var2) {
      BigInteger var3;
      for(var3 = new BigInteger(var0, var1, var2); var3.bitLength() != var0; var3 = new BigInteger(var0, var1, var2)) {
         ;
      }

      return var3;
   }

   private static int getInt(SecureRandom var0, int var1) {
      int var6;
      if((-var1 & var1) == var1) {
         long var2 = (long)var1;
         long var4 = (long)(var0.nextInt() & Integer.MAX_VALUE);
         var6 = (int)(var2 * var4 >> 31);
      } else {
         int var8;
         int var9;
         int var10;
         do {
            int var7 = var0.nextInt() & Integer.MAX_VALUE;
            var8 = var7 % var1;
            var9 = var7 - var8;
            var10 = var1 - 1;
         } while(var9 + var10 < 0);

         var6 = var8;
      }

      return var6;
   }

   private static Vector permuteList(Vector var0, SecureRandom var1) {
      Vector var2 = new Vector();
      Vector var3 = new Vector();
      int var4 = 0;

      while(true) {
         int var5 = var0.size();
         if(var4 >= var5) {
            Object var7 = var3.elementAt(0);
            var2.addElement(var7);
            var3.removeElementAt(0);

            while(var3.size() != 0) {
               Object var8 = var3.elementAt(0);
               int var9 = var2.size() + 1;
               int var10 = getInt(var1, var9);
               var2.insertElementAt(var8, var10);
               var3.removeElementAt(0);
            }

            return var2;
         }

         Object var6 = var0.elementAt(var4);
         var3.addElement(var6);
         ++var4;
      }
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      int var1 = this.param.getStrength();
      SecureRandom var2 = this.param.getRandom();
      int var3 = this.param.getCertainty();
      boolean var4 = this.param.isDebug();
      if(var4) {
         PrintStream var5 = System.out;
         StringBuilder var6 = (new StringBuilder()).append("Fetching first ");
         int var7 = this.param.getCntSmallPrimes();
         String var8 = var6.append(var7).append(" primes.").toString();
         var5.println(var8);
      }

      Vector var9 = findFirstPrimes(this.param.getCntSmallPrimes());
      Vector var11 = permuteList(var9, var2);
      BigInteger var12 = ONE;
      BigInteger var13 = ONE;
      int var14 = 0;

      while(true) {
         int var15 = var11.size() / 2;
         if(var14 >= var15) {
            int var23 = var11.size() / 2;

            while(true) {
               int var24 = var11.size();
               if(var23 >= var24) {
                  BigInteger var32 = var12.multiply(var13);
                  int var33 = var32.bitLength();
                  int var34 = var1 - var33 - 48;
                  int var35 = var34 / 2 + 1;
                  BigInteger var38 = generatePrime(var35, var3, var2);
                  int var39 = var34 / 2 + 1;
                  BigInteger var42 = generatePrime(var39, var3, var2);
                  long var43 = 0L;
                  if(var4) {
                     System.out.println("generating p and q");
                  }

                  BigInteger var47 = var38.multiply(var12).shiftLeft(1);
                  BigInteger var50 = var42.multiply(var13).shiftLeft(1);

                  while(true) {
                     BigInteger var54;
                     BigInteger var59;
                     do {
                        ++var43;
                        byte var51 = 24;
                        var54 = generatePrime(var51, var3, var2);
                        BigInteger var57 = var54.multiply(var47);
                        BigInteger var58 = ONE;
                        var59 = var57.add(var58);
                     } while(!var59.isProbablePrime(var3));

                     while(true) {
                        byte var62 = 24;
                        BigInteger var65 = generatePrime(var62, var3, var2);
                        if(!var54.equals(var65)) {
                           BigInteger var70 = var65.multiply(var50);
                           BigInteger var71 = ONE;
                           BigInteger var72 = var70.add(var71);
                           if(var72.isProbablePrime(var3)) {
                              BigInteger var77 = var54.multiply(var65);
                              BigInteger var80 = var32.gcd(var77);
                              BigInteger var81 = ONE;
                              if(var80.equals(var81)) {
                                 int var84 = var59.multiply(var72).bitLength();
                                 if(var84 >= var1) {
                                    if(var4) {
                                       PrintStream var94 = System.out;
                                       StringBuilder var95 = (new StringBuilder()).append("needed ");
                                       String var98 = var95.append(var43).append(" tries to generate p and q.").toString();
                                       var94.println(var98);
                                    }

                                    BigInteger var101 = var59.multiply(var72);
                                    BigInteger var102 = ONE;
                                    BigInteger var105 = var59.subtract(var102);
                                    BigInteger var106 = ONE;
                                    BigInteger var109 = var72.subtract(var106);
                                    BigInteger var110 = var105.multiply(var109);
                                    long var111 = 0L;
                                    if(var4) {
                                       System.out.println("generating g");
                                    }

                                    label108:
                                    while(true) {
                                       Vector var113 = new Vector();
                                       int var114 = 0;

                                       while(true) {
                                          int var115 = var11.size();
                                          BigInteger var124;
                                          if(var114 == var115) {
                                             var124 = ONE;
                                             int var136 = 0;

                                             while(true) {
                                                int var137 = var11.size();
                                                if(var136 >= var137) {
                                                   boolean var152 = false;
                                                   var23 = 0;

                                                   while(true) {
                                                      int var153 = var11.size();
                                                      if(var23 >= var153) {
                                                         break;
                                                      }

                                                      BigInteger var158 = (BigInteger)var11.elementAt(var23);
                                                      BigInteger var161 = var110.divide(var158);
                                                      BigInteger var162 = var124.modPow(var161, var101);
                                                      BigInteger var163 = ONE;
                                                      if(var162.equals(var163)) {
                                                         if(var4) {
                                                            PrintStream var164 = System.out;
                                                            StringBuilder var165 = (new StringBuilder()).append("g has order phi(n)/");
                                                            Object var168 = var11.elementAt(var23);
                                                            String var169 = var165.append(var168).append("\n g: ").append(var124).toString();
                                                            var164.println(var169);
                                                         }

                                                         var152 = true;
                                                         break;
                                                      }

                                                      ++var23;
                                                   }

                                                   if(!var152) {
                                                      BigInteger var170 = BigInteger.valueOf(4L);
                                                      BigInteger var171 = var110.divide(var170);
                                                      BigInteger var172 = var124.modPow(var171, var101);
                                                      BigInteger var173 = ONE;
                                                      if(var172.equals(var173)) {
                                                         if(var4) {
                                                            PrintStream var174 = System.out;
                                                            String var175 = "g has order phi(n)/4\n g:" + var124;
                                                            var174.println(var175);
                                                         }
                                                      } else {
                                                         BigInteger var178 = var110.divide(var54);
                                                         BigInteger var179 = var124.modPow(var178, var101);
                                                         BigInteger var180 = ONE;
                                                         if(var179.equals(var180)) {
                                                            if(var4) {
                                                               PrintStream var181 = System.out;
                                                               String var182 = "g has order phi(n)/p\'\n g: " + var124;
                                                               var181.println(var182);
                                                            }
                                                         } else {
                                                            BigInteger var185 = var110.divide(var65);
                                                            BigInteger var186 = var124.modPow(var185, var101);
                                                            BigInteger var187 = ONE;
                                                            if(var186.equals(var187)) {
                                                               if(var4) {
                                                                  PrintStream var188 = System.out;
                                                                  String var189 = "g has order phi(n)/q\'\n g: " + var124;
                                                                  var188.println(var189);
                                                               }
                                                            } else {
                                                               BigInteger var190 = var110.divide(var38);
                                                               BigInteger var191 = var124.modPow(var190, var101);
                                                               BigInteger var192 = ONE;
                                                               if(var191.equals(var192)) {
                                                                  if(var4) {
                                                                     PrintStream var193 = System.out;
                                                                     String var194 = "g has order phi(n)/a\n g: " + var124;
                                                                     var193.println(var194);
                                                                  }
                                                               } else {
                                                                  BigInteger var195 = var110.divide(var42);
                                                                  BigInteger var196 = var124.modPow(var195, var101);
                                                                  BigInteger var197 = ONE;
                                                                  if(!var196.equals(var197)) {
                                                                     if(var4) {
                                                                        PrintStream var200 = System.out;
                                                                        StringBuilder var201 = (new StringBuilder()).append("needed ");
                                                                        String var204 = var201.append(var111).append(" tries to generate g").toString();
                                                                        var200.println(var204);
                                                                        System.out.println();
                                                                        System.out.println("found new NaccacheStern cipher variables:");
                                                                        PrintStream var205 = System.out;
                                                                        String var206 = "smallPrimes: " + var11;
                                                                        var205.println(var206);
                                                                        PrintStream var207 = System.out;
                                                                        StringBuilder var208 = (new StringBuilder()).append("sigma:...... ");
                                                                        StringBuilder var210 = var208.append(var32).append(" (");
                                                                        int var211 = var32.bitLength();
                                                                        String var212 = var210.append(var211).append(" bits)").toString();
                                                                        var207.println(var212);
                                                                        PrintStream var213 = System.out;
                                                                        String var214 = "a:.......... " + var38;
                                                                        var213.println(var214);
                                                                        PrintStream var215 = System.out;
                                                                        String var216 = "b:.......... " + var42;
                                                                        var215.println(var216);
                                                                        PrintStream var217 = System.out;
                                                                        StringBuilder var218 = (new StringBuilder()).append("p\':......... ");
                                                                        String var220 = var218.append(var54).toString();
                                                                        var217.println(var220);
                                                                        PrintStream var221 = System.out;
                                                                        StringBuilder var222 = (new StringBuilder()).append("q\':......... ");
                                                                        String var224 = var222.append(var65).toString();
                                                                        var221.println(var224);
                                                                        PrintStream var225 = System.out;
                                                                        StringBuilder var226 = (new StringBuilder()).append("p:.......... ");
                                                                        String var228 = var226.append(var59).toString();
                                                                        var225.println(var228);
                                                                        PrintStream var229 = System.out;
                                                                        StringBuilder var230 = (new StringBuilder()).append("q:.......... ");
                                                                        String var232 = var230.append(var72).toString();
                                                                        var229.println(var232);
                                                                        PrintStream var233 = System.out;
                                                                        String var234 = "n:.......... " + var101;
                                                                        var233.println(var234);
                                                                        PrintStream var235 = System.out;
                                                                        String var236 = "phi(n):..... " + var110;
                                                                        var235.println(var236);
                                                                        PrintStream var237 = System.out;
                                                                        String var238 = "g:.......... " + var124;
                                                                        var237.println(var238);
                                                                        System.out.println();
                                                                     }

                                                                     int var239 = var32.bitLength();
                                                                     NaccacheSternKeyParameters var240 = new NaccacheSternKeyParameters((boolean)0, var124, var101, var239);
                                                                     int var241 = var32.bitLength();
                                                                     NaccacheSternPrivateKeyParameters var242 = new NaccacheSternPrivateKeyParameters(var124, var101, var241, var11, var110);
                                                                     return new AsymmetricCipherKeyPair(var240, var242);
                                                                  }

                                                                  if(var4) {
                                                                     PrintStream var198 = System.out;
                                                                     String var199 = "g has order phi(n)/b\n g: " + var124;
                                                                     var198.println(var199);
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                   continue label108;
                                                }

                                                BigInteger var140 = (BigInteger)var113.elementAt(var136);
                                                BigInteger var143 = (BigInteger)var11.elementAt(var136);
                                                BigInteger var146 = var32.divide(var143);
                                                BigInteger var150 = var140.modPow(var146, var101);
                                                BigInteger var151 = var124.multiply(var150).mod(var101);
                                                ++var136;
                                             }
                                          }

                                          BigInteger var120 = (BigInteger)var11.elementAt(var114);
                                          BigInteger var123 = var110.divide(var120);

                                          BigInteger var133;
                                          BigInteger var132;
                                          do {
                                             ++var111;
                                             var124 = new BigInteger(var1, var3, var2);
                                             var132 = var124.modPow(var123, var101);
                                             var133 = ONE;
                                          } while(var132.equals(var133));

                                          var113.addElement(var124);
                                          ++var114;
                                       }
                                    }
                                 }

                                 if(var4) {
                                    PrintStream var86 = System.out;
                                    StringBuilder var87 = (new StringBuilder()).append("key size too small. Should be ");
                                    StringBuilder var89 = var87.append(var1).append(" but is actually ");
                                    int var92 = var59.multiply(var72).bitLength();
                                    String var93 = var89.append(var92).toString();
                                    var86.println(var93);
                                 }
                              }
                              break;
                           }
                        }
                     }
                  }
               }

               BigInteger var29 = (BigInteger)var11.elementAt(var23);
               var13 = var13.multiply(var29);
               ++var23;
            }
         }

         BigInteger var20 = (BigInteger)var11.elementAt(var14);
         var12 = var12.multiply(var20);
         ++var14;
      }
   }

   public void init(KeyGenerationParameters var1) {
      NaccacheSternKeyGenerationParameters var2 = (NaccacheSternKeyGenerationParameters)var1;
      this.param = var2;
   }
}
