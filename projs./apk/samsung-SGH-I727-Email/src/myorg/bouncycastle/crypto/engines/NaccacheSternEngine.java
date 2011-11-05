package myorg.bouncycastle.crypto.engines;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Vector;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.params.NaccacheSternKeyParameters;
import myorg.bouncycastle.crypto.params.NaccacheSternPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.util.Arrays;

public class NaccacheSternEngine implements AsymmetricBlockCipher {

   private static BigInteger ONE = BigInteger.valueOf(1L);
   private static BigInteger ZERO = BigInteger.valueOf(0L);
   private boolean debug = 0;
   private boolean forEncryption;
   private NaccacheSternKeyParameters key;
   private Vector[] lookup = null;


   public NaccacheSternEngine() {}

   private static BigInteger chineseRemainder(Vector var0, Vector var1) {
      BigInteger var2 = ZERO;
      BigInteger var3 = ONE;
      int var4 = 0;

      while(true) {
         int var5 = var1.size();
         if(var4 >= var5) {
            int var7 = 0;

            while(true) {
               int var8 = var1.size();
               if(var7 >= var8) {
                  return var2.mod(var3);
               }

               BigInteger var9 = (BigInteger)var1.elementAt(var7);
               BigInteger var10 = var3.divide(var9);
               BigInteger var11 = var10.modInverse(var9);
               BigInteger var12 = var10.multiply(var11);
               BigInteger var13 = (BigInteger)var0.elementAt(var7);
               BigInteger var14 = var12.multiply(var13);
               var2 = var2.add(var14);
               ++var7;
            }
         }

         BigInteger var6 = (BigInteger)var1.elementAt(var4);
         var3 = var3.multiply(var6);
         ++var4;
      }
   }

   public byte[] addCryptedBlocks(byte[] var1, byte[] var2) throws InvalidCipherTextException {
      if(this.forEncryption) {
         label19: {
            int var3 = var1.length;
            int var4 = this.getOutputBlockSize();
            if(var3 <= var4) {
               int var5 = var2.length;
               int var6 = this.getOutputBlockSize();
               if(var5 <= var6) {
                  break label19;
               }
            }

            throw new InvalidCipherTextException("BlockLength too large for simple addition.\n");
         }
      } else {
         label23: {
            int var7 = var1.length;
            int var8 = this.getInputBlockSize();
            if(var7 <= var8) {
               int var9 = var2.length;
               int var10 = this.getInputBlockSize();
               if(var9 <= var10) {
                  break label23;
               }
            }

            throw new InvalidCipherTextException("BlockLength too large for simple addition.\n");
         }
      }

      BigInteger var11 = new BigInteger(1, var1);
      BigInteger var12 = new BigInteger(1, var2);
      BigInteger var13 = var11.multiply(var12);
      BigInteger var14 = this.key.getModulus();
      BigInteger var15 = var13.mod(var14);
      if(this.debug) {
         PrintStream var16 = System.out;
         String var17 = "c(m1) as BigInteger:....... " + var11;
         var16.println(var17);
         PrintStream var18 = System.out;
         String var19 = "c(m2) as BigInteger:....... " + var12;
         var18.println(var19);
         PrintStream var20 = System.out;
         String var21 = "c(m1)*c(m2)%n = c(m1+m2)%n: " + var15;
         var20.println(var21);
      }

      byte[] var22 = this.key.getModulus().toByteArray();
      Arrays.fill(var22, (byte)0);
      byte[] var23 = var15.toByteArray();
      int var24 = var22.length;
      int var25 = var15.toByteArray().length;
      int var26 = var24 - var25;
      int var27 = var15.toByteArray().length;
      System.arraycopy(var23, 0, var22, var26, var27);
      return var22;
   }

   public byte[] encrypt(BigInteger var1) {
      byte[] var2 = this.key.getModulus().toByteArray();
      Arrays.fill(var2, (byte)0);
      BigInteger var3 = this.key.getG();
      BigInteger var4 = this.key.getModulus();
      byte[] var5 = var3.modPow(var1, var4).toByteArray();
      int var6 = var2.length;
      int var7 = var5.length;
      int var8 = var6 - var7;
      int var9 = var5.length;
      System.arraycopy(var5, 0, var2, var8, var9);
      if(this.debug) {
         PrintStream var10 = System.out;
         StringBuilder var11 = (new StringBuilder()).append("Encrypted value is:  ");
         BigInteger var12 = new BigInteger(var2);
         String var13 = var11.append(var12).toString();
         var10.println(var13);
      }

      return var2;
   }

   public int getInputBlockSize() {
      int var1;
      if(this.forEncryption) {
         var1 = (this.key.getLowerSigmaBound() + 7) / 8 - 1;
      } else {
         var1 = this.key.getModulus().toByteArray().length;
      }

      return var1;
   }

