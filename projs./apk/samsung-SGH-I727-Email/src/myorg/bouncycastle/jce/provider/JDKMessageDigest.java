package myorg.bouncycastle.jce.provider;

import java.security.MessageDigest;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.digests.GOST3411Digest;
import myorg.bouncycastle.crypto.digests.MD2Digest;
import myorg.bouncycastle.crypto.digests.MD4Digest;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD128Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD160Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD256Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD320Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.digests.SHA224Digest;
import myorg.bouncycastle.crypto.digests.SHA256Digest;
import myorg.bouncycastle.crypto.digests.SHA384Digest;
import myorg.bouncycastle.crypto.digests.SHA512Digest;
import myorg.bouncycastle.crypto.digests.TigerDigest;
import myorg.bouncycastle.crypto.digests.WhirlpoolDigest;

public class JDKMessageDigest extends MessageDigest {

   Digest digest;


   protected JDKMessageDigest(Digest var1) {
      String var2 = var1.getAlgorithmName();
      super(var2);
      this.digest = var1;
   }

   public byte[] engineDigest() {
      byte[] var1 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var1, 0);
      return var1;
   }

   public void engineReset() {
      this.digest.reset();
   }

   public void engineUpdate(byte var1) {
      this.digest.update(var1);
   }

   public void engineUpdate(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }

   public static class RIPEMD256 extends JDKMessageDigest implements Cloneable {

      public RIPEMD256() {
         RIPEMD256Digest var1 = new RIPEMD256Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.RIPEMD256 var1 = (JDKMessageDigest.RIPEMD256)super.clone();
         RIPEMD256Digest var2 = (RIPEMD256Digest)this.digest;
         RIPEMD256Digest var3 = new RIPEMD256Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class GOST3411 extends JDKMessageDigest implements Cloneable {

      public GOST3411() {
         GOST3411Digest var1 = new GOST3411Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.GOST3411 var1 = (JDKMessageDigest.GOST3411)super.clone();
         GOST3411Digest var2 = (GOST3411Digest)this.digest;
         GOST3411Digest var3 = new GOST3411Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class RIPEMD160 extends JDKMessageDigest implements Cloneable {

      public RIPEMD160() {
         RIPEMD160Digest var1 = new RIPEMD160Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.RIPEMD160 var1 = (JDKMessageDigest.RIPEMD160)super.clone();
         RIPEMD160Digest var2 = (RIPEMD160Digest)this.digest;
         RIPEMD160Digest var3 = new RIPEMD160Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class MD5 extends JDKMessageDigest implements Cloneable {

      public MD5() {
         MD5Digest var1 = new MD5Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.MD5 var1 = (JDKMessageDigest.MD5)super.clone();
         MD5Digest var2 = (MD5Digest)this.digest;
         MD5Digest var3 = new MD5Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class MD4 extends JDKMessageDigest implements Cloneable {

      public MD4() {
         MD4Digest var1 = new MD4Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.MD4 var1 = (JDKMessageDigest.MD4)super.clone();
         MD4Digest var2 = (MD4Digest)this.digest;
         MD4Digest var3 = new MD4Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class Whirlpool extends JDKMessageDigest implements Cloneable {

      public Whirlpool() {
         WhirlpoolDigest var1 = new WhirlpoolDigest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.Whirlpool var1 = (JDKMessageDigest.Whirlpool)super.clone();
         WhirlpoolDigest var2 = (WhirlpoolDigest)this.digest;
         WhirlpoolDigest var3 = new WhirlpoolDigest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class MD2 extends JDKMessageDigest implements Cloneable {

      public MD2() {
         MD2Digest var1 = new MD2Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.MD2 var1 = (JDKMessageDigest.MD2)super.clone();
         MD2Digest var2 = (MD2Digest)this.digest;
         MD2Digest var3 = new MD2Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class SHA256 extends JDKMessageDigest implements Cloneable {

      public SHA256() {
         SHA256Digest var1 = new SHA256Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.SHA256 var1 = (JDKMessageDigest.SHA256)super.clone();
         SHA256Digest var2 = (SHA256Digest)this.digest;
         SHA256Digest var3 = new SHA256Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class SHA512 extends JDKMessageDigest implements Cloneable {

      public SHA512() {
         SHA512Digest var1 = new SHA512Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.SHA512 var1 = (JDKMessageDigest.SHA512)super.clone();
         SHA512Digest var2 = (SHA512Digest)this.digest;
         SHA512Digest var3 = new SHA512Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class SHA1 extends JDKMessageDigest implements Cloneable {

      public SHA1() {
         SHA1Digest var1 = new SHA1Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.SHA1 var1 = (JDKMessageDigest.SHA1)super.clone();
         SHA1Digest var2 = (SHA1Digest)this.digest;
         SHA1Digest var3 = new SHA1Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class Tiger extends JDKMessageDigest implements Cloneable {

      public Tiger() {
         TigerDigest var1 = new TigerDigest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.Tiger var1 = (JDKMessageDigest.Tiger)super.clone();
         TigerDigest var2 = (TigerDigest)this.digest;
         TigerDigest var3 = new TigerDigest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class SHA384 extends JDKMessageDigest implements Cloneable {

      public SHA384() {
         SHA384Digest var1 = new SHA384Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.SHA384 var1 = (JDKMessageDigest.SHA384)super.clone();
         SHA384Digest var2 = (SHA384Digest)this.digest;
         SHA384Digest var3 = new SHA384Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class RIPEMD128 extends JDKMessageDigest implements Cloneable {

      public RIPEMD128() {
         RIPEMD128Digest var1 = new RIPEMD128Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.RIPEMD128 var1 = (JDKMessageDigest.RIPEMD128)super.clone();
         RIPEMD128Digest var2 = (RIPEMD128Digest)this.digest;
         RIPEMD128Digest var3 = new RIPEMD128Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class RIPEMD320 extends JDKMessageDigest implements Cloneable {

      public RIPEMD320() {
         RIPEMD320Digest var1 = new RIPEMD320Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.RIPEMD320 var1 = (JDKMessageDigest.RIPEMD320)super.clone();
         RIPEMD320Digest var2 = (RIPEMD320Digest)this.digest;
         RIPEMD320Digest var3 = new RIPEMD320Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }

   public static class SHA224 extends JDKMessageDigest implements Cloneable {

      public SHA224() {
         SHA224Digest var1 = new SHA224Digest();
         super(var1);
      }

      public Object clone() throws CloneNotSupportedException {
         JDKMessageDigest.SHA224 var1 = (JDKMessageDigest.SHA224)super.clone();
         SHA224Digest var2 = (SHA224Digest)this.digest;
         SHA224Digest var3 = new SHA224Digest(var2);
         var1.digest = var3;
         return var1;
      }
   }
}
