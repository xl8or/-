package myorg.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.ElGamalKeyParameters;
import myorg.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.util.BigIntegers;

public class ElGamalEngine implements AsymmetricBlockCipher {

   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);
   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private int bitSize;
   private boolean forEncryption;
   private ElGamalKeyParameters key;
   private SecureRandom random;


   public ElGamalEngine() {}

   public int getInputBlockSize() {
      int var1;
      if(this.forEncryption) {
         var1 = (this.bitSize - 1) / 8;
      } else {
         var1 = (this.bitSize + 7) / 8 * 2;
      }

      return var1;
   }

   public int getOutputBlockSize() {
      int var1;
      if(this.forEncryption) {
         var1 = (this.bitSize + 7) / 8 * 2;
      } else {
         var1 = (this.bitSize - 1) / 8;
      }

      return var1;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         ElGamalKeyParameters var4 = (ElGamalKeyParameters)var3.getParameters();
         this.key = var4;
         SecureRandom var5 = var3.getRandom();
         this.random = var5;
      } else {
         ElGamalKeyParameters var7 = (ElGamalKeyParameters)var2;
         this.key = var7;
         SecureRandom var8 = new SecureRandom();
         this.random = var8;
      }

      this.forEncryption = var1;
      int var6 = this.key.getParameters().getP().bitLength();
      this.bitSize = var6;
      if(var1) {
         if(!(this.key instanceof ElGamalPublicKeyParameters)) {
            throw new IllegalArgumentException("ElGamalPublicKeyParameters are required for encryption.");
         }
      } else if(!(this.key instanceof ElGamalPrivateKeyParameters)) {
         throw new IllegalArgumentException("ElGamalPrivateKeyParameters are required for decryption.");
      }
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) {
      if(this.key == null) {
         throw new IllegalStateException("ElGamal engine not initialised");
      } else {
         int var4;
         if(this.forEncryption) {
            var4 = (this.bitSize - 1 + 7) / 8;
         } else {
            var4 = this.getInputBlockSize();
         }

         if(var3 > var4) {
            throw new DataLengthException("input too large for ElGamal cipher.\n");
         } else {
            BigInteger var7 = this.key.getParameters().getP();
            byte[] var45;
            if(this.key instanceof ElGamalPrivateKeyParameters) {
               byte[] var8 = new byte[var3 / 2];
               byte[] var9 = new byte[var3 / 2];
               int var10 = var8.length;
               byte var14 = 0;
               System.arraycopy(var1, var2, var8, var14, var10);
               int var16 = var8.length + var2;
               int var17 = var9.length;
               byte var21 = 0;
               System.arraycopy(var1, var16, var9, var21, var17);
               BigInteger var23 = new BigInteger;
               byte var25 = 1;
               var23.<init>(var25, var8);
               BigInteger var27 = new BigInteger;
               byte var29 = 1;
               var27.<init>(var29, var9);
               ElGamalPrivateKeyParameters var31 = (ElGamalPrivateKeyParameters)this.key;
               BigInteger var32 = ONE;
               BigInteger var35 = var7.subtract(var32);
               BigInteger var36 = var31.getX();
               BigInteger var37 = var35.subtract(var36);
               BigInteger var41 = var23.modPow(var37, var7);
               BigInteger var43 = var41.multiply(var27);
               var45 = BigIntegers.asUnsignedByteArray(var43.mod(var7));
            } else {
               byte[] var49;
               label50: {
                  if(var2 == 0) {
                     int var46 = var1.length;
                     if(var3 == var46) {
                        var49 = var1;
                        break label50;
                     }
                  }

                  var49 = new byte[var3];
                  byte var53 = 0;
                  System.arraycopy(var1, var2, var49, var53, var3);
               }

               BigInteger var55 = new BigInteger;
               byte var57 = 1;
               var55.<init>(var57, var49);
               int var59 = var55.bitLength();
               int var60 = var7.bitLength();
               if(var59 >= var60) {
                  throw new DataLengthException("input too large for ElGamal cipher.\n");
               }

               ElGamalPublicKeyParameters var63 = (ElGamalPublicKeyParameters)this.key;
               int var64 = var7.bitLength();
               BigInteger var65 = new BigInteger;
               SecureRandom var66 = this.random;
               var65.<init>(var64, var66);

               while(true) {
                  BigInteger var70 = ZERO;
                  if(!var65.equals(var70)) {
                     BigInteger var73 = TWO;
                     BigInteger var76 = var7.subtract(var73);
                     if(var65.compareTo(var76) <= 0) {
                        BigInteger var83 = this.key.getParameters().getG();
                        BigInteger var86 = var83.modPow(var65, var7);
                        BigInteger var87 = var63.getY();
                        BigInteger var90 = var87.modPow(var65, var7);
                        BigInteger var93 = var55.multiply(var90);
                        BigInteger var95 = var93.mod(var7);
                        byte[] var96 = var86.toByteArray();
                        byte[] var97 = var95.toByteArray();
                        byte[] var98 = new byte[this.getOutputBlockSize()];
                        int var99 = var96.length;
                        int var100 = var98.length / 2;
                        if(var99 > var100) {
                           int var103 = var98.length / 2;
                           int var104 = var96.length - 1;
                           int var105 = var103 - var104;
                           int var106 = var96.length - 1;
                           byte var108 = 1;
                           System.arraycopy(var96, var108, var98, var105, var106);
                        } else {
                           int var125 = var98.length / 2;
                           int var126 = var96.length;
                           int var127 = var125 - var126;
                           int var128 = var96.length;
                           byte var130 = 0;
                           System.arraycopy(var96, var130, var98, var127, var128);
                        }

                        int var112 = var97.length;
                        int var113 = var98.length / 2;
                        if(var112 > var113) {
                           int var116 = var98.length;
                           int var117 = var97.length - 1;
                           int var118 = var116 - var117;
                           int var119 = var97.length - 1;
                           byte var121 = 1;
                           System.arraycopy(var97, var121, var98, var118, var119);
                        } else {
                           int var134 = var98.length;
                           int var135 = var97.length;
                           int var136 = var134 - var135;
                           int var137 = var97.length;
                           byte var139 = 0;
                           System.arraycopy(var97, var139, var98, var136, var137);
                        }

                        var45 = var98;
                        break;
                     }
                  }

                  var65 = new BigInteger;
                  SecureRandom var79 = this.random;
                  var65.<init>(var64, var79);
               }
            }

            return var45;
         }
      }
   }
}
