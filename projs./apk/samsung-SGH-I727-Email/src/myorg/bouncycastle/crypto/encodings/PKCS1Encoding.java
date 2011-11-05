package myorg.bouncycastle.crypto.encodings;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;

public class PKCS1Encoding implements AsymmetricBlockCipher {

   private static final int HEADER_LENGTH = 10;
   public static final String STRICT_LENGTH_ENABLED_PROPERTY = "myorg.bouncycastle.pkcs1.strict";
   private AsymmetricBlockCipher engine;
   private boolean forEncryption;
   private boolean forPrivateKey;
   private SecureRandom random;
   private boolean useStrictLength;


   public PKCS1Encoding(AsymmetricBlockCipher var1) {
      this.engine = var1;
      boolean var2 = this.useStrict();
      this.useStrictLength = var2;
   }

   private byte[] decodeBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      byte[] var4 = this.engine.processBlock(var1, var2, var3);
      int var5 = var4.length;
      int var6 = this.getOutputBlockSize();
      if(var5 < var6) {
         throw new InvalidCipherTextException("block truncated");
      } else {
         byte var7 = var4[0];
         if(var7 != 1 && var7 != 2) {
            throw new InvalidCipherTextException("unknown block type");
         } else {
            if(this.useStrictLength) {
               int var8 = var4.length;
               int var9 = this.engine.getOutputBlockSize();
               if(var8 != var9) {
                  throw new InvalidCipherTextException("block incorrect size");
               }
            }

            int var10 = 1;

            while(true) {
               int var11 = var4.length;
               if(var10 == var11) {
                  break;
               }

               byte var12 = var4[var10];
               if(var12 == 0) {
                  break;
               }

               if(var7 == 1 && var12 != -1) {
                  throw new InvalidCipherTextException("block padding incorrect");
               }

               ++var10;
            }

            int var13 = var10 + 1;
            int var14 = var4.length;
            if(var13 <= var14 && var13 >= 10) {
               byte[] var15 = new byte[var4.length - var13];
               int var16 = var15.length;
               System.arraycopy(var4, var13, var15, 0, var16);
               return var15;
            } else {
               throw new InvalidCipherTextException("no data in block");
            }
         }
      }
   }

   private byte[] encodeBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      int var4 = this.getInputBlockSize();
      if(var3 > var4) {
         throw new IllegalArgumentException("input data too large");
      } else {
         byte[] var5 = new byte[this.engine.getInputBlockSize()];
         int var6;
         if(this.forPrivateKey) {
            var5[0] = 1;
            var6 = 1;

            while(true) {
               int var7 = var5.length - var3 - 1;
               if(var6 == var7) {
                  break;
               }

               var5[var6] = -1;
               ++var6;
            }
         } else {
            this.random.nextBytes(var5);
            var5[0] = 2;
            var6 = 1;

            while(true) {
               int var8 = var5.length - var3 - 1;
               if(var6 == var8) {
                  break;
               }

               while(var5[var6] == 0) {
                  byte var9 = (byte)this.random.nextInt();
                  var5[var6] = var9;
               }

               ++var6;
            }
         }

         int var10 = var5.length - var3 - 1;
         var5[var10] = 0;
         int var11 = var5.length - var3;
         System.arraycopy(var1, var2, var5, var11, var3);
         AsymmetricBlockCipher var12 = this.engine;
         int var13 = var5.length;
         return var12.processBlock(var5, 0, var13);
      }
   }

   private boolean useStrict() {
      String var1 = (String)AccessController.doPrivileged(new PKCS1Encoding.1());
      boolean var2;
      if(var1 != null && !var1.equals("true")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public int getInputBlockSize() {
      int var1 = this.engine.getInputBlockSize();
      int var2;
      if(this.forEncryption) {
         var2 = var1 - 10;
      } else {
         var2 = var1;
      }

      return var2;
   }

   public int getOutputBlockSize() {
      int var1 = this.engine.getOutputBlockSize();
      int var2;
      if(this.forEncryption) {
         var2 = var1;
      } else {
         var2 = var1 - 10;
      }

      return var2;
   }

   public AsymmetricBlockCipher getUnderlyingCipher() {
      return this.engine;
   }

   public void init(boolean var1, CipherParameters var2) {
      AsymmetricKeyParameter var5;
      if(var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         SecureRandom var4 = var3.getRandom();
         this.random = var4;
         var5 = (AsymmetricKeyParameter)var3.getParameters();
      } else {
         SecureRandom var7 = new SecureRandom();
         this.random = var7;
         var5 = (AsymmetricKeyParameter)var2;
      }

      this.engine.init(var1, var2);
      boolean var6 = var5.isPrivate();
      this.forPrivateKey = var6;
      this.forEncryption = var1;
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      byte[] var4;
      if(this.forEncryption) {
         var4 = this.encodeBlock(var1, var2, var3);
      } else {
         var4 = this.decodeBlock(var1, var2, var3);
      }

      return var4;
   }

   class 1 implements PrivilegedAction {

      1() {}

      public Object run() {
         return System.getProperty("myorg.bouncycastle.pkcs1.strict");
      }
   }
}