   public int getOutputBlockSize() {
      int var1;
      if(this.forEncryption) {
         var1 = this.key.getModulus().toByteArray().length;
      } else {
         var1 = (this.key.getLowerSigmaBound() + 7) / 8 - 1;
      }

      return var1;
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forEncryption = var1;
      if(var2 instanceof ParametersWithRandom) {
         var2 = ((ParametersWithRandom)var2).getParameters();
      }

      NaccacheSternKeyParameters var3 = (NaccacheSternKeyParameters)var2;
      this.key = var3;
      if(!this.forEncryption) {
         if(this.debug) {
            System.out.println("Constructing lookup Array");
         }

         NaccacheSternPrivateKeyParameters var4 = (NaccacheSternPrivateKeyParameters)this.key;
         Vector var5 = var4.getSmallPrimes();
         Vector[] var6 = new Vector[var5.size()];
         this.lookup = var6;
         int var7 = 0;

         while(true) {
            int var8 = var5.size();
            if(var7 >= var8) {
               return;
            }

            BigInteger var9 = (BigInteger)var5.elementAt(var7);
            int var10 = var9.intValue();
            Vector[] var11 = this.lookup;
            Vector var12 = new Vector();
            var11[var7] = var12;
            Vector var13 = this.lookup[var7];
            BigInteger var14 = ONE;
            var13.addElement(var14);
            if(this.debug) {
               PrintStream var15 = System.out;
               String var16 = "Constructing lookup ArrayList for " + var10;
               var15.println(var16);
            }

            BigInteger var17 = ZERO;

            for(int var18 = 1; var18 < var10; ++var18) {
               BigInteger var19 = var4.getPhi_n();
               var17 = var17.add(var19);
               BigInteger var20 = var17.divide(var9);
               Vector var21 = this.lookup[var7];
               BigInteger var22 = var4.getG();
               BigInteger var23 = var4.getModulus();
               BigInteger var24 = var22.modPow(var20, var23);
               var21.addElement(var24);
            }

            ++var7;
         }
      }
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if(this.key == null) {
         throw new IllegalStateException("NaccacheStern engine not initialised");
      } else {
         int var4 = this.getInputBlockSize() + 1;
         if(var3 > var4) {
            throw new DataLengthException("input too large for Naccache-Stern cipher.\n");
         } else {
            if(!this.forEncryption) {
               int var7 = this.getInputBlockSize();
               if(var3 < var7) {
                  throw new InvalidCipherTextException("BlockLength does not match modulus for Naccache-Stern cipher.\n");
               }
            }

            byte[] var13;
            label63: {
               if(var2 == 0) {
                  int var10 = var1.length;
                  if(var3 == var10) {
                     var13 = var1;
                     break label63;
                  }
               }

               var13 = new byte[var3];
               byte var17 = 0;
               System.arraycopy(var1, var2, var13, var17, var3);
            }

            BigInteger var19 = new BigInteger;
            byte var21 = 1;
            var19.<init>(var21, var13);
            if(this.debug) {
               PrintStream var23 = System.out;
               StringBuilder var24 = (new StringBuilder()).append("input as BigInteger: ");
               String var26 = var24.append(var19).toString();
               var23.println(var26);
            }

            byte[] var29;
            if(this.forEncryption) {
               var29 = this.encrypt(var19);
            } else {
               Vector var30 = new Vector();
               NaccacheSternPrivateKeyParameters var31 = (NaccacheSternPrivateKeyParameters)this.key;
               Vector var32 = var31.getSmallPrimes();
               int var33 = 0;

               while(true) {
                  int var34 = var32.size();
                  if(var33 >= var34) {
                     var29 = chineseRemainder(var30, var32).toByteArray();
                     break;
                  }

                  BigInteger var37 = var31.getPhi_n();
                  BigInteger var38 = (BigInteger)var32.elementAt(var33);
                  BigInteger var41 = var37.divide(var38);
                  BigInteger var42 = var31.getModulus();
                  BigInteger var46 = var19.modPow(var41, var42);
                  Vector var47 = this.lookup[var33];
                  int var48 = this.lookup[var33].size();
                  int var49 = ((BigInteger)var32.elementAt(var33)).intValue();
                  if(var48 != var49) {
                     if(this.debug) {
                        PrintStream var52 = System.out;
                        StringBuilder var53 = (new StringBuilder()).append("Prime is ");
                        Object var54 = var32.elementAt(var33);
                        StringBuilder var55 = var53.append(var54).append(", lookup table has size ");
                        int var56 = var47.size();
                        String var57 = var55.append(var56).toString();
                        var52.println(var57);
                     }

                     StringBuilder var58 = (new StringBuilder()).append("Error in lookup Array for ");
                     int var59 = ((BigInteger)var32.elementAt(var33)).intValue();
                     StringBuilder var60 = var58.append(var59).append(": Size mismatch. Expected ArrayList with length ");
                     int var61 = ((BigInteger)var32.elementAt(var33)).intValue();
                     StringBuilder var62 = var60.append(var61).append(" but found ArrayList of length ");
                     int var63 = this.lookup[var33].size();
                     String var64 = var62.append(var63).toString();
                     throw new InvalidCipherTextException(var64);
                  }

                  int var65 = var47.indexOf(var46);
                  char var67 = '\uffff';
                  if(var65 == var67) {
                     if(this.debug) {
                        PrintStream var68 = System.out;
                        StringBuilder var69 = (new StringBuilder()).append("Actual prime is ");
                        Object var70 = var32.elementAt(var33);
                        String var71 = var69.append(var70).toString();
                        var68.println(var71);
                        PrintStream var72 = System.out;
                        StringBuilder var73 = (new StringBuilder()).append("Decrypted value is ");
                        String var75 = var73.append(var46).toString();
                        var72.println(var75);
                        PrintStream var76 = System.out;
                        StringBuilder var77 = (new StringBuilder()).append("LookupList for ");
                        Object var78 = var32.elementAt(var33);
                        StringBuilder var79 = var77.append(var78).append(" with size ");
                        int var80 = this.lookup[var33].size();
                        String var81 = var79.append(var80).append(" is: ").toString();
                        var76.println(var81);
                        int var82 = 0;

                        while(true) {
                           int var83 = this.lookup[var33].size();
                           if(var82 >= var83) {
                              break;
                           }

                           PrintStream var86 = System.out;
                           Vector var87 = this.lookup[var33];
                           Object var89 = var87.elementAt(var82);
                           var86.println(var89);
                           ++var82;
                        }
                     }

                     throw new InvalidCipherTextException("Lookup failed");
                  }

                  BigInteger var90 = BigInteger.valueOf((long)var65);
                  var30.addElement(var90);
                  ++var33;
               }
            }

            return var29;
         }
      }
   }

