package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import myorg.bouncycastle.crypto.params.DESParameters;
import myorg.bouncycastle.jce.provider.JCEDHPublicKey;
import myorg.bouncycastle.util.Strings;

public class JCEDHKeyAgreement extends KeyAgreementSpi {

   private static final Hashtable algorithms = new Hashtable();
   private BigInteger g;
   private BigInteger p;
   private SecureRandom random;
   private BigInteger result;
   private BigInteger x;


   static {
      Integer var0 = new Integer(64);
      Integer var1 = new Integer(192);
      Integer var2 = new Integer(128);
      Object var3 = algorithms.put("DES", var0);
      Object var4 = algorithms.put("DESEDE", var1);
      Object var5 = algorithms.put("BLOWFISH", var2);
   }

   public JCEDHKeyAgreement() {}

   private byte[] bigIntToBytes(BigInteger var1) {
      byte[] var2 = var1.toByteArray();
      byte[] var5;
      if(var2[0] == 0) {
         byte[] var3 = new byte[var2.length - 1];
         int var4 = var3.length;
         System.arraycopy(var2, 1, var3, 0, var4);
         var5 = var3;
      } else {
         var5 = var2;
      }

      return var5;
   }

   protected Key engineDoPhase(Key var1, boolean var2) throws InvalidKeyException, IllegalStateException {
      if(this.x == null) {
         throw new IllegalStateException("Diffie-Hellman not initialised.");
      } else if(!(var1 instanceof DHPublicKey)) {
         throw new InvalidKeyException("DHKeyAgreement doPhase requires DHPublicKey");
      } else {
         DHPublicKey var3 = (DHPublicKey)var1;
         BigInteger var4 = var3.getParams().getG();
         BigInteger var5 = this.g;
         if(var4.equals(var5)) {
            BigInteger var6 = var3.getParams().getP();
            BigInteger var7 = this.p;
            if(var6.equals(var7)) {
               JCEDHPublicKey var12;
               if(var2) {
                  BigInteger var8 = ((DHPublicKey)var1).getY();
                  BigInteger var9 = this.x;
                  BigInteger var10 = this.p;
                  BigInteger var11 = var8.modPow(var9, var10);
                  this.result = var11;
                  var12 = null;
               } else {
                  BigInteger var13 = ((DHPublicKey)var1).getY();
                  BigInteger var14 = this.x;
                  BigInteger var15 = this.p;
                  BigInteger var16 = var13.modPow(var14, var15);
                  this.result = var16;
                  BigInteger var17 = this.result;
                  DHParameterSpec var18 = var3.getParams();
                  var12 = new JCEDHPublicKey(var17, var18);
               }

               return var12;
            }
         }

         throw new InvalidKeyException("DHPublicKey not for this KeyAgreement!");
      }
   }

   protected int engineGenerateSecret(byte[] var1, int var2) throws IllegalStateException, ShortBufferException {
      if(this.x == null) {
         throw new IllegalStateException("Diffie-Hellman not initialised.");
      } else {
         BigInteger var3 = this.result;
         byte[] var4 = this.bigIntToBytes(var3);
         int var5 = var1.length - var2;
         int var6 = var4.length;
         if(var5 < var6) {
            throw new ShortBufferException("DHKeyAgreement - buffer too short");
         } else {
            int var7 = var4.length;
            System.arraycopy(var4, 0, var1, var2, var7);
            return var4.length;
         }
      }
   }

   protected SecretKey engineGenerateSecret(String var1) {
      if(this.x == null) {
         throw new IllegalStateException("Diffie-Hellman not initialised.");
      } else {
         String var2 = Strings.toUpperCase(var1);
         BigInteger var3 = this.result;
         byte[] var4 = this.bigIntToBytes(var3);
         SecretKeySpec var7;
         if(algorithms.containsKey(var2)) {
            byte[] var5 = new byte[((Integer)algorithms.get(var2)).intValue() / 8];
            int var6 = var5.length;
            System.arraycopy(var4, 0, var5, 0, var6);
            if(var2.startsWith("DES")) {
               DESParameters.setOddParity(var5);
            }

            var7 = new SecretKeySpec(var5, var1);
         } else {
            var7 = new SecretKeySpec(var4, var1);
         }

         return var7;
      }
   }

   protected byte[] engineGenerateSecret() throws IllegalStateException {
      if(this.x == null) {
         throw new IllegalStateException("Diffie-Hellman not initialised.");
      } else {
         BigInteger var1 = this.result;
         return this.bigIntToBytes(var1);
      }
   }

   protected void engineInit(Key var1, SecureRandom var2) throws InvalidKeyException {
      if(!(var1 instanceof DHPrivateKey)) {
         throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey");
      } else {
         DHPrivateKey var3 = (DHPrivateKey)var1;
         this.random = var2;
         BigInteger var4 = var3.getParams().getP();
         this.p = var4;
         BigInteger var5 = var3.getParams().getG();
         this.g = var5;
         BigInteger var6 = var3.getX();
         this.result = var6;
         this.x = var6;
      }
   }

   protected void engineInit(Key var1, AlgorithmParameterSpec var2, SecureRandom var3) throws InvalidKeyException, InvalidAlgorithmParameterException {
      if(!(var1 instanceof DHPrivateKey)) {
         throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey for initialisation");
      } else {
         DHPrivateKey var4 = (DHPrivateKey)var1;
         this.random = var3;
         if(var2 != null) {
            if(!(var2 instanceof DHParameterSpec)) {
               throw new InvalidAlgorithmParameterException("DHKeyAgreement only accepts DHParameterSpec");
            }

            DHParameterSpec var5 = (DHParameterSpec)var2;
            BigInteger var6 = var5.getP();
            this.p = var6;
            BigInteger var7 = var5.getG();
            this.g = var7;
         } else {
            BigInteger var9 = var4.getParams().getP();
            this.p = var9;
            BigInteger var10 = var4.getParams().getG();
            this.g = var10;
         }

         BigInteger var8 = var4.getX();
         this.result = var8;
         this.x = var8;
      }
   }
}