   public byte[] processData(byte[] var1) throws InvalidCipherTextException {
      if(this.debug) {
         System.out.println();
      }

      int var2 = var1.length;
      int var3 = this.getInputBlockSize();
      byte[] var32;
      if(var2 > var3) {
         int var4 = this.getInputBlockSize();
         int var5 = this.getOutputBlockSize();
         if(this.debug) {
            PrintStream var6 = System.out;
            String var7 = "Input blocksize is:  " + var4 + " bytes";
            var6.println(var7);
            PrintStream var8 = System.out;
            String var9 = "Output blocksize is: " + var5 + " bytes";
            var8.println(var9);
            PrintStream var10 = System.out;
            StringBuilder var11 = (new StringBuilder()).append("Data has length:.... ");
            int var12 = var1.length;
            String var13 = var11.append(var12).append(" bytes").toString();
            var10.println(var13);
         }

         int var14 = 0;
         int var15 = 0;
         byte[] var16 = new byte[(var1.length / var4 + 1) * var5];

         while(true) {
            int var17 = var1.length;
            if(var14 >= var17) {
               byte[] var27 = new byte[var15];
               System.arraycopy(var16, 0, var27, 0, var15);
               if(this.debug) {
                  PrintStream var28 = System.out;
                  StringBuilder var29 = (new StringBuilder()).append("returning ");
                  int var30 = var27.length;
                  String var31 = var29.append(var30).append(" bytes").toString();
                  var28.println(var31);
               }

               var32 = var27;
               break;
            }

            int var18 = var14 + var4;
            int var19 = var1.length;
            byte[] var20;
            if(var18 < var19) {
               var20 = this.processBlock(var1, var14, var4);
               var14 += var4;
            } else {
               int var25 = var1.length - var14;
               var20 = this.processBlock(var1, var14, var25);
               int var26 = var1.length - var14;
               var14 += var26;
            }

            if(this.debug) {
               PrintStream var21 = System.out;
               String var22 = "new datapos is " + var14;
               var21.println(var22);
            }

            if(var20 == null) {
               if(this.debug) {
                  System.out.println("cipher returned null");
               }

               throw new InvalidCipherTextException("cipher returned null");
            }

            int var23 = var20.length;
            System.arraycopy(var20, 0, var16, var15, var23);
            int var24 = var20.length;
            var15 += var24;
         }
      } else {
         if(this.debug) {
            System.out.println("data size is less then input block size, processing directly");
         }

         int var33 = var1.length;
         var32 = this.processBlock(var1, 0, var33);
      }

      return var32;
   }

   public void setDebug(boolean var1) {
      this.debug = var1;
   }
}
